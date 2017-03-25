package ru.itis.androidplugin.elements;

import ru.itis.androidplugin.generation.LayoutGenerator;
import ru.itis.androidplugin.generation.NewLayoutsCreating;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.settings.UtilsEnvironment;
import ru.itis.androidplugin.view.MainView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by yasina on 10.02.17.
 */
public class MaterialRecyclerView extends MaterialItem{

    public static final String XML_VIEW_PATTERN = "<RecyclerView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        app:layout_item=\"@layout/%s\"\n"+
            "        app:layout_empty=\"@layout/%s\"\n"+
            "        app:layout_loading=\"@layout/%s\"\n"+
            "        android:clipToPadding=\"false\"\n"+
            "        android:paddingBottom=\"@dimen/activity_vertical_margin\""+
            "        android:paddingLeft=\"@dimen/activity_horizontal_margin\"" +
            "        android:paddingRight=\"@dimen/activity_horizontal_margin\"" +
            "        android:paddingTop=\"@dimen/activity_horizontal_margin\"" +
            "        />";
    public static final String ICON_PATH = "/icons/recycler_view.png";
    public static final String VIEW_NAME = "Recycler View";
    private final String EMPTY = "empty_";
    private final String LOADING = "loading_";
    private MaterialItem[] childrens = null;
    private int childrensNum = 3;
    private boolean isAlreadyInserted = false;

    //icons
    private final String ICON_REMOVE_PATH = "/icons/remove.png";
    private final String ICON_OPEN_PATH = "/icons/go.png";
    private final String ICON_ADD_PATH = "/icons/checkmark.png";
    private ImageIcon mRemoveIcon, mOpenIcon, mAddIcon;
    //icons

    private MainView mainView;
    public static final String[] recyclerViewTypes = new String[]{
        "LISTVIEW", "TABLEVIEW"
    };
    public String recyclerViewType;
    private LayoutGenerator layoutGenerator = null;

