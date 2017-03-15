package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.view.MainView;

import javax.swing.*;

/**
 * Created by yasina on 04.02.17.
 */
public class MaterialTextView extends MaterialItem{

    private static final String EMPTY = "materialTextView1";
    public static final String XML_VIEW_PATTERN = "<TextView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        android:layout_gravity=\"center_vertical\"\n"+
            "        android:text=\"hi\"/>";
    private static final String ICON_PATH = "/icons/text_field.png";
    private static final String VIEW_NAME = "Text View";

    private String mId = EMPTY;
    private ImageIcon mIcon;
    private String mViewParametrs;


    public MaterialTextView() {
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    public void setViewParametrs(String mViewParametrs) {
        this.mViewParametrs = mViewParametrs;
    }

    @Override
    public void setView(MainView mainView) {

    }

}
