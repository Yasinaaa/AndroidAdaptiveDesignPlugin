package ru.itis.androidplugin.settings;

import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.view.MainView;
import ru.itis.androidplugin.view.VisibleInvisible;

import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yasina on 06.04.17.
 */
public class FileOwnerListener implements Observer {

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
                //String[] answer = getOwner((String) arg);
                getOwner((String) arg);
                //mainView.itemActivityClassJLabel.setText(answer[0]);
                /*if(answer[1] != null){
                    mainView.titleParentIDJLabel.setVisible(true);
                    mainView.itemParentIDJLabel.setVisible(true);
                    mainView.itemParentIDJLabel.setText(answer[1]);
                    mainView.clickedMaterialItem = new MaterialChildRecyclerView((String) arg, "simple_item");
                    MainView.mainView.clickedMaterialItem.setView();
                }else {
                        /*mainView.clickedMaterialItem = new MaterialRecyclerView();
                        mainView.clickedMaterialItem.mLayoutPath = PluginProject.mLayoutPath;
                        MainView.mainView.clickedMaterialItem.setView();*/
                /*    mainView.currentMaterialItemParametersJPanel.setVisible(false);
                    mainView.titleParentIDJLabel.setVisible(false);
                    mainView.itemParentIDJLabel.setVisible(false);
                }*/

            }
        } else {
            System.out.println("FileOwnerObserver: Some other change to subject!");
        }
    }

    public static void getOwner(String path){
        //String[] answer = new String[3];
        File file = new File(path);
        VirtualFile layoutFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        try {
            InputStream stream = layoutFile.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader bufferReader = new BufferedReader(streamReader);
            String line = null;
            boolean isLayoutChild = false;
            while ((line=bufferReader.readLine()) != null){
                if(line.contains("tools:context=")){
                    MainView.mainView.itemActivityClassJLabel.setText(getAttrValue(line));
                    System.out.println("owner = " + MainView.mainView.itemActivityClassJLabel.getText());
                    break;
                }
                /*if(isLayoutChild){
                    answer[1] = setParentId(line);
                    break;
                }*/
                //if (line.contains("app:child_type_recyclerview=\"simple_item\"")){
                if (line.contains("app:layout_style=")){

                    MainView.mainView.titleLayoutTypeJLabel.setVisible(true);
                    MainView.mainView.itemLayoutTypeJLabel.setVisible(true);
                    MainView.mainView.itemLayoutTypeJLabel.setText(getAttrValue(line));
                }
                if (line.contains("app:parent_id=")){
                    isLayoutChild = true;
                    MainView.mainView.itemParentIDJLabel.setText(setParentId(line));
                    MainView.mainView.itemActivityClassJLabel.setText("");
                }
                if (line.contains("app:layout_type")){
                    MainView.mainView.invisibleJLabel.setText(getAttrValue(line));
                }
                /*if(line.contains("app:layout_type=\"bottom_sheet\">")){
                    MainView.mainView.itemParentIDJLabel.setText("");
                }*/

                if(line.contains("\">")) break;
            }

            VisibleInvisible.isChild(isLayoutChild, MainView.mainView);
            VisibleInvisible.cleanState(MainView.mainView);
            bufferReader.close();
            streamReader.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //return answer;
    }

    private static String getAttrValue(String line){
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            //answer[0] = matcher.group(1);
            return matcher.group(1);
        }
        return null;
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

    private static String setParentId(String line){
        String answer = null;
        int l1 = line.indexOf("/");
        int l2 = line.lastIndexOf("\"");
        answer = line.substring(line.indexOf("/") + 1, line.lastIndexOf("\""));
        System.out.println("parent id = " + answer);
        return answer;
    }
}

