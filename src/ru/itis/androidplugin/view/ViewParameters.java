package ru.itis.androidplugin.view;

import ru.itis.androidplugin.elements.MaterialItem;

import javax.swing.*;

/**
 * Created by yasina on 24.02.17.
 */
public abstract class ViewParameters extends JComponent {

    public JPanel mJPanel;
    public JLabel mNameTitleJLabel;
    public MaterialItem mMaterialItem;

    public ViewParameters(MaterialItem mMaterialItem){
       mJPanel = new JPanel();
       mNameTitleJLabel = new JLabel();
       this.mMaterialItem = mMaterialItem;
    }


}
