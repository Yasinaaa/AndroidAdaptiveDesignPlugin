package ru.itis.androidplugin.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.itis.androidplugin.elements.MaterialRecyclerView;
import ru.itis.androidplugin.settings.UtilsEnvironment;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yasina on 03.03.17.
 */
public class ItemRecyclerViewParameters{

    private JLabel mParentIDLabel;
    private JButton mCreateAdapterClassBtn;
    private MaterialRecyclerView materialRecyclerView;
    private JPanel mJPanel, mMainJPanel;

    public ItemRecyclerViewParameters(JPanel panel, MaterialRecyclerView materialRecyclerView) {
        this.materialRecyclerView = materialRecyclerView;
        this.mMainJPanel = panel;
        init();
        OnClickAddAdapterClass();
    }

    private void init() {
        mJPanel = new JPanel();

        mJPanel.setLayout(new GridLayoutManager(2, 2, new Insets(20, 10, 10, 10), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Parent ID");
        mJPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mJPanel.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mCreateAdapterClassBtn = new JButton();
        mCreateAdapterClassBtn.setText("Create Adapter Class");
        mJPanel.add(mCreateAdapterClassBtn, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mParentIDLabel = new JLabel();
//        mParentIDLabel.setText(materialRecyclerView.getId());
        mJPanel.add(mParentIDLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        mMainJPanel.add(mJPanel, new GridConstraints());
      /*  Border border = getBorder();
        Border margin = new EmptyBorder(20, 10, 10, 10);
        mJPanel.setBorder(new CompoundBorder(border, margin));*/
    }

   /* public void setPanel(JPanel panel){
        panel = mJPanel;
    }*/

    private void OnClickAddAdapterClass() {
        mCreateAdapterClassBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                materialRecyclerView.setId(mParentIDLabel.getText());
                UtilsEnvironment.insertInEditor(materialRecyclerView.getViewParametrs());
            }
        });
    }

}
