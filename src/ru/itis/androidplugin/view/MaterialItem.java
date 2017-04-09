package ru.itis.androidplugin.view;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    public String mLayoutName; // activit_main.xml
    public String mLayoutPath; // /home/yasina/.../activit_main.xml
    public String mClassPath;  // /home/yasina/.../ActivityMain.java
    public String mStyle;
    public MaterialItem[] mChildrenItems = null;
    public MaterialItem mParentItem = null;
    public String mParentItemName;
    public String mType;
    public String[] mAllTypes;

    public MaterialItem(String viewName, String pattern, String iconPath) {
        this.mViewName = viewName;
        this.mPattern = pattern;

        try {
             mIcon = new ImageIcon(ImageIO.read(getClass().getResource(iconPath)));
        }catch (IOException ignored) {}

    }

    public void setView() {}
    public void setViewParameters(){}
    public void insertToLayoutOrNo(){}

    public void onDocumentChangeListener(){};
    public void onFirstJComboBoxChangeListener(){};

}
