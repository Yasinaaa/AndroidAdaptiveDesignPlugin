package ru.itis.androidplugin.generator.helper;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.EverythingGlobalScope;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.itis.androidplugin.android.AndroidView;
import ru.itis.androidplugin.elements.MaterialChildRecyclerView;
import ru.itis.androidplugin.elements.MaterialItem;
import ru.itis.androidplugin.elements.MaterialRecyclerView;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.List;
import java.util.Stack;

public class AndroidLayoutParser extends DefaultHandler {

    private Project project;
    private VirtualFile file;
    private File file1;

    private AndroidView currentView;
    private int currentViewLevel;
    private Stack<Integer> prevLevels = new Stack<Integer>();
    private int level;

    public AndroidLayoutParser(Project project) {
        this.project = project;
    }

    public AndroidView parse(VirtualFile virtualFile) {
        this.file = virtualFile;
        try {
            return parse(virtualFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return new AndroidView();
        }
    }

    private AndroidView parse(InputStream inputStream) {
        currentViewLevel = 1;
        level = 1;
        this.currentView = new AndroidView();

        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(inputStream, this);

            return currentView;
        } catch (Exception e) {
            e.printStackTrace();
            return new AndroidView();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        level++;

        if ("fragment".equals(qName)) {
            return;
        }

        if ("include".equals(qName)) {
            String layoutValue = attributes.getValue("layout");
            if (layoutValue != null) {
                PsiFile include = findLayoutResource(file, project, getLayoutName(layoutValue));
                if (include != null) {
                    AndroidLayoutParser parser = new AndroidLayoutParser(project);
                    AndroidView view = parser.parse(include.getVirtualFile());

                    List<AndroidView> childNodes = view.getChildNodes();
                    if (!childNodes.isEmpty()) {
                        String id = getId(attributes);
                        if (id != null && id.length() > 0) {
                            if (childNodes.size() == 1) {
                                childNodes.get(0).setIdValue(id);
                            }
                        }

                        for (AndroidView node : childNodes) {
                            currentView.addSubView(node);
                        }
                    }

                    return;
                }
            }
        }

        if ("view".equals(qName)) {
            String className = attributes.getValue("class");
            if (className == null) {
                return;
            }
            qName = className;
        }

        String id = getId(attributes);
        if (id != null && id.length() > 0) {
            AndroidView view = new AndroidView();
            view.setTagName(qName);
            view.setIdValue(id);
            view.setMaterialItem(getMaterialItem(qName, attributes));
            currentView.addSubView(view);
            currentView = view;
            prevLevels.add(currentViewLevel);
            currentViewLevel = level;
        }
    }

    private static String getId(Attributes attributes) {
        String id = attributes.getValue("android:id");

        if (id != null && id.length() > 0) {
            int idStart = id.indexOf("/");
            if (idStart >= 0) {

                return id.substring(idStart + 1);
            }
        }

        return null;
    }

    private static MaterialItem getMaterialItem(String title, Attributes attributes){
        MaterialItem materialItem = null;
        if(title.contains("RecyclerView")){
            String tempText = null;
            materialItem = new MaterialRecyclerView();
            tempText = attributes.getValue("android:id");
            materialItem.mId = tempText.substring(tempText.indexOf("/") + 1);
            MaterialChildRecyclerView[] children = new MaterialChildRecyclerView[]{null, null, null};
            tempText = attributes.getValue("app:layout_item");
            children[0] = new MaterialChildRecyclerView(tempText.substring(tempText.indexOf("/") + 1),
                    MaterialChildRecyclerView.childRecyclerViewType[0]);
            tempText = attributes.getValue("app:layout_empty");
            if(tempText != null){
                children[1] = new MaterialChildRecyclerView(tempText.substring(tempText.indexOf("/") + 1),
                    MaterialChildRecyclerView.childRecyclerViewType[1]);
            }
            tempText = attributes.getValue("app:layout_loading");
            if(tempText != null){
                children[2] = new MaterialChildRecyclerView(tempText.substring(tempText.indexOf("/") + 1),
                        MaterialChildRecyclerView.childRecyclerViewType[2]);
            }
            materialItem.setСhild(children);
            materialItem.recyclerViewType = attributes.getValue("app:type_layout");
        }

        return materialItem;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        level--;
        if (currentView.getParent() != null
                && level < currentViewLevel) {
            currentViewLevel = prevLevels.pop();
            currentView = currentView.getParent();
        }
    }

    //Thanks for https://github.com/avast/android-butterknife-zelezny/blob/master/src/com/avast/android/butterknifezelezny/common/Utils.java

    private static PsiFile resolveLayoutResourceFile(VirtualFile file, Project project, String name) {
        // restricting the search to the current module - searching the whole project could return wrong layouts
        Module module = ModuleUtil.findModuleForFile(file, project);
        PsiFile[] files = null;
        if (module != null) {
            GlobalSearchScope moduleScope = module.getModuleWithDependenciesAndLibrariesScope(false);
            files = FilenameIndex.getFilesByName(project, name, moduleScope);
        }
        if (files == null || files.length <= 0) {
            // fallback to search through the whole project
            // useful when the project is not properly configured - when the resource directory is not configured
            files = FilenameIndex.getFilesByName(project, name, new EverythingGlobalScope(project));
            if (files.length <= 0) {
                return null; //no matching files
            }
        }

        // TODO - we have a problem here - we still can have multiple layouts (some coming from a dependency)
        // we need to resolve R class properly and find the proper layout for the R class
        return files[0];
    }

    /**
     * Try to find layout XML file by name
     *
     * @param file
     * @param project
     * @param fileName
     * @return
     */
    private static PsiFile findLayoutResource(VirtualFile file, Project project, String fileName) {
        String name = String.format("%s.xml", fileName);
        // restricting the search to the module of layout that includes the layout we are seaching for
        return resolveLayoutResourceFile(file,  project, name);
    }

    public static String getLayoutName(String layout) {
        if (layout == null || !layout.startsWith("@") || !layout.contains("/")) {
            return null; // it's not layout identifier
        }

        String[] parts = layout.split("/");
        if (parts.length != 2) {
            return null; // not enough parts
        }

        return parts[1];
    }
}
