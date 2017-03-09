package ru.itis.androidplugin.elements;

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


    public MaterialItem(String viewName, String pattern, String iconPath) {
        this.mViewName = viewName;
        this.mPattern = pattern;

        try {
             mIcon = new ImageIcon(ImageIO.read(getClass().getResource(iconPath)));

        }catch (IOException ignored) {}
    }

    public void setId(String id){
        mId = id;
        mViewParametrs = String.format(mPattern, id);
    }

    //public abstract String getViewParametrs();
}
