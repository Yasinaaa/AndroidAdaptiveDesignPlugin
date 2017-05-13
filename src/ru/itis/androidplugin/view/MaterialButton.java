package ru.itis.androidplugin.view;

import ru.itis.androidplugin.android.values.Drawables;
import ru.itis.androidplugin.android.values.Strings;
import ru.itis.androidplugin.generator.xml.XmlChanger;
import ru.itis.androidplugin.interfaces.ButtonInterface;
import ru.itis.androidplugin.presenters.ButtonPresenter;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialButton extends MaterialItem implements ButtonInterface {

    private static final String EMPTY = "materialButton1";
    public static final String XML_VIEW_PATTERN =
            "<Button\n" +
            "            android:layout_width=\"wrap_content\"\n" +
            "            android:layout_height=\"wrap_content\"\n" +
            "            android:id=\"@+id/%s\"";
    public static final String TEXT_BUTTON = "            android:text=\"@string/%s\"\n";
    public static final String BACKGROUND = "            android:background=\"@drawable/%s\"\n";
    public static final String LAYOUT_BELOW = "            android:layout_below=\"@id/%s\"\n";
    public static final String LAYOUT_MARGIN = "            android:layout_margin=\"%s\"\n";
    public static final String MIN_VALUES =
            "            android:minHeight=\"@dimen/material_button_height_minimum\"\n" +
            "            android:minWidth=\"@dimen/material_button_width_minimum\"\n";
    public static final String HORIZONTALL_PADDINGS =
            "            android:paddingRight=\"@dimen/material_button_padding_horizontal\"\n" +
            "            android:paddingLeft=\"@dimen/material_button_padding_horizontal\"\n";
    public static final String TEXT_PARAMETERS =
            "            android:textSize=\"@dimen/material_typography_regular_button_text_size\"\n" +
            "            android:textColor=\"@color/%s\"";
    public static final String ELEVATION = "            android:elevation=\"@dimen/material_button_flat_elevation\"\n";
    public static final String SHADOW_COLOR = "            android:shadowColor=\"@color/btn_raised_shadow_color\"\n";
    public static final String XML_END = "/>\n";
    public static final String TEXT_COLOR_DARK = "material_typography_primary_text_color_dark";
    public static final String TEXT_COLOR_LIGHT = "material_typography_primary_text_color_light";
    public static final String BORDERLESS_STYLE = "style=\"@style/Widget.AppCompat.Button.Borderless\"\n";

    public static final String BTN_RAISE = "btn_raise";
    public static final String BTN_FLAT = "btn_flat";


    public static final String ICON_PATH = "/icons/button.png";
    public static final String VIEW_NAME = "Button";
    public static final String RAISED_BUTTON = "Raised button";
    public static final String FLAT_BUTTON = "Flat button";
    public static final String[] STYLES = new String[]{
      RAISED_BUTTON, FLAT_BUTTON
    };
    private String theme, parentId, margin, text;
    private MainView mainView;
    private ButtonPresenter buttonPresenter;
    private Drawables drawables;


    public MaterialButton(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        mId = EMPTY;
    }

    @Override
    public void setView() {
        this.mainView = MainView.mainView;
        this.buttonPresenter = new ButtonPresenter(this);
        drawables = new Drawables();
        VisibleInvisible.buttonState(mainView);
        setAllValues();
    }

    @Override
    public String setRaisedButton() {
        StringBuilder stringBuilder = new StringBuilder();
        if(text != null){
            Strings.addLine(text);
            stringBuilder.append(String.format(TEXT_BUTTON, text));
        }

        if(theme != null) {
            if (!mainView.isRaisedButtonDrawablesAdded){
                drawables.addButtonDrawables(RAISED_BUTTON, theme);
                mainView.isRaisedButtonDrawablesAdded = true;
            }
            stringBuilder.append(buttonPresenter.setTextColorByChoosedTheme(theme));
        }

        stringBuilder.append(String.format(BACKGROUND, BTN_RAISE));
        if(parentId != null){
            stringBuilder.append(String.format(LAYOUT_BELOW, parentId));
        }
        if (margin != null){
            stringBuilder.append(String.format(LAYOUT_MARGIN, margin));
        }
        stringBuilder.append(MIN_VALUES);
        stringBuilder.append(HORIZONTALL_PADDINGS);
        stringBuilder.append(ELEVATION);
        stringBuilder.append(SHADOW_COLOR);
        stringBuilder.append(BORDERLESS_STYLE);
        stringBuilder.append(XML_END);
        return stringBuilder.toString();
    }

    @Override
    public String setFlatButton() {
        StringBuilder stringBuilder = new StringBuilder();
        if(text != null){
            Strings.addLine(text);
            stringBuilder.append(String.format(TEXT_BUTTON, text));
        }

        if(theme != null) {
            if (!mainView.isFlatButtonDrawablesAdded){
                drawables.addButtonDrawables(FLAT_BUTTON, theme);
                mainView.isRaisedButtonDrawablesAdded = true;
            }
            stringBuilder.append(buttonPresenter.setTextColorByChoosedTheme(theme));
        }

        stringBuilder.append(String.format(BACKGROUND, BTN_FLAT));
        if(parentId != null){
            stringBuilder.append(String.format(LAYOUT_BELOW, parentId));
        }
        if (margin != null){
            stringBuilder.append(String.format(LAYOUT_MARGIN, margin));
        }
        stringBuilder.append(MIN_VALUES);
        stringBuilder.append(HORIZONTALL_PADDINGS);
        stringBuilder.append(ELEVATION);
        stringBuilder.append(SHADOW_COLOR);
        stringBuilder.append(XML_END);
        return stringBuilder.toString();
    }

    @Override
    public String setDarkColor() {
        return String.format(TEXT_PARAMETERS, TEXT_COLOR_DARK);
    }

    @Override
    public String setLightColor() {
        return String.format(TEXT_PARAMETERS, TEXT_COLOR_LIGHT);
    }

    @Override
    public void onAddToLayoutClickListener(){
        try {
            setViewParameters();
            XmlChanger.insertInEditor(mViewParametrs);

        }catch (java.io.IOException e){
        }
    }

    @Override
    public void setViewParameters(){
        getAllValues();
        mViewParametrs = buttonPresenter.setTextByChoosedStyle(mStyle);
    }

    public void setAllValues() {
        mainView.titleParentViewJLabel.setText("Button ID");
        mainView.typeJLabel.setText("Style");
        mainView.titleMaterialItemJLabel.setText("Text");
        mainView.title4JLabel.setText("Theme");
        mainView.title5JLabel.setText("Layout below");
        mainView.emptyItemJLabel.setText("Margin");
        mainView.emptyItemLayoutJTextField.setText("10dp");

        //mType = mainView.invisibleJLabel.getText();
        setStyles();
        setThemes(mainView.jComboBox2);
        buttonPresenter.setAllElementIds(mainView.jComboBox3);
    }

    public void getAllValues() {
        mId = mainView.itemParentViewJTextField.getText();
        mParentItemName = PluginProject.getCurrentVirtualFile().getCanonicalPath();
        mStyle = mainView.typeJComboBox.getSelectedItem().toString();
        theme = mainView.jComboBox2.getSelectedItem().toString();
        margin = mainView.emptyItemLayoutJTextField.getText();
        text = mainView.itemMaterialItemJTextField.getText();
        String temp = mainView.jComboBox3.getSelectedItem().toString();
        if(!temp.equals("")){
            parentId = temp;
        }
    }

    public void setStyles(){
        for (String type : STYLES) {
            mainView.typeJComboBox.addItem(type);
        }
    }

    public void setThemes(JComboBox jComboBox){
        jComboBox.addItem("");
        for (String theme : Constants.THEMES) {
            jComboBox.addItem(theme);
        }
    }
}
