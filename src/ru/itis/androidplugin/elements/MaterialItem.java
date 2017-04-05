package ru.itis.androidplugin.elements;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.android.AndroidManifest;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.view.MainView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by yasina on 10.02.17.
 */
public abstract class MaterialItem {

    public String mViewName;
    public ImageIcon mIcon;
    public String mViewParametrs;
    public String mPattern;
    public String mId;
    public String mLayoutPath;
    public String mClassPath;

    //todo change
    public String recyclerViewType;

    public MaterialItem(String viewName, String pattern, String iconPath) {
        this.mViewName = viewName;
        this.mPattern = pattern;

        try {
             mIcon = new ImageIcon(ImageIO.read(getClass().getResource(iconPath)));
        }catch (IOException ignored) {}


    }

    public void setId(String id){
        mId = id;
    }

    public void setLayoutPath(String layoutName){
        mLayoutPath = layoutName;
    }

    public String getLayoutPath(){
        return mLayoutPath;
    }

    public void setViewParameters(){

    }

    public MaterialItem[] getСhild() {
        return null;
    }

    public void setСhild(MaterialItem[] child) {}

    //todo: remove one of it
    public void setView(MainView mainView) {

    }

    public void setView() {

    }

    public void setViewChildAndParent(MainView mainView) {

    }

    public void insertToLayoutOrNo(){

    }
    public MaterialItem getParent() {
        return null;
    }

    public void setParent(MaterialItem parent) {}

    public void addItemToHistoryList(MainView mainView){
        // <- ->
        /*mainView.tenClickedMaterialItems.add(mainView.currentItem, this);
        mainView.setBackNextLabelsVisiblility();
        mainView.currentItem++;*/
        // <- ->
    }

    public void hideNotNeededThings(MainView mainView){

    }

    public String getmClassPath() {
        return mClassPath;
    }

    public void setmClassPath(String mClassPath) {
        this.mClassPath = mClassPath;
    }

    public void removeAllExistenActionListeners(JButton button){
        ActionListener[] allActionListeners = button.getListeners(ActionListener.class);
        for (ActionListener actionListener: allActionListeners){
            button.removeActionListener(actionListener);
        }
    }


}
