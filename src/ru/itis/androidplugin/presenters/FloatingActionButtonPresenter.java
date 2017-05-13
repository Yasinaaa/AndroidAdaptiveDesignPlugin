package ru.itis.androidplugin.presenters;

import ru.itis.androidplugin.interfaces.FloatingActionButtonInterface;

import javax.swing.*;

/**
 * Created by yasina on 10.04.17.
 */
public class FloatingActionButtonPresenter extends CommonPresenter {

    private FloatingActionButtonInterface floatingActionButtonType;
    private final String USUAL_FAB = "usual";
    private final String BOTTOM_SHEET_FAB = "bottom sheet item";
    private final String TOOLBAR_FAB = "toolbar item";
    private String[] types = new String[]{USUAL_FAB, BOTTOM_SHEET_FAB, TOOLBAR_FAB};

    public FloatingActionButtonPresenter(FloatingActionButtonInterface floatingActionButtonTypes) {
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
