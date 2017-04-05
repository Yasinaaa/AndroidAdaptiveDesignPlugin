package ru.itis.androidplugin.android;

import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import ru.itis.androidplugin.settings.PluginProject;

/**
 * Created by yasina on 05.04.17.
 */
public class Gradle {

    private static final String BEGIN = "apply plugin: 'com.android.application'";
    private static final String MATERIAL_LIB ="compile 'blue.aodev:material-values:1.1.1'";

    public static void addMaterialVauesLibToProject(){
        //todo make method

        PsiFile[] allGradles = FilenameIndex.getFilesByName(PluginProject.mProject, "build.gradle",
                GlobalSearchScope.allScope(PluginProject.mProject));

        /*for (PsiFile gradleFile: allGradles){
            String text = gradleFile.getText();

            if (text.contains(BEGIN)){
                PsiElementFactory factory = JavaPsiFacade.getElementFactory(PluginProject.mProject);
                PsiComment lib = factory.createCommentFromText(MATERIAL_LIB, gradleFile);
                gradleFile.add(lib);
            }
        }*/
    }
}
