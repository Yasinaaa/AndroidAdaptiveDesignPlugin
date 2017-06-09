package ru.itis.androidplugin.view;

/**
 * Created by yasina on 05.04.17.
 */
public class VisibleInvisible {

    public static void cleanState(MainView mainView){
        mainView.invisibleJLabel.setVisible(false);
        mainView.currentMaterialItemParametersJPanel.setVisible(false);
    }

    public static void isChild(boolean answer, MainView mainView){
        //top
        if (answer){
            mainView.titleParentIDJLabel.setVisible(true);
            mainView.itemParentIDJLabel.setVisible(true);
            mainView.titleLayoutTypeJLabel.setVisible(true);
            mainView.itemLayoutTypeJLabel.setVisible(true);
        }else {
            mainView.titleParentIDJLabel.setVisible(false);
            mainView.itemParentIDJLabel.setVisible(false);
            mainView.titleLayoutTypeJLabel.setVisible(false);
            mainView.itemLayoutTypeJLabel.setVisible(false);
        }
        //top
    }

    public static void layoutsForRecyclerViewCreated(MainView mainView){
        mainView.openItemLayoutJLabel.setVisible(true);
        mainView.openEmptyLayoutJLabel.setVisible(true);
        mainView.openLoadingLayoutJLabel.setVisible(true);
    }

    private static void cleanItems(MainView mainView){
        mainView.itemParentViewJTextField.setText("");
        mainView.typeJComboBox.removeAllItems();
        mainView.itemMaterialItemJTextField.setText("");
        mainView.jComboBox2.removeAllItems();
        mainView.jComboBox3.removeAllItems();
        mainView.emptyItemLayoutJTextField.setText("");
        mainView.loadingItemLayoutJTextField.setText("");
    }

    public static void recyclerViewState(MainView mainView){

        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.title4JLabel.setVisible(false);
        mainView.title5JLabel.setVisible(false);
        mainView.emptyItemJLabel.setVisible(true);
        mainView.titleLoadingJLabel.setVisible(true);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.emptyItemLayoutJTextField.setVisible(true);
        mainView.loadingItemLayoutJTextField.setVisible(true);
        mainView.jComboBox2.setVisible(false);
        mainView.jComboBox3.setVisible(false);
        mainView.titleItemsJLabel.setVisible(false);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(false);
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
        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);

