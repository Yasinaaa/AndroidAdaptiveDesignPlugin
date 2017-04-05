package ru.itis.androidplugin.interfaces;

import ru.itis.androidplugin.view.MainView;
import ru.itis.androidplugin.view.MaterialBottomSheets;

/**
 * Created by yasina on 05.04.17.
 */
public interface BottomSheetPresenter {

    public void setAllValues();
    public void getAllValues();
    public void setOrNoTitle();
    public void openLayout(String path);
    public void generateItemsLayoutTitle();

}
