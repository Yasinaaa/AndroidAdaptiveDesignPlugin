package ru.itis.androidplugin.view;

import b.g.i.S;
import ru.itis.androidplugin.android.values.Strings;
import ru.itis.androidplugin.generator.XmlChanger;
import ru.itis.androidplugin.interfaces.TextViewTypes;
import ru.itis.androidplugin.presenters.TextViewPresenter;
import ru.itis.androidplugin.settings.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by yasina on 04.02.17.
 */
public class MaterialTextView extends MaterialItem implements TextViewTypes{

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
    public static final String TEXT = "        android:text=\"@string/%s\"";
    public static final String TEXT_SIZE = "        android:textSize=\"@dimen/%s\"";
    public static final String TEXT_COLOR = "        android:textColor=\"@color/%s\"";
    public static final String XML_END = "/>\n";

    //constants
    public static final String BOTTOM_SHEET_TEXT_SIZE = "@dimen/material_bottom_sheet_contents_text_size";
    public static final String BOTTOM_SHEET_TEXTVIEW_HEIGHT = "@dimen/material_bottom_sheet_list_header_height";
    public static final String BOTTOM_SHEET_TEXT_COLOR = "@color/material_bottom_sheet_title_text_color";
    //constants

    private static final String ICON_PATH = "/icons/text_field.png";
    private final String ICON_OPEN_PATH = "/icons/go.png";
    private static final String VIEW_NAME = "Text View";

    private MainView mainView;
    private TextViewPresenter textViewPresenter;
    private String titleText;
    private String iconName;


    public MaterialTextView() {
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        mId = EMPTY;
    }


    @Override
    public void setView() {
        mainView = MainView.mainView;
        VisibleInvisible.textViewState(mainView);
        mType = mainView.invisibleJLabel.getText();
        setAllValues();
        textViewPresenter = new TextViewPresenter(this);
        textViewPresenter.setParametersByChoosedStyle(mType);

    }

    private void setAllValues(){
        mainView.titleParentViewJLabel.setText("Text View ID");
        mainView.typeJLabel.setText("Style");
        mainView.titleMaterialItemJLabel.setText("Text");

    }

    @Override
    public void setUsualOptions(String[] types) {
        for (String type: types){
            mainView.typeJComboBox.addItem(type);
        }
    }

    @Override
    public void setBottomSheetOptions(String[] types) {

        for (String type: types){
            mainView.typeJComboBox.addItem(type);
        }

        try {
            ImageIcon mOpenIcon = new ImageIcon(ImageIO.read(getClass().getResource(ICON_OPEN_PATH)));
            textViewPresenter.setIconToLabel(mainView.removeEmptyLayoutJLabel, mOpenIcon);
            textViewPresenter.setAllDrawablesList(mainView.jComboBox2);
            mainView.openIconJLabel.getMouseListeners()[0] = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    textViewPresenter.chooseIcon(mainView.jComboBox2);
                }
            };
            mainView.removeItemLayoutJLabel.setVisible(true);
            mainView.openIconJLabel.setVisible(true);
            textViewPresenter.setRemoveButton(mainView.removeItemLayoutJLabel,
                    mainView.itemMaterialItemJTextField,
                    "");

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*if(mainView.invisibleJLabel.getText().equals(MaterialBottomSheets.VIEW_NAME)){

        }*/
    }
    @Override
    public void setViewParameters(){
        mId = mainView.itemParentViewJTextField.getText();
        mStyle = mainView.typeJComboBox.getSelectedItem().toString();


        mViewParametrs = textViewPresenter.setTextByChoosedStyle(mStyle);
    }

    @Override
    public void insertToLayoutOrNo(){
        try {
            setViewParameters();
            XmlChanger.changeXml(null, null, mViewParametrs);

        }catch (java.io.IOException e){

        }

    }
    @Override
    public String setBottomSheetTextView() {
        iconName = mainView.jComboBox2.getSelectedItem().toString().replaceAll(".xml","");

        if(mainView.itemMaterialItemJTextField.isEnabled()){
            titleText = mainView.itemMaterialItemJTextField.getText();
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format(XML_VIEW_PATTERN, new String[]{mId, Constants.WRAP_CONTENT,
                        BOTTOM_SHEET_TEXTVIEW_HEIGHT}));

        if (titleText != null){
            Strings.addLine(titleText);
            stringBuilder.append(String.format(TEXT, titleText));
        }
        stringBuilder.append(GRAVITY_CENTER);
        if(iconName != null){
            stringBuilder.append(String.format(DRAWABLE, iconName));
        }
        stringBuilder.append(String.format(TEXT_SIZE, BOTTOM_SHEET_TEXT_SIZE));
        stringBuilder.append(String.format(TEXT_COLOR, BOTTOM_SHEET_TEXT_COLOR));
        stringBuilder.append(XML_END);
        return stringBuilder.toString();
    }

    @Override
    public String setUsualTextView() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format(XML_VIEW_PATTERN, new String[]{mId, Constants.WRAP_CONTENT,
                        Constants.WRAP_CONTENT}));

        if (titleText != null){
            Strings.addLine(titleText);
            stringBuilder.append(String.format(TEXT, titleText));
        }
        stringBuilder.append(XML_END);
        return stringBuilder.toString();
    }

}
