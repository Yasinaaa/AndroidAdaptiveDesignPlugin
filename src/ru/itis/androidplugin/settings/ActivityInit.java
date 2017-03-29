package ru.itis.androidplugin.settings;

import com.*;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PropertyUtil;
import ru.itis.androidplugin.settings.interfaces.InsertMetodToClass;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import static ru.itis.androidplugin.settings.PluginProject.mProject;

/**
 * Created by yasina on 27.03.17.
 */
public class ActivityInit {

    public ActivityInit(){

    }

    private AndroidView getAndroidViews(VirtualFile layoutFile, Project project) {
        AndroidLayoutParser parser = new AndroidLayoutParser(project);
        return parser.parse(layoutFile);
    }

    public  void addInitToClass(String parentPath, String layoutPath){
        PsiClass psiClass = JavaPsiFacade.getInstance(PluginProject.mProject).findClass(parentPath,
                GlobalSearchScope.allScope(PluginProject.mProject));

        System.out.println("p=" + psiClass.getContainingFile().getOriginalFile().getVirtualFile().getCanonicalPath());
        File file = new File(layoutPath);
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        AndroidView androidView = getAndroidViews(virtualFile, PluginProject.mProject);


        new WriteCommandAction.Simple(mProject) {
            @Override
            protected void run() throws Throwable {
                //PsiClass resultClass = InsertMetodToClass.generateOutput(psiClass, androidView);
                /*InsertMetodToClass.createField(psiClass, "yasina");
                saveClass(psiClass);*/
                PsiElementFactory elementFactory = JavaPsiFacade.getInstance(PluginProject.mProject).getElementFactory();
                //psiClass.add(elementFactory.createFieldFromText("private int yasina=0;", psiClass.getOriginalElement()));
                //psiClass.add(elementFactory.createMethodFromText("public int describeContents() { return 0; }", psiClass));
                generateOutput(androidView,psiClass,layoutPath);
            }
        }.execute();

    }

    public static final String ANDROID_VIEW_CLASS = "android.view.View";
    public static final String ANDROID_VIEW_GROUP_CLASS = "android.view.ViewGroup";
    public static final String ANDROID_LAYOUT_INFLATER_CLASS = "android.view.LayoutInflater";
    public static final String ANDROID_CONTEXT_CLASS = "android.content.Context";
    public static final String ANDROID_RECYCLER_VIEW_CLASS = "android.support.v7.widget.RecyclerView";
    public static final String ANDROID_RECYCLER_VIEW_VIEWHOLDER_CLASS = "android.support.v7.widget.RecyclerView.ViewHolder";
    public static final String ANDROID_RECYCLER_VIEW_VIEWHOLDER_VIEW_FIELD_NAME = "itemView";

    private boolean recyclerViewSupport;

    public void setRecyclerViewSupport(boolean support) {
        recyclerViewSupport = support;
    }

    public final boolean hasRecyclerViewSupport() {
        return recyclerViewSupport;
    }


    public PsiClass generateOutput(AndroidView androidView, PsiClass psiClass, String layoutFileName) {
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
            //generateRecyclerViewCompatConstructor(psiClass, layoutFileName);
        }
        generateBody(androidView, butterKnife, psiClass, project);
        //callInitMethodOnCreateView(psiClass);
        //addRClassImport(psiClass, layoutFileName);

        return psiClass;
        }else {
            return null;
        }
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

    protected void generateBody(AndroidView androidView, ButterKnife butterKnife, final PsiClass psiClass, Project project) {
        FieldGenerator fieldGenerator = new FieldGenerator();
        Map<AndroidView, PsiField> fieldMappings = fieldGenerator.generateFields(
                androidView, project, butterKnife, new FieldGenerator.AddToPsiClassCallback(psiClass));
        generateInitMethod(androidView, butterKnife, fieldMappings, psiClass);
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

    private void generateInitMethod(AndroidView androidView, ButterKnife butterKnife, Map<AndroidView, PsiField> fieldMappings, PsiClass psiClass) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiClass.getProject());

        /*PsiClass viewClass = ClassHelper.findClass(psiClass.getProject(), ANDROID_VIEW_CLASS);
        PsiParameter viewParam = factory.createParameter("view", factory.createType(viewClass));
        initMethod.getParameterList().add(viewParam);

        androidView.setTagName(ANDROID_VIEW_CLASS);
        androidView.setIdValue(viewParam.getName());*/
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
            callInitMethodOnCreateView(psiClass, initMethod);
        }

    }

    private void callInitMethodOnCreateView(PsiClass psiClass, PsiMethod psiMethod){
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        //    @Override
    //protected void onCreate(Bundle savedInstanceState) {
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
        findViewByIdStatementGenerator.createFindViewStatements(view, fieldAssigner);
    }

}
