package ru.itis.androidplugin.view;

import ru.itis.androidplugin.generator.XmlChanger;
import ru.itis.androidplugin.generator.XmlGenerator;
import ru.itis.androidplugin.presenters.BottomNavigationPresenterImpl;
import ru.itis.androidplugin.settings.Constants;

import javax.swing.*;

/**
 * Created by yasina on 09.03.17.
 */
public class MaterialBottomNavigation extends MaterialItem {

    private static final String EMPTY = "bottomNavigationView1";
    public static final String XML_VIEW_PATTERN =
            "  <android.support.design.widget.BottomNavigationView\n" +
            "        android:id=\"@+id/%s\"\n" +
            "        android:layout_width=\"match_parent\"\n" +
            "        android:layout_height=\"@dimen/bottom_navigation_view_height\"\n" +
            "        android:layout_gravity=\"bottom\"\n" +
            "        android:background=\"?android:attr/windowBackground\"\n" +
            "        app:menu=\"@menu/%s\"/>\n";
    public static final String ICON_PATH = "/icons/bottom_navigation.png";
    public static final String VIEW_NAME = "Bottom Navigation";

    //private String mId = EMPTY;
    //private ImageIcon mIcon;
    private String menuPath;
    private MainView mainView;
    private BottomNavigationPresenterImpl bottomNavigationPresenter;
    private XmlGenerator menuLayoutGenerator;
    private JPanel[] allPanels;
    private JTextField[] allIDs;
    private JLabel[] allRemoves, allChoosers;
    private JComboBox[] allDrawables;

    public MaterialBottomNavigation(){
        super(VIEW_NAME, XML_VIEW_PATTERN, ICON_PATH);
    }

    @Override
    public MaterialItem[] getСhild() {
        return null;
    }

    @Override
    public void setView() {
        mainView = MainView.mainView;

        bottomNavigationPresenter = new BottomNavigationPresenterImpl();
        menuLayoutGenerator = new XmlGenerator();
        VisibleInvisible.bottomNavigationState(mainView);
        init();

        mainView.titleParentViewJLabel.setText("BottomNavigation ID");
        mainView.titleMaterialItemJLabel.setText("Items layout");

        bottomNavigationPresenter.setAllItems(allPanels, allDrawables, allRemoves, allChoosers);
        bottomNavigationPresenter.addNewItemToBottomNavigationView(mainView.addNewItemJLabel,
                allPanels);
        bottomNavigationPresenter.generateItemsLayoutTitle(mainView.itemParentViewJTextField,
                mainView.itemMaterialItemJTextField);
        bottomNavigationPresenter.openItemsLayout(mainView.openItemLayoutJLabel, "/menu" +
                mainView.itemMaterialItemJTextField.getText() + ".xml");

    }

    @Override
    public void setViewParameters() {
        String[] parametrs = new String[]{mId, mainView.itemMaterialItemJTextField.getText()};
        mViewParametrs = String.format(mPattern, parametrs);
    }

    @Override
    public void insertToLayoutOrNo(){
        try {
            System.out.println("insertToLayoutOrNo BottomNavigation");
            setId(mainView.itemParentViewJTextField.getText());
            setViewParameters();
            XmlChanger.changeXml(Constants.BOTTOM_NAVIGATION_VIEW_HEIGHT,
                    Constants.BOTTOM_NAVIGATION_VIEW_HEIGHT_VALUE,
                    mViewParametrs);

            bottomNavigationPresenter.clickAddToLayout(allPanels, allIDs, allDrawables, allRemoves, allChoosers);
            mainView.openItemLayoutJLabel.setVisible(true);
            makeMenuLayout();
        }catch (java.io.IOException e){

        }
    }

    private void makeMenuLayout(){
        menuLayoutGenerator.insertNewMenu(bottomNavigationPresenter.allItems,
                mainView.itemMaterialItemJTextField.getText() + ".xml");
    }

    private void init(){
        allPanels = new JPanel[]{mainView.item1JPanel,mainView.item2JPanel,mainView.item3JPanel,
                mainView.item4JPanel,mainView.item5JPanel};
        allIDs = new JTextField[]{mainView.item1JTextField,mainView.item2JTextField,mainView.item3JTextField,
                mainView.item4JTextField,mainView.item5JTextField};
        allRemoves = new JLabel[]{mainView.removeIcon1JLabel,mainView.removeIcon2JLabel,mainView.removeIcon3JLabel,
                mainView.removeIcon4JLabel,mainView.removeIcon5JLabel};
        allChoosers = new JLabel[]{mainView.chooseIcon1JLabel,mainView.chooseIcon2JLabel,mainView.chooseIcon3JLabel,
                mainView.chooseIcon4JLabel,mainView.chooseIcon5JLabel};
        allDrawables = new JComboBox[]{mainView.chooseIcon1JComboBox,mainView.chooseIcon2JComboBox,
                mainView.chooseIcon3JComboBox, mainView.chooseIcon4JComboBox,mainView.chooseIcon5JComboBox,};
    }
}
