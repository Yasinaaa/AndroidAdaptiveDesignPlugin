package ru.itis.androidplugin.presenters;

import ru.itis.androidplugin.interfaces.DividerTypes;
import ru.itis.androidplugin.view.MaterialBottomSheets;
import ru.itis.androidplugin.view.MaterialDivider;

import javax.swing.*;

/**
 * Created by yasina on 10.04.17.
 */
public class DividerPresenter extends CommonPresenter {

    private DividerTypes dividerTypes;


    public DividerPresenter(DividerTypes dividerTypes) {
        super();
        this.dividerTypes = dividerTypes;
    }


    @Override
    public void setChildViewParameters() {

    }

    public String setTextByChoosedStyle(String style){
        switch (style){
            case MaterialDivider.USUAL_DIVIDER:
                return dividerTypes.setUsualStyle();
            case MaterialDivider.BOTTOM_SHEET_DIVIDER:
                return dividerTypes.setBottomSheetStyle();
            default:
                return dividerTypes.setUsualStyle();
        }
    }


}
