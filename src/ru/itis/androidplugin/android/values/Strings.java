package ru.itis.androidplugin.android.values;

import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 24.03.17.
 */
public class Strings {

    private static final String STRING_TAG = "<string name=\"%s\">%s</string>";

    public static void addLines(List<String> allStrings){
        List<String> list = null;
        String valuePath = getPathToString();

        try {
            list = Files.readAllLines(Paths.get(valuePath));
            for(int i=0; i<allStrings.size(); i++){
                String id = allStrings.get(i).replaceAll(" ","_").toLowerCase();
                String newTag = String.format(STRING_TAG, new String[]{id, allStrings.get(i)});
                boolean l = list.contains("    " + newTag);
                if (!list.contains("    " + newTag)){
                    list.add(list.size() - 1,"    " + newTag);
                }

            }

            Files.write(Paths.get(valuePath), list);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getPathToString(){
        return PluginProject.mProject.getBasePath() + Constants.RES_PATH + "values/strings.xml";
    }
}
