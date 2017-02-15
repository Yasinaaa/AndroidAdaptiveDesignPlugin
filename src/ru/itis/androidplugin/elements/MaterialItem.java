package ru.itis.androidplugin.elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 * Created by yasina on 10.02.17.
 */
public class MaterialItem {

    public String mViewName;
    public String mId;
    public ImageIcon mIcon;
    public String mViewParametrs;

    public MaterialItem(String viewName, String id, String iconPath) {
        this.mViewName = viewName;
        this.mId = id;

        try {
             mIcon = new ImageIcon(ImageIO.read(getClass().getResource(iconPath)));

        }catch (IOException ignored) {}
    }

    public void init(String pattern, String id){
        mViewParametrs = String.format(pattern, id);
    }


}
