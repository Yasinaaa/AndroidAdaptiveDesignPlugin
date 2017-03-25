package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.view.MainView;

import javax.imageio.ImageIO;
import javax.swing.*;
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

    public void setView(MainView mainView) {

    }

    public void setViewChildAndParent(MainView mainView) {

    }

    public MaterialItem getParent() {
        return null;
    }

    public void setParent(MaterialItem parent) {}

    public void addItemToHistoryList(MainView mainView){
        // <- ->
        mainView.tenClickedMaterialItems.add(mainView.currentItem, this);
        mainView.setBackNextLabelsVisiblility();
        mainView.currentItem++;
        // <- ->
    }

    public void hideNotNeededThings(MainView mainView){

    }
}
