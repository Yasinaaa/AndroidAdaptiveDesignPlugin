package ru.itis.androidplugin.settings;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;

/**
 * Created by yasina on 24.02.17.
 */
public class PluginProject implements ProjectComponent {

    public static Project mProject;
    public static String mLayoutPath;
    private MessageBus mMessageBus;
    public static FileOwner fileParameters;

    public PluginProject(Project project) {
        mProject = project;

    }
    public static VirtualFile getCurrentVirtualFile() {
        FileEditorManager manager = FileEditorManager.getInstance(PluginProject.mProject);
        Editor editor = manager.getSelectedTextEditor();
        Document document = editor.getDocument();
        final VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
        return virtualFile;
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
        fileParameters = new FileOwner();

        System.out.println("mProject.getBasePath()= " + mProject.getBasePath());
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "PluginProject";
    }

    @Override
    public void projectOpened() {

        mMessageBus = mProject.getMessageBus();
        mMessageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileOpened(source, file);
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileClosed(source, file);
            }

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                mLayoutPath = event.getNewFile().getPath();
                fileParameters.setFilePath(mLayoutPath);
                System.out.println("selectionChanged=" + mLayoutPath);
                super.selectionChanged(event);
            }
        });
    }

    @Override
    public void projectClosed() {
        // called when project is being closed
        mMessageBus.dispose();
        System.out.println("mMessageBus.dispose()");

    }


}
