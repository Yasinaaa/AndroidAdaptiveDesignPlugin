package ru.itis.androidplugin.presenters;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DocumentAdapter;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by yasina on 04.04.17.
 */
public class BottomNavigationPresenter extends CommonPresenter {

    public ItemBottomNavigation[] allItems;
    private int bottomNavigationItemsCounts = 0;
    private final int maxNumOfBottomNavigationItems = 5;

    public BottomNavigationPresenter() {
        super();
        allItems = new ItemBottomNavigation[maxNumOfBottomNavigationItems];
    }

    public void addNewItemToBottomNavigationView(JLabel openJLabel, JPanel[] panels) {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (bottomNavigationItemsCounts >= maxNumOfBottomNavigationItems){
                    //todo dialog error
                }else {
                    panels[bottomNavigationItemsCounts].setVisible(true);
                    allItems[bottomNavigationItemsCounts] = new ItemBottomNavigation();
                    bottomNavigationItemsCounts++;
                }

            }
        };

        try{
            openJLabel.getMouseListeners()[0] = mouseAdapter;
        }catch (java.lang.ArrayIndexOutOfBoundsException e){
            openJLabel.addMouseListener(mouseAdapter);
        }

    }


    public void clickAddToLayout(JPanel[] allPanels, JTextField[] allIDs, JComboBox[] allDrawables,
                                  JLabel[] allRemoves, JLabel[] allChoosers){
        for (int i=0; i<maxNumOfBottomNavigationItems; i++) {
            if (allItems[i] != null) {
                allItems[i] = new ItemBottomNavigation(i, allIDs[i].getText(), allDrawables[i].getSelectedItem().toString());
            }
        }

    }


    public void setAllItems(JPanel[] allPanels, JComboBox[] allDrawables,
                             JLabel[] allRemoves, JLabel[] allChoosers){
        for (int i=0; i<maxNumOfBottomNavigationItems; i++){
            setParameters(i, allChoosers[i], allDrawables[i],
                    allRemoves[i], allPanels[i]);
        }
    }

    private void setParameters(int id, JLabel label, JComboBox jComboBox, JLabel removeLabel, JPanel panel){
        setAllDrawablesList(jComboBox);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooseIcon(jComboBox);
            }
        });
        removeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.setVisible(false);
                allItems[id]=null;
            }
        });
    }

    @Override
    public void setChildViewParameters() {

    }

    public class ItemBottomNavigation{

        private int id;
        private String title, drawable;

        public ItemBottomNavigation() {

        }

        public ItemBottomNavigation(int id, String title, String drawable) {
            this.id = id;
            this.title = title;
            this.drawable = drawable;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDrawable() {
            return drawable;
        }

        public void setDrawable(String drawable) {
            this.drawable = drawable;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
