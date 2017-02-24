package ru.itis.androidplugin.view;

import ru.itis.androidplugin.elements.MaterialItem;

import javax.swing.*;

/**
 * Created by yasina on 24.02.17.
 */
public class ViewParameters extends JComponent {

    public JPanel mJPanel;
    public JLabel mLabelItemTitle;
    public MaterialItem mMaterialItem;

    public ViewParameters(){
       mJPanel = new JPanel();
       mLabelItemTitle = new JLabel();
    }
}
