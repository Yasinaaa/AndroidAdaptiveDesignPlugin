package ru.itis.androidplugin.view;

import ru.itis.androidplugin.android.values.Drawables;
import ru.itis.androidplugin.android.values.Styles;
import ru.itis.androidplugin.generator.xml.XmlChanger;
import ru.itis.androidplugin.generator.xml.XmlGenerator;
import ru.itis.androidplugin.interfaces.ToolbarInterface;
import ru.itis.androidplugin.presenters.ToolbarPresenter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by yasina on 04.05.17.
 */
public class MaterialToolbar extends MaterialItem implements ToolbarInterface{

    private static final String EMPTY = "toolbar1";

    public static final String XML_TOOLBAR =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<menu xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                    "      xmlns:app=\"http://schemas.android.com/apk/res-auto\">\n";
    public static final String XML_TOOLBAR_END ="</menu>";

    private final String ACTION_SEARCH =
            "    <item\n"+
                    "android:id=\"@+id/action_search\"\n"+
                    "android:title=\"@android:string/search_go\"\n"+
                    "android:icon=\"@drawable/icon_toolbar_search\"\n"+
                    "app:showAsAction=\"always|collapseActionView\"\n"+
                    "app:actionViewClass=\"android.support.v7.widget.SearchView\" />\n";
    private final String ACTION_EDIT =
            "    <item\n"+
            "        android:id=\"@+id/action_edit\"\n"+
            "        android:title=\"@string/edit\"\n"+
            "        android:icon=\"@drawable/icon_toolbar_edit\"\n"+
            "        app:showAsAction=\"ifRoom\" />\n";
    private final String ACTION_MICRO =
            "    <item\n"+
            "        android:id=\"@+id/action_micro\"\n"+
            "        android:title=\"@string/microphone\"\n"+
            "        android:icon=\"@drawable/icon_toolbar_micro\"\n"+
            "        yourapp:showAsAction=\"always\" />\n";
    private final String ACTION_REMOVE =
            "    <item\n"+
            "        android:id=\"@+id/action_remove\"\n"+
            "        android:title=\"@string/remove\"\n"+
            "        android:icon=\"@drawable/icon_toolbar_remove\"\n"+
            "        yourapp:showAsAction=\"always\" />\n";

    public static final String ICON_PATH = "/icons/toolbar.png";
    public static final String VIEW_NAME = "Toolbar";

    private MainView mainView;
    private ToolbarPresenter toolbarPresenter;
    private XmlGenerator layoutGenerator;

    private String recyclerViewID;
    //, navigationViewID;

    public MaterialToolbar() {
        super(VIEW_NAME, null, ICON_PATH);
        mId = EMPTY;
    }

    @Override
    public void setView() {
        mainView = MainView.mainView;
        toolbarPresenter = new ToolbarPresenter(this);
        layoutGenerator = new XmlGenerator();
        VisibleInvisible.toolbarState(mainView);
        setAllValues();
    }

    @Override
    public void onAddToLayoutClickListener(){
        try {
            setViewParameters();
            // add drawables
            XmlChanger.insertInLayoutTop("  app:contains=\"@menu/menu_" + mId + "\"\n"
                    + "  app:toolbar_type=\"" + mStyle + "\"");

            layoutGenerator.insertNewLayout(mViewParametrs,
                    "/menu/menu_" + mId + ".xml");

        }catch (java.io.IOException e){
        }
    }

    @Override
    public void setViewParameters(){
        getAllValues();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(XML_TOOLBAR);
        toolbarPresenter.setChoosedType(mStyle, stringBuilder);
        stringBuilder.append(XML_TOOLBAR_END);
        mViewParametrs = stringBuilder.toString();

    }

    public void setAllValues() {
        mainView.titleParentViewJLabel.setText("Toolbar ID");
        mainView.titleMaterialItemJLabel.setText("Title");
        mainView.typeJLabel.setText("Type");
        setTypesToComboBox(mainView.typeJComboBox);

        toolbarPresenter.setParameters(STANDARD_TYPE);
        mainView.typeJComboBox.getMouseListeners()[0] = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toolbarPresenter.setParameters(mainView.typeJComboBox.getSelectedItem().toString());
            }
        };
    }

    public void getAllValues() {
        mStyle = mainView.typeJComboBox.getSelectedItem().toString();
        toolbarPresenter.getParameters(mStyle);
        mId = mainView.itemParentViewJTextField.getText().toString();
    }


    @Override
    public void setParametersStandardStyle(){
        VisibleInvisible.setStandardToolbar(mainView);
        mainView.title4JLabel.setText("RecyclerView ID");
        toolbarPresenter.setAllParentIDs(mainView.jComboBox2);
    }

    @Override
    public void getParametersStandardStyle(){
        try{
            recyclerViewID = mainView.jComboBox2.getSelectedItem().toString();
        }catch (java.lang.NullPointerException e){
            recyclerViewID = null;
        }
        /*try{
            navigationViewID = mainView.jComboBox3.getSelectedItem().toString();
        }catch (java.lang.NullPointerException e){
            navigationViewID = null;
        }*/


    }

    @Override
    public void generateStandardToolbar(StringBuilder stringBuilder) {
        stringBuilder.append(ACTION_SEARCH);
        stringBuilder.append(ACTION_EDIT);
    }

    @Override
    public void setParametersSearchStyle(){}
    @Override
    public void getParametersSearchStyle(){}

    @Override
    public void setParametersRemoveStyle(){}
    @Override
    public void getParametersRemoveStyle(){}

    @Override
    public void setParametersSortStyle(){}
    @Override
    public void getParametersSortStyle(){}

    @Override
    public void setParametersExtendedStyle(){}
    @Override
    public void getParametersExtendedStyle(){}


    @Override
    public void generateSearchToolbar(StringBuilder stringBuilder) {
        stringBuilder.append(ACTION_SEARCH);
        stringBuilder.append(ACTION_REMOVE);
        stringBuilder.append(ACTION_MICRO);
    }

    @Override
    public void generateRemoveToolbar(StringBuilder stringBuilder) {
        stringBuilder.append(ACTION_SEARCH);
        stringBuilder.append(ACTION_EDIT);
    }

    @Override
    public void generateSortToolbar(StringBuilder stringBuilder) {

    }

    @Override
    public void generateExtendedToolbar(StringBuilder stringBuilder) {
        stringBuilder.append(ACTION_SEARCH);
        stringBuilder.append(ACTION_EDIT);
    }


    private void setTypesToComboBox(JComboBox jComboBox){
        for(String type: ALL_TYPES) {
            jComboBox.addItem(type);
        }
    }
}
