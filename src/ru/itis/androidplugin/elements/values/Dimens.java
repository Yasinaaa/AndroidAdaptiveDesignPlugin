package ru.itis.androidplugin.elements.values;

import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    private String[] allValues = new String[]{
            "values", "values-hdpi", "values-xhdpi", "values-xxhdpi", "values-xxxhdpi"
    };
    private final String mTextXML= "<resources>\n</resources>";

    public Dimens(){

    }
    public void addAllDimens(String[] lines){
        for (String valueXML : allValues){
            addLines(getPathToDimens(valueXML), lines);
        }
    }

    private void addLines(String projectPath, String[] lines){
        List<String> list = null;

        try {
            list = Files.readAllLines(Paths.get(projectPath));
            for(int i=0; i<lines.length; i++){
                boolean l = list.contains(lines[i]);
                if (!list.contains(lines[i])){
                    list.add(list.size() - 1,"    " + lines[i]);
                }

            }

            Files.write(Paths.get(projectPath), list);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getPathToDimens(String value){
        String path = PluginProject.mProject.getBasePath() + Constants.RES_PATH + value;
        File file = new File(path);

        try {
            if (!file.exists()) {
                //file.createNewFile();
                file.mkdirs();
                FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile()+ "/dimens.xml");
                OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
                osw.write(mTextXML);
                osw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return path + "/dimens.xml";
    }



    public String[] getActivityDimens(){
        return new String[]{MDPI_ACTIVITY_HORISONTAL_MARGIN, MDPI_ACTIVITY_VERTICAL_MARGIN,
                MDPI_ACTIVITY_DETAIL_VERTICAL_MARGIN};
    }
}
