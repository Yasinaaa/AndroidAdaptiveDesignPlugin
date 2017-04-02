package ru.itis.androidplugin.generator;

import ru.itis.androidplugin.android.AndroidView;
import com.GenerateViewPresenterAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.elements.MaterialChildRecyclerView;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by yasina on 01.04.17.
 */
public class XmlGenerator extends Generator{

    private final String SIMPLE_RELATIVE_LAYOUT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "              android:orientation=\"vertical\"\n" +
            "              android:layout_width=\"match_parent\"\n" +
            "              android:layout_height=\"match_parent\">\n" +
            "\n" +
            "</RelativeLayout>";

    private final String CHILD_RECYCLERVIEW = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "              xmlns:app=\"http://schemas.android.com/apk/res-auto\"" +
            "              android:orientation=\"vertical\"\n" +
            "              android:layout_width=\"match_parent\"\n" +
            "              android:layout_height=\"match_parent\"\n" +
            "              app:child_type_recyclerview=\"%s\""+
            "              app:parent_recyclerview=\"@id/%s\">"+
            "\n" +
            "</RelativeLayout>";

    public XmlGenerator(){
    }

    public VirtualFile insertNewLayout(MaterialChildRecyclerView materialChildRecyclerView, String name){
        final VirtualFile[] solutionVirtualFile = {null};

        try {
            init();
            String path = createPathToRESFolder(virtualFile) + "/layout/" + name + ".xml";

            File file = new File(path);
            solutionVirtualFile[0] = LocalFileSystem.getInstance().findFileByIoFile(file);

            if (solutionVirtualFile[0] == null) {
                new WriteCommandAction.Simple(PluginProject.mProject) {
                    @Override
                    protected void run() throws Throwable {
                        File file = new File(path);
                        file.getParentFile().mkdirs();
                        solutionVirtualFile[0] = materialChildRecyclerView == null ?
                                createCleanXMLfile(SIMPLE_RELATIVE_LAYOUT, file) :
                                createCleanXMLfile(String.format(CHILD_RECYCLERVIEW,
                                        new String[]{
                                                materialChildRecyclerView.getAttrType(),
                                                materialChildRecyclerView.getParent().mId
                                        }),file);
                    }
                }.execute();
            }

        } catch (GenerateViewPresenterAction.CancellationException ignored) {
            if (ignored.getMessage() != null && PluginProject.mProject != null) {
                Messages.showErrorDialog(PluginProject.mProject, ignored.getMessage(), "Error");
            }
        }
        return solutionVirtualFile[0];
    }

    //todo: change method
    public void initAllScreenLayouts(String layoutTitle, String[] folderTitles) {
        init();
        AndroidView androidView = AndroidView.getAndroidViews(virtualFile);
        String path = createPathToRESFolder(virtualFile);

        new WriteCommandAction.Simple(PluginProject.mProject) {
            @Override
            protected void run() throws Throwable {
                for (String folder : folderTitles){
                    // "layout-sw600dp"
                    File file = new File(path + "/" + folder);
                    file.mkdir();
                    file = new File(path + layoutTitle);
                    createCleanXMLfile(SIMPLE_RELATIVE_LAYOUT, file);
                }
            }
        }.execute();
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    //todo: should it return VirtualFile?
    private VirtualFile createCleanXMLfile(String inputText, File file) {

        VirtualFile solutionVirtualFile = null;
        boolean fileExits = true;
        try {

            if (!file.exists()) {
                fileExits = false;
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
            osw.write(inputText);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileExits) {
            solutionVirtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            if (solutionVirtualFile == null)
                return null;

        } else {
            solutionVirtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file);
        }
        return solutionVirtualFile;
    }

    private String createPathToRESFolder(VirtualFile file){
        ProjectRootManager rootManager = ProjectRootManager.getInstance(PluginProject.mProject);
        Module module = rootManager.getFileIndex().getModuleForFile(file);
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        VirtualFile[] contentRoots = moduleRootManager.getContentRoots();
        String path = contentRoots[0].getPath() + "/src/main/res";
        return path;
    }

    public void openFile(VirtualFile virtualFile){
        fileEditorManager.openFile(virtualFile, true, true);
    }

}
