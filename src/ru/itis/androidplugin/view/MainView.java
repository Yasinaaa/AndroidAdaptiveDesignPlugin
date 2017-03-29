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

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ru.itis.androidplugin.adapters.ViewRender;
import ru.itis.androidplugin.elements.MaterialItem;
import ru.itis.androidplugin.generation.NewLayoutsCreating;
import ru.itis.androidplugin.settings.ActivityInit;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.FileParameters;
import ru.itis.androidplugin.settings.PluginProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Observer;

/**
 * Created by yasina on 09.03.17.
 */
public class MainView extends JPanel {

    public JPanel panel;
    private JList materialJList;
    public JTextField itemMaterialItemJTextField;
    public JButton saveLayoutButton;
    public JLabel titleActivityClassJLabel;
    public JLabel addActivityClassJLabel;
    public JLabel titleMaterialItemJLabel;
    public JLabel nextIconJLabel;
    public JLabel iconPrevJLabel;
    public JLabel titleParentViewJLabel;
    public JTextField itemParentViewJTextField;
    public JList itemsLayoutJList;
    public JLabel itemsLayoutsJLabel;
    public JPanel currentMaterialItemParametersJPanel;
    public JButton addToLayoutButton;
    public JLabel emptyItemJLabel;
    public JLabel titleLoadingJLabel;
    public JComboBox typeJComboBox;
    public JLabel typeJLabel;
    public JTextField emptyItemLayoutJTextField;
    public JTextField loadingItemLayoutJTextField;
    public JLabel openItemLayoutJLabel;
    public JLabel openEmptyLayoutJLabel;
    public JLabel openLoadingLayoutJLabel;
    public JLabel removeEmptyLayoutJLabel;
    public JLabel removeLoadingLayoutJLabel;
    private JLabel itemActivityClassJLabel;
    private int lastIndex;
    public LinkedList<MaterialItem> tenClickedMaterialItems;
    public int currentItem = 0;
    public MaterialItem clickedMaterialItem;
    private Observer fileObserver;
    private String path;

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
        setObserverToOwnerClass();
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

