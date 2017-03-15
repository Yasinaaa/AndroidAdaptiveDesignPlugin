package ru.itis.androidplugin.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by yasina on 15.03.17.
 */
public class FileChangedListener implements ApplicationComponent, BulkFileListener{
    public MessageBusConnection mMessageBusConnection;

    public FileChangedListener() {
        mMessageBusConnection = ApplicationManager.getApplication().getMessageBus().connect();
    }

    @Override
    public void initComponent() {
        mMessageBusConnection.subscribe(VirtualFileManager.VFS_CHANGES, this);
    }

    @Override
    public void disposeComponent() {
        mMessageBusConnection.disconnect();
    }

    @NotNull
    @Override
    public String getComponentName() {
        return null;
    }

    @Override
    public void before(@NotNull List<? extends VFileEvent> events) {
        for(VFileEvent v: events){
            System.out.println("before =" + v.getFile().getPath());
        }
    }

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        for(VFileEvent v: events){
            System.out.println("after =" + v.getFile().getPath());
        }
    }
}
