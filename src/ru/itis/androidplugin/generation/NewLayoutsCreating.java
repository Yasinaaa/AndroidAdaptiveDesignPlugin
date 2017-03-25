package ru.itis.androidplugin.generation;

import com.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
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
import ru.itis.androidplugin.view.MainView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by yasina on 16.03.17.
 */
public class NewLayoutsCreating {

    private final String mTextXML= "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "              android:orientation=\"vertical\"\n" +
            "              android:layout_width=\"match_parent\"\n" +
            "              android:layout_height=\"match_parent\">\n" +
            "\n" +
            "</RelativeLayout>";

    public NewLayoutsCreating(){}

    public void initAllScreenLayouts(String name) {
        FileEditorManager manager = FileEditorManager.getInstance(PluginProject.mProject);
        Document document = manager.getSelectedTextEditor().getDocument();
        final VirtualFile layoutFile = FileDocumentManager.getInstance().getFile(document);
        String path = layoutFile.getPath().substring(0, layoutFile.getPath().lastIndexOf("/"));

        Module module = getModuleOfFile(layoutFile);
        final AndroidManifest androidManifest = getAndroidManifest(module, layoutFile);
        AndroidView androidView = getAndroidViews(layoutFile);

        String newPath = createPathToLayoutFolder(layoutFile) + "/" + "layout-sw600dp";
        System.out.println(newPath);
        new WriteCommandAction.Simple(PluginProject.mProject) {
            @Override
            protected void run() throws Throwable {
                writeAndroidStringToLocal(newPath, name);
            }
        }.execute();
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
    private VirtualFile writeAndroidStringToLocal(String path, String name) {
        File file = new File(path);
        file.mkdir();
        file = new File(path+name);

        VirtualFile virtualFile = null;
        boolean fileExits = true;
        try {
            //file.getParentFile().mkdirs();
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
            /*VirtualFile finalVirtualFile = virtualFile;
            virtualFile.refresh(true, false, new Runnable() {
                @Override
                public void run() {
                    openFile(finalVirtualFile);
                }
            });*/
        } else {
            virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file);
            //openFile(virtualFile);

        }
        return virtualFile;
    }

    private void openFile(VirtualFile virtualFile){
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(PluginProject.mProject);
        fileEditorManager.openFile(virtualFile, true, true);

    }


    private String createPathToLayoutFolder(VirtualFile file) {
        ProjectRootManager rootManager = ProjectRootManager.getInstance(PluginProject.mProject);
        Module module = rootManager.getFileIndex().getModuleForFile(file);
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        VirtualFile[] contentRoots = moduleRootManager.getContentRoots();
        String path = contentRoots[0].getPath() + "/src/main/res";
        return path;
    }

    private AndroidManifest getAndroidManifest(Module module, VirtualFile layoutFile) {
        VirtualFile manifestFile = getManifestFile(module, layoutFile);
        AndroidManifest androidManifest = new AndroidManifestParser().parse(manifestFile);
        if (androidManifest == null) {
            throw new GenerateViewPresenterAction.CancellationException("Failed to read AndroidManifest.xml");
        }
        return androidManifest;
    }
    private VirtualFile getManifestFile(Module module, VirtualFile layoutFile) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        VirtualFile[] contentRoots = moduleRootManager.getContentRoots();
        VirtualFile result = lookupForManifest(layoutFile.getParent().getParent(), contentRoots);
        if (result == null) {
            throw new GenerateViewPresenterAction.CancellationException("AndroidManifest.xml not found");
        }
        return result;
    }
    private VirtualFile lookupForManifest(VirtualFile dir, VirtualFile[] topDirs) {
        //noinspection UnsafeVfsRecursion
        for (VirtualFile file : dir.getChildren()) {
            if (!file.isDirectory() && "AndroidManifest.xml".equals(file.getName())) {
                return file;
            }
        }

        for (VirtualFile topDir : topDirs) {
            if (topDir.equals(dir)) {
                return null;
            }
        }

        VirtualFile parent = dir.getParent();
        if (!dir.isDirectory()) {
            return null;
        }

        return lookupForManifest(parent, topDirs);
    }

    private AndroidView getAndroidViews(VirtualFile layoutFile) {
        AndroidLayoutParser parser = new AndroidLayoutParser(PluginProject.mProject);
        return parser.parse(layoutFile);
    }

    private Module getModuleOfFile(VirtualFile layoutFile) {
        ProjectRootManager rootManager = ProjectRootManager.getInstance(PluginProject.mProject);
        Module module = rootManager.getFileIndex().getModuleForFile(layoutFile);
        if (module == null) {
            throw new GenerateViewPresenterAction.CancellationException("Failed to determine module with selected layout");
        }
        return module;
    }
}
