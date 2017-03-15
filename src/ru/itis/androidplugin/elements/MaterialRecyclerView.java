package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.settings.GenerateClass;
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
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.createButton.setVisible(true);

        mainView.titleMaterialItemJLabel.setText("Recycler View ID");
        mainView.createButton.setText("Create Recycler View");

        mainView.createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setId(mainView.itemMaterialItemJTextField.getText());
                UtilsEnvironment.insertInEditor(mViewParametrs);

                CreateAdapterDialog dialog = new CreateAdapterDialog(mainView,
                        mainView.clickedMaterialItem);
                dialog.pack();
                dialog.setVisible(true);
                //setViewItemRecyclerView(mainView);
            }
        });

    }


    public void hideNotNeededThings(MainView mainView){
        mainView.titleParentViewJLabel.setVisible(false);
        mainView.itemParentViewJLabel.setVisible(false);
        mainView.createAdapterButton.setVisible(false);
    }

    @Override
    public MaterialItem getСhild() {
        return null;
    }

    @Override
    public void setСhild(MaterialItem child) {
        this.child = child;
    }

    @Override
    public void setViewChildAndParent(MainView mainView) {
        mainView.titleParentViewJLabel.setText("RecyclerView ID");
        mainView.itemParentViewTextField.setVisible(true);
        mainView.createAdapterButton.setVisible(false);
        mainView.itemParentViewJLabel.setVisible(false);


    }

}