        mainView.typeJLabel.setVisible(false);
        mainView.titleMaterialItemJLabel.setVisible(true);// bottom_navigation
        mainView.title4JLabel.setVisible(false);
        mainView.title5JLabel.setVisible(false);
        mainView.emptyItemJLabel.setVisible(false);
        mainView.titleLoadingJLabel.setVisible(false);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);

        mainView.typeJComboBox.setVisible(false);
        mainView.itemMaterialItemJTextField.setVisible(true);// bottom_navigation
        mainView.emptyItemLayoutJTextField.setVisible(false);
        mainView.loadingItemLayoutJTextField.setVisible(false);
        mainView.jComboBox2.setVisible(false);
        mainView.jComboBox3.setVisible(false);
        mainView.titleItemsJLabel.setVisible(true);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(false);
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
    public static void bottomSheetsState(MainView mainView){
        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.emptyItemJLabel.setVisible(true);
        mainView.title4JLabel.setVisible(false);
        mainView.title5JLabel.setVisible(false);
        mainView.titleLoadingJLabel.setVisible(false);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.emptyItemLayoutJTextField.setVisible(true);
        mainView.jComboBox2.setVisible(false);
        mainView.jComboBox3.setVisible(false);
        mainView.loadingItemLayoutJTextField.setVisible(false);
        mainView.titleItemsJLabel.setVisible(false);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(false);
        mainView.openItemLayoutJLabel.setVisible(false);
        mainView.removeEmptyLayoutJLabel.setVisible(true);
        mainView.removeLoadingLayoutJLabel.setVisible(false);
        //3

        //4
        mainView.openEmptyLayoutJLabel.setVisible(false);
        mainView.openLoadingLayoutJLabel.setVisible(false);
        mainView.addNewItemJLabel.setVisible(false);
        //4
    }

    public static void bottomSheetIncludeCreated(MainView mainView){
        mainView.openItemLayoutJLabel.setVisible(true);
    }

    public static void textViewState(MainView mainView){
        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.title4JLabel.setVisible(true);
        mainView.title5JLabel.setVisible(false);
        mainView.emptyItemJLabel.setVisible(false);
        mainView.titleLoadingJLabel.setVisible(false);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.jComboBox2.setVisible(true);
        mainView.jComboBox3.setVisible(false);
        mainView.emptyItemLayoutJTextField.setVisible(false);
        mainView.loadingItemLayoutJTextField.setVisible(false);
        mainView.titleItemsJLabel.setVisible(false);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(false);
        mainView.removeEmptyLayoutJLabel.setVisible(false);
        mainView.removeLoadingLayoutJLabel.setVisible(false);
        //3

        //4
        mainView.openItemLayoutJLabel.setVisible(false);
        mainView.openEmptyLayoutJLabel.setVisible(false);
        mainView.openLoadingLayoutJLabel.setVisible(false);
        mainView.addNewItemJLabel.setVisible(false);
        //4
    }

    public static void floatingActionButtonState(MainView mainView){
        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(false);
        mainView.title4JLabel.setVisible(true);
        mainView.title5JLabel.setVisible(false);
        mainView.emptyItemJLabel.setVisible(false);
        mainView.titleLoadingJLabel.setVisible(false);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(false);
        mainView.jComboBox2.setVisible(true);
        mainView.jComboBox3.setVisible(true);
        mainView.emptyItemLayoutJTextField.setVisible(false);
        mainView.loadingItemLayoutJTextField.setVisible(false);
        mainView.titleItemsJLabel.setVisible(false);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(true);
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

    public static void dividerState(MainView mainView){
        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(false);
        mainView.title4JLabel.setVisible(true);
        mainView.title5JLabel.setVisible(false);
        mainView.emptyItemJLabel.setVisible(false);
        mainView.titleLoadingJLabel.setVisible(false);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(false);
        mainView.jComboBox2.setVisible(true);
        mainView.jComboBox3.setVisible(false);
        mainView.emptyItemLayoutJTextField.setVisible(false);
        mainView.loadingItemLayoutJTextField.setVisible(false);
        mainView.titleItemsJLabel.setVisible(false);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(false);
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
    public static void buttonState(MainView mainView){
        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.title4JLabel.setVisible(true);
        mainView.title5JLabel.setVisible(true);
        mainView.emptyItemJLabel.setVisible(true);
        mainView.titleLoadingJLabel.setVisible(false);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.jComboBox2.setVisible(true);
        mainView.jComboBox3.setVisible(true);
        mainView.emptyItemLayoutJTextField.setVisible(true);
        mainView.loadingItemLayoutJTextField.setVisible(false);
        mainView.titleItemsJLabel.setVisible(false);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(false);
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

    public static void toolbarState(MainView mainView){
        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.title4JLabel.setVisible(false);
        mainView.title5JLabel.setVisible(false);
        mainView.emptyItemJLabel.setVisible(false);
        mainView.titleLoadingJLabel.setVisible(false);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(true);
        mainView.jComboBox2.setVisible(false);
        mainView.jComboBox3.setVisible(false);
        mainView.emptyItemLayoutJTextField.setVisible(false);
        mainView.loadingItemLayoutJTextField.setVisible(false);
        mainView.titleItemsJLabel.setVisible(false);
        mainView.item1JPanel.setVisible(false);
        mainView.item2JPanel.setVisible(false);
        mainView.item3JPanel.setVisible(false);
        mainView.item4JPanel.setVisible(false);
        mainView.item5JPanel.setVisible(false);

        mainView.addToLayoutButton.setVisible(true);
        //2

        //3
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(false);
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

    public static void setStandardToolbar(MainView mainView){
        mainView.title4JLabel.setVisible(true);
        mainView.title5JLabel.setVisible(true);
        mainView.jComboBox2.setVisible(true);
        mainView.jComboBox3.setVisible(true);
    }

    public static void cardState(MainView mainView){
        cleanItems(mainView);
        mainView.currentMaterialItemParametersJPanel.setVisible(true);
        //1
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.typeJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setVisible(false);
        mainView.title4JLabel.setVisible(true);
        mainView.title5JLabel.setVisible(false);
        mainView.emptyItemJLabel.setVisible(true);
        mainView.titleLoadingJLabel.setVisible(true);
        //1

        //2
        mainView.itemParentViewJTextField.setVisible(true);
        mainView.typeJComboBox.setVisible(true);
        mainView.itemMaterialItemJTextField.setVisible(false);
        mainView.jComboBox2.setVisible(true);
        mainView.jComboBox3.setVisible(false);
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
        mainView.removeItemLayoutJLabel.setVisible(false);
        mainView.openIconJLabel.setVisible(true);
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

    public static void setStandardCard(MainView mainView){
        mainView.title4JLabel.setVisible(true);
        mainView.title5JLabel.setVisible(true);
        mainView.jComboBox2.setVisible(true);
        mainView.jComboBox3.setVisible(true);
    }


}
