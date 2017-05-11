package ru.itis.androidplugin.view;

import ru.itis.androidplugin.android.values.Drawables;
import ru.itis.androidplugin.android.values.Strings;
import ru.itis.androidplugin.android.values.Styles;
import ru.itis.androidplugin.generator.XmlChanger;
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
    public static final String XML_VIEW_PATTERN =
            "<include " +
            "        layout=\"@layout/%s\" " +
            "        toolbar_type=\"%s\" />" +
            "\n";
    public static final String XML_END = "    </android.support.design.widget.AppBarLayout>\n";

    public static final String ICON_PATH = "/icons/toolbar.png";
    public static final String VIEW_NAME = "Toolbar";

    private MainView mainView;
    private ToolbarPresenter toolbarPresenter;

    private String recyclerViewID, navigationViewID;

    public MaterialToolbar() {
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        mId = EMPTY;
    }

    @Override
    public void setView() {
        mainView = MainView.mainView;
        toolbarPresenter = new ToolbarPresenter(this);
        VisibleInvisible.toolbarState(mainView);
        setAllValues();
    }

    @Override
    public void onAddToLayoutClickListener(){
        try {
            setViewParameters();
            // add drawables
            XmlChanger.insertInEditor(mViewParametrs);

        }catch (java.io.IOException e){
        }
    }

    @Override
    public void setViewParameters(){
        getAllValues();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(XML_VIEW_PATTERN, new String[]{mId, mStyle}));
        //stringBuilder.append(XML_END);
        mViewParametrs = stringBuilder.toString();
        toolbarPresenter.setChoosedType(mStyle);
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
    }


    @Override
    public void setParametersStandardStyle(){
        VisibleInvisible.setStandardToolbar(mainView);
        mainView.title4JLabel.setText("RecyclerView ID");
        mainView.title5JLabel.setText("NavigationView ID");
        toolbarPresenter.setAllParentIDs(mainView.jComboBox2, mainView.jComboBox3);
    }

    @Override
    public void getParametersStandardStyle(){
        try{
            navigationViewID = mainView.jComboBox3.getSelectedItem().toString();
        }catch (java.lang.NullPointerException e){
            recyclerViewID = null;
        }
        try{
            navigationViewID = mainView.jComboBox3.getSelectedItem().toString();
        }catch (java.lang.NullPointerException e){
            navigationViewID = null;
        }

    }

    @Override
    public void generateStandardToolbar() {
        // add drawables
        Drawables drawables = new Drawables();
        drawables.addDrawablesForToolbar();

        // add to styles.xml
        Styles.addToolbarStyle();
        // create menu xml
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
    public void generateSearchToolbar() {

    }

    @Override
    public void generateRemoveToolbar() {

    }

    @Override
    public void generateSortToolbar() {

    }

    @Override
    public void generateExtendedToolbar() {

    }


    private void setTypesToComboBox(JComboBox jComboBox){
        for(String type: ALL_TYPES) {
            jComboBox.addItem(type);
        }
    }
}
