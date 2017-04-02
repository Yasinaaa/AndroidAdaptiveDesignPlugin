package ru.itis.androidplugin.generator;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.android.AndroidManifest;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.settings.PluginProject;

/**
 * Created by yasina on 01.04.17.
 */
public abstract class Generator {

    public Editor editor;
    public Document document;
    public VirtualFile virtualFile;
    public FileEditorManager fileEditorManager;
    //private String pathToLastFolder;
    public AndroidView androidViews;
    public AndroidManifest androidManifest;
    public Module module;

    public void init(){
        fileEditorManager = FileEditorManager.getInstance(PluginProject.mProject);
        editor = fileEditorManager.getSelectedTextEditor();
        document = editor.getDocument();
        virtualFile = FileDocumentManager.getInstance().getFile(document);
        //pathToLastFolder = virtualFile.getPath().substring(0, virtualFile.getPath().lastIndexOf("/"));
    }

    public void extendedInit(){
        init();
        androidManifest = new AndroidManifest(virtualFile);
        module = androidManifest.getModuleOfFile(virtualFile);
        androidViews = AndroidView.getAndroidViews(virtualFile);
    }
}
