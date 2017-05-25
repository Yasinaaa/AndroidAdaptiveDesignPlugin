package ru.itis.androidplugin.interfaces;

/**
 * Created by yasina on 04.05.17.
 */
public interface ToolbarInterface {

    String STANDARD_TYPE = "standard";
    String SEARCH_TYPE = "search";
    String REMOVE_TYPE = "remove";
    String SORT_TYPE = "sort";
    String EXTENDED_TYPE = "extended";
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
