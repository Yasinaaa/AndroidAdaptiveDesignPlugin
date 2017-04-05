package ru.itis.androidplugin.interfaces;

import javax.swing.*;

/**
 * Created by yasina on 04.04.17.
 */
public interface BottomNavigationPresenter {

    public void generateItemsLayoutTitle(JTextField idJTextField, JTextField itemsLayoutJTextField);
    public void addNewItemToBottomNavigationView(JLabel openJLabel, JPanel[] panels);
    public void openItemsLayout(JLabel label,String path);
    public void setAllItems(JPanel[] allPanels, JComboBox[] allDrawables,
                            JLabel[] allRemoves, JLabel[] allChoosers);
    public void clickAddToLayout(JPanel[] allPanels, JTextField[] allIDs, JComboBox[] allDrawables,
                                 JLabel[] allRemoves, JLabel[] allChoosers);

}
