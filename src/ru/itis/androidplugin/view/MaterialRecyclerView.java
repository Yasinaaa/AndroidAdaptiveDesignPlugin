package ru.itis.androidplugin.view;

import ru.itis.androidplugin.generator.xml.XmlGenerator;
import ru.itis.androidplugin.interfaces.RecyclerViewInterface;
import ru.itis.androidplugin.presenters.RecyclerViewPresenter;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.generator.xml.XmlChanger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by yasina on 10.02.17.
 */
public class MaterialRecyclerView extends MaterialItem implements RecyclerViewInterface{

    private static final String XML_VIEW_PATTERN =
            "<android.support.v7.widget.RecyclerView\n"+
            "        android:id=\"@+id/%s\"\n"+
            "        android:layout_width=\"wrap_content\"\n"+
            "        android:layout_height=\"wrap_content\"\n"+
            "        app:layout_style=\"%s\"\n" +
            "        app:layout_item=\"@layout/%s\"\n %s%s"+
            "        android:clipToPadding=\"false\"\n"+
            "        android:paddingBottom=\"@dimen/activity_vertical_margin\""+
            "        android:paddingLeft=\"@dimen/activity_horizontal_margin\"" +
            "        android:paddingRight=\"@dimen/activity_horizontal_margin\"" +
            "        android:paddingTop=\"@dimen/activity_horizontal_margin\"" +
            "        />";
    private final String CHILD_RECYCLERVIEW = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<RelativeLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "              xmlns:app=\"http://schemas.android.com/apk/res-auto\"" +
            "              android:orientation=\"vertical\"\n" +
            "              android:layout_width=\"match_parent\"\n" +
            "              android:layout_height=\"match_parent\"\n" +
            "              app:layout_style=\"%s\"\n" +
            "              app:layout_type=\"%s\""+
            "              app:parent_id=\"@id/%s\">"+
            "\n" +
            "</RelativeLayout>";
    private final String[] ANOTHER_LAYOUTS = new String[]{
            "        app:layout_empty=\"@layout/%s\"\n",
            "        app:layout_loading=\"@layout/%s\"\n"
    };

    private static final String ICON_PATH = "/icons/recycler_view.png";
    public static final String VIEW_NAME = "Recycler View";
    private final String EMPTY = "empty_";
    private final String LOADING = "loading_";
    private int childrensNum = 3;

    //icons
    private final String ICON_REMOVE_PATH = "/icons/remove.png";
    private final String ICON_OPEN_PATH = "/icons/go.png";
    private final String ICON_ADD_PATH = "/icons/checkmark.png";
    private ImageIcon mRemoveIcon, mOpenIcon, mAddIcon;
    //icons

    private ArrayList<JLabel> mouseClickedLabels;
    private MainView mainView;
    private RecyclerViewPresenter recyclerViewPresenter;
    //public static final String[] recyclerViewTypes

    private XmlGenerator layoutGenerator = null;
    private String[] recyclerViewInnerType = new String[]{
            "recyclerview_horizontall_linearlayout", "recyclerview_verticall_linearlayout",
            "recyclerview_tablelayout"
    };

    public MaterialRecyclerView(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
        //createChildItems();
    }

    @Override
    public void setView() {
        this.mainView = MainView.mainView;
        layoutGenerator = new XmlGenerator();
        recyclerViewPresenter = new RecyclerViewPresenter(this);
        mouseClickedLabels = new ArrayList<JLabel>();
        VisibleInvisible.recyclerViewState(mainView);
        setAllValues();
    }

    @Override
    public void setAllValues() {
        setIcons();
        mainView.titleMaterialItemJLabel.setText("Item layout");
        mainView.titleParentViewJLabel.setText("Recycler View ID");
        setLayoutJLabelClickers();
        mStyle = recyclerViewInnerType[0];
        mainView.typeJComboBox.addItem(recyclerViewInnerType[0]);
        mainView.typeJComboBox.addItem(recyclerViewInnerType[1]);
        mainView.typeJComboBox.addItem(recyclerViewInnerType[2]);
    }

    @Override
    public void getAllValues() {
        super.getAllValues();
    }

    @Override
    public void onAddToLayoutClickListener(){
        try {
            mId = mainView.itemParentViewJTextField.getText();
            setViewParameters();
            XmlChanger.addDimens(Constants.RECYCLERVIEW_DIMENS_TAGS,
                    Constants.RECYCLERVIEW_DIMENS_VALUE);
            XmlChanger.insertInEditor(mViewParametrs);

            VisibleInvisible.layoutsForRecyclerViewCreated(mainView);
        }catch (java.io.IOException e){

        }

    }

    @Override
    public void onFirstJComboBoxChangeListener(){
        mStyle = (String) mainView.typeJComboBox.getSelectedItem();
    }

