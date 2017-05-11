package ru.itis.androidplugin.generator;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.UndoConfirmationPolicy;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiUtilBase;
import ru.itis.androidplugin.android.values.Attrs;
import ru.itis.androidplugin.android.values.Dimens;

import java.io.IOException;

/**
 * Created by yasina on 10.02.17.
 */
public class XmlChanger {

    private static boolean attrsIsAdded = false;

    public static void addAttrs() throws IOException {
        if (!attrsIsAdded) {
            new Attrs().addAttrsToProject();
            attrsIsAdded = true;
        }
    }

    public static void addDimens(String[] allValues)
            throws IOException{

        if(allValues != null){
            new Dimens().addAllDimens(allValues);
        }
    }
    public static void addDimens(String value)
            throws IOException{

        if(value != null){
            new Dimens().addAllDimens(new String[]{value});
        }
    }

    public static void addDimens(String[] allDimensTag, int[] allDimensValue)
            throws IOException{

        if(allDimensTag != null && allDimensValue != null){
            new Dimens().addAllDimens(allDimensTag, allDimensValue);
        }
    }

    public static void addDimens(String tag, int value)
            throws IOException{
        if(tag != null && value != 0) {
            new Dimens().addLine(tag, value + "");
        }
    }

    public static void insertInEditor(final String text)
            throws IOException {

        Project project = getOpenProject();
        Editor editor = getEditor(project);

        if (project != null && editor != null && text != null && !text.isEmpty()) {
            CaretModel caretModel = editor.getCaretModel();
            final Integer currentOffset = caretModel.getOffset();
            final SelectionModel selectionModel = editor.getSelectionModel();

            CommandProcessor.getInstance().executeCommand(project, () -> ApplicationManager.getApplication().runWriteAction(() -> {
                Integer textLen = text.length();
                Document document = editor.getDocument();

                //todo: changed to addition after </> or </%s>
                /*if (selectionModel.hasSelection()) {
                    int selectionStart = selectionModel.getSelectionStart();
                    document.replaceString(selectionStart, selectionModel.getSelectionEnd(), text);
                    selectionModel.removeSelection();
                    editor.getCaretModel().moveToOffset(selectionStart + textLen);
                } else {*/
                //document.insertString(currentOffset, text);
                //editor.getCaretModel().moveToOffset(currentOffset + textLen);
                //}

                VirtualFile file = FileDocumentManager.getInstance().getFile(document);
                if (file != null) {
                    PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
                    if(!psiFile.getText().contains(text) && psiFile != null){
                        document.insertString(currentOffset, text);
                        editor.getCaretModel().moveToOffset(currentOffset + textLen);
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


}
