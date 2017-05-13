package ru.itis.androidplugin.view;

import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.android.values.Strings;
import ru.itis.androidplugin.generator.xml.XmlChanger;
import ru.itis.androidplugin.generator.xml.XmlGenerator;
import ru.itis.androidplugin.presenters.BottomSheetPresenter;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

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
    public static final String ITEM_BANNER_LIST_STYLE =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                    "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
                    "    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
                    "    android:layout_width=\"match_parent\"\n" +
                    "    android:layout_height=\"wrap_content\"\n" +
                    "    android:elevation=\"@dimen/material_bottom_sheet_elevation\"\n" +
                    "    android:background=\"@color/material_bottom_sheet_background_fill\"\n" +
                    "    app:behavior_hideable=\"true\"\n" +
                    "    app:behavior_peekHeight=\"@dimen/bottom_sheet_peek_height\"\n" +
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
    public static final String ITEM_BANNER_GRID_STYLE =
            "<GridLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
            "    android:layout_width=\"match_parent\"\n" +
            "    android:layout_height=\"wrap_content\"\n" +
            "    android:columnCount=\"@dimen/bottom_sheet_column_count\"\n" +
            "    android:paddingRight=\"@dimen/material_bottom_sheet_grid_padding_horizontal\"\n" +
            "    android:paddingBottom=\"@dimen/material_bottom_sheet_grid_padding_bottom\"\n" +
            "    android:orientation=\"horizontal\"\n" +
            "    app:layout_behavior=\"@string/bottom_sheet_behavior\"\n" +
            "    android:elevation=\"@dimen/material_bottom_sheet_elevation\"\n" +
            "    android:background=\"@color/material_bottom_sheet_background_fill\"\n" +
            "    app:behavior_hideable=\"true\"\n" +
            "    app:behavior_peekHeight=\"@dimen/bottom_sheet_peek_height\">";
    public static final String ITEM_END_LIST_STYLE = "\n</LinearLayout>";
    public static final String ITEM_END_GRID_STYLE = "\n</GridLayout>";
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

        //temp
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openItemLayoutJLabel.setVisible(false);
        mainView.removeEmptyLayoutJLabel.setVisible(false);
        mainView.removeLoadingLayoutJLabel.setVisible(false);
        mainView.openEmptyLayoutJLabel.setVisible(false);
        mainView.openLoadingLayoutJLabel.setVisible(false);
        mainView.addNewItemJLabel.setVisible(false);
        //temp
    }

    @Override
    public void onAddToLayoutClickListener(){
        try {
            setViewParameters();
            addBottomSheetToLayout();
            VisibleInvisible.bottomSheetIncludeCreated(mainView);


            VirtualFile virtualFile = xmlGenerator.insertNewLayout(insertStyle(),
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

    public void addBottomSheetToLayout() throws IOException {
        switch (mStyle) {
            case "list_style":
                XmlChanger.addDimens(Constants.BOTTOM_SHEET_PEEK_HEIGHT);
                break;
            case "grid_style":
                XmlChanger.addDimens(new String[]{Constants.BOTTOM_SHEET_PEEK_HEIGHT,
                Constants.BOTTOM_SHEET_COLUMN_COUNT});
                break;
            default:
                break;
        }
        XmlChanger.insertInEditor(mViewParametrs);
    }

    public String insertStyle(){
        StringBuilder stringBuilder = new StringBuilder();
        switch (mStyle){
            case "list_style":
                stringBuilder.append(String.format(ITEM_BANNER_LIST_STYLE, mStyle));

                if(title != null){
                    String new_string = "title_" + title;
                    String titleText = String.format(ITEM_TITLE, new String[]{new_string, title});
                    Strings.addLine(title);
                    stringBuilder.append(titleText);
                }
                stringBuilder.append(ITEM_END_LIST_STYLE);
                return stringBuilder.toString();

            case "grid_style":
                stringBuilder.append(String.format(ITEM_BANNER_GRID_STYLE, mStyle));
                stringBuilder.append(ITEM_END_GRID_STYLE);
                return stringBuilder.toString();
            default:
                return null;
        }
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
        if(mainView.emptyItemLayoutJTextField.isEnabled() || mainView.emptyItemLayoutJTextField.isVisible()){
            title = mainView.itemMaterialItemJTextField.getText();
        }
    }

    @Override
    public void onJComboBox1SelectedItemListener(){

        String selectedItem = mainView.typeJComboBox.getSelectedItem().toString();
        if(selectedItem.equals("list_style")){
            mainView.emptyItemJLabel.setVisible(true);
            mainView.emptyItemLayoutJTextField.setVisible(true);
            mainView.removeEmptyLayoutJLabel.setVisible(true);
        }
        if(selectedItem.equals("grid_style")) {
            mainView.emptyItemJLabel.setVisible(false);
            mainView.emptyItemLayoutJTextField.setVisible(false);
            mainView.removeEmptyLayoutJLabel.setVisible(false);
        }
    }

}