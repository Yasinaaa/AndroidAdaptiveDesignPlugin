package ru.itis.androidplugin.android;

import ru.itis.androidplugin.android.values.Strings;
import ru.itis.androidplugin.presenters.BottomNavigationPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 04.04.17.
 */
public class Menu {
    private static final String BANNER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<menu xmlns:android=\"http://schemas.android.com/apk/res/android\">\n";
    private static final String END = "\n </menu>";
    private static final String ITEM = "\n <item\n" +
            "        android:id=\"@+id/%s\"\n" +
            "        android:icon=\"@drawable/%s\"\n" +
            "        android:title=\"@string/%s\" />";

    public static File generateMenu(String path, BottomNavigationPresenter.ItemBottomNavigation[] allItems){
        File file = new File(path);

        try {
            FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
            osw.write(BANNER);
            List<String> strings = new ArrayList<String>();
            for (int i=0; i<allItems.length; i++){
                if(allItems[i] != null){
                    strings.add(allItems[i].getTitle());
                    if(allItems[i].getDrawable().contains(".")){
                        allItems[i].setDrawable(allItems[i].getDrawable().substring(0, allItems[i].getDrawable().indexOf(".")));
                    }
                    osw.write(String.format(ITEM, new String[]{
                            allItems[i].getTitle(), allItems[i].getDrawable(),
                            allItems[i].getTitle().replaceAll(" ","_").toLowerCase()
                    }));
                }
            }
            Strings.addLines(strings);
            osw.write(END);
            osw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
