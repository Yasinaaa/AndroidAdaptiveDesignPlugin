package ru.itis.androidplugin.presenters;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DocumentAdapter;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.view.MainView;
import ru.itis.androidplugin.view.VisibleInvisible;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by yasina on 06.04.17.
 */
public abstract class CommonPresenter {

    private final String ICON_REMOVE_PATH = "/icons/remove.png";
    private final String ICON_ADD_PATH = "/icons/checkmark.png";
    public ImageIcon mRemoveIcon, mAddIcon;

    public CommonPresenter() {
        try {
            mRemoveIcon = new ImageIcon(ImageIO.read(getClass().getResource(ICON_REMOVE_PATH)));
            mAddIcon  = new ImageIcon(ImageIO.read(getClass().getResource(ICON_ADD_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRemoveIcon(JLabel label){
        label.setIcon(mRemoveIcon);
    }

    public void setTextToLabel(JLabel label, String text){
        label.setVisible(true);
        label.setText(text);
    }

    public void setIconToLabel(JLabel label, Icon icon){
        label.setVisible(true);
        label.setIcon(icon);
    }

    public void openItemLayout(JLabel label, String path){
        //todo change
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileEditorManager fileEditorManager = FileEditorManager.getInstance(PluginProject.mProject);
                File file = new File(path);
                VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
                fileEditorManager.openFile(virtualFile, true, true);
                setChildViewParameters();
            }
        });
    }

    public abstract void setChildViewParameters();


    public JComboBox setAllDrawablesList(JComboBox iconJComboBox) {
        File file = new File(PluginProject.mProject.getBasePath() + "/app/src/main/res/drawable");
        File[] allDrawables = file.listFiles();

        //iconJComboBox.addItem("");
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

    public void generateItemsLayoutTitle(String beginText, JTextField idJTextField, JTextField itemsLayoutJTextField) {
        String text = idJTextField.getText();
        if(!text.equals("")){
            itemsLayoutJTextField.setText(beginText + text);
        }else {
            itemsLayoutJTextField.setText(null);
        }
    }

    public void setRemoveButton(JLabel addRemoveLabel, JTextField textField, String text){
        if (addRemoveLabel != null){
            addRemoveLabel.setVisible(false);
            addRemoveLabel.setIcon(mRemoveIcon);

            MouseListener mouseListener2 = addRemoveLabel.getMouseListeners()[0];
            mouseListener2 = new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    ImageIcon i = (ImageIcon) addRemoveLabel.getIcon();
                    if(i.equals(mRemoveIcon)){
                        addRemoveLabel.setIcon(mAddIcon);
                        textField.setText("None");
                        textField.setEnabled(false);
                    }
                    else if(i.equals(mAddIcon)){
                        addRemoveLabel.setIcon(mRemoveIcon);
                        //materialChildRecyclerView.mType +
                        //mainView.itemParentViewJTextField.getText()
                        textField.setText(text);
                        textField.setEnabled(true);
                    }

                }
            };
        }
    }

    public List<AndroidView> getAllParentIDs(){
        VirtualFile virtualFile = PluginProject.getCurrentVirtualFile();
        AndroidView androidView = AndroidView.getAndroidViews(virtualFile);
        return androidView.getAllChildViews();
    }

    public void setAllElementIds(JComboBox jComboBox){
        List<AndroidView> androidView = getAllParentIDs();
        for (int i=0; i<androidView.size(); i++) {
            jComboBox.addItem(androidView.get(i).getIdValue());
        }
    }
}
