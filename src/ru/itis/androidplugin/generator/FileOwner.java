package ru.itis.androidplugin.generator;

import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.settings.ToolWindowFactory;

import javax.swing.*;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yasina on 15.03.17.
 */
public class FileOwner extends Observable{

    private String filePath;

    public FileOwner() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        setChanged();
        notifyObservers(filePath);
    }


    public static class FileOwnerListener implements Observer {

        private JLabel label;

        public FileOwnerListener(JLabel label) {
            this.label = label;
        }

        @Override
        public void update(Observable obj, Object arg) {
            if (arg instanceof String) {
                if (chooseType((String) arg).equals(fileTypes[0])) label.setText(getOwner((String) arg));
            } else {
                System.out.println("FileOwnerObserver: Some other change to subject!");
            }
        }

        public static String getOwner(String path){
            String owner = null;
            File file = new File(path);
            VirtualFile layoutFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            try {
                InputStream stream = layoutFile.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(stream);
                BufferedReader bufferReader = new BufferedReader(streamReader);
                String line = null;
                while ((line=bufferReader.readLine()) != null){
                    if(line.contains("tools:context=")){
                        Pattern pattern = Pattern.compile("\"(.*?)\"");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            owner = matcher.group(1);
                        }
                        System.out.println("owner = " + owner);
                        break;
                    }
                    if(line.contains("\">")) break;
                }

                bufferReader.close();
                streamReader.close();
                stream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return owner;
        }

        public String[] fileTypes = new String[]{
                "Xml","Default"
        };

        private String chooseType(String filePath){
            String type = null;
            if(filePath != null){

                if (filePath.matches("(.*)/values(\\-*)(.*)") || !filePath.contains(".xml")){
                    type = fileTypes[1];
                    ToolWindowFactory.pluginToolWindow.setAvailable(false, null);

                }else {
                    type = fileTypes[0];
                    ToolWindowFactory.pluginToolWindow.setAvailable(true, null);
                    ToolWindowFactory.pluginToolWindow.show(null);
                }

            }
            return type;
        }
    }


}