    public void setViewChildAndParent(MainView mainView) {
        mainView.titleParentViewJLabel.setText("RecyclerView ID");
        VisibleInvisible.isChild(true, mainView);
    }

    @Override
    public void setViewParameters(){
        String[] parametrs = createChildItems(new JTextField[]{mainView.itemMaterialItemJTextField,
                mainView.emptyItemLayoutJTextField, mainView.loadingItemLayoutJTextField});
        mViewParametrs = String.format(mPattern, parametrs);
    }

    private String[] createChildItems(JTextField[] textFields){
        String[] items = new String[5];
        items[0] = mId;
        items[1] = mStyle;

        /*for (int i = 2; i < childrensNum + 2; i++) {
            String text = textFields[i-2].getText();

            if (text.equals("None")) {
                //mChildrenItems[i-2] = null;
                items[i] = "";
            }
            else {
                //mChildrenItems[i-2].mId = text;
                //MaterialChildRecyclerView materialChildRecyclerView = (MaterialChildRecyclerView)mChildrenItems[i-2];

                String inputText = String.format(CHILD_RECYCLERVIEW,
                        new String[]{
                                mStyle,
                                recyclerViewPresenter.getAttrType(mType),
                                materialChildRecyclerView.mParentItem.mId
                        });
                mChildrenItems[i-2].mLayoutPath = layoutGenerator.
                        insertNewLayout(inputText,"/layout/" + text + ".xml").getCanonicalPath();
                items[i] = i>2 ? String.format(ANOTHER_LAYOUTS[i-3], text) : text;
            }
        }*/

        for(int i=2;i<textFields.length + 2; i++){
            String text = textFields[i-2].getText();

            if (text.equals("None") || text.isEmpty()) {
                items[i] = "";
            }
            else {
                String inputText = String.format(CHILD_RECYCLERVIEW,
                        new String[]{
                                mStyle,
                                recyclerViewPresenter.getAttrType(ALL_TYPES[i-2]),
                                mId
                        });
                layoutGenerator.insertNewLayout(inputText,"/layout/" + text + ".xml");
                items[i] = i>2 ? String.format(ANOTHER_LAYOUTS[i-3], text) : text;
            }
        }
        return items;
    }

    /*private void createChildItems(){
        for (int i = 0; i < childrensNum; i++) {
            mChildrenItems[i] = new MaterialChildRecyclerView();
            mChildrenItems[i].mParentItem = this;
            mChildrenItems[i].mType = MaterialChildRecyclerView.mAllTypes[i];
        }
    }*/


    @Override
    public void onDocumentChangeListener(){
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

    private void setLayoutJLabelClickers(){
        for (String type: ALL_TYPES){
            //icons
            setLayoutJLabelClickers(mainView.itemMaterialItemJTextField, null,
                    mainView.openItemLayoutJLabel, ITEM_TYPE);
            setLayoutJLabelClickers(mainView.emptyItemLayoutJTextField, mainView.removeEmptyLayoutJLabel,
                    mainView.openEmptyLayoutJLabel, EMPTY_TYPE);
            setLayoutJLabelClickers(mainView.loadingItemLayoutJTextField, mainView.removeLoadingLayoutJLabel,
                    mainView.openLoadingLayoutJLabel, LOADING_TYPE);
            //icons
        }
    }

    private void setLayoutJLabelClickers(JTextField textField, JLabel addRemoveLabel, JLabel openLabel,
                                         String type){
        openLabel.setVisible(false);

        MouseListener mouseListener = openLabel.getMouseListeners()[0];
        mouseListener = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {

                String inputText = String.format(CHILD_RECYCLERVIEW,
                        new String[]{
                                mStyle,
                                recyclerViewPresenter.getAttrType(type),
                                mId
                        });
                layoutGenerator.openFile(layoutGenerator.insertNewLayout(inputText,
                         "/layout/" + textField.getText() + ".xml"));
               // materialChildRecyclerView.setView();

            }
        };


        if (addRemoveLabel != null){
            addRemoveLabel.setVisible(false);
            addRemoveLabel.setIcon(mRemoveIcon);

            MouseListener mouseListener2 = addRemoveLabel.getMouseListeners()[0];
            mouseListener2 = new MouseAdapter()
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
                        textField.setText(recyclerViewPresenter.getAttrType(type) +
                                mainView.itemParentViewJTextField.getText());
                        textField.setEnabled(true);
                    }

                }
            };
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


    @Override
    public void setParametersStandardStyle() {

    }

    @Override
    public void setParametersSearchStyle() {

    }

    @Override
    public void setParametersRemoveStyle() {

    }

    @Override
    public void getParametersStandardStyle() {

    }

    @Override
    public void getParametersSearchStyle() {

    }

    @Override
    public void getParametersRemoveStyle() {

    }
}
