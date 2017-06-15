package ru.itis.androidplugin.generator.classes;

import ru.itis.androidplugin.generator.classes.patterns.ClassPattern;
import ru.itis.androidplugin.generator.classes.helper.*;
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
import ru.itis.androidplugin.generator.xml.helper.AndroidLayoutParser;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.File;

import static ru.itis.androidplugin.settings.PluginProject.mProject;

/**
 * Created by yasina on 27.03.17.
 */
public class ClassGenerator extends Generator {

    private PsiPackage selectedPackage;
    private PsiDirectory resultDirectory;

    public ClassGenerator(){

    }

    public String insertNewClass(ClassPattern classPattern, String layoutPath, final PsiPackage selectedPackage,
                               PsiDirectory resultDirectory, boolean isInit){
        if(!isInit){
            extendedInit();
        }

        String fileName = setJavaClassName(layoutPath, classPattern.getType());
        throwIfFileAlreadyExists(resultDirectory, fileName);
        String className = classPattern.removeExtension(fileName);
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
                String path = saveClass(resultDirectory, resultClass);
                classPattern.afterSaveClass(path);
            }
        }.execute();
        return className;
    }

    public String insertNewClass(ClassPattern classPattern, String layoutPath){
        extendedInit();
        PackageChooserDialog packageChooserDialog = new PackageChooserDialog("Destination Package", module);

        if (packageChooserDialog.showAndGet()) {
            selectedPackage = packageChooserDialog.getSelectedPackage();
            resultDirectory = getPsiDirectoryFromPackage(selectedPackage);
            return insertNewClass(classPattern, layoutPath, selectedPackage, resultDirectory, true);
        }
        return null;
    }

    private String saveClass(final PsiDirectory resultDirectory, final PsiClass resultClass) {
        if (resultDirectory != null) {
            if (resultClass != null) {
                PsiClass added = (PsiClass) resultDirectory.add(resultClass);

                VirtualFile virtualFile = added.getContainingFile().getVirtualFile();
                String path = virtualFile.getPath();

                PsiFile psiFile = added.getNavigationElement().getContainingFile();
                JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(added.getProject());
                styleManager.optimizeImports(psiFile);
                CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(added.getProject());
                PsiClass formatted = (PsiClass) codeStyleManager.reformat(added);
                formatted.navigate(true);
                return path;
            }
        }
        return null;
    }

    private AndroidView getAndroidViews(VirtualFile layoutFile, Project project) {
        AndroidLayoutParser parser = new AndroidLayoutParser(project);
        return parser.parse(layoutFile);
    }

    public void addInitToClass(ClassPattern classPattern, String parentPath, String layoutPath){
        init();
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
            /*if (name != null && name.equalsIgnoreCase(fileName)) {
                throw new GenerateViewPresenterAction.CancellationException("File \"" + fileName + "\" already exists");
            }*/
        }
    }

    private String setJavaClassName(String layoutPath, String type) {

        if(StringUtils.containsIgnoreCase(layoutPath, "item_")){
            layoutPath = layoutPath.substring(layoutPath.indexOf("_") + 1);
            if (layoutPath.contains(".xml")){
                layoutPath = layoutPath.replace(".xml","");
            }
        }
        //String className = StringUtils.capitalize(layoutPath) + "Holder";
        String className = StringUtils.capitalize(layoutPath) + type;
        String fileName = Messages.showInputDialog(PluginProject.mProject, "Input class name", "Creating File",
                Messages.getQuestionIcon(), className, null);
        /*if (fileName == null || fileName.length() == 0) {
            throw new GenerateViewPresenterAction.CancellationException("Incorrect file name");
        }*/
        if (!fileName.contains(".java")) {
            fileName += ".java";
        }
        return fileName;
    }

    public PsiPackage getSelectedPackage() {
        return selectedPackage;
    }

    public void setSelectedPackage(PsiPackage selectedPackage) {
        this.selectedPackage = selectedPackage;
    }

    public PsiDirectory getResultDirectory() {
        return resultDirectory;
    }

    public void setResultDirectory(PsiDirectory resultDirectory) {
        this.resultDirectory = resultDirectory;
    }
}
