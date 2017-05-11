package ru.itis.androidplugin.android.values;

import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by yasina on 05.05.17.
 */
public class Styles {

    private static final String STYLE_TOOLBAR =
            "    <style name=\"AppSearchViewStyle\" parent=\"Widget.AppCompat.SearchView\">\n" +
            "        <item name=\"android:textCursorDrawable\">@drawable/white_cursor</item>\n" +
            "        <item name=\"queryBackground\">@android:color/transparent</item>\n" +
            "        <item name=\"searchIcon\">@drawable/icon_toolbar_search</item>\n" +
            "        <item name=\"closeIcon\">@drawable/icon_toolbar_clear</item>\n" +
            "        <item name=\"queryHint\">@string/app_name</item>\n" +
            "        <item name=\"android:imeActionId\">6</item>\n" +
            "    </style>";


    public static void addToolbarStyle(){
        List<String> list = null;
        String valuePath = getPathToStyles();

        try {
            list = Files.readAllLines(Paths.get(valuePath));
            //TEMP
            if (!list.contains("    " + STYLE_TOOLBAR)){
                list.add(list.size() - 1, STYLE_TOOLBAR);
            }
            //TEMP
            Files.write(Paths.get(valuePath), list);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getPathToStyles(){
        return PluginProject.mProject.getBasePath() + Constants.RES_PATH + "values/styles.xml";
    }

}
