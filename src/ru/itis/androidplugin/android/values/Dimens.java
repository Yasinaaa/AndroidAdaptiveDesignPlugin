package ru.itis.androidplugin.android.values;

import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


/**
 * Created by yasina on 24.03.17.
 */
public class Dimens {

    private String[] allValues = new String[]{
            "values", "values-hdpi", "values-xhdpi", "values-xxhdpi", "values-xxxhdpi"
    };
    private double[] allDensitys = new double[]{
            1.0, 1.2, 1.8, 2.4, 3.0
    };
    private final String mTextXML= "<resources>\n</resources>";

    public Dimens(){

    }
    public void addAllDimens(String[] allDimensTag, int[] allDimensValue){
        for (int i = 0; i<allValues.length; i++){
            addLines(getPathToDimens(allValues[i]), allDimensTag, allDimensValue, allDensitys[i]);
        }
    }

    private void addLines(String valuePath, String[] allDimensTag, int[] allDimensValue, double density){
        List<String> list = null;

        try {
            list = Files.readAllLines(Paths.get(valuePath));
            for(int i=0; i<allDimensTag.length; i++){
                String newDensity = new Float(allDimensValue[i]*density).toString().
                        replaceAll("\\.?0*$", "");
                String newTag = String.format(allDimensTag[i], newDensity);
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



}
