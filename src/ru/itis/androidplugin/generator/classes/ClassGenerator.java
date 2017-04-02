package ru.itis.androidplugin.generator.classes;

import com.*;
import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang.StringUtils;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.generator.Generator;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.File;

import static ru.itis.androidplugin.settings.PluginProject.mProject;

/**
 * Created by yasina on 27.03.17.
 */
public class ClassGenerator extends Generator {


    public ClassGenerator(){

    }

    public void insertNewClass(ClassPattern classPattern, String layoutPath){
        extendedInit();

        PackageChooserDialog packageChooserDialog = new PackageChooserDialog("Destination Package", module);

        PsiPackage selectedPackage;
        if (packageChooserDialog.showAndGet()) {
            selectedPackage = packageChooserDialog.getSelectedPackage();
            PsiDirectory resultDirectory = getPsiDirectoryFromPackage(selectedPackage);
            String fileName = setJavaClassName(layoutPath);
            throwIfFileAlreadyExists(resultDirectory, fileName);
            String className = FileUtil.removeExtension(fileName);
            //todo: show error dialog
            //throwIfFileAlreadyExists(resultDirectory, className);
            //todo change


            new WriteCommandAction.Simple(PluginProject.mProject) {
                @Override
                protected void run() throws Throwable {
                    PsiClass inputClass = classPattern.generatePsiClass(selectedPackage.getQualifiedName()
                            + "." + className);
                    PsiClass resultClass =
                            classPattern.createOrUpdateClass(androidViews, inputClass);
                    saveClass(resultDirectory, resultClass);
                }
            }.execute();
        }
    }

    private void saveClass(final PsiDirectory resultDirectory, final PsiClass resultClass) {
        if (resultDirectory != null) {
            if (resultClass != null) {
                PsiClass added = (PsiClass) resultDirectory.add(resultClass);
                PsiFile psiFile = added.getNavigationElement().getContainingFile();
                JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(added.getProject());
                styleManager.optimizeImports(psiFile);
                CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(added.getProject());
                PsiClass formatted = (PsiClass) codeStyleManager.reformat(added);
                formatted.navigate(true);
            }
        }
    }

    private AndroidView getAndroidViews(VirtualFile layoutFile, Project project) {
        AndroidLayoutParser parser = new AndroidLayoutParser(project);
        return parser.parse(layoutFile);
    }

    public void addInitToClass(ClassPattern classPattern, String parentPath, String layoutPath){
        PsiClass psiClass = JavaPsiFacade.getInstance(PluginProject.mProject).findClass(parentPath,
                GlobalSearchScope.allScope(PluginProject.mProject));

        File file = new File(layoutPath);
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        AndroidView androidView = getAndroidViews(virtualFile, PluginProject.mProject);


        new WriteCommandAction.Simple(mProject) {
            @Override
            protected void run() throws Throwable {
                classPattern.createOrUpdateClass(androidView, psiClass);
            }
        }.execute();

    }

    private PsiDirectory getPsiDirectoryFromPackage(PsiPackage selectedPackage) {
        PsiDirectory[] allDirectories = PackageUtil.getDirectories(selectedPackage, null, false);

        PsiDirectory p = null;
        if (allDirectories.length > 1) {
            String[] dirs = new String[allDirectories.length];
            for (int i = 0; i < allDirectories.length; i++) {
                dirs[i] = allDirectories[i].getVirtualFile().getPath();
            }
            ChooseDialog dialog = new ChooseDialog(selectedPackage.getProject(), "Directory Selection",
                    "Package referenced to several folders. Select result destination",
                    dirs,
                    0);
            int index = 0;

            if (dialog.showAndGet()) {
                index = dialog.getSelectedIndex();
                /*if (index >= 0) {
                    p = directories.get(index);
                }*/
            }
            p = allDirectories[index];
        } else if (allDirectories.length == 1) {
            p = allDirectories[0];
        }
        return p;
        //throw new GenerateViewPresenterAction.CancellationException();
    }

    private void throwIfFileAlreadyExists(PsiDirectory resultDirectory, String fileName) {
        for (PsiFile file : resultDirectory.getFiles()) {
            String name = file.getName();
            if (name != null && name.equalsIgnoreCase(fileName)) {
                throw new GenerateViewPresenterAction.CancellationException("File \"" + fileName + "\" already exists");
            }
        }
    }

