package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.view.RecyclerViewParameters;
import ru.itis.androidplugin.view.ViewParameters;

import javax.swing.*;
import java.awt.*;

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

    public MaterialRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }


    @Override
    public void setView(ViewParameters mSupplemementalViewParameter, JPanel jPanel){
        mSupplemementalViewParameter = new RecyclerViewParameters(this);
        jPanel.add(mSupplemementalViewParameter.mJPanel, BorderLayout.WEST);
    }

    @Override
    public String getViewParametrs() {
        return mViewParametrs;
    }

    public String getId() {
        return mId;
    }

}
