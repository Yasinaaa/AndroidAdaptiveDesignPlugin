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
    private String viewHolderClassName;

    @Override
    public String getType() {
        return TYPE;
    }

    public RecyclerViewAdapterPattern(AndroidManifest androidManifest, String viewHolderClassName){
        super(androidManifest);
        this.viewHolderClassName = viewHolderClassName;
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
        PsiMethod constructor = factory.createConstructor();

        /*if (constructor.getBody() == null) {
            throw new RuntimeException("Failed to create ViewHolder constructor");
        }

        constructor.getBody().add(factory.createStatementFromText(
                "mValues = items;", constructor.getContext()));
        psiClass.add(constructor);*/
    }

    @Override
    public void afterSaveClass(AndroidView androidView, PsiClass psiClass) {
        addExtendsToClass(psiClass);
    }

    private void addExtendsToClass(PsiClass psiClass){
        VirtualFile virtualFile = psiClass.getContainingFile().getVirtualFile();
        String path = virtualFile.getPath();
        File file = new File(path);
        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get(virtualFile.getPath()));
            String packageName = list.get(0);
            for (int i=0; i<list.size(); i++){
                if(list.contains("public class " + psiClass.getName())){
                    list.set(i, list.get(i).replace("{", "extends RecyclerView.Adapter<" + viewHolderClassName + ">{"));
                    break;
                }
            }
            Files.write(Paths.get(path), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
