package ru.itis.androidplugin.elements;

import org.apache.commons.lang.StringUtils;
import ru.itis.androidplugin.generator.GenerateClass;
import ru.itis.androidplugin.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    public static final String VIEW_NAME = "Recycler View";
    private String childItemPath = null;
    private MaterialItem parentRecyclerView = null;
    private ImageIcon mIcon;
    public static String[] childRecyclerViewType = new String[]{
      "item_", "empty_", "loading_"
    };
    private String type;

    public MaterialChildRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    @Override
    public void setView(MainView mainView) {
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

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateClass generateClass = new GenerateClass();
                generateClass.generateClass("LaHolder");
                mainView.saveLayoutButton.removeActionListener(this);
            }
        };
        mainView.saveLayoutButton.setText("Save layout & Create Adapter");
        mainView.saveLayoutButton.addActionListener(actionListener);
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
}
