package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.generation.NewLayoutsCreating;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.settings.UtilsEnvironment;
import ru.itis.androidplugin.view.CreateAdapterDialog;
import ru.itis.androidplugin.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yasina on 10.02.17.
 */
public class MaterialRecyclerView extends MaterialItem{

    public static final String XML_VIEW_PATTERN = "<RecyclerView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        />";
    public static final String ICON_PATH = "/icons/recycler_view.png";
    public static final String VIEW_NAME = "Recycler View";
    private MaterialItem child = null;

    private ImageIcon mIcon;

    public MaterialRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    @Override
    public void setView(MainView mainView) {

        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setText("Item layout");
        //mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        mainView.createButton.setVisible(true);
        mainView.titleParentViewJLabel.setVisible(true);

        mainView.titleParentViewJLabel.setText("Recycler View ID");
        //mainView.createButton.setText("Save layout");
        mainView.itemMaterialItemJLabel.setVisible(true);
        mainView.itemMaterialItemJLabel.setText("Empty");

        if(getСhild() != null){
            mainView.titleMaterialItemJLabel.setVisible(true);
            mainView.titleMaterialItemJLabel.setText("Items layout");
            mainView.itemMaterialItemJLabel.setVisible(true);
            mainView.itemMaterialItemJLabel.setText(child.mId);
            mainView.itemMaterialItemJTextField.setVisible(false);
            mainView.itemParentViewJTextField.setVisible(true);
            mainView.itemParentViewJTextField.setText(mId);
            mainView.itemParentViewJLabel.setVisible(false);
        }else {
            System.out.println("no children");
            mainView.itemMaterialItemJLabel.setVisible(false);
        }

        //mainView.createItemLayoutButton.setVisible(true);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setId(mainView.itemParentViewJTextField.getText());

                UtilsEnvironment.insertInEditor(mViewParametrs);

                CreateAdapterDialog dialog = new CreateAdapterDialog(mainView, MaterialRecyclerView.this);
                dialog.pack();
                dialog.setVisible(true);
                mainView.createButton.removeActionListener(this);
            }
        };
        mainView.createItemLayoutButton.addActionListener(actionListener);
        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = PluginProject.mLayoutPath;
                String newPath = path.substring(path.lastIndexOf("/"), path.length());
                NewLayoutsCreating newLayoutsCreating = new NewLayoutsCreating();
                newLayoutsCreating.initAllScreenLayouts(mainView, newPath);
            }
        };

        mainView.createButton.addActionListener(actionListener2);
    }


    public void hideNotNeededThings(MainView mainView){
        mainView.titleParentViewJLabel.setVisible(false);
        mainView.itemParentViewJLabel.setVisible(false);
        mainView.createItemLayoutButton.setVisible(false);
    }

    @Override
    public MaterialItem getСhild() {
        return child;
    }

    @Override
    public void setСhild(MaterialItem child) {
        this.child = child;
    }

    @Override
    public void setViewChildAndParent(MainView mainView) {
        mainView.titleParentViewJLabel.setText("RecyclerView ID");
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.createItemLayoutButton.setVisible(false);
        mainView.itemParentViewJLabel.setVisible(false);


    }

}
