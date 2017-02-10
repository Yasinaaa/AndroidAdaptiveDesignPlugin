package ru.itis.androidplugin;

import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * Created by yasina on 15.01.17.
 */
public class ApplicationCOmponent implements ApplicationComponent {
    public ApplicationCOmponent() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here

    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "ApplicationCOmponent";
    }
}
