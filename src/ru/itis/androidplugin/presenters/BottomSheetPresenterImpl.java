package ru.itis.androidplugin.presenters;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DocumentAdapter;
import ru.itis.androidplugin.interfaces.BottomSheetPresenter;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.view.MainView;
import ru.itis.androidplugin.view.MaterialBottomSheets;
import ru.itis.androidplugin.view.VisibleInvisible;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by yasina on 05.04.17.
 */
public class BottomSheetPresenterImpl implements BottomSheetPresenter {

    private final String ICON_REMOVE_PATH = "/icons/remove.png";
    private final String ICON_ADD_PATH = "/icons/checkmark.png";
    private String[] bottomSheetsStyles = new String[]{
            "list_style_with_title", "list_style_no_title", "grid_style"
    };
    private ImageIcon mRemoveIcon, mAddIcon;
    private MainView mainView;
    private MaterialBottomSheets materialBottomSheets;

    public BottomSheetPresenterImpl(MainView mainView, MaterialBottomSheets materialBottomSheets) {
        this.mainView = mainView;
        this.materialBottomSheets = materialBottomSheets;
    }

    @Override
    public void setAllValues() {
        mainView.titleParentViewJLabel.setText("BottomSheet ID");
        mainView.typeJLabel.setText("Style");
        mainView.emptyItemJLabel.setText("Title");
        for(String s: bottomSheetsStyles){
            mainView.typeJComboBox.addItem(s);
        }
        try {
            mRemoveIcon = new ImageIcon(ImageIO.read(getClass().getResource(ICON_REMOVE_PATH)));
            mAddIcon  = new ImageIcon(ImageIO.read(getClass().getResource(ICON_ADD_PATH)));
            mainView.removeEmptyLayoutJLabel.setIcon(mRemoveIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllValues() {
        materialBottomSheets.mId = mainView.itemParentViewJTextField.getText();
        materialBottomSheets.mParentItemName = PluginProject.getCurrentVirtualFile().getCanonicalPath();
        materialBottomSheets.mLayoutName = mainView.itemMaterialItemJTextField.getText();
        materialBottomSheets.mStyle = mainView.typeJComboBox.getSelectedItem().toString();
        if(mainView.itemMaterialItemJTextField.isEnabled()){
            materialBottomSheets.title = mainView.itemMaterialItemJTextField.getText();

        }

    }

    @Override
    public void setOrNoTitle() {
        mainView.removeEmptyLayoutJLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ImageIcon i = (ImageIcon) mainView.removeEmptyLayoutJLabel.getIcon();
                if(i.equals(mRemoveIcon)){
                    mainView.removeEmptyLayoutJLabel.setIcon(mAddIcon);
                    mainView.emptyItemLayoutJTextField.setText("None");
                    mainView.emptyItemLayoutJTextField.setEnabled(false);
                }
                else if(i.equals(mAddIcon)){
                    mainView.removeEmptyLayoutJLabel.setIcon(mRemoveIcon);
                    mainView.emptyItemLayoutJTextField.setText("");
                    mainView.emptyItemLayoutJTextField.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void openLayout(String path) {
        mainView.openItemLayoutJLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileEditorManager fileEditorManager = FileEditorManager.getInstance(PluginProject.mProject);
                File file = new File(path);
                VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
                fileEditorManager.openFile(virtualFile, true, true);
                itemBottomSheetView();
            }
        });
    }

    public void itemBottomSheetView(){
        VisibleInvisible.cleanState(mainView);
        VisibleInvisible.isChild(true, mainView);
        mainView.itemParentIDJLabel.setText(materialBottomSheets.mId);
    }

    @Override
    public void generateItemsLayoutTitle() {
        mainView.itemParentViewJTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent e) {
                String text = mainView.itemParentViewJTextField.getText();
                if(!text.equals("")){
                    mainView.itemMaterialItemJTextField.setText("bottom_sheet_" + text);
                }else {
                    mainView.itemMaterialItemJTextField.setText(null);
                }
            }
        });
    }
}
