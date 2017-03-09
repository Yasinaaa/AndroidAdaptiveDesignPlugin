package ru.itis.androidplugin.settings.interfaces;

import com.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.util.XmlPsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

/**
 * Created by yasina on 24.02.17.
 */
public class AbstractLayoutGenerationPattern implements CodeGenerationPattern {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getSuggestedClassName(String layoutFileName) {
        return layoutFileName;
    }

    @Override
    public PsiClass generateOutput(Project project,
                                   String layoutFileName, String outputClass) {

        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);

        PsiClass psiClass = factory.createClass(ClassHelper.getClassNameFromFullQualified(outputClass));


       /* PsiElement psiElement = factory.createTypeElement()
        PsiResourceVariable psiFile = factory.createResourceFromText();*/

        createField(psiClass, "dd");
        return psiClass;
    }

    public PsiField createField(PsiClass viewClass, String fieldName) {
        PsiElementFactory factory = JavaPsiFacade.getElementFactory(viewClass.getProject());
        PsiField field = factory.createField(fieldName, factory.createType(viewClass));
        PsiModifierList modifierList = field.getModifierList();
        if (modifierList != null) {
            modifierList.setModifierProperty(PsiModifier.PRIVATE, true);
        } else {
            throw new GenerateViewPresenterAction.CancellationException("Failed to create field");
        }
        return field;
    }

    @Override
    public void setup(Project project) {

    }

    public void writeAndroidStringToLocal(final Project myProject, String filePath, String name) {
        File file = new File(filePath + "/" + name);
        final VirtualFile virtualFile;
        boolean fileExits = true;
        try {
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                fileExits = false;
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
           // osw.write(getFileContent(fileContent));
            osw.write("ddfdsfsd");
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileExits) {
            virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            if (virtualFile == null)
                return;
            virtualFile.refresh(true, false, new Runnable() {
                @Override
                public void run() {
                   // openFileInEditor(myProject, virtualFile);
                }
            });
        } else {
            virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file);
           // openFileInEditor(myProject, virtualFile);
        }
    }
}
