package ru.itis.androidplugin.generator.classes;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import ru.itis.androidplugin.android.AndroidManifest;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.generator.helper.ButterKnife;
import ru.itis.androidplugin.generator.helper.ClassHelper;
import ru.itis.androidplugin.generator.helper.FieldGenerator;
import ru.itis.androidplugin.settings.PluginProject;
import java.util.Map;

/**
 * Created by yasina on 02.04.17.
 */
public class ActivityPattern extends ClassPattern {

    public ActivityPattern(AndroidManifest androidManifest) {
        super(androidManifest);
    }

    @Override
    public PsiClass createOrUpdateClass(AndroidView androidView, PsiClass psiClass) {
        getClassType(psiClass);
        if (!androidView.getChildNodes().isEmpty()) {

            Project project = PluginProject.mProject;
            PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
            ButterKnife butterKnife = ButterKnife.find(project);
            if (butterKnife != null) {
                addImport(psiClass, butterKnife.getInjectViewClass());
                addImport(psiClass, butterKnife.getInjectorPsiClass());
            }
            if (recyclerViewSupport) {
                PsiClass rvClass = ClassHelper.findClass(project, ANDROID_RECYCLER_VIEW_CLASS);
                PsiClass rvHolderClass = ClassHelper.findClass(project, ANDROID_RECYCLER_VIEW_VIEWHOLDER_CLASS);

                addImport(psiClass, rvClass);
                psiClass.getExtendsList().add(factory.createClassReferenceElement(rvHolderClass));

            }
            createClassItems(androidView, butterKnife, psiClass);
            addRClassImport(psiClass, androidManifest);

            return psiClass;
        }else {
            return null;
        }
    }

    @Override
    protected void createClassItems(AndroidView androidView, ButterKnife butterKnife,
                                    PsiClass psiClass) {
        FieldGenerator fieldGenerator = new FieldGenerator();
        Map<AndroidView, PsiField> fieldMappings = fieldGenerator.generateFields(
                androidView, PluginProject.mProject, butterKnife,
                new FieldGenerator.AddToPsiClassCallback(psiClass));

        generateGetters(psiClass, fieldMappings.values());
        PsiMethod initMethod;
        try {
            initMethod = psiClass.findMethodsByName("init", true)[0];
            if (initMethod == null) {
                System.out.println("hah");
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            initMethod = factory.createMethodFromText("public void init() {}", psiClass);
            addFindViewStatements(factory, initMethod, androidView, fieldMappings);
            psiClass.add(initMethod);
            try {
                callInitMethodOnCreateView(psiClass, initMethod);
            } catch (java.lang.ArrayIndexOutOfBoundsException e2) {

            }

        }
    }

    private void callInitMethodOnCreateView(PsiClass psiClass, PsiMethod psiMethod){
        PsiMethod onCreateView = psiClass.findMethodsByName("onCreate", true)[0];

        //setContentView(R.layout.activity_main);

        if(!onCreateView.getBody().getText().contains("init();")) {
            PsiStatement assignmentStatement =
                    factory.createStatementFromText("init();", psiClass);
            onCreateView.getBody().add(assignmentStatement);
        }
    }

}
