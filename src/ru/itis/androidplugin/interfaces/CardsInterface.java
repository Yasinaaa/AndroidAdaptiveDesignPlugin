package ru.itis.androidplugin.interfaces;

/**
 * Created by yasina on 13.05.17.
 */
public interface CardsInterface {

    String STANDARD_TYPE = "standard";
    String COMMENT_TYPE = "search";
    String ADDITIONAL_INFO_TYPE = "remove";;
    String[] ALL_TYPES = new String[]{
            STANDARD_TYPE, COMMENT_TYPE, ADDITIONAL_INFO_TYPE
    };

    void setParametersStandardStyle();
    void setParametersCommentStyle();
    void setParametersAdditionalInfoStyle();

    void getParametersStandardStyle();
    void getParametersCommentStyle();
    void getParametersAdditionalInfoStyle();
}
