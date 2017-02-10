/*
 * Copyright 2016 dvdandroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.itis.androidplugin.settings;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import ru.itis.androidplugin.view.Palette;

import java.awt.*;

/**
 * Created by yasina on 10.02.17.
 */
public class ToolWindowFactory implements com.intellij.openapi.wm.ToolWindowFactory {

    //TODO: get title from Settings and remove this item
    String mTitle = "Easy Adaptive Design";


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull com.intellij.openapi.wm.ToolWindow toolWindow) {
        ToolWindow newToolWindow = new ToolWindow();
        Content content = ContentFactory.SERVICE.getInstance().createContent(newToolWindow, "", false);

        toolWindow.setAvailable(true, null);
        toolWindow.setToHideOnEmptyContent(true);
        toolWindow.setTitle(mTitle);

        toolWindow.getContentManager().addContent(content);
    }

    public class ToolWindow extends SimpleToolWindowPanel {
        public ToolWindow() {
            super(false);
            add(new Palette().panel, BorderLayout.CENTER);
        }

    }

}