    private String setJavaClassName(String layoutPath) {
        String className = StringUtils.capitalize(layoutPath) + "_" + "Holder";
        String fileName = Messages.showInputDialog(PluginProject.mProject, "Input class name", "Creating File",
                Messages.getQuestionIcon(), className, null);
        if (fileName == null || fileName.length() == 0) {
            throw new GenerateViewPresenterAction.CancellationException("Incorrect file name");
        }
        if (!fileName.contains(".java")) {
            fileName += ".java";
        }
        return fileName;
    }
/*
    public static final String ANDROID_VIEW_CLASS = "android.view.View";
    public static final String ANDROID_VIEW_GROUP_CLASS = "android.view.ViewGroup";
    public static final String ANDROID_LAYOUT_INFLATER_CLASS = "android.view.LayoutInflater";
    public static final String ANDROID_CONTEXT_CLASS = "android.content.Context";
    public static final String ANDROID_RECYCLER_VIEW_CLASS = "android.support.v7.widget.RecyclerView";
    public static final String ANDROID_RECYCLER_VIEW_VIEWHOLDER_CLASS = "android.support.v7.widget.RecyclerView.ViewHolder";
    public static final String ANDROID_RECYCLER_VIEW_VIEWHOLDER_VIEW_FIELD_NAME = "itemView";

    private boolean recyclerViewSupport;
    private String type;

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
            type="ViewHolder";
        }

        String[] types = new String[]{
          "Activity", "Fragment", "ViewHolder"
        };


    }

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
        createClassItems(androidView, butterKnife, psiClass, true);

            //callInitMethodOnCreateView(psiClass);
        addRClassImport(psiClass, androidManifest);

        return psiClass;
        }else {
            return null;
        }
    }

    protected PsiClass createOrUpdateClass(AndroidView androidView, String className) {

        PsiElementFactory factory = JavaPsiFacade.getElementFactory(PluginProject.mProject);
        PsiClass psiClass = factory.createClass(ClassHelper.getClassNameFromFullQualified(className));
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
        createClassItems(androidView, butterKnife, psiClass, false);
        addRClassImport(psiClass, androidManifest);

        return psiClass;
    }

    private void addRClassImport(PsiClass psiClass, AndroidManifest androidManifest) {
        PsiClass rClass = ClassHelper.findClass(psiClass.getProject(), androidManifest.getPackageName() + ".R");
        addImport(psiClass, rClass);
    }

    protected void addImport(PsiClass psiClass, PsiClass importClass) {
        PsiFile containingFile = psiClass.getNavigationElement().getContainingFile();
        JavaCodeStyleManager manager = JavaCodeStyleManager.getInstance(psiClass.getProject());
        manager.addImport((PsiJavaFile) containingFile, importClass);
    }

    protected void createClassItems(AndroidView androidView, ButterKnife butterKnife,
                                    final PsiClass psiClass, boolean addInitMethod) {

        FieldGenerator fieldGenerator = new FieldGenerator();
        Map<AndroidView, PsiField> fieldMappings = fieldGenerator.generateFields(
                androidView, PluginProject.mProject, butterKnife, new FieldGenerator.AddToPsiClassCallback(psiClass));
        if(addInitMethod){
            generateInitMethod(androidView, butterKnife, fieldMappings, psiClass);
        }else {
            generateConstructor(androidView, butterKnife, fieldMappings, psiClass);
        }
        generateGetters(psiClass, fieldMappings.values());
    }
    protected String generateGetterName(String field) {
        StringBuilder buffer = new StringBuilder(field);
        buffer.replace(0, 1, String.valueOf(buffer.charAt(0)).toUpperCase());
        buffer.insert(0, "get");
        return buffer.toString();
    }

    private void generateGetters(PsiClass psiClass, Collection<PsiField> psiFields) {
        for (PsiField psiField : psiFields) {
            PsiMethod method = PropertyUtil.generateGetterPrototype(psiField);
            PsiMethod existsMethod = psiClass.findMethodBySignature(method, true);
            if (existsMethod == null && method != null){
                psiClass.add(method);
            }
        }
    }
    private void generateConstructor(AndroidView androidView, ButterKnife butterKnife, Map<AndroidView, PsiField> fieldMappings, PsiClass psiClass) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        PsiMethod constructor = factory.createConstructor();
        PsiClass viewClass = ClassHelper.findClass(psiClass.getProject(), ANDROID_VIEW_CLASS);
        PsiParameter viewParam = factory.createParameter("view", factory.createType(viewClass));
        constructor.getParameterList().add(viewParam);

        if (constructor.getBody() == null) {
            throw new GenerateViewPresenterAction.CancellationException("Failed to create ViewHolder constructor");
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

    }

    private void generateInitMethod(AndroidView androidView, ButterKnife butterKnife, Map<AndroidView, PsiField> fieldMappings, PsiClass psiClass) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiClass.getProject());


        PsiMethod initMethod;
        try {
            initMethod = psiClass.findMethodsByName("init", true)[0];
            if (initMethod == null){
                System.out.println("hah");
            }
        }catch (java.lang.ArrayIndexOutOfBoundsException e){
            initMethod = factory.createMethodFromText("public void init() {}", psiClass);
            addFindViewStatements(factory, initMethod, androidView, fieldMappings);
            psiClass.add(initMethod);
            try {
                callInitMethodOnCreateView(psiClass, initMethod);
            }catch (java.lang.ArrayIndexOutOfBoundsException e2){

            }

        }

    }

    private void callInitMethodOnCreateView(PsiClass psiClass, PsiMethod psiMethod){
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiClass.getProject());

        PsiMethod onCreateView = psiClass.findMethodsByName("onCreate", true)[0];

        //setContentView(R.layout.activity_main);

        if(!onCreateView.getBody().getText().contains("init();")) {
            PsiStatement assignmentStatement =
                    factory.createStatementFromText("init();", psiClass);
            onCreateView.getBody().add(assignmentStatement);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void addFindViewStatements(final PsiElementFactory factory, final PsiMethod constructor,
                                       final AndroidView view, final Map<AndroidView, PsiField> fieldMappings) {
        FindViewByIdStatementGenerator findViewByIdStatementGenerator = new FindViewByIdStatementGenerator();
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
            throw new GenerateViewPresenterAction.CancellationException("Failed to create recyclerView compat constructor");
        }

        PsiStatement callPrimaryConstructorStatement =
                factory.createStatementFromText("this(" + inflaterParam.getName() + ".inflate(R.layout."
                        + FileUtil.removeExtension(layoutFileName)
                        + ", " + viewParentParam.getName()
                        + ", false));", constructor.getContext());
        constructor.getBody().add(callPrimaryConstructorStatement);
        psiClass.add(constructor);
    }*/
}