    public MaterialRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        setIcons();
        layoutGenerator = new LayoutGenerator();
        childrens = new MaterialItem[childrensNum];
    }

    @Override
    public void setView(MainView mainView) {
        this.mainView = mainView;
        //panel
        mainView.currentMaterialItemParametersJPanel.setVisible(true);

        //textfield
        mainView.itemMaterialItemJTextField.setVisible(true);
        setOnRecyclerViewTitleChanged();
        //textfield

        //labels
        mainView.titleMaterialItemJLabel.setVisible(true);
        mainView.titleMaterialItemJLabel.setText("Item layout");
        mainView.titleParentViewJLabel.setVisible(true);
        mainView.titleParentViewJLabel.setText("Recycler View ID");

        //icons
        mainView.openItemLayoutJLabel.setVisible(false);
        setLayoutJLabelClickers(mainView.itemMaterialItemJTextField, null,
                mainView.openItemLayoutJLabel, null);
        mainView.openEmptyLayoutJLabel.setVisible(false);
        mainView.openLoadingLayoutJLabel.setVisible(false);
        mainView.removeEmptyLayoutJLabel.setVisible(false);
        mainView.removeEmptyLayoutJLabel.setIcon(mRemoveIcon);
        setLayoutJLabelClickers(mainView.emptyItemLayoutJTextField, mainView.removeEmptyLayoutJLabel,
                mainView.openEmptyLayoutJLabel, EMPTY);
        mainView.removeLoadingLayoutJLabel.setVisible(false);
        mainView.removeLoadingLayoutJLabel.setIcon(mRemoveIcon);
        setLayoutJLabelClickers(mainView.loadingItemLayoutJTextField, mainView.removeLoadingLayoutJLabel,
                mainView.openLoadingLayoutJLabel, LOADING);
        //icons

        //labels

        //buttons
        mainView.saveLayoutButton.setVisible(true);
        //buttons

        //combobox
        mainView.typeJComboBox.addItem(recyclerViewTypes[0]);
        mainView.typeJComboBox.addItem(recyclerViewTypes[1]);
        //combobox

        if (getСhild() != null) {
            /*mainView.itemMaterialItemJTextField.setEnabled(false); todo: maybe it's can be important
            mainView.itemMaterialItemJTextField.setText(childrens.mId);
            mainView.itemMaterialItemJTextField.setVisible(false);
            mainView.itemParentViewJTextField.setVisible(true);
            mainView.itemParentViewJTextField.setText(mId);*/
        } else {
            System.out.println("no children");
        }

        mainView.addToLayoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add to layout btn clicked");
                insertToLayoutOrNo();
                mainView.addToLayoutButton.removeActionListener(this);
            }
        });
        mainView.saveLayoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("save layout btn clicked");
                String path = PluginProject.mLayoutPath;
                String newPath = path.substring(path.lastIndexOf("/"), path.length());
                NewLayoutsCreating newLayoutsCreating = new NewLayoutsCreating();
                newLayoutsCreating.initAllScreenLayouts(newPath);
                mainView.saveLayoutButton.removeActionListener(this);
            }
        });

    }

    private void insertToLayoutOrNo(){
        if(!isAlreadyInserted){
            setId(mainView.itemParentViewJTextField.getText());
            setViewParameters();
            UtilsEnvironment.insertInEditor(mViewParametrs);
            isAlreadyInserted = true;
        }
    }

    @Override
    public MaterialItem[] getСhild() {
        return childrens;
    }

    @Override
    public void setСhild(MaterialItem[] child) {
        this.childrens = child;
    }

    @Override
    public void setViewChildAndParent(MainView mainView) {
        mainView.titleParentViewJLabel.setText("RecyclerView ID");
        mainView.itemParentViewJTextField.setVisible(true);
    }

    @Override
    public void setViewParameters(){
        createChildItems(new JTextField[]{mainView.itemMaterialItemJTextField,
                mainView.emptyItemLayoutJTextField, mainView.loadingItemLayoutJTextField});

        String[] parametrs = {mId, childrens[0].mViewName, childrens[1].mViewName, childrens[2].mViewName};
        mViewParametrs = String.format(mPattern, parametrs);
    }

    private void createChildItems(JTextField[] textFields){
        for (int i = 0; i< childrensNum; i++) {
            if (textFields[i].getText().equals("None"))
                childrens[i] = null;
            else {
                childrens[i] = new MaterialChildRecyclerView();
                childrens[i].setParent(this);
                childrens[i].mViewName = textFields[i].getText();
                childrens[i].setLayoutPath(layoutGenerator.
                        insertNewLayout(textFields[i].getText()).getCanonicalPath());
            }
        }
    }

    private void setOnRecyclerViewTitleChanged(){
        mainView.itemParentViewJTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setLayoutsNames();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setLayoutsNames();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               setLayoutsNames();
            }
        });
    }
    private void setLayoutsNames(){
        String recyclerViewTitle = mainView.itemParentViewJTextField.getText();
        if(!mainView.itemParentViewJTextField.getText().equals("")){
            mainView.itemMaterialItemJTextField.setText("item_"
                    + mainView.itemParentViewJTextField.getText());

            if (mainView.emptyItemLayoutJTextField.isEnabled())
            {
                mainView.emptyItemLayoutJTextField.setText(EMPTY
                        + mainView.itemParentViewJTextField.getText());
            }

            if (mainView.loadingItemLayoutJTextField.isEnabled())
            {
                mainView.loadingItemLayoutJTextField.setText(LOADING
                        + mainView.itemParentViewJTextField.getText());
            }

            mainView.removeEmptyLayoutJLabel.setVisible(true);
            mainView.removeLoadingLayoutJLabel.setVisible(true);
        }else {

            mainView.itemMaterialItemJTextField.setText(null);
            if (mainView.loadingItemLayoutJTextField.isEnabled()){
                mainView.loadingItemLayoutJTextField.setText(null);
                mainView.removeLoadingLayoutJLabel.setVisible(false);
            }

            if (mainView.emptyItemLayoutJTextField.isEnabled()){
                mainView.emptyItemLayoutJTextField.setText(null);
                mainView.removeEmptyLayoutJLabel.setVisible(false);
            }

        }
    }

    /*private void setOpenItemLayoutJLabelClick(){
        mainView.openItemLayoutJLabel.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(layoutGenerator != null){
                    layoutGenerator.openFile();
                }else {
                    layoutGenerator = new LayoutGenerator();
                    layoutGenerator.insertNewLayout(mainView.itemMaterialItemJTextField.getText());
                }
                childRecyclerView.setView(mainView);
                childRecyclerView.addItemToHistoryList(mainView);
            }
        });
    }*/

    private void setLayoutJLabelClickers(JTextField textField, JLabel addRemoveLabel, JLabel openLabel,
                                         String layoutType){
        openLabel.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                //todo: change
                layoutGenerator.openFile(layoutGenerator.insertNewLayout(textField.getText()));
                /*
                childRecyclerView.setView(mainView);
                childRecyclerView.addItemToHistoryList(mainView);
                */
            }
        });
        if (addRemoveLabel != null){
            addRemoveLabel.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {

                    ImageIcon i = (ImageIcon) addRemoveLabel.getIcon();
                    if(i.equals(mRemoveIcon)){
                        addRemoveLabel.setIcon(mAddIcon);
                        textField.setText("None");
                        textField.setEnabled(false);
                    }
                    else if(i.equals(mAddIcon)){
                        addRemoveLabel.setIcon(mRemoveIcon);
                        textField.setText(layoutType + mainView.itemParentViewJTextField.getText());
                        textField.setEnabled(true);
                    }

                }
            });
        }
    }


    private void setIcons(){
        try {
            mRemoveIcon = new ImageIcon(ImageIO.read(getClass().getResource(ICON_REMOVE_PATH)));
            mAddIcon  = new ImageIcon(ImageIO.read(getClass().getResource(ICON_ADD_PATH)));
            mOpenIcon = new ImageIcon(ImageIO.read(getClass().getResource(ICON_OPEN_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
