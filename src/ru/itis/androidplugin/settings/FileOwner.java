package ru.itis.androidplugin.settings;

import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import ru.itis.androidplugin.view.MaterialChildRecyclerView;
import ru.itis.androidplugin.view.MainView;

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
}
