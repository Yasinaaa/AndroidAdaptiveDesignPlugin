package ru.itis.androidplugin.presenters;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DocumentAdapter;
import ru.itis.androidplugin.interfaces.BottomNavigationPresenter;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by yasina on 04.04.17.
 */
public class BottomNavigationPresenterImpl implements BottomNavigationPresenter {

    public ItemBottomNavigation[] allItems;
    private int bottomNavigationItemsCounts = 0;
    private final int maxNumOfBottomNavigationItems = 5;

    public BottomNavigationPresenterImpl() {
        allItems = new ItemBottomNavigation[maxNumOfBottomNavigationItems];
    }

    @Override
    public void generateItemsLayoutTitle(JTextField idJTextField, JTextField itemsLayoutJTextField) {
        idJTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent e) {
                String text = idJTextField.getText();
                if(!text.equals("")){
                    itemsLayoutJTextField.setText("bottom_navigation_item_" + text);
                }else {
                    itemsLayoutJTextField.setText(null);
                }
            }
        });
    }

    @Override
    public void addNewItemToBottomNavigationView(JLabel openJLabel, JPanel[] panels) {
        openJLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /*ItemBottomNavigation newItem = new ItemBottomNavigation();
                panel.add(new ItemBottomNavigation());
                allItems.add(newItem);*/
                if (bottomNavigationItemsCounts >= maxNumOfBottomNavigationItems){
                    //todo dialog error
                }else {
                    panels[bottomNavigationItemsCounts].setVisible(true);
                    allItems[bottomNavigationItemsCounts] = new ItemBottomNavigation();
                    bottomNavigationItemsCounts++;
                }

            }
        });
    }

    @Override
    public void openItemsLayout(JLabel label, String path){
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileEditorManager fileEditorManager = FileEditorManager.getInstance(PluginProject.mProject);
                File file = new File(path);
                VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
                fileEditorManager.openFile(virtualFile, true, true);
            }
        });

    }


    public JComboBox setAllDrawablesList(JComboBox iconJComboBox) {
        File file = new File(PluginProject.mProject.getBasePath() + "/app/src/main/res/drawable");
        File[] allDrawables = file.listFiles();

        iconJComboBox.addItem("");
        for(int i=0; i < allDrawables.length; i++){
            iconJComboBox.addItem(allDrawables[i].getName());
        }

        iconJComboBox.setEditable(true);

        return iconJComboBox;
    }


    public void chooseIcon(JComboBox drawableJComboBox) {
        File file = new File(PluginProject.mProject.getBasePath() + "/app/src");
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(true, true, false, false, false, false);
        VirtualFile selectedFile = FileChooser.chooseFile(fileChooserDescriptor, PluginProject.mProject, virtualFile);
        String path = selectedFile.getCanonicalPath();

        if (!path.contains(".png")) {
            JFrame frame = new JFrame();
            Object stringArray[] = {"You need to choose png file", "Cancel"};
            JOptionPane.showOptionDialog(frame, "It's not PNG", "Error",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray,
                    stringArray[0]);
        } else {
            drawableJComboBox.addItem(path.substring(path.lastIndexOf("/") + 1,
                    path.lastIndexOf(".")));
        }
    }


    @Override
    public void clickAddToLayout(JPanel[] allPanels, JTextField[] allIDs, JComboBox[] allDrawables,
                                  JLabel[] allRemoves, JLabel[] allChoosers){
        for (int i=0; i<maxNumOfBottomNavigationItems; i++) {
            if (allItems[i] != null) {
                allItems[i] = new ItemBottomNavigation(i, allIDs[i].getText(), allDrawables[i].getSelectedItem().toString());
            }
        }

    }

    @Override
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
