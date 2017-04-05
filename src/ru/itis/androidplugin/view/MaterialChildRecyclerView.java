package ru.itis.androidplugin.view;

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
    //private String childItemPath = null;
    private MaterialItem parentRecyclerView = null;
    private ImageIcon mIcon;
    public static String[] childRecyclerViewType = new String[]{
      "item_", "empty_", "loading_"
    };
    private String type;

    public MaterialChildRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    public MaterialChildRecyclerView(String path, String attrType){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        mId = path.substring(path.lastIndexOf("/") + 1, path.indexOf("."));
        type = setNormalType(attrType);
        setLayoutPath(path);
        //todo get by id

    }

    @Override
    public void setView() {
        MainView mainView = MainView.mainView;

        String[] answer = FileOwner.FileOwnerListener.getOwner(PluginProject.mLayoutPath);
        mainView.itemActivityClassJLabel.setText(answer[0]);
        if (answer[1] != null) {
            mainView.titleParentIDJLabel.setVisible(true);
            mainView.itemParentIDJLabel.setVisible(true);
            mainView.itemParentIDJLabel.setText(answer[1]);
        }
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

    @Override
    public MaterialItem getParent() {
        return parentRecyclerView;
    }

    @Override
    public void setParent(MaterialItem parent) {
        this.parentRecyclerView = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttrType() {
        switch (type){
            case "item_":
                return "simple_item";
            case "empty_":
                return "empty_item";
            case "loading_":
                return "loading_item";
        }
        return type;
    }

    public String setNormalType(String text) {
        switch (text){
            case "simple_item":
                return "item_";
            case "empty_item":
                return "empty_";
            case "loading_item":
                return "loading_";
        }
        return type;
    }
}
