package ru.itis.androidplugin.android.values;

import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.view.MaterialButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by yasina on 11.04.17.
 */
public class Drawables {

    private final String XML = ".xml";

    //raised button
    private final String BTN_CORNER = "btn_corner";
    private final String RAISE_BTN_DISABLED = "raise_btn_disabled";
    private final String RAISE_BTN_PRESSED = "raise_btn_pressed";
    private final String RAISE_BTN_FOCUSED = "raise_btn_focused";
    private final String BTN_RAISE = "btn_raise";
    private final String PATH_TO_XMLS = "/xmls/";
    //raised button

    //light theme
    //todo change CORNER_COLOR_LIGHT
    private final String CORNER_COLOR_LIGHT = "@android:color/white";
    private final String DISABLED_COLOR_LIGHT = "material_button_raised_background_color_disabled_light_theme";
    private final String PRESSED_COLOR_LIGHT = "material_divider_color_light_theme";
    private final String FOCUSED_COLOR_LIGHT = "material_button_flat_background_color_pressed_light_theme";
    //light theme

    //dark theme
    //todo change CORNER_COLOR_DARK
    private final String CORNER_COLOR_DARK = "@android:color/black";
    private final String DISABLED_COLOR_DARK = "material_button_raised_background_color_disabled_dark_theme";
    private final String PRESSED_COLOR_DARK = "material_divider_color_dark_theme";
    private final String FOCUSED_COLOR_DARK = "material_button_flat_background_color_pressed_dark_theme";
    //dark theme

    //toolbar
    private final String PATH_TO_TOOLBAR_FOLDER = "/toolbar/";
    private final
    String[] TOOLBAR_DRAWABLES = new String[] {"white_cursor.xml",
            "icon_toolbar_search.png", "icon_toolbar_clear.png"};
    //toolbar

    private String[] buttonItems, drawablesColors;

    public Drawables(){
    }

    public void addDrawablesForToolbar(){

        // white_cursor to drawable folder
        // icon_toolbar_search and icon_toolbar_clear

        try {
            for(int i = 0; i < TOOLBAR_DRAWABLES.length; i++){

                Path path = Paths.get(getPathToResourses(PATH_TO_TOOLBAR_FOLDER + TOOLBAR_DRAWABLES[i]));
                Path p = Paths.get(getPathToDrawable()+ "/" + TOOLBAR_DRAWABLES[i]);
                if(!Files.exists(p)){
                    Files.copy(path, p);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addButtonDrawables(String buttonStyle, String theme){
        List<String> list = null;
        chooseItemsByButtonStyle(theme,buttonStyle);

        try {
           for(int i = 0; i < buttonItems.length; i++){
               list = Files.readAllLines(Paths.get(
                       getPathToResourses(PATH_TO_XMLS + buttonItems[i] + XML)));
               Path path = Paths.get(getPathToDrawable() + buttonItems[i] + XML);
               if(!Files.exists(path)){
                   if(i != buttonItems.length - 1) {
                       for (int j = 0; j < list.size(); j++) {
                           if (list.get(j).contains("android:color=")) {
                               list.set(j, String.format(list.get(j), drawablesColors[i]));
                               break;
                           }
                       }
                   }
                   Files.write(path, list);
               }

           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPathToDrawable(){
        return PluginProject.mProject.getBasePath() + Constants.RES_PATH + "drawable/";
    }

    private String getPathToResourses(String value){
        return getClass().getResource(value).getPath();
    }

    private String[] getFlatButtonItems(){
        return new String[]{ };
    }

    private String[] getRaisedButtonItems(){
        return new String[]{ BTN_CORNER, RAISE_BTN_DISABLED, RAISE_BTN_PRESSED,
                RAISE_BTN_FOCUSED, BTN_RAISE};
    }

    private String[] getDarkThemeRaisedButtonItems(){
        return new String[]{CORNER_COLOR_DARK, DISABLED_COLOR_DARK, PRESSED_COLOR_DARK, FOCUSED_COLOR_DARK};
    }

    private String[] getLightThemeRaisedButtonItems(){
        return new String[]{CORNER_COLOR_LIGHT, DISABLED_COLOR_LIGHT, PRESSED_COLOR_LIGHT, FOCUSED_COLOR_LIGHT};
    }

    private void chooseItemsByButtonStyle(String theme, String buttonStyle){
        switch (buttonStyle){
            case MaterialButton.FLAT_BUTTON:
                buttonItems = getFlatButtonItems();
                drawablesColors = chooseFlatButtonItemsByTheme(theme);
            case MaterialButton.RAISED_BUTTON:
                buttonItems = getRaisedButtonItems();
                drawablesColors = chooseRaisedButtonItemsByTheme(theme);
        }
    }

    private String[] chooseRaisedButtonItemsByTheme(String theme){
        switch (theme){
            case Constants.DARK_THEME:
                return getDarkThemeRaisedButtonItems();
            case Constants.LIGHT_THEME:
                return getLightThemeRaisedButtonItems();
        }
        return null;
    }

    private String[] chooseFlatButtonItemsByTheme(String theme){
        switch (theme){
            case Constants.DARK_THEME:
            case Constants.LIGHT_THEME:
        }
        return null;
    }

}
