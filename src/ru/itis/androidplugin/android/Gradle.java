package ru.itis.androidplugin.android;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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

        for (PsiFile gradleFile: allGradles){
            String text = gradleFile.getText();

            if (text.contains(BEGIN)){

                VirtualFile virtualFile = gradleFile.getVirtualFile();
                try {
                    List<String> list = Files.readAllLines(Paths.get(virtualFile.getCanonicalPath()));
                    if(!list.contains("    " + MATERIAL_LIB)){
                        list.add(list.size() - 1, "    " + MATERIAL_LIB);
                        Files.write(Paths.get(virtualFile.getCanonicalPath()), list);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
