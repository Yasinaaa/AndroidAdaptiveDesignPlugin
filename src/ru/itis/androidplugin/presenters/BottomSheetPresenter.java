package ru.itis.androidplugin.presenters;

import com.intellij.ui.DocumentAdapter;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.view.MainView;
import ru.itis.androidplugin.view.MaterialBottomSheets;
import ru.itis.androidplugin.view.MaterialItem;
import ru.itis.androidplugin.view.VisibleInvisible;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by yasina on 05.04.17.
 */

public class BottomSheetPresenter extends CommonPresenter {

    private MaterialBottomSheets materialBottomSheets;

    public BottomSheetPresenter(MaterialBottomSheets materialBottomSheets) {
        super();
        this.materialBottomSheets = materialBottomSheets;
    }


    public void setOrNoTitle(JLabel removeIconJLable, JTextField removeJTextField) {
        removeIconJLable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ImageIcon i = (ImageIcon) removeIconJLable.getIcon();
                if(i.equals(mRemoveIcon)){
                    removeIconJLable.setIcon(mAddIcon);
                    removeJTextField.setText("None");
                    removeJTextField.setEnabled(false);
                }
                else if(i.equals(mAddIcon)){
                    removeIconJLable.setIcon(mRemoveIcon);
                    removeJTextField.setText("");
                    removeJTextField.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void setChildViewParameters(){
        VisibleInvisible.cleanState(MainView.mainView);
        VisibleInvisible.isChild(true, MainView.mainView);
        //todo change
        MainView.mainView.itemParentIDJLabel.setText(materialBottomSheets.mId);
    }

}
