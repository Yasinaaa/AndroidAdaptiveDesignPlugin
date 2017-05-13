package ru.itis.androidplugin.generator.classes.patterns;

import ru.itis.androidplugin.generator.classes.helper.*;
import com.intellij.psi.*;
import ru.itis.androidplugin.android.AndroidManifest;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.settings.PluginProject;

import java.util.Map;

/**
 * Created by yasina on 02.04.17.
 */
public class RecyclerViewHolderPattern extends ClassPattern {

    public RecyclerViewHolderPattern(AndroidManifest androidManifest){
        super(androidManifest);
    }

    @Override
    public PsiClass createOrUpdateClass(AndroidView androidView, PsiClass psiClass) {
        getClassType(psiClass);
        ButterKnife butterKnife = ButterKnife.find(PluginProject.mProject);
        if (butterKnife != null) {
            addImport(psiClass, butterKnife.getInjectViewClass());
            addImport(psiClass, butterKnife.getInjectorPsiClass());
        }
        if (recyclerViewSupport) {
            PsiClass rvClass = ClassHelper.findClass(PluginProject.mProject,
                    ANDROID_RECYCLER_VIEW_CLASS);
            PsiClass rvHolderClass = ClassHelper.findClass(PluginProject.mProject,
                    ANDROID_RECYCLER_VIEW_VIEWHOLDER_CLASS);

            addImport(psiClass, rvClass);
            psiClass.getExtendsList().add(factory.createClassReferenceElement(rvHolderClass));
            generateRecyclerViewCompatConstructor(psiClass.getName(), psiClass);
        }
        //todo: change
        createClassItems(androidView, butterKnife, psiClass);
        addRClassImport(psiClass, androidManifest);

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
        PsiClass viewClass = ClassHelper.findClass(psiClass.getProject(), ANDROID_VIEW_CLASS);
        PsiParameter viewParam = factory.createParameter("view", factory.createType(viewClass));
        constructor.getParameterList().add(viewParam);

        if (constructor.getBody() == null) {
            throw new RuntimeException("Failed to create ViewHolder constructor");
        }

        if (hasRecyclerViewSupport()) {
            constructor.getBody().add(factory.createStatementFromText(
                    "super(" + viewParam.getName() + ");", constructor.getContext()));
        }

        androidView.setTagName(ANDROID_VIEW_CLASS);
        androidView.setIdValue(viewParam.getName());
        if (butterKnife != null) {
            String injectorClassName = butterKnife.getInjectorPsiClass().getName();
            PsiStatement injectStatement =
                    factory.createStatementFromText(injectorClassName
                            + "." + butterKnife.getMethodName()
                            + "(this, " + viewParam.getName() + ");", constructor.getContext());
            constructor.getBody().add(injectStatement);
        } else {
            addFindViewStatements(factory, constructor, androidView, fieldMappings);
        }

        psiClass.add(constructor);
        generateGetters(psiClass, fieldMappings.values());
    }

    protected void generateRecyclerViewCompatConstructor(String layoutFileName, PsiClass psiClass) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        PsiMethod constructor = factory.createConstructor();
        PsiClass layoutInflaterClass = ClassHelper.findClass(psiClass.getProject(), ANDROID_LAYOUT_INFLATER_CLASS);
        PsiParameter inflaterParam = factory.createParameter("inflater", factory.createType(layoutInflaterClass));
        PsiClass viewGroupClass = ClassHelper.findClass(psiClass.getProject(), ANDROID_VIEW_GROUP_CLASS);
        PsiParameter viewParentParam = factory.createParameter("parent", factory.createType(viewGroupClass));
        constructor.getParameterList().add(inflaterParam);
        constructor.getParameterList().add(viewParentParam);

        if (constructor.getBody() == null) {
            throw new RuntimeException("Failed to create recyclerView compat constructor");
        }

        PsiStatement callPrimaryConstructorStatement =
                factory.createStatementFromText("this(" + inflaterParam.getName() + ".inflate(R.layout."
                        + removeExtension(layoutFileName)
                        + ", " + viewParentParam.getName()
                        + ", false));", constructor.getContext());
        constructor.getBody().add(callPrimaryConstructorStatement);
        psiClass.add(constructor);
    }
}
