package ru.itis.androidplugin.view;

import ru.itis.androidplugin.generator.XmlChanger;
import ru.itis.androidplugin.interfaces.FloatingActionButtonTypes;
import ru.itis.androidplugin.presenters.FloatingActionButtonPresenter;
import ru.itis.androidplugin.settings.PluginProject;

import java.awt.event.*;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialFloatingActionButton extends MaterialItem implements FloatingActionButtonTypes {

    private static final String EMPTY = "fab1";
    public static final String XML_VIEW_PATTERN =
            "<android.support.design.widget.FloatingActionButton\n" +
            "        android:id=\"@+id/%s\"\n" +
            "        android:layout_width=\"wrap_content\"\n" +
            "        android:layout_height=\"wrap_content\"\n";
    //todo: if it's not @drawable, but something as @android:drawable/ ???
    public static final String SRC = "android:srcCompat=\"@drawable/%s\"\n";
    public static final String ANCHOR = "app:layout_anchor=\"@id/%s\"\n";
    public static final String ANCHOR_GRAVITY = "app:layout_anchorGravity=\"%s\"\n";
    public static final String XML_END = "android:layout_margin=\"@dimen/material_button_fab_edge_margin\"/>\n";
    public static final String ICON_PATH = "/icons/fab.png";
    public static final String VIEW_NAME = "Floating Action Button";

    //bottom sheet style
    public static final String ANCHOR_GRAVITY_BOTTOM_SHEET = "top|right";
    //bottom sheet style

    //usual style
    public static final String USUAL_GRAVITY = "android:layout_gravity=\"bottom|end\"\n";
    //usual style

    private MainView mainView;
    private FloatingActionButtonPresenter floatingActionButtonPresenter;
    private String iconName, parentId;

    public MaterialFloatingActionButton(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        mId = EMPTY;
    }

    @Override
    public void setView() {
        this.mainView = MainView.mainView;
        this.floatingActionButtonPresenter = new FloatingActionButtonPresenter(this);
        VisibleInvisible.floatingActionButtonState(mainView);
        setAllValues();
    }

    @Override
    public void onJComboBox1SelectedItemListener(){
        mStyle = mainView.typeJComboBox.getSelectedItem().toString();
        floatingActionButtonPresenter.setParametersByChoosedStyle(mStyle);
    }

    @Override
    public void setUsualOptions() {
        mainView.title5JLabel.setVisible(false);
        mainView.jComboBox3.setVisible(false);
    }

    @Override
    public void setBottomSheetToolbarOptions() {
        mainView.title5JLabel.setVisible(true);
        mainView.jComboBox3.setVisible(true);
        floatingActionButtonPresenter.setAllElementIds(mainView.jComboBox3);
    }

    @Override
    public String setBottomSheetStyle() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format(XML_VIEW_PATTERN, mId));

        if (iconName != null){
            stringBuilder.append(String.format(SRC, iconName));
        }
        if(parentId != null){
            stringBuilder.append(String.format(ANCHOR, parentId));
        }
        stringBuilder.append(String.format(ANCHOR_GRAVITY, ANCHOR_GRAVITY_BOTTOM_SHEET));
        stringBuilder.append(XML_END);
        return stringBuilder.toString();
    }

    @Override
    public String setToolbarStyle() {
        return null;
    }



    @Override
    public String setUsualStyle() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format(XML_VIEW_PATTERN, mId));

        if (iconName != null){
            stringBuilder.append(String.format(SRC, iconName));
        }

        stringBuilder.append(USUAL_GRAVITY);
        stringBuilder.append(XML_END);
        return stringBuilder.toString();
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
        mViewParametrs = floatingActionButtonPresenter.setTextByChoosedStyle(mStyle);
    }


    public void setAllValues() {
        mainView.titleParentViewJLabel.setText("FAB ID");
        mainView.typeJLabel.setText("Style");
        mainView.title4JLabel.setText("Icon");
        mainView.emptyItemJLabel.setText("Parent ID");
        floatingActionButtonPresenter.setStyles(mainView.typeJComboBox);
        mainView.openIconJLabel.getMouseListeners()[0] = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                floatingActionButtonPresenter.chooseIcon(mainView.typeJComboBox);
            }
        };
        floatingActionButtonPresenter.setAllDrawablesList(mainView.jComboBox2);
    }

    public void getAllValues() {
        mId = mainView.itemParentViewJTextField.getText();
        mParentItemName = PluginProject.getCurrentVirtualFile().getCanonicalPath();
        iconName = mainView.jComboBox2.getSelectedItem().toString();
        mStyle = mainView.typeJComboBox.getSelectedItem().toString();
        if(mainView.jComboBox3.isVisible()){
            parentId = mainView.jComboBox3.getSelectedItem().toString();
        }
    }
}
