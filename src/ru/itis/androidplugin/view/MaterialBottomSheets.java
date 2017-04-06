package ru.itis.androidplugin.view;

import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.generator.XmlChanger;
import ru.itis.androidplugin.generator.XmlGenerator;
import ru.itis.androidplugin.presenters.BottomSheetPresenterImpl;
import ru.itis.androidplugin.settings.Constants;

import javax.swing.*;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialBottomSheets extends MaterialItem {

    private static final String EMPTY = "bottomSheets1";
    public static final String XML_VIEW_PATTERN = "<include\n" +
            "        android:id=\"@+id/%s\"\n" +
            "        app:include_type=\"bottom_sheet\"" +
            "        layout=\"@layout/%s\" />";
    public static final String ICON_PATH = "/icons/bottom_sheets.png";
    public static final String VIEW_NAME = "Bottom Sheets";
    public static final String ITEM_BANNER =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
            "    android:layout_width=\"match_parent\"\n" +
            "    android:layout_height=\"300dp\"\n" +
            "    android:background=\"#d3d3d3\"\n" +
            "    app:behavior_hideable=\"true\"\n" +
            "    app:behavior_peekHeight=\"70dp\"\n" +
            "    app:bottom_sheet_style=\"%s\"\n" +
            "    app:layout_behavior=\"@string/bottom_sheet_behavior\">";
    public static final String ITEM_TITLE =
            "    <TextView\n" +
            "        android:id=\"@+id/title\"\n" +
            "        android:layout_width=\"wrap_content\"\n" +
            "        android:layout_height=\"wrap_content\"\n" +
            "        android:layout_margin=\"7dp\"\n" +
            "        android:text=\"%s\"\n" +
            "        android:textSize=\"18sp\" />";
    public static final String ITEM_END = "</RelativeLayout>";


    private MainView mainView;

    //view
    public String title;

    //view
    private BottomSheetPresenterImpl bottomSheetPresenter;
    private XmlGenerator xmlGenerator;

    public MaterialBottomSheets() {
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        mId = EMPTY;
    }

    public MaterialBottomSheets(String mId, String parent, String style, String title) {
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        this.mId = mId;
        this.mParentItemName = parent;
        this.mStyle = style;
        this.title = title;
    }

    @Override
    public void setView() {
        mainView = MainView.mainView;
        bottomSheetPresenter = new BottomSheetPresenterImpl(mainView, this);
        xmlGenerator = new XmlGenerator();
        VisibleInvisible.bottomSheetsState(mainView);
        bottomSheetPresenter.generateItemsLayoutTitle();
        bottomSheetPresenter.setAllValues();
        bottomSheetPresenter.setOrNoTitle();
    }



    @Override
    public void insertToLayoutOrNo(){
        try {
            setViewParameters();
            XmlChanger.changeXml(null, null, mViewParametrs);
            VisibleInvisible.bottomSheetIncludeCreated(mainView);

            String titleText = null;
            if(!title.isEmpty()){
                titleText = String.format(ITEM_TITLE, title);
            }

            VirtualFile virtualFile = xmlGenerator.insertNewLayout(new String[]
                    {String.format(ITEM_BANNER, mStyle), titleText, ITEM_END}, "/layout/" + mLayoutName + ".xml");
            bottomSheetPresenter.openLayout(virtualFile.getCanonicalPath());

        }catch (java.io.IOException e){

        }

    }

    @Override
    public void setViewParameters(){
        bottomSheetPresenter.getAllValues();
        String[] parameters = new String[]{mId, mLayoutName};
        mViewParametrs = String.format(mPattern, parameters);
    }
}