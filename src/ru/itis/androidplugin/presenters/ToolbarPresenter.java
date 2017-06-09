package ru.itis.androidplugin.presenters;

import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.generator.CommonGenerators;
import ru.itis.androidplugin.interfaces.ToolbarInterface;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by yasina on 04.05.17.
 */
public class ToolbarPresenter extends CommonPresenter{

    public static final String TOOLBAR = "/xmls/toolbar.xml";
    public static final String PATH_VALUES = String.format("%stoolbar.xml", Constants.VALUES_PATH);
    public static final String PATH = String.format("%s" + PATH_VALUES, PluginProject.mProject.getBasePath());

    private ToolbarInterface toolbarTypes;

    public ToolbarPresenter(ToolbarInterface toolbarTypes){
        super();
        this.toolbarTypes = toolbarTypes;
    }

    public void setParameters(String style){
        switch (style){
            case ToolbarInterface.STANDARD_TYPE:
                toolbarTypes.setParametersStandardStyle();
                break;
            case ToolbarInterface.SEARCH_TYPE:
                toolbarTypes.setParametersSearchStyle();
                break;
            case ToolbarInterface.REMOVE_TYPE:
                toolbarTypes.setParametersRemoveStyle();
                break;
            case ToolbarInterface.SORT_TYPE:
                toolbarTypes.setParametersSortStyle();
                break;
            case ToolbarInterface.EXTENDED_TYPE:
                toolbarTypes.setParametersExtendedStyle();
                break;
            default:
                toolbarTypes.setParametersStandardStyle();
                break;
        }
    }

    public void getParameters(String style){
        switch (style){
            case ToolbarInterface.STANDARD_TYPE:
                toolbarTypes.getParametersStandardStyle();
                break;
            case ToolbarInterface.SEARCH_TYPE:
                toolbarTypes.getParametersSearchStyle();
                break;
            case ToolbarInterface.REMOVE_TYPE:
                toolbarTypes.getParametersRemoveStyle();
                break;
            case ToolbarInterface.SORT_TYPE:
                toolbarTypes.getParametersSortStyle();
                break;
            case ToolbarInterface.EXTENDED_TYPE:
                toolbarTypes.getParametersExtendedStyle();
                break;
            default:
                toolbarTypes.getParametersStandardStyle();
                break;
        }
    }

    public void setChoosedType(String style, StringBuilder stringBuilder){
        switch (style){
            case ToolbarInterface.STANDARD_TYPE:
                toolbarTypes.generateStandardToolbar(stringBuilder);
                break;
            case ToolbarInterface.SEARCH_TYPE:
                toolbarTypes.generateSearchToolbar(stringBuilder);
                break;
            case ToolbarInterface.REMOVE_TYPE:
                toolbarTypes.generateRemoveToolbar(stringBuilder);
                break;
            case ToolbarInterface.SORT_TYPE:
                toolbarTypes.generateSortToolbar(stringBuilder);
                break;
            case ToolbarInterface.EXTENDED_TYPE:
                toolbarTypes.generateExtendedToolbar(stringBuilder);
                break;
            default:
                toolbarTypes.generateStandardToolbar(stringBuilder);
                break;
        }
    }

    public void createToolbarXml(){
        URL url = getClass().getResource(TOOLBAR);
        try {
            CommonGenerators.addXMLToProject(url, PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToolbarToStyleFile(){
        URL url = getClass().getResource(TOOLBAR);
        try {
            CommonGenerators.addXMLToProject(url, PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCodeForJavaClass(){

    }

    @Override
    public void setChildViewParameters() {

    }

    public void setAllParentIDs(JComboBox recyclerView, JComboBox navigationView){
        List<AndroidView> elements = getAllParentIDs();
        for (int i=0; i<elements.size(); i++) {
            if(elements.get(i).getClassName().contains("RecyclerView")){
                recyclerView.addItem(elements.get(i).getIdValue());
            }
            if(elements.get(i).getClassName().contains("NavigationView")){
                navigationView.addItem(elements.get(i).getIdValue());
            }
        }
    }


}
