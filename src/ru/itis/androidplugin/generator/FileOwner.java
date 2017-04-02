package ru.itis.androidplugin.generator;

import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.elements.MaterialChildRecyclerView;
import ru.itis.androidplugin.elements.MaterialRecyclerView;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.settings.ToolWindowFactory;
import ru.itis.androidplugin.view.MainView;

import javax.swing.*;
import java.awt.event.ActionListener;
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

        /*
        private JLabel classLabel, parentIdLabel, parentIdItem;
        public FileOwnerListener(JLabel classLabel, JLabel parentIdLabel, JLabel parentIdItem) {
            this.classLabel = classLabel;
            this.parentIdLabel = parentIdLabel;
            this.parentIdItem = parentIdItem;
        }*/
        private MainView mainView;

        public FileOwnerListener(MainView mainView){
            this.mainView = mainView;
        }

        @Override
        public void update(Observable obj, Object arg) {
            if (arg instanceof String) {
                if (chooseType((String) arg).equals(fileTypes[0])){
                    String[] answer = getOwner((String) arg);
                    mainView.itemActivityClassJLabel.setText(answer[0]);
                    if(answer[1] != null){
                        mainView.titleParentIDJLabel.setVisible(true);
                        mainView.itemParentIDJLabel.setVisible(true);
                        mainView.itemParentIDJLabel.setText(answer[1]);
                        mainView.clickedMaterialItem = new MaterialChildRecyclerView((String) arg, "simple_item");
                        MainView.mainView.clickedMaterialItem.setView();
                    }else {
                        /*mainView.clickedMaterialItem = new MaterialRecyclerView();
                        mainView.clickedMaterialItem.mLayoutPath = PluginProject.mLayoutPath;
                        MainView.mainView.clickedMaterialItem.setView();*/
                        mainView.currentMaterialItemParametersJPanel.setVisible(false);
                        mainView.titleParentIDJLabel.setVisible(false);
                        mainView.itemParentIDJLabel.setVisible(false);
                    }

                }
            } else {
                System.out.println("FileOwnerObserver: Some other change to subject!");
            }
        }

        public static String[] getOwner(String path){
            String[] answer = new String[3];
            File file = new File(path);
            VirtualFile layoutFile = LocalFileSystem.getInstance().findFileByIoFile(file);
            try {
                InputStream stream = layoutFile.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(stream);
                BufferedReader bufferReader = new BufferedReader(streamReader);
                String line = null;
                boolean isItemChild = false;
                while ((line=bufferReader.readLine()) != null){
                    if(line.contains("tools:context=")){
                        Pattern pattern = Pattern.compile("\"(.*?)\"");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            answer[0] = matcher.group(1);
                        }
                        System.out.println("owner = " + answer[0]);
                        break;
                    }
                    if(isItemChild){
                        answer[1] = isRecyclerViewChild(line);
                        break;
                    }
                    if (line.contains("app:child_type_recyclerview=\"simple_item\"")){
                        /*int l1 = line.indexOf("\"" + 1);
                        int l2 = line.lastIndexOf("\"");
                        answer[2] = line.substring(line.indexOf("\""), line.lastIndexOf("\""));*/
                        isItemChild = true;
                    }

                    if(line.contains("\">")) break;
                }

                bufferReader.close();
                streamReader.close();
                stream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return answer;
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

        private static String isRecyclerViewChild(String line){
            String answer = null;
            if (line.contains("app:parent_recyclerview=")){
                int l1 = line.indexOf("/");
                int l2 = line.lastIndexOf("\"");
                answer = line.substring(line.indexOf("/") + 1, line.lastIndexOf("\""));
                System.out.println("parent id = " + answer);
            }
            return answer;
        }
    }


}
