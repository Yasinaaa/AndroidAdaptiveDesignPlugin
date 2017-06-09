package ru.itis.androidplugin.view;

import ru.itis.androidplugin.android.values.Strings;
import ru.itis.androidplugin.generator.xml.XmlChanger;
import ru.itis.androidplugin.interfaces.CardsInterface;
import ru.itis.androidplugin.presenters.CardsPresenter;
import ru.itis.androidplugin.settings.Constants;

import javax.swing.*;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialCards extends MaterialItem implements CardsInterface {

    private static final String EMPTY = "materialRecyclerView1";
    public static final String XML_VIEW_PATTERN =
            "      <android.support.v7.widget.CardView\n" +
            "            android:id=\"@+id/%s\"\n" +
            "            android:layout_gravity=\"center\"\n" +
            "            android:layout_width=\"match_parent\"\n" +
            "            android:layout_height=\"@dimen/material_card_title_block_small_height\"\n" +
            "            app:cardCornerRadius=\"@dimen/card_corner_radius\">";
    public static final String CARD_STANDART = "          <TextView\n" +
            "                  android:id=\"@+id/%s\"\n" +
            "                  android:text=\"@string/%s\"\n" +
            "                  android:layout_width=\"match_parent\"\n" +
            "                  android:layout_height=\"match_parent\"\n" +
            "                  android:paddingLeft=\"@dimen/material_card_title_block_padding_horizontal\"\n" +
            "                  android:paddingRight=\"@dimen/material_card_title_block_padding_horizontal\"\n" +
            "                  android:paddingTop=\"@dimen/material_card_title_block_small_padding_vertical\"\n" +
            "                  android:paddingBottom=\"@dimen/material_card_title_block_small_padding_vertical\"\n" +
            "                  android:drawableRight=\"@drawable/%s\"\n" +
            "                  android:drawablePadding=\"@dimen/material_bottom_sheet_list_item_label_padding_start\"\n" +
            "          />";
    public static final String ICON_PATH = "/icons/cards.png";
    public static final String VIEW_NAME = "Cards";

    private String mId = EMPTY;
    private MainView mainView;
    private CardsPresenter cardsPresenter;
    private StringBuilder xmlCodeBuilder;

    public MaterialCards(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    @Override
    public void setView() {
        mainView = MainView.mainView;
        cardsPresenter = new CardsPresenter(this);
        VisibleInvisible.cardState(mainView);
        setValues();
    }

    private void setValues(){
        setTypesToComboBox(mainView.typeJComboBox);
        mainView.emptyItemJLabel.setText("Title");
        mainView.titleLoadingJLabel.setText("Text");
        cardsPresenter.setAllDrawablesList(mainView.jComboBox2);
    }

    @Override
    public void onAddToLayoutClickListener(){
        try {
            setViewParameters();
            XmlChanger.addDimens(Constants.CARD_CORNER_RADIUS);
            XmlChanger.insertInEditor(mViewParametrs);

        }catch (java.io.IOException e){}

    }

    @Override
    public void setViewParameters(){
        getAllValues();
        xmlCodeBuilder = new StringBuilder();
        xmlCodeBuilder.append(String.format(XML_VIEW_PATTERN, mId));

        //mViewParametrs = xmlCodeBuilder.toString();
    }

    public void getAllValues() {
        mStyle = mainView.typeJComboBox.getSelectedItem().toString();
        mId = mainView.itemParentViewJTextField.getText().toString();

        cardsPresenter.getParameters(mStyle);
    }

    @Override
    public void setParametersStandardStyle() {

    }

    @Override
    public void setParametersCommentStyle() {

    }

    @Override
    public void setParametersAdditionalInfoStyle() {

    }

    @Override
    public void getParametersStandardStyle() {
        mainView.emptyItemLayoutJTextField.getText();
        Strings.addLine(mainView.emptyItemLayoutJTextField.getText() + "\n" +
                mainView.loadingItemLayoutJTextField.getText());
        xmlCodeBuilder.append(String.format(XML_VIEW_PATTERN, new String[]{"standard_" + mId}));
    }

    @Override
    public void getParametersCommentStyle() {

    }

    @Override
    public void getParametersAdditionalInfoStyle() {

    }


    private void setTypesToComboBox(JComboBox jComboBox){
        for(String type: ALL_TYPES) {
            jComboBox.addItem(type);
        }
    }


}
