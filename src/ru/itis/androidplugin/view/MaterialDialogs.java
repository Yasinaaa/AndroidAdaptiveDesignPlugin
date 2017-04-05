package ru.itis.androidplugin.view;

import javax.swing.*;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialDialogs extends MaterialItem {

    private static final String EMPTY = "materialRecyclerView1";
    public static final String XML_VIEW_PATTERN = "<RecyclerView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        />";
    public static final String ICON_PATH = "/icons/dialogs.png";
    public static final String VIEW_NAME = "Dialogs";

    private String mId = EMPTY;
    private ImageIcon mIcon;

    public MaterialDialogs(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    @Override
    public void setView(MainView mainView) {

    }

}
