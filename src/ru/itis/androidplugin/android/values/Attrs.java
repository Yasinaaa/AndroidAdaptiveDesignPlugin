package ru.itis.androidplugin.android.values;

import ru.itis.androidplugin.settings.Constants;
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
    public static final String PATH_VALUES = String.format("%sattrs.xml",Constants.VALUES_PATH);
    public static final String PATH = String.format("%s" + PATH_VALUES, PluginProject.mProject.getBasePath());

    public Attrs(){

    }

    public void addAttrsToProject() throws IOException {
        URL url = getClass().getResource(ATTRS);

        File attrsFile = null;
        try {
            attrsFile = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File addToProject = new File(PATH);
        if(!addToProject.exists()){

        }else if (attrsFile == attrsFile){
            Files.deleteIfExists(addToProject.toPath());
        }
        Files.copy(attrsFile.toPath(), addToProject.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

    }
}