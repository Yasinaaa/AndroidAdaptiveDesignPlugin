package ru.itis.androidplugin.android;

import com.sun.org.apache.xpath.internal.operations.And;
import ru.itis.androidplugin.generator.xml.helper.AndroidLayoutParser;
import ru.itis.androidplugin.view.MaterialItem;
import ru.itis.androidplugin.generator.classes.helper.*;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.settings.PluginProject;

import java.util.ArrayList;
import java.util.List;

public class AndroidView{

    private final String TOOLBAR = "Toolbar";

    private String tagName;
    private String classSimpleName;
    private String className;
    private String idValue;
    private String camelCaseId;
    private MaterialItem materialItem;

    private AndroidView parent;
    private List<AndroidView> subViews = new ArrayList<AndroidView>();

    public AndroidView() {
    }

    public static AndroidView getAndroidViews(VirtualFile layoutFile) {
        AndroidLayoutParser parser = new AndroidLayoutParser(PluginProject.mProject);
        return parser.parse(layoutFile);
    }

    public String getTagName() {

        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName.trim();
        if (tagName.contains(".")) {
            className = tagName.replaceAll("\\$", ".");
            classSimpleName = ClassHelper.getClassNameFromFullQualified(tagName).replaceAll("\\$", ".");
        } else {
            if (tagName.equalsIgnoreCase("View")
                    || tagName.equals("ViewStub")
                    || tagName.equals("TextureView")
                    || tagName.equals("Surface")
                    || tagName.equals("SurfaceView")
                    ) {
                className = "android.view." + tagName;
            } else if (tagName.equals("GestureOverlayView")) {
                className = "android.gesture." + tagName;
            } else if (tagName.equals("WebView")) {
                className = "android.webkit." + tagName;
            }else if (tagName.equals("RecyclerView")) {
                className = "android.support.v7.widget.RecyclerView";
            } else {
                className = "android.widget." + tagName;
            }
            classSimpleName = tagName;
        }
    }
    /*public String getChildPath(){
        if(className.contains("RecyclerView")){

        }
        return
    }*/

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
        camelCaseId = ClassHelper.formatCamelCaseFromUnderscore(idValue);
    }

    public void addSubView(AndroidView view) {
        view.setParent(this);
        this.subViews.add(view);
    }

    public String getCamelCaseId() {
        return camelCaseId;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public String getClassName() {
        return className;
    }


    public List<AndroidView> getChildNodes() {
        return subViews;
    }

    public List<AndroidView> getAllChildViews() {
        List<AndroidView> result = new ArrayList<AndroidView>();
        collectViews(result);
        return result;
    }

    public AndroidView isHasToolbar(){
        List<AndroidView> result = getAllChildViews();
        for(AndroidView androidView: result){
            if (androidView.tagName.contains(TOOLBAR)){
                return androidView;
            }
        }
        return null;
    }

    private void collectViews(List<AndroidView> result) {
        for (AndroidView view : subViews) {
            result.add(view);
            view.collectViews(result);
        }
    }

    public boolean isContainsRecyclerView(){
        List<AndroidView> allChildViews = getAllChildViews();
        for(AndroidView androidView : allChildViews){
            if(androidView.materialItem.mViewName.equals("RecyclerView")){
                return true;
            }
        }
        return false;
    }

    public MaterialItem getMaterialItem() {
        return materialItem;
    }

    public void setMaterialItem(MaterialItem materialItem) {
        this.materialItem = materialItem;
    }

    public AndroidView getParent() {
        return parent;
    }

    public void setParent(AndroidView parent) {
        this.parent = parent;
    }


    public String getNodeName() {
        return idValue + " - " +  className;
    }

}