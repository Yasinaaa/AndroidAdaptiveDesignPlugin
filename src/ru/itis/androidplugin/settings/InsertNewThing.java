package ru.itis.androidplugin.settings;

import com.*;
import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.JavaProjectRootsUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.ui.CheckboxTreeBase;
import com.intellij.ui.CheckedTreeNode;
import ru.itis.androidplugin.settings.interfaces.AbstractLayoutGenerationPattern;
import ru.itis.androidplugin.settings.interfaces.LayoutPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 24.02.17.
 */
public class InsertNewThing {

    private final Project project = PluginProject.mProject;
    private Editor editor = getEditor(project);

    public InsertNewThing() {

    }
    private Editor getEditor(Project curProject) {
        if (curProject == null) {
            curProject = project;
        }
        if (curProject != null) {
            FileEditorManager manager = FileEditorManager.getInstance(curProject);

            return manager.getSelectedTextEditor();
        }

        return null;
    }

    public void insertNewLayout(String name){

        try {
            if (project == null) {
                throw new GenerateViewPresenterAction.CancellationException("Unable to retrieve project");
            }

            Document document = editor.getDocument();
            final VirtualFile layoutFile = FileDocumentManager.getInstance().getFile(document);
            Module module = getModuleOfFile(project, layoutFile); // app

            PsiPackage selectedPackage = selectDestinationPackage();
            final PsiDirectory resultDirectory = getPsiDirectoryFromPackage(selectedPackage);
          //  final String outputClassName = selectedPackage.getQualifiedName() + "." + "my.xml";

            new WriteCommandAction.Simple(project) {
                @Override
                protected void run() throws Throwable {
                    AbstractLayoutGenerationPattern layoutGenerationPattern = new AbstractLayoutGenerationPattern();
                    PsiClass resultClass = layoutGenerationPattern.generateOutput(project, name, "Holder");
                    saveClass(resultDirectory, resultClass);
                }
            }.execute();
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                }
            });
        } catch (GenerateViewPresenterAction.CancellationException ignored) {
            if (ignored.getMessage() != null && project != null) {
                Messages.showErrorDialog(project, ignored.getMessage(), "Error");
            }
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

    private PsiPackage lastSelectedPackage;

    private AndroidView selectViews(Project project, AndroidView androidView) {
        MultichoiceTreeDialog treeDialog = new MultichoiceTreeDialog(project, "Views To Find", androidView,
                new CheckboxTreeBase.CheckPolicy(false, false, false, false));
        if (treeDialog.showAndGet()) {
            CheckedTreeNode node = treeDialog.getResult();
            AndroidView result = new AndroidView();
            buildTree(result, node);
            return result;
        } else {
            throw new GenerateViewPresenterAction.CancellationException();
        }
    }

    private void buildTree(AndroidView parent, CheckedTreeNode node) {
        AndroidView current = parent;
        if (node.isChecked()) {
            AndroidView view = (AndroidView) node.getUserObject();
            if (view.getParent() != null) {
                current = new AndroidView();
                current.setIdValue(view.getIdValue());
                current.setTagName(view.getTagName());
                parent.addSubView(current);
            }
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            buildTree(current, (CheckedTreeNode) node.getChildAt(i));
        }
    }

    private void throwIfFileAlreadyExists(PsiDirectory resultDirectory, String fileName) {
        for (PsiFile file : resultDirectory.getFiles()) {
            String name = file.getName();
            if (name != null && name.equalsIgnoreCase(fileName)) {
                throw new GenerateViewPresenterAction.CancellationException("File \"" + fileName + "\" already exists");
            }
        }
    }


    private PsiDirectory getPsiDirectoryFromPackage(PsiPackage selectedPackage) {
        PsiDirectory[] allDirectories = PackageUtil.getDirectories(selectedPackage, null, false);
        List<PsiDirectory> directories = new ArrayList<PsiDirectory>(allDirectories.length);
        for (PsiDirectory directory : allDirectories) {
            if (!JavaProjectRootsUtil.isInGeneratedCode(directory.getVirtualFile(), selectedPackage.getProject())) {
                directories.add(directory);
            }
        }

        if (directories.size() > 1) {
            String[] dirs = new String[directories.size()];
            for (int i = 0; i < directories.size(); i++) {
                dirs[i] = directories.get(i).getVirtualFile().getPath();
            }
            ChooseDialog dialog = new ChooseDialog(selectedPackage.getProject(), "Directory Selection",
                    "Package referenced to several folders. Select result destination",
                    dirs,
                    0);
            if (dialog.showAndGet()) {
                int index = dialog.getSelectedIndex();
                if (index >= 0) {
                    return directories.get(index);
                }
            }
        } else if (directories.size() == 1) {
            return directories.get(0);
        }
        throw new GenerateViewPresenterAction.CancellationException();
    }


    private PsiPackage selectDestinationPackage() {
        PackageChooserDialog packageChooserDialog = new PackageChooserDialog("Destination Package", project);
        if (lastSelectedPackage != null) {
            packageChooserDialog.selectPackage(lastSelectedPackage.getQualifiedName());
        } else {
          //  packageChooserDialog.selectPackage(manifest.getPackageName());
        }
        if (packageChooserDialog.showAndGet()) {
            return lastSelectedPackage = packageChooserDialog.getSelectedPackage();
        } else {
            throw new GenerateViewPresenterAction.CancellationException();
        }
    }

    private Module getModuleOfFile(Project project, VirtualFile layoutFile) {
        ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
        Module module = rootManager.getFileIndex().getModuleForFile(layoutFile);



        if (module == null) {
            throw new GenerateViewPresenterAction.CancellationException("Failed to determine module with selected layout");
        }
        return module;
    }


}
