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

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.itis.androidplugin.adapters.ViewRender;
import ru.itis.androidplugin.elements.MaterialItem;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

/**
 * Created by yasina on 09.03.17.
 */
public class MainView extends JPanel {

    public JPanel panel;
    private JList materialJList;
    public JTextField itemMaterialItemJTextField;
    public JButton createButton;
    public JLabel titleActivityClassJLabel;
    public JLabel itemActivityClassJLabel;
    public JLabel titleMaterialItemJLabel;
    public JLabel nextIconJLabel;
    public JLabel prevIconJLabel;
    public JButton createItemLayoutButton;
    public JLabel itemParentViewJLabel;
    public JLabel titleParentViewJLabel;
    public JTextField itemParentViewJTextField;
    public JList layoutItemsJList;
    public JLabel layoutItemsJLabel;
    public JPanel currentMaterialItemParametersJPanel;
    public JLabel itemMaterialItemJLabel;
    private int lastIndex;
    public LinkedList<MaterialItem> tenClickedMaterialItems;
    public int currentItem = 0;
    public MaterialItem clickedMaterialItem;

    public MainView() {
        //TODO change this part
        // begin
        DefaultListModel<MaterialItem> listModel = new DefaultListModel<>();
        for (MaterialItem item : Constants.mViewMaterialItems) {
            listModel.addElement(item);
        }
        // end

        init(listModel);

    }

    private void init(DefaultListModel<MaterialItem> listModel) {
        tenClickedMaterialItems = new LinkedList<MaterialItem>();

        materialJList.setCellRenderer(new ViewRender());
        materialJList.setModel(listModel);
        materialJList.setLayoutOrientation(JList.VERTICAL_WRAP);
        materialJList.setBorder(new EmptyBorder(10, 10, 10, 10));
        materialJList.addMouseListener(new RightClickPopup());
        materialJList.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                materialJList.setVisibleRowCount(5);
            }
        });
        materialJList.setVisibleRowCount(5);

        layoutItemsJLabel.setVisible(false);
        layoutItemsJList.setVisible(false);
        currentMaterialItemParametersJPanel.setVisible(false);
        currentMaterialItemParametersJPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        titleMaterialItemJLabel.setVisible(false);
        itemMaterialItemJTextField.setVisible(false);
        titleParentViewJLabel.setVisible(false);
        itemParentViewJLabel.setVisible(false);
        createButton.setVisible(false);
        createItemLayoutButton.setVisible(false);
        prevIconJLabel.setVisible(false);
        prevIconJLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                prevClicked();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        nextIconJLabel.setVisible(false);
        nextIconJLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextClicked();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
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
        panel.setLayout(new GridLayoutManager(5, 5, new Insets(10, 10, 10, 10), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel.add(scrollPane1, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        materialJList = new JList();
        scrollPane1.setViewportView(materialJList);
        titleActivityClassJLabel = new JLabel();
        titleActivityClassJLabel.setText("ActivityClass");
        panel.add(titleActivityClassJLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemActivityClassJLabel = new JLabel();
        itemActivityClassJLabel.setText("MainActivity");
        panel.add(itemActivityClassJLabel, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        layoutItemsJLabel = new JLabel();
        layoutItemsJLabel.setText("Layout items");
        panel.add(layoutItemsJLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prevIconJLabel = new JLabel();
        prevIconJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/back_arrow.png")));
        prevIconJLabel.setText("");
        panel.add(prevIconJLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        layoutItemsJList = new JList();
        panel.add(layoutItemsJList, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        currentMaterialItemParametersJPanel = new JPanel();
        currentMaterialItemParametersJPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(currentMaterialItemParametersJPanel, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleParentViewJLabel = new JLabel();
        titleParentViewJLabel.setText("Parent ID");
        currentMaterialItemParametersJPanel.add(titleParentViewJLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemParentViewJTextField = new JTextField();
        currentMaterialItemParametersJPanel.add(itemParentViewJTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        itemMaterialItemJTextField = new JTextField();
        itemMaterialItemJTextField.setText("");
        currentMaterialItemParametersJPanel.add(itemMaterialItemJTextField, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        itemParentViewJLabel = new JLabel();
        itemParentViewJLabel.setText("");
        currentMaterialItemParametersJPanel.add(itemParentViewJLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleMaterialItemJLabel = new JLabel();
        titleMaterialItemJLabel.setText("");
        currentMaterialItemParametersJPanel.add(titleMaterialItemJLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemMaterialItemJLabel = new JLabel();
        itemMaterialItemJLabel.setText("");
        currentMaterialItemParametersJPanel.add(itemMaterialItemJLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createItemLayoutButton = new JButton();
        createItemLayoutButton.setText("Create ");
        currentMaterialItemParametersJPanel.add(createItemLayoutButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createButton = new JButton();
        createButton.setText("Save layout");
        panel.add(createButton, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextIconJLabel = new JLabel();
        nextIconJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/next_arrow.png")));
        nextIconJLabel.setText("");
        panel.add(nextIconJLabel, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }


    class RightClickPopup extends MouseAdapter {

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
            /*itemPictureJLabel.setIcon(Constants.mViewMaterialItems[index].mIcon);
            itemPictureJLabel.setText(Constants.mViewMaterialItems[index].mViewName);
            itemPictureJLabel.setHorizontalTextPosition(JLabel.CENTER);
            itemPictureJLabel.setVerticalTextPosition(JLabel.BOTTOM);*/

            clickedMaterialItem = Constants.mViewMaterialItems[index];
            clickedMaterialItem.setView(MainView.this);

            if (clickedMaterialItem.getParent() != null)
                clickedMaterialItem.hideNotNeededThings(MainView.this);

            clickedMaterialItem.mLayoutPath = PluginProject.mLayoutPath;
            clickedMaterialItem.addItemToHistoryList(MainView.this);
            list.setSelectedIndex(index);
        }

    }

    public void setBackNextLabelsVisiblility() {
        System.out.println("tenClickedMaterialItems.size()=" + tenClickedMaterialItems.size());
        if (tenClickedMaterialItems.size() >= 2)
            prevIconJLabel.setVisible(true);
        if (tenClickedMaterialItems.size() >= 3)
            nextIconJLabel.setVisible(true);
    }

    public void prevClicked() {
        System.out.println("prev clicked");
        nextIconJLabel.setVisible(true);
        MaterialItem materialItem = tenClickedMaterialItems.get(currentItem - 1);
        materialItem.setView(MainView.this);
    }

    public void nextClicked() {
        MaterialItem materialItem = tenClickedMaterialItems.get(currentItem + 1);
        materialItem.setView(MainView.this);
    }


}