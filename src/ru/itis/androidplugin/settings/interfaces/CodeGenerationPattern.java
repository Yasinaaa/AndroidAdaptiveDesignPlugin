package ru.itis.androidplugin.settings.interfaces;

import com.AndroidManifest;
import com.AndroidView;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public interface CodeGenerationPattern {

    String getName();

    String getSuggestedClassName(String layoutFileName);

    PsiClass generateOutput(Project project,
                            String layoutFileName, String outputClass);

    void setup(Project project);

}
