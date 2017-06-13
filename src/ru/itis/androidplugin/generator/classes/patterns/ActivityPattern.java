package ru.itis.androidplugin.generator.classes.patterns;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import ru.itis.androidplugin.android.AndroidManifest;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.generator.classes.helper.ButterKnife;
import ru.itis.androidplugin.generator.classes.helper.ClassHelper;
import ru.itis.androidplugin.generator.classes.helper.FieldGenerator;
import ru.itis.androidplugin.interfaces.ToolbarInterface;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ru.itis.androidplugin.settings.PluginProject.mProject;

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
    public void createClassItems(AndroidView androidView, ButterKnife butterKnife,
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
        initToolbarMethod(androidView, psiClass);
    }

    String[] ON_CREATE_OPTIONS_MENU_STATEMENTS = new String[]{
            "MenuInflater inflater = getMenuInflater();\n",
            "inflater.inflate(R.menu.%s, menu);\n",
            "final MenuItem searchItem = menu.findItem(R.id.action_search);\n",
            "final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);\n",
            "searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {\n ",
            "@Override\n"+ "public boolean onQueryTextSubmit(String query) {\n" + "return false;\n"+ "}\n\n",
            "@Override\n"+ "public boolean onQueryTextChange(String newText) {\n" + "return false;\n" + "}\n",
            "};",
            "return true;"
    };

    private void initToolbarMethod(AndroidView androidView, PsiClass psiClass){
        String onCreateOptionsMenu =
                "    @Override\n" +
                "    public boolean onCreateOptionsMenu(Menu menu) {\n" +
                "        MenuInflater inflater = getMenuInflater();\n" +
                "        inflater.inflate(R.menu.toolbar, menu);\n" +
                "\n" +
                "        final MenuItem searchItem = menu.findItem(R.id.action_search);\n" +
                "        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);\n" +
                "        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {\n" +
                "            @Override\n" +
                "            public boolean onQueryTextSubmit(String query) {\n" +
                "                return false;\n" +
                "            }\n" +
                "\n" +
                "            @Override\n" +
                "            public boolean onQueryTextChange(String newText) {\n" +
                "                return false;\n" +
                "            }\n" +
                "        });*/\n" +
                "\n" +
                "       return true;\n" +
                "\n" +
                "    }";

       String onOptionItemSelectedMethod =
               "     @Override\n" +
               "    public boolean onOptionsItemSelected(MenuItem item) {\n" +
               "        switch (item.getItemId()) {\n" +
               "\n" +
               "            case R.id.action_settings:\n" +
               "                Toast.makeText(this, \"Settings selected\", Toast.LENGTH_SHORT)\n" +
               "                        .show();\n" +
               "                break;\n" +
               "            default:\n" +
               "                break;\n" +
               "        }\n" +
               "\n" +
               "     return true;\n" +
               "    }";
       String newToolbarClass = "\npublic interface ToolbarStates {\n" +
               "\n" +
               "    void setSelectedStateToolbar(int num, int count);\n" +
               "    void setRemoveStateToolbar(int id);\n" +
               "\n}";
       String removeMethods =
               "    @Override\n" +
               "    public void setSelectedStateToolbar(int num, int count) {\n" +
               "        getSupportActionBar().setTitle(num + \" from \" + count);\n" +
               "    }\n" +
               "\n" +
               "    @Override\n" +
               "    public void setRemoveStateToolbar(int id) {\n" +
               "\n" +
               "    }";


       AndroidView.Toolbar toolbar = androidView.getToolbar();
       if(toolbar != null){
            System.out.println(androidView.getToolbar().type);

           List<String> list = null;
           VirtualFile virtualFile = psiClass.getContainingFile().getVirtualFile();
           String path = virtualFile.getPath();
           File file = new File(path);

           try {
               list = Files.readAllLines(Paths.get(virtualFile.getPath()));
               String packageName = list.get(0);

               switch (toolbar.type){
                   case ToolbarInterface.STANDARD_TYPE:{
                       if(!list.contains(onCreateOptionsMenu))
                           list.add(list.size() - 1, onCreateOptionsMenu);
                       if(!list.contains(onOptionItemSelectedMethod))
                           list.add(list.size() - 1, onOptionItemSelectedMethod);

                       break;
                  }
                   case ToolbarInterface.REMOVE_TYPE:{

                       for(int i=0; i<list.size(); i++){
                           if(list.get(i).contains("public class")){
                               list.set(i, list.get(i).replace("{", "implements ToolbarStates {"));
                               break;
                           }
                       }
                       list.add(list.size() - 1, removeMethods);
                       Files.write(Paths.get(path), list);

                       int slash = path.lastIndexOf("/");
                       path = path.substring(0,slash) + "/ToolbarStates.java";
                       list = new ArrayList<String>();
                       list.add(packageName);
                       list.add(newToolbarClass);
                       Files.write(Paths.get(path), list);

                       break;
                   }
               }

               Files.write(Paths.get(path), list);


           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    private void addStatementsToMethod(String[] statementsText, String addToText, PsiClass psiClass, PsiMethod psiMethod){
        for(int num = 0; num < statementsText.length; num++){
            if (statementsText[num].contains("public") ){
                PsiMethod newMethod =
                        factory.createMethodFromText(statementsText[num], psiClass);
                psiMethod.getBody().add(newMethod);


            }else if(statementsText[num].contains("new ")) {
               // factory.createField(statementsText[num], null);
                //factory.createCommentFromText(statementsText[num], null);
                //factory.createCodeBlockFromText(statementsText[num], null);
                //factory.createExpressionFromText(statementsText[num], null);

            }
               /* PsiStatement newStatement =
                        factory.createStatementFromText(statementsText[num], psiClass);

                //addStatementsToMethod(Arrays.copyOfRange(statementsText, num+1, statementsText.length),
                  //      null, psiClass, newStatement);

                psiMethod.getBody().add(newStatement);

            }*/

            /*
                PsiClass newClassExample = factory.createClass(statementsText[num]);

                PsiClass psiClass2 = JavaPsiFacade.getInstance(PluginProject.mProject).
                        findClass(parentPath,
                        GlobalSearchScope.allScope(PluginProject.mProject));

                factory.createClassReferenceElement()
                psiMethod.getBody().add(newClassExample);
                addStatementsToMethod(Arrays.copyOfRange(statementsText, num+1, statementsText.length), null, newClassExample, psiMethod);

            }*/
            else{

                if (statementsText[num].contains("%s")) {
                    // if it's situation as inflater.inflate(R.menu.%s, menu);
                    statementsText[num] = String.format(statementsText[num], addToText);
                }
                PsiStatement newStatement =
                        factory.createStatementFromText(statementsText[num], psiClass);

                psiMethod.getBody().add(newStatement);
            }
        }
        psiClass.add(psiMethod);
    }



    private boolean isMethod(String text){
        String[] access = new String[]{"private", "public", "protected"};
        String[] type = new String[]{"boolean"};

        return false;
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
