package ru.itis.androidplugin.generation;

import com.GenerateViewPresenterAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.settings.interfaces.AbstractLayoutGenerationPattern;
import ru.itis.androidplugin.settings.interfaces.LayoutPattern;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by yasina on 01.03.17.
 */
public class LayoutGenerator {

    private final Project project = PluginProject.mProject;
    private Editor editor = getEditor(project);
    private final String mTextXML= "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "              android:orientation=\"vertical\"\n" +
            "              android:layout_width=\"match_parent\"\n" +
            "              android:layout_height=\"match_parent\">\n" +
            "\n" +
            "</RelativeLayout>";

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

    public LayoutGenerator() {
    }


    public VirtualFile insertNewLayout(String name){
        final VirtualFile[] virtualFile = {null};

        try {
            if (project == null) {
                throw new GenerateViewPresenterAction.CancellationException("Unable to retrieve project");
            }

            Document document = editor.getDocument();
            final VirtualFile layoutFile = FileDocumentManager.getInstance().getFile(document);
            String path = layoutFile.getPath().substring(0,layoutFile.getPath().lastIndexOf("/"));
            String newPath =  createPathToLayoutFolder(path, layoutFile) + "/" + name + ".xml";

            File file = new File(newPath);
            virtualFile[0] = LocalFileSystem.getInstance().findFileByIoFile(file);

            if (virtualFile[0] == null) {
                new WriteCommandAction.Simple(project) {
                    @Override
                    protected void run() throws Throwable {
                        virtualFile[0] = writeAndroidStringToLocal(newPath);
                    }
                }.execute();
            }

        } catch (GenerateViewPresenterAction.CancellationException ignored) {
            if (ignored.getMessage() != null && project != null) {
                Messages.showErrorDialog(project, ignored.getMessage(), "Error");
            }
        }
        return virtualFile[0];
    }

    private VirtualFile writeAndroidStringToLocal(String path) {
        File file = new File(path);
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
            osw.write(mTextXML);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileExits) {
            virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            if (virtualFile == null)
                return null;
            /*virtualFile.refresh(true, false, new Runnable() {
                @Override
                public void run() {
                    openFile(virtualFile);
                }
            });*/
        } else {
            virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file);
        }
        return virtualFile;
    }

    public void openFile(VirtualFile virtualFile){
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(PluginProject.mProject);
        fileEditorManager.openFile(virtualFile, true, true);
    }

    private String createPathToLayoutFolder(String name, VirtualFile file){
        ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
        Module module = rootManager.getFileIndex().getModuleForFile(file);
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        VirtualFile[] contentRoots = moduleRootManager.getContentRoots();
        String path = contentRoots[0].getPath() + "/src/main/res" + "/layout";
        return path;
    }
}
