package ru.itis.androidplugin.view;

import ru.itis.androidplugin.presenters.CommonFunctions;

import javax.swing.*;

/**
 * Created by yasina on 04.02.17.
 */
public class MaterialTextView extends MaterialItem{

    private static final String EMPTY = "materialTextView1";
    //wrap_content
    public static final String XML_VIEW_PATTERN =
            "<TextView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"%s\"\n"+
            "        android:layout_height=\"%s\"\n";
    public static final String GRAVITY_CENTER = "        android:gravity=\"center\"";
    public static final String DRAWABLE =
    "        android:drawableLeft=\"@drawable/%s\"" +
    "        android:drawablePadding=\"@dimen/material_bottom_sheet_list_item_label_padding_start\"\n";
    public static final String TEXT = "        android:text=\"%s\"";
    public static final String TEXT_SIZE = "        android:textSize=\"@dimen/%s\"";
    public static final String TEXT_COLOR = "        android:textColor=\"@color/%s\"";
    public static final String LAYOUT_BELOW = "        android:layout_below=\"@id/%s\"";
    public static final String XML_END = "/>\n";

    private static final String ICON_PATH = "/icons/text_field.png";
    private static final String VIEW_NAME = "Text View";

    private MainView mainView;


    public MaterialTextView() {
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        mId = EMPTY;
    }

    public void setViewParametrs(String mViewParametrs) {
        this.mViewParametrs = mViewParametrs;
    }

    @Override
    public void setView() {
        mainView = MainView.mainView;
        VisibleInvisible.textViewState(mainView);

        mainView.titleParentViewJLabel.setText("Text View ID");
        mainView.typeJLabel.setText("Style");
        mainView.titleMaterialItemJLabel.setText("Text");

    }

    @Override
    public void parentOptions() {
        if(mParentItem.mViewName.equals(MaterialBottomSheets.VIEW_NAME)){
            CommonFunctions.setTextToLabel(mainView.emptyItemJLabel, "Icon");
        }
    }
}
