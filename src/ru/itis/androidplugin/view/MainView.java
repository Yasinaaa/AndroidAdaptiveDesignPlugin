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

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DocumentAdapter;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.itis.androidplugin.adapters.ViewRender;
import ru.itis.androidplugin.android.AndroidManifest;
import ru.itis.androidplugin.android.Gradle;
import ru.itis.androidplugin.generator.xml.XmlGenerator;
import ru.itis.androidplugin.generator.classes.patterns.ActivityPattern;
import ru.itis.androidplugin.generator.classes.ClassGenerator;
import ru.itis.androidplugin.generator.classes.patterns.RecyclerViewHolderPattern;
import ru.itis.androidplugin.settings.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Observer;

import static ru.itis.androidplugin.settings.PluginProject.getCurrentVirtualFile;

/**
 * Created by yasina on 09.03.17.
 */
public class MainView extends JPanel {

    public static MainView mainView;
    public JPanel panel;
    private JList materialJList;
    public JTextField itemMaterialItemJTextField;
    public JButton saveLayoutButton;
    public JLabel titleActivityClassJLabel;
    public JLabel addActivityClassJLabel;
    public JLabel titleMaterialItemJLabel;
    public JLabel titleParentViewJLabel;
    public JTextField itemParentViewJTextField;
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
    public JLabel itemActivityClassJLabel;
    public JLabel titleParentIDJLabel;
    public JLabel itemParentIDJLabel;
    public JLabel titleItemsJLabel;
    public JLabel addNewItemJLabel;
    public JLabel titleItem1JLabel;
    public JLabel titleItem2JLabel;
    public JLabel titleItem3JLabel;
    public JLabel titleItem4JLabel;
    public JLabel titleItem5JLabel;
    public JLabel titleIcon1JLabel;
    public JLabel titleIcon2JTextField;
    public JLabel titleIcon3JLabel;
    public JLabel titleIcon4JLabel;
    public JLabel titleIcon5JLabel;
    public JTextField item1JTextField;
    public JTextField item2JTextField;
    public JTextField item3JTextField;
    public JTextField item4JTextField;
    public JTextField item5JTextField;
    public JPanel item1JPanel;
    public JPanel item2JPanel;
    public JPanel item3JPanel;
    public JPanel item4JPanel;
    public JPanel item5JPanel;
    public JLabel chooseIcon1JLabel;
    public JLabel chooseIcon2JLabel;
    public JLabel chooseIcon3JLabel;
    public JLabel chooseIcon4JLabel;
    public JLabel chooseIcon5JLabel;
    public JLabel removeIcon1JLabel;
    public JLabel removeIcon2JLabel;
    public JLabel removeIcon3JLabel;
    public JLabel removeIcon4JLabel;
    public JLabel removeIcon5JLabel;
    public JComboBox chooseIcon1JComboBox;
    public JComboBox chooseIcon2JComboBox;
    public JComboBox chooseIcon3JComboBox;
    public JComboBox chooseIcon4JComboBox;
    public JComboBox chooseIcon5JComboBox;
    public JLabel removeItemLayoutJLabel;
    public JComboBox jComboBox2;
    public JLabel title4JLabel;
    public JLabel titleLayoutTypeJLabel;
    public JLabel itemLayoutTypeJLabel;
    public JLabel invisibleJLabel;
    public JLabel openIconJLabel;
    public JLabel title5JLabel;
    public JComboBox jComboBox3;
    public boolean isRaisedButtonDrawablesAdded = false;
    public boolean isFlatButtonDrawablesAdded = false;

    public LinkedList<MaterialItem> tenClickedMaterialItems;
    public int currentItem = 0;
    public MaterialItem clickedMaterialItem;
    private Observer fileObserver;
    private String path;
    public ActionListener saveLayoutActionListener;
    private VirtualFile virtualFile;
    private AndroidManifest androidManifest;


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
        mainView = this;
        Gradle.addMaterialVauesLibToProject();
        virtualFile = getCurrentVirtualFile();
        VisibleInvisible.cleanState(this);

        if (virtualFile != null) System.out.println("current = " + virtualFile.getCanonicalPath());

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

        currentMaterialItemParametersJPanel.setVisible(false);
        currentMaterialItemParametersJPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        /*titleParentIDJLabel.setVisible(false);
        itemParentIDJLabel.setVisible(false);
        titleMaterialItemJLabel.setVisible(false);
        itemMaterialItemJTextField.setVisible(false);
        titleParentViewJLabel.setVisible(false);*/
        //bottomNavigationJPanel.setVisible(false);

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

        addToLayoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add to layout btn clicked");
                clickedMaterialItem.onAddToLayoutClickListener();
            }
        });
        itemParentViewJTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent e) {
                clickedMaterialItem.onDocumentChangeListener();
            }
        });

        typeJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedMaterialItem.onFirstJComboBoxChangeListener();
            }
        });
        setMouseListener(removeItemLayoutJLabel);
        setMouseListener(removeEmptyLayoutJLabel);
        setMouseListener(removeLoadingLayoutJLabel);
        //setMouseListener(addNewItemJLabel);
        setMouseListener(openItemLayoutJLabel);
        setMouseListener(openEmptyLayoutJLabel);
        setMouseListener(openLoadingLayoutJLabel);
        setMouseListener(openIconJLabel);
        //setActionListener(emptyItemLayoutJTextField);

        mainView.typeJComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                clickedMaterialItem.onJComboBox1SelectedItemListener();
            }
        });

        saveLayoutActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("save layout btn clicked");
                String newPath = PluginProject.
                        mLayoutPath.substring(PluginProject.mLayoutPath.lastIndexOf("/"),
                        PluginProject.mLayoutPath.length());

                XmlGenerator xmlGenerator = new XmlGenerator();

                //xmlGenerator.initAllScreenLayouts(newPath, XmlTypes.getXmlTypes());

                ClassGenerator activityInit = new ClassGenerator();
                VirtualFile virtualFile = getCurrentVirtualFile();
                androidManifest = new AndroidManifest(virtualFile);

                if (itemParentIDJLabel.getText() != null && itemParentIDJLabel.isVisible()) {
                    //todo: change
                    activityInit.insertNewClass(new RecyclerViewHolderPattern(androidManifest),
                            virtualFile.getName());
                } else {
                    path = itemActivityClassJLabel.getText();
                    activityInit.addInitToClass(new ActivityPattern(androidManifest),
                            path, PluginProject.mLayoutPath);
                }
                //todo: change
                androidManifest.setDifferentScreenSupport();

            }
        };
        saveLayoutButton.addActionListener(saveLayoutActionListener);
        setObserverToOwnerClass();
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
        panel.setLayout(new GridLayoutManager(6, 10, new Insets(10, 10, 10, 10), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel.add(scrollPane1, new GridConstraints(3, 0, 1, 10, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        materialJList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        materialJList.setModel(defaultListModel1);
        scrollPane1.setViewportView(materialJList);
        currentMaterialItemParametersJPanel = new JPanel();
        currentMaterialItemParametersJPanel.setLayout(new GridLayoutManager(15, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(currentMaterialItemParametersJPanel, new GridConstraints(4, 0, 1, 10, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleParentViewJLabel = new JLabel();
        titleParentViewJLabel.setText("ID");
        currentMaterialItemParametersJPanel.add(titleParentViewJLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleMaterialItemJLabel = new JLabel();
        titleMaterialItemJLabel.setText("Item layout");
        currentMaterialItemParametersJPanel.add(titleMaterialItemJLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemParentViewJTextField = new JTextField();
        itemParentViewJTextField.setText("");
        currentMaterialItemParametersJPanel.add(itemParentViewJTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        itemMaterialItemJTextField = new JTextField();
        itemMaterialItemJTextField.setText("");
        currentMaterialItemParametersJPanel.add(itemMaterialItemJTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        emptyItemJLabel = new JLabel();
        emptyItemJLabel.setText("Empty layout");
        currentMaterialItemParametersJPanel.add(emptyItemJLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleLoadingJLabel = new JLabel();
        titleLoadingJLabel.setText("Loading layout");
        currentMaterialItemParametersJPanel.add(titleLoadingJLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        typeJLabel = new JLabel();
        typeJLabel.setText("Type");
        currentMaterialItemParametersJPanel.add(typeJLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        typeJComboBox = new JComboBox();
        currentMaterialItemParametersJPanel.add(typeJComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emptyItemLayoutJTextField = new JTextField();
        currentMaterialItemParametersJPanel.add(emptyItemLayoutJTextField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        loadingItemLayoutJTextField = new JTextField();
        currentMaterialItemParametersJPanel.add(loadingItemLayoutJTextField, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        removeEmptyLayoutJLabel = new JLabel();
        removeEmptyLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeEmptyLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(removeEmptyLayoutJLabel, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openEmptyLayoutJLabel = new JLabel();
        openEmptyLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/go.png")));
        openEmptyLayoutJLabel.setText("");
        openEmptyLayoutJLabel.setVerticalAlignment(3);
        openEmptyLayoutJLabel.setVerticalTextPosition(3);
        currentMaterialItemParametersJPanel.add(openEmptyLayoutJLabel, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        currentMaterialItemParametersJPanel.add(spacer1, new GridConstraints(7, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        item2JPanel = new JPanel();
        item2JPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        currentMaterialItemParametersJPanel.add(item2JPanel, new GridConstraints(10, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleItem2JLabel = new JLabel();
        titleItem2JLabel.setText("Item ID");
        item2JPanel.add(titleItem2JLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item2JTextField = new JTextField();
        item2JPanel.add(item2JTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        titleIcon2JTextField = new JLabel();
        titleIcon2JTextField.setText("Icon");
        item2JPanel.add(titleIcon2JTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon2JLabel = new JLabel();
        chooseIcon2JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/choose.png")));
        chooseIcon2JLabel.setText("");
        item2JPanel.add(chooseIcon2JLabel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeIcon2JLabel = new JLabel();
        removeIcon2JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeIcon2JLabel.setText("");
        item2JPanel.add(removeIcon2JLabel, new GridConstraints(0, 3, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon2JComboBox = new JComboBox();
        item2JPanel.add(chooseIcon2JComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item3JPanel = new JPanel();
        item3JPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        currentMaterialItemParametersJPanel.add(item3JPanel, new GridConstraints(11, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleItem3JLabel = new JLabel();
        titleItem3JLabel.setText("Item ID");
        item3JPanel.add(titleItem3JLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item3JTextField = new JTextField();
        item3JPanel.add(item3JTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        titleIcon3JLabel = new JLabel();
        titleIcon3JLabel.setText("Icon");
        item3JPanel.add(titleIcon3JLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon3JLabel = new JLabel();
        chooseIcon3JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/choose.png")));
        chooseIcon3JLabel.setText("");
        item3JPanel.add(chooseIcon3JLabel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeIcon3JLabel = new JLabel();
        removeIcon3JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeIcon3JLabel.setText("");
        item3JPanel.add(removeIcon3JLabel, new GridConstraints(0, 3, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon3JComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        chooseIcon3JComboBox.setModel(defaultComboBoxModel1);
        item3JPanel.add(chooseIcon3JComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item4JPanel = new JPanel();
        item4JPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        currentMaterialItemParametersJPanel.add(item4JPanel, new GridConstraints(12, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleItem4JLabel = new JLabel();
        titleItem4JLabel.setText("Item ID");
        item4JPanel.add(titleItem4JLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item4JTextField = new JTextField();
        item4JPanel.add(item4JTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        titleIcon4JLabel = new JLabel();
        titleIcon4JLabel.setText("Icon");
        item4JPanel.add(titleIcon4JLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon4JLabel = new JLabel();
        chooseIcon4JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/choose.png")));
        chooseIcon4JLabel.setText("");
        item4JPanel.add(chooseIcon4JLabel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeIcon4JLabel = new JLabel();
        removeIcon4JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeIcon4JLabel.setText("");
        item4JPanel.add(removeIcon4JLabel, new GridConstraints(0, 3, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon4JComboBox = new JComboBox();
        item4JPanel.add(chooseIcon4JComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item5JPanel = new JPanel();
        item5JPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        currentMaterialItemParametersJPanel.add(item5JPanel, new GridConstraints(13, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleItem5JLabel = new JLabel();
        titleItem5JLabel.setText("Item ID");
        item5JPanel.add(titleItem5JLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item5JTextField = new JTextField();
        item5JPanel.add(item5JTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        titleIcon5JLabel = new JLabel();
        titleIcon5JLabel.setText("Icon");
        item5JPanel.add(titleIcon5JLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon5JLabel = new JLabel();
        chooseIcon5JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/choose.png")));
        chooseIcon5JLabel.setText("");
        item5JPanel.add(chooseIcon5JLabel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeIcon5JLabel = new JLabel();
        removeIcon5JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeIcon5JLabel.setText("");
        item5JPanel.add(removeIcon5JLabel, new GridConstraints(0, 3, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon5JComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        chooseIcon5JComboBox.setModel(defaultComboBoxModel2);
        item5JPanel.add(chooseIcon5JComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item1JPanel = new JPanel();
        item1JPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        currentMaterialItemParametersJPanel.add(item1JPanel, new GridConstraints(9, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleItem1JLabel = new JLabel();
        titleItem1JLabel.setText("Item ID");
        item1JPanel.add(titleItem1JLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        item1JTextField = new JTextField();
        item1JPanel.add(item1JTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        titleIcon1JLabel = new JLabel();
        titleIcon1JLabel.setText("Icon");
        item1JPanel.add(titleIcon1JLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon1JLabel = new JLabel();
        chooseIcon1JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/choose.png")));
        chooseIcon1JLabel.setText("");
        item1JPanel.add(chooseIcon1JLabel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeIcon1JLabel = new JLabel();
        removeIcon1JLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeIcon1JLabel.setText("");
        item1JPanel.add(removeIcon1JLabel, new GridConstraints(0, 3, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseIcon1JComboBox = new JComboBox();
        item1JPanel.add(chooseIcon1JComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleItemsJLabel = new JLabel();
        titleItemsJLabel.setText("Items");
        currentMaterialItemParametersJPanel.add(titleItemsJLabel, new GridConstraints(8, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jComboBox2 = new JComboBox();
        currentMaterialItemParametersJPanel.add(jComboBox2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        title4JLabel = new JLabel();
        title4JLabel.setText("Icon");
        currentMaterialItemParametersJPanel.add(title4JLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addNewItemJLabel = new JLabel();
        addNewItemJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
        addNewItemJLabel.setText("");
        addNewItemJLabel.setVerticalAlignment(3);
        addNewItemJLabel.setVerticalTextPosition(3);
        currentMaterialItemParametersJPanel.add(addNewItemJLabel, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeLoadingLayoutJLabel = new JLabel();
        removeLoadingLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeLoadingLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(removeLoadingLayoutJLabel, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeItemLayoutJLabel = new JLabel();
        removeItemLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/remove.png")));
        removeItemLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(removeItemLayoutJLabel, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openLoadingLayoutJLabel = new JLabel();
        openLoadingLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/go.png")));
        openLoadingLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(openLoadingLayoutJLabel, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openItemLayoutJLabel = new JLabel();
        openItemLayoutJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/go.png")));
        openItemLayoutJLabel.setText("");
        currentMaterialItemParametersJPanel.add(openItemLayoutJLabel, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openIconJLabel = new JLabel();
        openIconJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/choose.png")));
        openIconJLabel.setText("");
        currentMaterialItemParametersJPanel.add(openIconJLabel, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jComboBox3 = new JComboBox();
        currentMaterialItemParametersJPanel.add(jComboBox3, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        title5JLabel = new JLabel();
        title5JLabel.setText("Parent ID");
        currentMaterialItemParametersJPanel.add(title5JLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addToLayoutButton = new JButton();
        addToLayoutButton.setText("Add to Layout");
        currentMaterialItemParametersJPanel.add(addToLayoutButton, new GridConstraints(14, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveLayoutButton = new JButton();
        saveLayoutButton.setText("Save layout");
        panel.add(saveLayoutButton, new GridConstraints(5, 0, 1, 10, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        itemActivityClassJLabel = new JLabel();
        itemActivityClassJLabel.setText("");
        panel.add(itemActivityClassJLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleActivityClassJLabel = new JLabel();
        titleActivityClassJLabel.setText("Owner Class");
        panel.add(titleActivityClassJLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), null, 0, false));
        titleParentIDJLabel = new JLabel();
        titleParentIDJLabel.setText("Parent ID");
        panel.add(titleParentIDJLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), null, 0, false));
        itemParentIDJLabel = new JLabel();
        itemParentIDJLabel.setText("");
        panel.add(itemParentIDJLabel, new GridConstraints(1, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addActivityClassJLabel = new JLabel();
        addActivityClassJLabel.setIcon(new ImageIcon(getClass().getResource("/icons/choose.png")));
        addActivityClassJLabel.setText("");
        panel.add(addActivityClassJLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleLayoutTypeJLabel = new JLabel();
        titleLayoutTypeJLabel.setText("Layout Type");
        panel.add(titleLayoutTypeJLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), null, 0, false));
        itemLayoutTypeJLabel = new JLabel();
        itemLayoutTypeJLabel.setText("");
        panel.add(itemLayoutTypeJLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        invisibleJLabel = new JLabel();
        invisibleJLabel.setText("");
        panel.add(invisibleJLabel, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
            clickedMaterialItem = Constants.mViewMaterialItems[index];
            clickedMaterialItem.setView();
            clickedMaterialItem.mLayoutPath = PluginProject.getCurrentVirtualFile().getCanonicalPath();
            list.setSelectedIndex(index);
        }

    }

    private void setObserverToOwnerClass() {
        fileObserver = new FileOwnerListener(this);
        if (virtualFile != null) FileOwnerListener.getOwner(virtualFile.getCanonicalPath());
        PluginProject.fileParameters.addObserver(fileObserver);
    }

    private void setMouseListener(JLabel label) {

        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            }
        });
    }

    private void setActionListener(JTextField jTextField) {
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


}