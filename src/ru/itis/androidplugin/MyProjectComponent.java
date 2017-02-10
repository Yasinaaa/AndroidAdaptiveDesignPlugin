package ru.itis.androidplugin;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Created by yasina on 15.01.17.
 */
public class MyProjectComponent implements ProjectComponent {

    private Project mProject;

    public MyProjectComponent(Project project) {
        mProject = project;
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
        return "MyProjectComponent";
    }

    @Override
    public void projectOpened() {
        // called when project is opened
      /*  File f = new File("/home/yasina/IntelliJIDEAProjects/myfolder/TestAndroidPluginApp");
        f.mkdir();*/

     /*   System.out.println(mProject.getBasePath());
        boolean success = (new File("/home/yasina/IntelliJIDEAProjects/myfolder/TestAndroidPluginApp/mynewfolder")).mkdirs();
        if (!success) {
            System.out.println("failed");// Directory creation failed
        }

        TempDialog dialog = new TempDialog();
        dialog.pack();
        dialog.setVisible(true);*/

    }

    @Override
    public void projectClosed() {
        // called when project is being closed
    }
}
