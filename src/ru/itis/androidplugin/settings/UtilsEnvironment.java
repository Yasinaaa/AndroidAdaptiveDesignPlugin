package ru.itis.androidplugin.settings;

import com.*;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.UndoConfirmationPolicy;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiUtilBase;

/**
 * Created by yasina on 10.02.17.
 */
public class UtilsEnvironment {

    public static void insertInEditor(final String text) {
        Project project = getOpenProject();
        Editor editor = getEditor(project);

        if (project != null && editor != null && text != null && !text.isEmpty()) {
            CaretModel caretModel = editor.getCaretModel();
            final Integer currentOffset = caretModel.getOffset();
            final SelectionModel selectionModel = editor.getSelectionModel();

            CommandProcessor.getInstance().executeCommand(project, () -> ApplicationManager.getApplication().runWriteAction(() -> {
                Integer textLen = text.length();
                Document document = editor.getDocument();

                if (selectionModel.hasSelection()) {
                    int selectionStart = selectionModel.getSelectionStart();
                    document.replaceString(selectionStart, selectionModel.getSelectionEnd(), text);
                    selectionModel.removeSelection();
                    editor.getCaretModel().moveToOffset(selectionStart + textLen);
                } else {
                    document.insertString(currentOffset, text);
                    editor.getCaretModel().moveToOffset(currentOffset + textLen);
                }

                VirtualFile file = FileDocumentManager.getInstance().getFile(document);
                if (file != null) {
                    PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
                    if (psiFile != null) {
                        CodeStyleManager.getInstance(project).reformatText(psiFile, currentOffset, currentOffset + textLen);
                    }
                }
            }), "Paste", UndoConfirmationPolicy.DO_NOT_REQUEST_CONFIRMATION);

            VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(editor.getDocument());
            if (virtualFile != null) {
                FileEditorManager.getInstance(project).openFile(virtualFile, true);
            }
        }
    }

    private static Project getOpenProject() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();

        return (projects.length > 0) ? projects[0] : null;
    }

    private static Editor getEditor(Project curProject) {
        if (curProject == null) {
            curProject = getOpenProject();
        }
        if (curProject != null) {
            FileEditorManager manager = FileEditorManager.getInstance(curProject);

            return manager.getSelectedTextEditor();
        }

        return null;
    }

    private static void insertNewClassToProject(final String text) {
        final Project project = getOpenProject();
       /* try {
            if (project == null) {
                throw new GenerateViewPresenterAction.CancellationException("Unable to retrieve project");
            }
            final VirtualFile layoutFile = getSelectedLayoutFile(e);
            Module module = getModuleOfFile(project, layoutFile);
            final AndroidManifest androidManifest = getAndroidManifest(module, layoutFile);
            AndroidView androidView = getAndroidViews(layoutFile, project);

            final CodeGenerationPattern pattern = selectCodeGenerationPattern(project, layoutFile);
            final AndroidView filteredViews = selectViews(project, androidView);
            PsiPackage selectedPackage = selectDestinationPackage(module, androidManifest);
            final PsiDirectory resultDirectory = getPsiDirectoryFromPackage(selectedPackage);
            String fileName = selectJavaClassName(project, layoutFile, pattern);
            throwIfFileAlreadyExists(resultDirectory, fileName);
            String className = FileUtil.removeExtension(fileName);

            final String outputClassName = selectedPackage.getQualifiedName() + "." + className;
            pattern.setup(project);
            new WriteCommandAction.Simple(project) {
                @Override
                protected void run() throws Throwable {
                    PsiClass resultClass = pattern.generateOutput(project, androidManifest, filteredViews,
                            layoutFile.getName(), outputClassName);
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

        */
    }

}
