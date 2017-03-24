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
    private boolean isAlreadyInserted = false;
    private ImageIcon mIcon;
    private MainView mainView;
    public enum RecyclerViewType{
        LISTVIEW, TABLEVIEW
    }
    public RecyclerViewType recyclerViewType;

    public MaterialRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    @Override
    public void setView(MainView mainView) {
        this.mainView = mainView;
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setText("Item layout");
        //mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        mainView.saveLayoutButton.setVisible(true);
        mainView.titleParentViewJLabel.setVisible(true);

        mainView.titleParentViewJLabel.setText("Recycler View ID");
        mainView.createItemLayoutButton.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.itemMaterialItemJTextField.setEnabled(false);
        mainView.itemMaterialItemJTextField.setText("Empty");

        System.out.println("mainView.itemMaterialItemJTextField.isVisible()=" + mainView.itemMaterialItemJTextField.isVisible());

        if(getСhild() != null){
            mainView.titleMaterialItemJLabel.setVisible(true);
            mainView.titleMaterialItemJLabel.setText("Items layout");
            mainView.itemMaterialItemJTextField.setEnabled(false);
            mainView.itemMaterialItemJTextField.setText(child.mId);
            mainView.itemMaterialItemJTextField.setVisible(false);
            mainView.itemParentViewJTextField.setVisible(true);
            mainView.itemParentViewJTextField.setText(mId);

        }else {
            System.out.println("no children");
        }

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("create item layout clicked");
                insertToLayoutOrNo();

                CreateAdapterDialog dialog = new CreateAdapterDialog(mainView, MaterialRecyclerView.this);
                dialog.pack();
                dialog.setVisible(true);
                mainView.createItemLayoutButton.removeActionListener(this);
            }
        };
        mainView.createItemLayoutButton.addActionListener(actionListener);

        ActionListener actionListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add to layout btn clicked");
                insertToLayoutOrNo();
                mainView.addToLayoutButton.addActionListener(this);
            }
        };

        mainView.addToLayoutButton.addActionListener(actionListener2);

        ActionListener actionListener3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("save layout btn clicked");
                String path = PluginProject.mLayoutPath;
                String newPath = path.substring(path.lastIndexOf("/"), path.length());
                NewLayoutsCreating newLayoutsCreating = new NewLayoutsCreating();
                newLayoutsCreating.initAllScreenLayouts(mainView, newPath);
                mainView.saveLayoutButton.addActionListener(this);
            }
        };
        mainView.saveLayoutButton.addActionListener(actionListener3);
    }

    private void insertToLayoutOrNo(){
        if(!isAlreadyInserted){
            setId(mainView.itemParentViewJTextField.getText());

            UtilsEnvironment.insertInEditor(mViewParametrs);
            isAlreadyInserted = true;
        }
    }

    public void hideNotNeededThings(MainView mainView){
        mainView.titleParentViewJLabel.setVisible(false);
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
    }

}