        itemsLayoutsJLabel.setVisible(false);
        itemsLayoutJList.setVisible(false);
        currentMaterialItemParametersJPanel.setVisible(false);
        currentMaterialItemParametersJPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        titleMaterialItemJLabel.setVisible(false);
        itemMaterialItemJTextField.setVisible(false);
        titleParentViewJLabel.setVisible(false);
        iconPrevJLabel.setVisible(false);
        iconPrevJLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                prevClicked();
            }
        });
        nextIconJLabel.setVisible(false);
        nextIconJLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                nextClicked();
            }
        });
        addActivityClassJLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                File file = new File(PluginProject.mProject.getBasePath() + "/app/src");
                VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
                FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(true, true, false, false, false, false);
                VirtualFile selectedFile = FileChooser.chooseFile(fileChooserDescriptor, PluginProject.mProject, virtualFile);
                path = selectedFile.getCanonicalPath();

                //todo: change layout's line (if it already exists) tools:context="..."
                if (!path.contains(".java")) {
                    JFrame frame = new JFrame();
                    Object stringArray[] = {"Choose java", "Cancel"};
                    JOptionPane.showOptionDialog(frame, "It's not Java class", "Error",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray,
                            stringArray[0]);
                } else {
                    //todo: add to layout tools:context="..."
                    itemActivityClassJLabel.setText(path.substring(path.indexOf("java") + 5, path.lastIndexOf(".java")).replaceAll("/", "."));
                }
            }
        });


        saveLayoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("save layout btn clicked");
                String newPath = PluginProject.mLayoutPath.substring(PluginProject.mLayoutPath.lastIndexOf("/"),
                        PluginProject.mLayoutPath.length());
                /*NewLayoutsCreating newLayoutsCreating = new NewLayoutsCreating();
                newLayoutsCreating.initAllScreenLayouts(newPath);

                path = itemActivityClassJLabel.getText();
                new ActivityInit().addInitToClass(path, PluginProject.mLayoutPath);*/
                /*GenerateAction generateAction = new GenerateAction();
                generateAction.actionPerformed();*/
                /*
                  FileEditorManager manager = FileEditorManager.getInstance(project);
        Editor editor = manager.getSelectedTextEditor();
        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(PluginProject.mProject).getPsiFile(document);

                 */

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
        panel.setLayout(new GridLayoutManager(5, 17, new Insets(10, 10, 10, 10), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel.add(scrollPane1, new GridConstraints(1, 0, 1, 16, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        materialJList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        materialJList.setModel(defaultListModel1);
        scrollPane1.setViewportView(materialJList);
        iconPrevJLabel = new JLabel();
        iconPrevJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/back_arrow.png")));
        iconPrevJLabel.setText("");
        panel.add(iconPrevJLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentMaterialItemParametersJPanel = new JPanel();
        currentMaterialItemParametersJPanel.setLayout(new GridLayoutManager(6, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(currentMaterialItemParametersJPanel, new GridConstraints(3, 0, 1, 16, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleParentViewJLabel = new JLabel();
        titleParentViewJLabel.setText("Parent ID");
        currentMaterialItemParametersJPanel.add(titleParentViewJLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleMaterialItemJLabel = new JLabel();
        titleMaterialItemJLabel.setText("Item layout");
        currentMaterialItemParametersJPanel.add(titleMaterialItemJLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemParentViewJTextField = new JTextField();
        currentMaterialItemParametersJPanel.add(itemParentViewJTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addToLayoutButton = new JButton();
        addToLayoutButton.setText("Add to Layout");
        currentMaterialItemParametersJPanel.add(addToLayoutButton, new GridConstraints(5, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemMaterialItemJTextField = new JTextField();
        itemMaterialItemJTextField.setText("");
        currentMaterialItemParametersJPanel.add(itemMaterialItemJTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        emptyItemJLabel = new JLabel();
        emptyItemJLabel.setText("Empty layout");
        currentMaterialItemParametersJPanel.add(emptyItemJLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleLoadingJLabel = new JLabel();
        titleLoadingJLabel.setText("Loading layout");
        currentMaterialItemParametersJPanel.add(titleLoadingJLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        typeJLabel = new JLabel();
        typeJLabel.setText("Type");
        currentMaterialItemParametersJPanel.add(typeJLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        typeJComboBox = new JComboBox();
        currentMaterialItemParametersJPanel.add(typeJComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emptyItemLayoutJTextField = new JTextField();
        currentMaterialItemParametersJPanel.add(emptyItemLayoutJTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        loadingItemLayoutJTextField = new JTextField();
        currentMaterialItemParametersJPanel.add(loadingItemLayoutJTextField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        removeEmptyLayoutJLabel = new JLabel();
        removeEmptyLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeEmptyLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(removeEmptyLayoutJLabel, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openItemLayoutJLabel = new JLabel();
        openItemLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/go.png")));
        openItemLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(openItemLayoutJLabel, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeLoadingLayoutJLabel = new JLabel();
        removeLoadingLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeLoadingLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(removeLoadingLayoutJLabel, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openEmptyLayoutJLabel = new JLabel();
        openEmptyLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/go.png")));
        openEmptyLayoutJLabel.setText("");
        openEmptyLayoutJLabel.setVerticalAlignment(3);
        openEmptyLayoutJLabel.setVerticalTextPosition(3);
        currentMaterialItemParametersJPanel.add(openEmptyLayoutJLabel, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openLoadingLayoutJLabel = new JLabel();
        openLoadingLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/go.png")));
        openLoadingLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(openLoadingLayoutJLabel, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveLayoutButton = new JButton();
        saveLayoutButton.setText("Create another versions of layout");
        panel.add(saveLayoutButton, new GridConstraints(4, 0, 1, 16, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemActivityClassJLabel = new JLabel();
        itemActivityClassJLabel.setText("");
        panel.add(itemActivityClassJLabel, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleActivityClassJLabel = new JLabel();
        titleActivityClassJLabel.setText("Own Class");
        panel.add(titleActivityClassJLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemsLayoutsJLabel = new JLabel();
        itemsLayoutsJLabel.setText("Layout items");
        panel.add(itemsLayoutsJLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addActivityClassJLabel = new JLabel();
        addActivityClassJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/choose.png")));
        addActivityClassJLabel.setText("");
        panel.add(addActivityClassJLabel, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemsLayoutJList = new JList();
        panel.add(itemsLayoutJList, new GridConstraints(2, 2, 1, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        nextIconJLabel = new JLabel();
        nextIconJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/next_arrow.png")));
        nextIconJLabel.setText("");
        panel.add(nextIconJLabel, new GridConstraints(2, 15, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
            iconPrevJLabel.setVisible(true);
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

    private void setObserverToOwnerClass() {
        fileObserver = new FileParameters.FileOwnerListener(itemActivityClassJLabel);
        itemActivityClassJLabel.setText(FileParameters.FileOwnerListener.getOwner(PluginProject.mLayoutPath));
        PluginProject.fileParameters.addObserver(fileObserver);
    }

}