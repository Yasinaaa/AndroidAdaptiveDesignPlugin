package ru.itis.androidplugin.elements;

import org.apache.commons.lang.StringUtils;
import ru.itis.androidplugin.settings.GenerateClass;
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

    public MaterialChildRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    @Override
    public void setView(MainView mainView) {

        mainView.itemParentViewJTextField.setVisible(false);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.addToLayoutButton.setVisible(false);
        mainView.itemMaterialItemJTextField.setEnabled(true);
        mainView.itemMaterialItemJTextField.setText(StringUtils.capitalize(parentRecyclerView.mId)+"Adapter");

        mainView.createItemLayoutButton.setText("Create Adapter");
        mainView.titleMaterialItemJLabel.setText("Adapter Class name");
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.itemParentViewJTextField.setEnabled(false);
        mainView.itemParentViewJTextField.setText(parentRecyclerView.mId);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateClass generateClass = new GenerateClass();
                generateClass.generateClass(mainView.itemMaterialItemJTextField.getText());
                mainView.createItemLayoutButton.removeActionListener(this);
            }
        };
        mainView.createItemLayoutButton.addActionListener(actionListener);
    }

    @Override
    public MaterialItem getParent() {
        return parentRecyclerView;
    }

    @Override
    public void setParent(MaterialItem parent) {
        this.parentRecyclerView = parent;
    }

}
