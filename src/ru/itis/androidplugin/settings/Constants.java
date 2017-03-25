package ru.itis.androidplugin.settings;

import ru.itis.androidplugin.elements.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by yasina on 12.02.17.
 */
public class Constants {

    public static final String THIS = "this";
    public static final String SET_MATERIAL_ITEM = "set material %s";
    public static final String BACKGROUND_ITEM = "#E3F2FD";
    public static final String XMLNS = "xmlns:app=\"http://schemas.android.com/apk/res-auto\"";
    public static final String RES_PATH = "/app/src/main/res/";
    public static final String VALUES_PATH = String.format("%s/values/", RES_PATH);

    //dimens {
        // elements text {
        public static final String ACTIVITY_HORISONTAL_MARGIN =
                "<dimen name=\"activity_horizontal_margin\">%ddp</dimen>";
        public static final String ACTIVITY_VERTICAL_MARGIN =
                "<dimen name=\"activity_vertical_margin\">%ddp</dimen>";
        public static final String ACTIVITY_DETAIL_VERTICAL_MARGIN =
                "<dimen name=\"activity_detail_vertical_margin\">88dp</dimen>";
        // elements text }

        // mobile constants {
        public static final int M_ACTIVITY_HORISONTAL_MARGIN = 16;
        public static final int M_ACTIVITY_VERTICAL_MARGIN = 16;
        public static final int M_ACTIVITY_DETAIL_VERTICAL_MARGIN = 88;

        public static final String MDPI_ACTIVITY_HORISONTAL_MARGIN =
                String.format(ACTIVITY_HORISONTAL_MARGIN, M_ACTIVITY_HORISONTAL_MARGIN);
        public static final String MDPI_ACTIVITY_VERTICAL_MARGIN =
                String.format(ACTIVITY_VERTICAL_MARGIN, M_ACTIVITY_VERTICAL_MARGIN);
        public static final String MDPI_ACTIVITY_DETAIL_VERTICAL_MARGIN =
                String.format(ACTIVITY_DETAIL_VERTICAL_MARGIN, M_ACTIVITY_DETAIL_VERTICAL_MARGIN);
        // mobile constants }
    //dimens }

    private static MaterialRecyclerView materialRecyclerView = new MaterialRecyclerView();
    private static MaterialTextView materialTextView = new MaterialTextView();
    private static MaterialBottomNavigation materialBottomNavigation = new MaterialBottomNavigation();
    private static MaterialBottomSheets materialBottomSheets = new MaterialBottomSheets();
    private static MaterialButton materialButton = new MaterialButton();
    private static MaterialCards materialCards = new MaterialCards();
    private static MaterialChips materialChips = new MaterialChips();
    private static MaterialDataTables materialDataTables = new MaterialDataTables();
    private static MaterialDialogs materialDialogs = new MaterialDialogs();
    private static MaterialDividers materialDividers = new MaterialDividers();
    private static MaterialFloatingActionButton materialFloatingActionButton = new MaterialFloatingActionButton();

    public static MaterialItem[] mViewMaterialItems = new MaterialItem[]{
            materialRecyclerView,  materialTextView, materialBottomNavigation, materialBottomSheets,
            materialButton, materialCards, materialChips, materialDataTables, materialDialogs,
            materialDividers, materialFloatingActionButton
    };

}
