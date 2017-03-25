package ru.itis.androidplugin.elements.values;

import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static ru.itis.androidplugin.settings.Constants.MDPI_ACTIVITY_DETAIL_VERTICAL_MARGIN;
import static ru.itis.androidplugin.settings.Constants.MDPI_ACTIVITY_HORISONTAL_MARGIN;
import static ru.itis.androidplugin.settings.Constants.MDPI_ACTIVITY_VERTICAL_MARGIN;

/**
 * Created by yasina on 24.03.17.
 */
public class Dimens {

    private String projectPath;

    public Dimens(){

    }

    public void addLines(String[] lines){
        List<String> list = null;
        projectPath = getPathToDimens();

        try {
            list = Files.readAllLines(Paths.get(projectPath));
            list.add(list.size() - 1,"    ");
            for(int i=0; i<lines.length; i++){
                if (!list.contains(lines[i]))
                    list.add(list.size() - 1,"    " + lines[i]);
                    //list.add(list.size() - (lines.length-i), lines[i]);

            }
            Files.write(Paths.get(projectPath), list);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getPathToDimens(){
        return PluginProject.mProject.getBasePath() + Constants.VALUES_PATH
                + "/dimens.xml";
    }

    public String[] getActivityDimens(){
        return new String[]{MDPI_ACTIVITY_HORISONTAL_MARGIN, MDPI_ACTIVITY_VERTICAL_MARGIN,
                MDPI_ACTIVITY_DETAIL_VERTICAL_MARGIN};
    }
}
