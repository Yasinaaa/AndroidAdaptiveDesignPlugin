package ru.itis.androidplugin.interfaces;

/**
 * Created by yasina on 08.04.17.
 */
public interface TextViewInterface {

    void setUsualOptions(String[] types);
    void setBottomSheetLayoutOptions(String[] types);
    void setBottomSheetGridOptions(String[] types);

    String setBottomSheetLayoutTextView();
    String setBottomSheetGridTextView();
    String setUsualTextView();
}
