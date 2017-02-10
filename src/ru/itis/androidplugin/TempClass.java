package ru.itis.androidplugin;

import com.intellij.designer.PaletteToolWindowContent;
import com.intellij.designer.palette.PaletteToolWindowManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

/**
 * Created by yasina on 31.01.17.
 */
public class TempClass implements PaletteToolWindowContent{
    @Override
    public void clearActiveItem() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void dispose() {

    }
        /*extends PaletteToolWindowManager {
    public TempClass(Project project, FileEditorManager fileEditorManager) {
        super(project, fileEditorManager);
    }*/
}
