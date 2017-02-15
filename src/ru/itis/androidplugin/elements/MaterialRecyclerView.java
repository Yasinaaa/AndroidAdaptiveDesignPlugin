package ru.itis.androidplugin.elements;

import javax.swing.*;

/**
 * Created by yasina on 10.02.17.
 */
public class MaterialRecyclerView extends MaterialItem{

    private static final String EMPTY = "materialRecyclerView1";
    public static final String XML_VIEW_PATTERN = "<RecyclerView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        />";
    public static final String ICON_PATH = "/icons/ic_recyclerview.png";
    public static final String VIEW_NAME = "RecyclerView";

    private String mId = EMPTY;
    private ImageIcon mIcon;
    private String mViewParametrs;

    public MaterialRecyclerView(){
        super(VIEW_NAME, EMPTY, ICON_PATH);
        init(XML_VIEW_PATTERN, EMPTY);
    }

    public MaterialRecyclerView(String id) {
        super(VIEW_NAME, id, ICON_PATH);
        this.mId = id;
        init(XML_VIEW_PATTERN, id);
    }




}
