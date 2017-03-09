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
