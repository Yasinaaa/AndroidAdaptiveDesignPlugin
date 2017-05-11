package ru.itis.androidplugin.view;

import ru.itis.androidplugin.generator.XmlChanger;
import ru.itis.androidplugin.interfaces.DividerTypes;
import ru.itis.androidplugin.presenters.DividerPresenter;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialDivider extends MaterialItem implements DividerTypes{

    private static final String EMPTY = "materialDivider1";
    public static final String ICON_PATH = "/icons/dividers.png";
    public static final String VIEW_NAME = "Dividers";
    public static final String XML_VIEW_PATTERN =
            "    <View\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"@dimen/material_divider_height\"\n" +
            "        android:id=\"@+id/%s\"\n";
    public static final String BACKGROUND = "android:background=\"%s\"";
    public static final String MARGIN_TOP = "android:layout_marginTop=\"%s\"\n";
    public static final String MARGIN_BOTTOM = "android:layout_marginBottom=\"%s\"\n";
    public static final String XML_END = "/>\n";

    //THEMES
    public static final String LIGHT_THEME = "@android:color/background_light";
    public static final String DARK_THEME = "@android:color/darker_gray";
    //THEMES

    //bottom sheet
    public static final String MARGIN_TOP_BOTTOM_SHEET = "@dimen/material_bottom_sheet_list_divider_margin_top";
    public static final String MARGIN_BOTTOM_BOTTOM_SHEET = "@dimen/material_bottom_sheet_list_divider_margin_bottom";
    //bottom sheet

    private MainView mainView;
    private DividerPresenter dividerPresenter;
    private String theme;
    public static final String USUAL_DIVIDER = "usual";
    public static final String BOTTOM_SHEET_DIVIDER = "bottom sheet item";
    private final String[] types = new String[]{USUAL_DIVIDER, BOTTOM_SHEET_DIVIDER};

    public MaterialDivider(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        mId = EMPTY;
    }

    @Override
    public void setView() {
        mainView = MainView.mainView;
        dividerPresenter = new DividerPresenter(this);
        VisibleInvisible.dividerState(mainView);
        setAllValues();
    }

    @Override
    public void onAddToLayoutClickListener(){
        try {
            setViewParameters();
            XmlChanger.addDimens(null, null);
            XmlChanger.insertInEditor(mViewParametrs);

        }catch (java.io.IOException e){
        }
    }

    @Override
    public void setViewParameters(){
        getAllValues();
        mViewParametrs = dividerPresenter.setTextByChoosedStyle(mStyle);
    }

    @Override
    public String setBottomSheetStyle() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(XML_VIEW_PATTERN, mId));
        stringBuilder.append(setBackground());
        stringBuilder.append(String.format(MARGIN_TOP, MARGIN_TOP_BOTTOM_SHEET));
        stringBuilder.append(String.format(MARGIN_BOTTOM, MARGIN_BOTTOM_BOTTOM_SHEET));
        stringBuilder.append(XML_END);
        return stringBuilder.toString();
    }

    @Override
    public String setUsualStyle() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(XML_VIEW_PATTERN, mId));
        stringBuilder.append(setBackground());
        stringBuilder.append(XML_END);
        return stringBuilder.toString();
    }

    public void setAllValues() {
        mainView.titleParentViewJLabel.setText("Divider ID");
        mainView.typeJLabel.setText("Style");
        mainView.title4JLabel.setText("Theme");
        mType = mainView.invisibleJLabel.getText();
        setStyles(mType, mainView.typeJComboBox);
        setThemes(mainView.jComboBox2);
    }

    public void getAllValues() {
        mId = mainView.itemParentViewJTextField.getText();
        mParentItemName = PluginProject.getCurrentVirtualFile().getCanonicalPath();
        mStyle = mainView.typeJComboBox.getSelectedItem().toString();
        theme = mainView.jComboBox2.getSelectedItem().toString();
    }

    public String setBackground(){
        String text = null;
        if(theme.equals(Constants.THEMES[0])){
            text = DARK_THEME;
        }
        if(theme.equals(Constants.THEMES[1])){
            text = LIGHT_THEME;
        }
        return String.format(BACKGROUND, text);
    }

    public void setStyles(String mType, JComboBox jComboBox){
        if(mType.equals(MaterialBottomSheets.VIEW_NAME))
        {
            jComboBox.addItem(BOTTOM_SHEET_DIVIDER);
        }else {
            for (String type : types) {
                jComboBox.addItem(type);
            }
        }
    }

    public void setThemes(JComboBox jComboBox){
        for (String theme : Constants.THEMES) {
            jComboBox.addItem(theme);
        }
    }
}
