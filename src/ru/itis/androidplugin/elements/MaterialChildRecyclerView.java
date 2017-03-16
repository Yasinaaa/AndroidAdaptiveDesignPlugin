package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.settings.GenerateClass;
import ru.itis.androidplugin.settings.UtilsEnvironment;
import ru.itis.androidplugin.view.CreateAdapterDialog;
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
        mainView.itemMaterialItemJLabel.setVisible(false);

        mainView.createButton.setText("Create Adapter");
        mainView.titleMaterialItemJLabel.setText("Adapter Class name");
        mainView.itemParentViewJLabel.setVisible(true);
        mainView.itemParentViewJLabel.setText(parentRecyclerView.mId);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateClass generateClass = new GenerateClass();
                generateClass.generateClass(mainView.itemMaterialItemJTextField.getText());
                mainView.createButton.removeActionListener(this);
            }
        };
        mainView.createButton.addActionListener(actionListener);


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
