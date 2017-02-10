package ru.itis.androidplugin.elements;

import javax.swing.*;

/**
 * Created by yasina on 04.02.17.
 */
public class MaterialTextView extends MaterialItem{

    public static final String XML_VIEW_PATTERN = "<TextView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        android:layout_gravity=\"center_vertical\"\n"+
            "         android:text=\"hi\"/>";
    public static final String ICON_PATH = "/icons/ic_recyclerview.png";
    public static final String VIEW_NAME = "RecyclerView";

    private String mId;
    private ImageIcon mIcon;
    private String mViewParametrs;

    public MaterialTextView(String id) {
        super(VIEW_NAME, id, ICON_PATH);
        this.mId = id;
    }



  /*  private String capitalize(String str) {
        String[] words = str.split("_");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            ret.append(Character.toUpperCase(words[i].charAt(0)));
            ret.append(words[i].substring(1));
            if (i < words.length - 1) {
                ret.append(' ');
            }
        }
        return ret.toString();
    }

    @Override
    public String toString() {
        return fixedName;
    }*/
}
