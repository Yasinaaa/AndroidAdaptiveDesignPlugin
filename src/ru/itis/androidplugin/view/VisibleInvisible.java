package ru.itis.androidplugin.view;

import javax.swing.*;

/**
 * Created by yasina on 05.04.17.
 */
public class VisibleInvisible {

    public static void cleanState(MainView mainView){
        mainView.currentMaterialItemParametersJPanel.setVisible(false);
    }

    public static void isChild(boolean answer, MainView mainView){
        //top
        if (answer){
            mainView.titleParentIDJLabel.setVisible(true);
            mainView.itemParentIDJLabel.setVisible(true);
        }else {
            mainView.titleParentIDJLabel.setVisible(false);
            mainView.itemParentIDJLabel.setVisible(false);
        }
        //top
    }

    public static void layoutsForRecyclerViewCreated(MainView mainView){
        mainView.openItemLayoutJLabel.setVisible(true);
        mainView.openEmptyLayoutJLabel.setVisible(true);
        mainView.openLoadingLayoutJLabel.setVisible(true);
    }

    public static void recyclerViewState(MainView mainView){

        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.emptyItemJLabel.setVisible(true);
        mainView.titleLoadingJLabel.setVisible(true);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.emptyItemLayoutJTextField.setVisible(true);
        mainView.loadingItemLayoutJTextField.setVisible(true);

        mainView.titleItemsJLabel.setVisible(false);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.openItemLayoutJLabel.setVisible(false);
        mainView.removeEmptyLayoutJLabel.setVisible(false);
        mainView.removeLoadingLayoutJLabel.setVisible(false);
        //3

        //4
        mainView.openEmptyLayoutJLabel.setVisible(false);
        mainView.openLoadingLayoutJLabel.setVisible(false);

        mainView.addNewItemJLabel.setVisible(false);
        //4
    }

    public static void bottomNavigationState(MainView mainView){
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);

        mainView.typeJLabel.setVisible(false);
        mainView.titleMaterialItemJLabel.setVisible(true);// bottom_navigation
        mainView.emptyItemJLabel.setVisible(false);
        mainView.titleLoadingJLabel.setVisible(false);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);

        mainView.typeJComboBox.setVisible(false);
        mainView.itemMaterialItemJTextField.setVisible(true);// bottom_navigation
        mainView.emptyItemLayoutJTextField.setVisible(false);
        mainView.loadingItemLayoutJTextField.setVisible(false);

        mainView.titleItemsJLabel.setVisible(true);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.openItemLayoutJLabel.setVisible(false);
        mainView.removeEmptyLayoutJLabel.setVisible(false);
        mainView.removeLoadingLayoutJLabel.setVisible(false);
        //3

        //4
        mainView.openEmptyLayoutJLabel.setVisible(false);
        mainView.openLoadingLayoutJLabel.setVisible(false);

        mainView.addNewItemJLabel.setVisible(true);
        //4
    }
}
