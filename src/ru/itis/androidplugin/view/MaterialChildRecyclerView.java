package ru.itis.androidplugin.view;

import b.g.i.S;
import ru.itis.androidplugin.settings.FileOwner;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;

/**
 * Created by yasina on 15.03.17.
 */
public class MaterialChildRecyclerView extends MaterialItem{

    public static final String XML_VIEW_PATTERN = "<RecyclerView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        />";

    public static final String ICON_PATH = "/icons/recycler_view.png";
    public static final String VIEW_NAME = "Recycler View Child";
    public static final String[] mAllTypes = new String[]{ "item_", "empty_", "loading_"};

    public MaterialChildRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    public MaterialChildRecyclerView(String id, String attrType){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        //mId = path.substring(path.lastIndexOf("/") + 1, path.indexOf("."));
        mId = id;
        mType = setNormalType(attrType);
        //mLayoutPath = path;

        //todo get by id

    }

    @Override
    public void setView() {
        MainView mainView = MainView.mainView;
        VisibleInvisible.cleanState(mainView);

        /*String[] answer = FileOwner.FileOwnerListener.getOwner(PluginProject.mLayoutPath);
        mainView.itemActivityClassJLabel.setText(answer[0]);
        if (answer[1] != null) {
            mainView.titleParentIDJLabel.setVisible(true);
            mainView.itemParentIDJLabel.setVisible(true);
            mainView.itemParentIDJLabel.setText(answer[1]);
        }*/
        mainView.currentMaterialItemParametersJPanel.setVisible(false);
        /*mainView.itemParentViewJTextField.setVisible(false);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.addToLayoutButton.setVisible(false);
        mainView.itemMaterialItemJTextField.setEnabled(true);
        mainView.itemMaterialItemJTextField.setText(StringUtils.capitalize(parentRecyclerView.mId)+"Adapter");

        mainView.titleMaterialItemJLabel.setText("Adapter Class name");
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.itemParentViewJTextField.setEnabled(false);
        mainView.itemParentViewJTextField.setText(parentRecyclerView.mId);*/

        /*ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // generateClass = new GenerateClass();
                //generateClass.generateClass("LaHolder");
                ActivityInit activityInit = new ActivityInit();
                //todo get name from Dialog
                activityInit.insertNewClass("LaHolder");
                //mainView.saveLayoutButton.removeActionListener(this);
            }
        };
        mainView.saveLayoutButton.setText("Save layout & Create Adapter");
        mainView.saveLayoutButton.addActionListener(actionListener);*/
    }


    public String getAttrType() {
        switch (mType){
            case "item_":
                return "recyclerview_simple_item";
            case "empty_":
                return "recyclerview_empty_item";
            case "loading_":
                return "recyclerview_loading_item";
        }
        return mType;
    }

    public String setNormalType(String text) {
        switch (text){
            case "recyclerview_simple_item":
                return "item_";
            case "recyclerview_empty_item":
                return "empty_";
            case "recyclerview_loading_item":
                return "loading_";
        }
        return mType;
    }
}
