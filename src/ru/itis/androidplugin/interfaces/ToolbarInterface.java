package ru.itis.androidplugin.interfaces;

/**
 * Created by yasina on 04.05.17.
 */
public interface ToolbarInterface {

    String STANDARD_TYPE = "STANDARD";
    String SEARCH_TYPE = "SEARCH";
    String REMOVE_TYPE = "REMOVE";
    String SORT_TYPE = "SORT";
    String EXTENDED_TYPE = "EXTENDED";
    String[] ALL_TYPES = new String[]{
            STANDARD_TYPE, SEARCH_TYPE, REMOVE_TYPE, SORT_TYPE, EXTENDED_TYPE
    };

    void setParametersStandardStyle();
    void setParametersSearchStyle();
    void setParametersRemoveStyle();
    void setParametersSortStyle();
    void setParametersExtendedStyle();

    void getParametersStandardStyle();
    void getParametersSearchStyle();
    void getParametersRemoveStyle();
    void getParametersSortStyle();
    void getParametersExtendedStyle();

    void generateStandardToolbar();
    void generateSearchToolbar();
    void generateRemoveToolbar();
    void generateSortToolbar();
    void generateExtendedToolbar();

}
