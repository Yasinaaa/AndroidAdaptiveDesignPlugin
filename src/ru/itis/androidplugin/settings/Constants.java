package ru.itis.androidplugin.settings;

import ru.itis.androidplugin.elements.MaterialItem;
import ru.itis.androidplugin.elements.MaterialRecyclerView;
import ru.itis.androidplugin.elements.MaterialTextView;

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

    public static MaterialItem[] mViewMaterialItems = new MaterialItem[]{
            materialRecyclerView,  materialTextView
    };
}
