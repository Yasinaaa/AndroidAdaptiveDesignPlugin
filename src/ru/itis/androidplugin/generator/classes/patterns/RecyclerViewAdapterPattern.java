package ru.itis.androidplugin.generator.classes.patterns;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import ru.itis.androidplugin.android.AndroidManifest;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.generator.classes.helper.ButterKnife;
import ru.itis.androidplugin.generator.classes.helper.ClassHelper;
import ru.itis.androidplugin.generator.classes.helper.FieldGenerator;
import ru.itis.androidplugin.interfaces.ToolbarInterface;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yasina on 15.06.17.
 */
public class RecyclerViewAdapterPattern extends ClassPattern {

    private final String TYPE = "Adapter";
    private String viewHolderClassName, layoutName, className;

    @Override
    public String getType() {
        return TYPE;
    }

    public RecyclerViewAdapterPattern(AndroidManifest androidManifest, String viewHolderClassName, String layoutName){
        super(androidManifest);
        this.viewHolderClassName = viewHolderClassName;
        this.layoutName = layoutName.substring(0, layoutName.indexOf("."));
    }

    @Override
    public PsiClass createOrUpdateClass(AndroidView androidView, PsiClass psiClass) {
        getClassType(psiClass);
       ButterKnife butterKnife = ButterKnife.find(PluginProject.mProject);
       /* if (butterKnife != null) {
            addImport(psiClass, butterKnife.getInjectViewClass());
            addImport(psiClass, butterKnife.getInjectorPsiClass());
        }
        PsiClass rvClass = ClassHelper.findClass(PluginProject.mProject,
                ANDROID_RECYCLER_VIEW_CLASS);
        addImport(psiClass, rvClass);*/
        PsiClass rvClass = ClassHelper.findClass(PluginProject.mProject,
                ANDROID_RECYCLER_VIEW_CLASS);
        addImport(psiClass, rvClass);

        //todo: change
        createClassItems(androidView, butterKnife, psiClass);
        addRClassImport(psiClass, androidManifest);

        //psiClass.add();

        return psiClass;
    }

    @Override
    public void createClassItems(AndroidView androidView, ButterKnife butterKnife,
                                 PsiClass psiClass) {
        FieldGenerator fieldGenerator = new FieldGenerator();
        Map<AndroidView, PsiField> fieldMappings = fieldGenerator.generateFields(
                androidView, PluginProject.mProject, butterKnife, new FieldGenerator.AddToPsiClassCallback(psiClass));

        PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiClass.getProject());
       // PsiMethod constructor = factory.createConstructor();

       /* PsiField psiField = factory.createField("private final List<Object> mValues;", null);
        PsiMethod psiMethod = factory.createMethodFromText("    @Override\n" +
                "    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {return new MyRecyclerViewHolder(itemView);\n" +
                "    }", psiClass);*/



        /*if (constructor.getBody() == null) {
            throw new RuntimeException("Failed to create ViewHolder constructor");
        }

        constructor.getBody().add(factory.createStatementFromText(
                "mValues = items;", constructor.getContext()));
        psiClass.add(constructor);*/
    }

    @Override
    public void afterSaveClass(String path) {
        addExtendsToClass(path);
    }

    private void addExtendsToClass(String path){
        File file = new File(path);
        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get(path));
            for (int i=0; i<list.size(); i++){
                className = path.substring(path.lastIndexOf("/") + 1, path.indexOf("."));
                if(list.get(i).contains("public class " + className)){
                    list.set(i, list.get(i).replace("{", "extends RecyclerView.Adapter<" + viewHolderClassName + ">{"));
                    break;
                }
            }
            addMethods(list);
            Files.write(Paths.get(path), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String imports =
            "\nimport android.content.Context;\n" +
                    "import android.support.v7.widget.RecyclerView;\n" +
                    "import android.view.LayoutInflater;\n" +
                    "import android.view.View;\n" +
                    "import android.view.ViewGroup;\n" +
                    "import java.util.List;";
    String fields = "    private final List<Object> mValues;\n    private Context context;\n";
    String constructor =
            "\n" +"    public %s(List<Object> items) {\n" +
            "        mValues = items;\n" +
            "    }";
    String onCreateViewHolderMethod =
            "\n" + "    @Override\n" +
            "    public %s onCreateViewHolder(ViewGroup parent, int viewType) {\n" +
            "        context = parent.getContext();\n" +
            "        View itemView = LayoutInflater.from(context)\n" +
            "                .inflate(R.layout.%s, parent, false);\n" +
            "\n" +
            "        return new %s(itemView);\n" +
            "    }\n\n" +
            "    @Override\n" + "    public int getItemCount() {\n" + "        return mValues.size();\n" + "    }";
    String onBindViewHolder =
            "\n" +"    @Override\n" +
                    "    public void onBindViewHolder(final %s holder, final int position) {\n" +
                    "       /* holder.getCardView().setOnLongClickListener(new View.OnLongClickListener() {\n" +
                    "            @Override\n" +
                    "            public boolean onLongClick(View v) {\n" +
                    "                changeBackground(holder);\n" +
                    "            }\n" +
                    "        });*/\n" +
                    "    }";
    private void addMethods(List<String> list){
        list.add(2, imports);
        list.add(list.size() - 1, fields);
        list.add(list.size() - 1, String.format(constructor, className));
        list.add(list.size() - 1, String.format(onCreateViewHolderMethod, new String[]
                {className, layoutName, viewHolderClassName}));
        list.add(list.size() - 1, String.format(onBindViewHolder, viewHolderClassName));
    }



}
