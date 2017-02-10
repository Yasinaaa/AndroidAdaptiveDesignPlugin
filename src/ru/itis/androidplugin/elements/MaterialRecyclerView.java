package ru.itis.androidplugin.elements;

import javax.swing.*;

/**
 * Created by yasina on 10.02.17.
 */
public class MaterialRecyclerView extends MaterialItem{

    public static final String XML_VIEW_PATTERN = "<RecyclerView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        />";
    public static final String ICON_PATH = "/icons/ic_recyclerview.png";
    public static final String VIEW_NAME = "RecyclerView";

    private String mId;
    private ImageIcon mIcon;
    private String mViewParametrs;

    public MaterialRecyclerView(String id) {
        //String xmlViewPattern, String viewName, String id, String iconPath, String viewParametrs

        super(VIEW_NAME, id, ICON_PATH);
        this.mId = id;
        //mViewParametrs = String.format(XML_VIEW_PATTERN, mId);
       /* try {
           // mIcon = new ImageIcon(ImageIO.read(getClass().getResource("ICON_PATH")));

        }catch (IOException ignored) {}*/
    }



}
