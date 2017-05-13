package ru.itis.androidplugin.generator.classes.patterns;

import ru.itis.androidplugin.generator.helper.*;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PropertyUtil;
import ru.itis.androidplugin.android.AndroidManifest;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.settings.PluginProject;

import java.util.Collection;
import java.util.Map;

/**
 * Created by yasina on 02.04.17.
 */
public abstract class ClassPattern {
    public static final String ANDROID_VIEW_CLASS = "android.view.View";
    public static final String ANDROID_VIEW_GROUP_CLASS = "android.view.ViewGroup";
    public static final String ANDROID_LAYOUT_INFLATER_CLASS = "android.view.LayoutInflater";
    public static final String ANDROID_CONTEXT_CLASS = "android.content.Context";
    public static final String ANDROID_RECYCLER_VIEW_CLASS = "android.support.v7.widget.RecyclerView";
    public static final String ANDROID_RECYCLER_VIEW_VIEWHOLDER_CLASS = "android.support.v7.widget.RecyclerView.ViewHolder";
    public static final String ANDROID_RECYCLER_VIEW_VIEWHOLDER_VIEW_FIELD_NAME = "itemView";

    protected boolean recyclerViewSupport;
    protected String type;
    protected PsiElementFactory factory;
    protected AndroidManifest androidManifest;

    public ClassPattern(AndroidManifest androidManifest){
        this.androidManifest = androidManifest;
        factory = JavaPsiFacade.getElementFactory(PluginProject.mProject);
    }

    public void setRecyclerViewSupport(boolean support) {
        recyclerViewSupport = support;
    }

    public final boolean hasRecyclerViewSupport() {
        return recyclerViewSupport;
    }


    public void getClassType(PsiClass psiClass){

        PsiClassType[] psiClassTypes = psiClass.getExtendsListTypes();
        try {
            String currentClassExtends = psiClassTypes[0].getClassName();
            type = "Activity";
        }catch (java.lang.ArrayIndexOutOfBoundsException e){
            type = "ViewHolder";
        }

        String[] types = new String[]{
                "Activity", "Fragment", "ViewHolder"
        };
    }

    public abstract PsiClass createOrUpdateClass(AndroidView androidView, PsiClass psiClass);
    protected abstract void createClassItems(AndroidView androidView, ButterKnife butterKnife,
                                             PsiClass psiClass);

    protected PsiClass generatePsiClass(String className){

        PsiClass psiClass = factory.createClass(ClassHelper.
                getClassNameFromFullQualified(className));
        return psiClass;
    }

    protected void addRClassImport(PsiClass psiClass, AndroidManifest androidManifest) {
        PsiClass rClass = ClassHelper.findClass(psiClass.getProject(), androidManifest.getPackageName() + ".R");
        addImport(psiClass, rClass);
    }

    protected void addImport(PsiClass psiClass, PsiClass importClass) {
        PsiFile containingFile = psiClass.getNavigationElement().getContainingFile();
        JavaCodeStyleManager manager = JavaCodeStyleManager.getInstance(psiClass.getProject());
        manager.addImport((PsiJavaFile) containingFile, importClass);
    }

    protected String generateGetterName(String field) {
        StringBuilder buffer = new StringBuilder(field);
        buffer.replace(0, 1, String.valueOf(buffer.charAt(0)).toUpperCase());
        buffer.insert(0, "get");
        return buffer.toString();
    }

    protected void generateGetters(PsiClass psiClass, Collection<PsiField> psiFields) {
        for (PsiField psiField : psiFields) {
            PsiMethod method = PropertyUtil.generateGetterPrototype(psiField);
            PsiMethod existsMethod = psiClass.findMethodBySignature(method, true);
            if (existsMethod == null && method != null){
                psiClass.add(method);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected void addFindViewStatements(final PsiElementFactory factory, final PsiMethod constructor,
                                       final AndroidView view, final Map<AndroidView, PsiField> fieldMappings) {
        FindViewByIdStatementGenerator findViewByIdStatementGenerator = new
                FindViewByIdStatementGenerator();
        FindViewByIdStatementGenerator.ClassFieldAssigner fieldAssigner =
                new FindViewByIdStatementGenerator.ClassFieldAssigner(fieldMappings, view.getIdValue()) {
                    @Override
                    protected void onStatementCreated(String statement, PsiField field, AndroidView view) {

                        if(!constructor.getBody().getText().contains(statement)){
                            PsiStatement assignmentStatement =
                                    factory.createStatementFromText(statement, constructor.getContext());
                            constructor.getBody().add(assignmentStatement);
                        }

                    }

                    @Override
                    public boolean shouldProcessView(AndroidView view) {
                        return view.getParent() != null;
                    }
                };
        findViewByIdStatementGenerator.createFindViewStatements(type, view, fieldAssigner);
    }

    public static String removeExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index > 0) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }
}
