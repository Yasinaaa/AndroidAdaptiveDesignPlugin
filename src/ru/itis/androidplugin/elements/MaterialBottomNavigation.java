package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.settings.UtilsEnvironment;
import ru.itis.androidplugin.view.CreateAdapterDialog;
import ru.itis.androidplugin.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialBottomNavigation extends MaterialItem {

    private static final String EMPTY = "materialRecyclerView1";
    public static final String XML_VIEW_PATTERN = "<RecyclerView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        />";
    public static final String ICON_PATH = "/icons/bottom_navigation.png";
    public static final String VIEW_NAME = "Bottom Navigation";

    private String mId = EMPTY;
    private ImageIcon mIcon;

    public MaterialBottomNavigation(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }


    @Override
    public MaterialItem getСhild() {
        return null;
    }

    @Override
    public void setView(MainView mainView) {

    }
}
