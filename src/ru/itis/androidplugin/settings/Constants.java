package ru.itis.androidplugin.settings;

import ru.itis.androidplugin.view.*;

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
    public static final String WRAP_CONTENT = "wrap_content";
    public static final String MATCH_PARENT = "match_parent";

    //dimens {
        // view text {
        public static final String ACTIVITY_HORISONTAL_MARGIN =
                "    <dimen name=\"activity_horizontal_margin\">%sdp</dimen>";
        public static final String ACTIVITY_VERTICAL_MARGIN =
                "    <dimen name=\"activity_vertical_margin\">%sdp</dimen>";
        public static final String ACTIVITY_DETAIL_VERTICAL_MARGIN =
                "    <dimen name=\"activity_detail_vertical_margin\">%sdp</dimen>";
        public static final String[] RECYCLERVIEW_DIMENS_TAGS = new String[]{
          ACTIVITY_HORISONTAL_MARGIN, ACTIVITY_VERTICAL_MARGIN
        };
        public static final String BOTTOM_NAVIGATION_VIEW_HEIGHT =
                "    <dimen name=\"bottom_navigation_view_height\">%sdp</dimen>";
        // view text }

        // mobile constants {
        public static final int M_ACTIVITY_HORISONTAL_MARGIN = 16;
        public static final int M_ACTIVITY_VERTICAL_MARGIN = 16;
        public static final int M_ACTIVITY_DETAIL_VERTICAL_MARGIN = 88;
        public static final int[] RECYCLERVIEW_DIMENS_VALUE = new int[]{M_ACTIVITY_HORISONTAL_MARGIN,
        M_ACTIVITY_VERTICAL_MARGIN};
        public static final int BOTTOM_NAVIGATION_VIEW_HEIGHT_VALUE = 56;

        public static final String BOTTOM_SHEET_PEEK_HEIGHT =
            "    <dimen name=\"bottom_sheet_peek_height\">70dp</dimen>";
        public static final String BOTTOM_SHEET_COLUMN_COUNT =
            "    <dimen name=\"bottom_sheet_column_count\">3</dimen>";
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
