package ru.itis.androidplugin.android;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import ru.itis.androidplugin.settings.PluginProject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by yasina on 30.03.17.
 */
public class AndroidManifest extends DefaultHandler {

    private final String SCREEN_SUPPORT = "    <supports-screens \n" + "%s%s%s%s%s" + "/>";

    private final String SMALL_SCREEN = "        android:smallScreens=\"true\"\n";
    private final String NORMAL_SCREEN = "        android:normalScreens=\"true\"\n";
    private final String LARGE_SCREEN = "        android:largeScreens=\"true\"\n";
    private final String XLARGE_SCREEN = "        android:xlargeScreens=\"true\"\n";
    private final String ANY_DENSITY = "        android:anyDensity=\"true\" ";
    private final String[] ALL_SCREENS = new String[]{
            SMALL_SCREEN, NORMAL_SCREEN, LARGE_SCREEN, XLARGE_SCREEN, ANY_DENSITY
    };

    private String packageName;
    public static VirtualFile manifestFile;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public AndroidManifest(VirtualFile layoutFile){
        Module module = getModuleOfFile(layoutFile);
        manifestFile = getManifestFile(module, layoutFile);
        parse(manifestFile);
    }


    public void setDifferentScreenSupport(){
        //todo: change
        File androidManifest = new File(manifestFile.getCanonicalPath());
        /*try {
            List<String> list = Files.readAllLines(Paths.get(manifestFile.getCanonicalPath()));
            String supportScreen = String.format(SCREEN_SUPPORT, ALL_SCREENS);
            boolean l = list.contains(supportScreen);
            if (!list.contains(supportScreen)){
                list.add(list.size() - 1, supportScreen);
            }

            Files.write(Paths.get(manifestFile.getCanonicalPath()), list);

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }


    private VirtualFile getManifestFile(Module module, VirtualFile layoutFile) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        VirtualFile[] contentRoots = moduleRootManager.getContentRoots();
        VirtualFile result = lookupForManifest(layoutFile.getParent().getParent(), contentRoots);
        if (result == null) {
            throw new RuntimeException("AndroidManifest.xml not found");
        }
        return result;
    }
    public Module getModuleOfFile(VirtualFile layoutFile) {
        ProjectRootManager rootManager = ProjectRootManager.getInstance(PluginProject.mProject);
        Module module = rootManager.getFileIndex().getModuleForFile(layoutFile);
        if (module == null) {
            throw new RuntimeException("Failed to determine module with selected layout");
        }
        return module;
    }

    private VirtualFile lookupForManifest(VirtualFile dir, VirtualFile[] topDirs) {
        //noinspection UnsafeVfsRecursion
        for (VirtualFile file : dir.getChildren()) {
            if (!file.isDirectory() && "AndroidManifest.xml".equals(file.getName())) {
                return file;
            }
        }

        for (VirtualFile topDir : topDirs) {
            if (topDir.equals(dir)) {
                return null;
            }
        }

        VirtualFile parent = dir.getParent();
        if (!dir.isDirectory()) {
            return null;
        }

        return lookupForManifest(parent, topDirs);
    }

    public void parse(VirtualFile virtualFile) {
        try {
            parse(virtualFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(InputStream inputStream) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(inputStream, this);
        } catch (Exception e) {
            e.printStackTrace();
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
        if (qName.equals("manifest")) {
            setPackageName(attributes.getValue("package"));
        }
    }

}
