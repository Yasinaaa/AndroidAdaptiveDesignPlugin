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

package ru.itis.androidplugin.view;

import ru.itis.androidplugin.adapters.ViewRender;
import ru.itis.androidplugin.elements.MaterialItem;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.UtilsEnvironment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author dvdandroid
 */
public class MainView extends JPanel {

    public JPanel panel;
    private JList<MaterialItem> parentColors;
    private JButton saveLayoutAndCreateButton;
    private ViewParameters mSupplemementalViewParameter;
    private int lastIndex;

    public MainView() {
        DefaultListModel<MaterialItem> listModel = new DefaultListModel<>();
        for (MaterialItem item : Constants.mViewMaterialItems) {
            listModel.addElement(item);

        }

        parentColors.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        parentColors.setCellRenderer(new ViewRender());
        parentColors.setModel(listModel);
        parentColors.setVisibleRowCount(4);
        parentColors.setBorder(new EmptyBorder(10, 10, 10, 10));

        /*childrenColors.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        childrenColors.setCellRenderer(new ColorRender());
        childrenColors.addMouseListener(new RightClickPopup());
        */

        parentColors.addMouseListener(new RightClickPopup());


    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel.add(scrollPane1, BorderLayout.NORTH);
        parentColors = new JList();
        scrollPane1.setViewportView(parentColors);
        saveLayoutAndCreateButton = new JButton();
        saveLayoutAndCreateButton.setText("Save layout and create another versions of them");
        panel.add(saveLayoutAndCreateButton, BorderLayout.SOUTH);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

    class RightClickPopup extends MouseAdapter {

        private static final String PASTE = "Paste %s element as %s";

        private JPopupMenu popup;

        @Override
        public void mousePressed(MouseEvent e) {
            JList list = (JList) e.getSource();
            if (list.locationToIndex(e.getPoint()) == -1 && !e.isShiftDown()) {
                list.clearSelection();
                return;
            }

            int index = list.locationToIndex(e.getPoint());

            if (index != -1 && !list.getCellBounds(index, index).contains(e.getPoint())) {
                return;
            }
            //TODO: change this thing
            Constants.mViewMaterialItems[index].setView(mSupplemementalViewParameter, panel);
           /* buildPopup(list, index);


            if (list == parentColors) {


                lastIndex = index;


                if (SwingUtilities.isRightMouseButton(e)) {
                    buildPopup(list, index);
                    //

                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            } else {
                System.out.println("child index=" + index);
                buildPopup(list, index);
                popup.show(e.getComponent(), e.getX(), e.getY());
            }*/

            list.setSelectedIndex(index);
        }


        private void buildPopup(JList list, int index) {
            popup = new JPopupMenu();

            JMenuItem materialViewJMenuItems = new JMenuItem(String.format(PASTE, Constants.THIS,
                    String.format(Constants.SET_MATERIAL_ITEM, Constants.mViewMaterialItems[index].mViewName)));
            materialViewJMenuItems.addActionListener(e -> UtilsEnvironment.insertInEditor(
                    Constants.mViewMaterialItems[index].mViewParametrs));

            this.popup.add(materialViewJMenuItems);

        }



    }

}