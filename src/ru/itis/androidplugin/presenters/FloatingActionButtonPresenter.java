package ru.itis.androidplugin.presenters;

import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.generator.XmlChanger;
import ru.itis.androidplugin.interfaces.FloatingActionButtonTypes;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 10.04.17.
 */
public class FloatingActionButtonPresenter extends CommonPresenter {

    private FloatingActionButtonTypes floatingActionButtonType;
    private final String USUAL_FAB = "usual";
    private final String BOTTOM_SHEET_FAB = "bottom sheet item";
    private final String TOOLBAR_FAB = "toolbar item";
    private String[] types = new String[]{USUAL_FAB, BOTTOM_SHEET_FAB, TOOLBAR_FAB};

    public FloatingActionButtonPresenter(FloatingActionButtonTypes floatingActionButtonTypes) {
        super();
        this.floatingActionButtonType = floatingActionButtonTypes;
    }


    @Override
    public void setChildViewParameters() {

    }

    public void setStyles(JComboBox jComboBox){
        for (String type: types) {
            jComboBox.addItem(type);
        }
    }

    public void setParametersByChoosedStyle(String style){
        switch (style){
            case USUAL_FAB:
                floatingActionButtonType.setUsualOptions();
                break;
            case BOTTOM_SHEET_FAB:
                case TOOLBAR_FAB:
                floatingActionButtonType.setBottomSheetToolbarOptions();
                break;
            default:
                break;
        }
    }

    public String setTextByChoosedStyle(String style){
        switch (style){
            case USUAL_FAB:
                return floatingActionButtonType.setUsualStyle();
            case BOTTOM_SHEET_FAB:
                return floatingActionButtonType.setBottomSheetStyle();
            case TOOLBAR_FAB:
                return floatingActionButtonType.setToolbarStyle();
            default:
                return floatingActionButtonType.setUsualStyle();
        }
    }


}
