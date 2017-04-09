package ru.itis.androidplugin.view;

import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.android.values.Strings;
import ru.itis.androidplugin.generator.XmlChanger;
import ru.itis.androidplugin.generator.XmlGenerator;
import ru.itis.androidplugin.presenters.BottomSheetPresenter;
import ru.itis.androidplugin.settings.PluginProject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialBottomSheets extends MaterialItem {

    private static final String EMPTY = "bottomSheets1";
    public static final String XML_VIEW_PATTERN = "<include\n" +
            "        android:id=\"@+id/%s\"\n" +
            "        layout=\"@layout/%s\" />";
    public static final String ICON_PATH = "/icons/bottom_sheets.png";
    public static final String VIEW_NAME = "bottom_sheet";
    //todo create dimen for app:behavior_peekHeight
    public static final String ITEM_BANNER =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                    "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
                    "    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
                    "    android:layout_width=\"match_parent\"\n" +
                    "    android:layout_height=\"wrap_content\"\n" +
                    "    android:elevation=\"@dimen/material_bottom_sheet_elevation\"\n" +
                    "    android:background=\"@color/material_bottom_sheet_background_fill\"\n" +
                    "    app:behavior_hideable=\"true\"\n" +
                    "    app:behavior_peekHeight=\"70dp\"\n" +
                    "    app:layout_behavior=\"@string/bottom_sheet_behavior\"\n" +
                    "    android:paddingLeft=\"@dimen/material_bottom_sheet_list_padding_horizontal\"\n" +
                    "    android:paddingRight=\"@dimen/material_bottom_sheet_list_padding_horizontal\"\n" +
                    "    android:paddingBottom=\"@dimen/material_bottom_sheet_list_padding_bottom\"\n" +
                    "    android:orientation=\"vertical\"\n" +
                    "    app:layout_type=\""+ VIEW_NAME + "\"> \n";
    public static final String ITEM_TITLE =
            "    <TextView\n" +
                    "        android:id=\"@+id/%s\"\n" +
                    "        android:layout_width=\"wrap_content\"\n" +
                    "        android:layout_height=\"@dimen/material_bottom_sheet_list_header_height\"\n" +
                    "        android:text=\"@string/%s\"\n" +
                    "        android:gravity=\"center\"\n" +
                    "        android:textColor=\"@color/material_bottom_sheet_title_text_color\"\n" +
                    "        android:textSize=\"@dimen/material_bottom_sheet_title_text_size\" />";
    public static final String ITEM_END = "\n</LinearLayout>";
    private final String ITEM_BEGIN_NAME = "bottom_sheet_";
    private final String[] bottomSheetsStyles = new String[]{
            "list_style", "grid_style"
    };

    private MainView mainView;

    //view
    public String title;

    //view
    private BottomSheetPresenter bottomSheetPresenter;
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
        bottomSheetPresenter = new BottomSheetPresenter(this);
        xmlGenerator = new XmlGenerator();
        VisibleInvisible.bottomSheetsState(mainView);
        bottomSheetPresenter.generateItemsLayoutTitle(ITEM_BEGIN_NAME, mainView.itemParentViewJTextField,
                mainView.itemMaterialItemJTextField);
        setAllValues();
        bottomSheetPresenter.setOrNoTitle(mainView.removeEmptyLayoutJLabel, mainView.emptyItemLayoutJTextField);
    }

    @Override
    public void insertToLayoutOrNo(){
        try {
            setViewParameters();
            XmlChanger.changeXml(null, null, mViewParametrs);
            VisibleInvisible.bottomSheetIncludeCreated(mainView);

            String titleText = null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format(ITEM_BANNER, mStyle));

            if(title != null){
                String new_string = "title_" + title;
                titleText = String.format(ITEM_TITLE, new String[]{new_string, title});
                Strings.addLine(title);
                stringBuilder.append(titleText);
            }
            stringBuilder.append(ITEM_END);

            VirtualFile virtualFile = xmlGenerator.insertNewLayout(stringBuilder.toString(),
                    "/layout/" + mLayoutName + ".xml");
            bottomSheetPresenter.openItemLayout(mainView.openItemLayoutJLabel,virtualFile.getCanonicalPath());

        }catch (java.io.IOException e){

        }

    }

    @Override
    public void setViewParameters(){
        getAllValues();
        String[] parameters = new String[]{mId, mLayoutName};
        mViewParametrs = String.format(mPattern, parameters);
    }

    public void setAllValues() {
        mainView.titleParentViewJLabel.setText("BottomSheet ID");
        mainView.titleMaterialItemJLabel.setText("Layout");
        mainView.typeJLabel.setText("Style");
        mainView.emptyItemJLabel.setText("Title");
        mainView.typeJComboBox.removeAllItems();
        for(String s: bottomSheetsStyles){
            mainView.typeJComboBox.addItem(s);
        }
        bottomSheetPresenter.setRemoveIcon(mainView.removeEmptyLayoutJLabel);
    }


    public void getAllValues() {
        mId = mainView.itemParentViewJTextField.getText();
        mParentItemName = PluginProject.getCurrentVirtualFile().getCanonicalPath();
        mLayoutName = mainView.itemMaterialItemJTextField.getText();
        mStyle = mainView.typeJComboBox.getSelectedItem().toString();
        if(mainView.emptyItemLayoutJTextField.isEnabled()){
            title = mainView.itemMaterialItemJTextField.getText();
        }
    }


}