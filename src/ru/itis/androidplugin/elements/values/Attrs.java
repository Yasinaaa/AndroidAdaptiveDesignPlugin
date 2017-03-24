package ru.itis.androidplugin.elements.values;

import ru.itis.androidplugin.settings.PluginProject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


/**
 * Created by yasina on 24.03.17.
 */
public class Attrs {

    public static final String ATTRS = "/xmls/attrs.xml";
    public static final String PATH_VALUES = "/app/src/main/res/values/attrs.xml";
    public static final String PATH = String.format("%s" + PATH_VALUES, PluginProject.mProject.getBasePath());

    public Attrs(){

    }

    public void addAttrsToProject(){
        URL url = getClass().getResource(ATTRS);

        File attrsFile = null;
        try {
            attrsFile = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File addToProject = new File(PATH);
        if(!addToProject.exists()){
            try {
                Files.copy(attrsFile.toPath(), addToProject.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
