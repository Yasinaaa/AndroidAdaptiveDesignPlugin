package ru.itis.androidplugin.interfaces;

/**
 * Created by yasina on 13.05.17.
 */
public interface RecyclerViewInterface {

    String ITEM_TYPE = "item_";
    String EMPTY_TYPE = "empty_";
    String LOADING_TYPE = "loading_";
    String[] ALL_TYPES = new String[]{
            ITEM_TYPE, EMPTY_TYPE, LOADING_TYPE
    };

    void setParametersStandardStyle();
    void setParametersSearchStyle();
    void setParametersRemoveStyle();

    void getParametersStandardStyle();
    void getParametersSearchStyle();
    void getParametersRemoveStyle();
}
