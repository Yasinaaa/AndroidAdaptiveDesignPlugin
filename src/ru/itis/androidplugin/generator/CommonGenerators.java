package ru.itis.androidplugin.generator;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by yasina on 04.05.17.
 */
public class CommonGenerators {

    public static void addXMLToProject(URL urlToPluginFile, String pathInProject) throws IOException {

        File pluginFile = null;
        try {
            pluginFile = new File(urlToPluginFile.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File addToProject = new File(pathInProject);
        if(!addToProject.exists()){

        }else if (addToProject == pluginFile){
            Files.deleteIfExists(addToProject.toPath());
        }

        Files.copy(pluginFile.toPath(), addToProject.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

    }
}
