package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.view.ViewParameters;

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
    private static final String ICON_PATH = "/icons/ic_recyclerview.png";
    private static final String VIEW_NAME = "TextView";

    private String mId = EMPTY;
    private ImageIcon mIcon;
    private String mViewParametrs;


    public MaterialTextView() {
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    @Override
    public String getViewParametrs() {
        return null;
    }

    public void setViewParametrs(String mViewParametrs) {
        this.mViewParametrs = mViewParametrs;
    }

    @Override
    public void setView(ViewParameters mSupplemementalViewParameter, JPanel jPanel) {
        //TODO: set view for TextView
    }
}
