package ru.itis.androidplugin.view;

import com.GenerateViewPresenterAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.itis.androidplugin.elements.MaterialRecyclerView;
import ru.itis.androidplugin.generation.LayoutGenerator;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.settings.InsertNewThing;
import ru.itis.androidplugin.settings.PluginProject;
import ru.itis.androidplugin.settings.UtilsEnvironment;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yasina on 24.02.17.
 */
public class RecyclerViewParameters extends ViewParameters {

    private JTextField mNameTextField;
    private JTextField mItemLayoutTextField;
    private JButton mCreateItemLayoutBtn;
    private JButton mAddToLayoutBtn;
    private MaterialRecyclerView materialRecyclerView;

    public RecyclerViewParameters(MaterialRecyclerView materialRecyclerView) {
        super(materialRecyclerView);
        init();
        this.materialRecyclerView = materialRecyclerView;

        OnClickAddToLayout();
        OnClickCreateItemLayout();
    }

    private void init() {

        mJPanel = new JPanel();
        mJPanel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        mNameTitleJLabel = new JLabel();
        mNameTitleJLabel.setText("Name");
        mJPanel.add(mNameTitleJLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED, null, null, null,
                0, false));
        mNameTextField = new JTextField();
        mJPanel.add(mNameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Item layout");
        mJPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mItemLayoutTextField = new JTextField();
        mJPanel.add(mItemLayoutTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        mCreateItemLayoutBtn = new JButton();
        mCreateItemLayoutBtn.setText("Create");
        mJPanel.add(mCreateItemLayoutBtn, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mJPanel.add(spacer1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mAddToLayoutBtn = new JButton();
        mAddToLayoutBtn.setText("Add to layout");
        mJPanel.add(mAddToLayoutBtn, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        Border border = getBorder();
        Border margin = new EmptyBorder(20, 10, 10, 10);
        mJPanel.setBorder(new CompoundBorder(border, margin));
    }

    private void OnClickCreateItemLayout() {
        mCreateItemLayoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutGenerator layoutGenerator = new LayoutGenerator();
                layoutGenerator.insertNewLayout(mItemLayoutTextField.getText());
                materialRecyclerView.setId(mNameTitleJLabel.getText());

               /* ItemRecyclerViewParameters itemRecyclerViewParameters =
                        new ItemRecyclerViewParameters(mJPanel, materialRecyclerView);*/

              /*  mNameTitleJLabel.setText("Parent ID");
                mNameTextField.setText(materialRecyclerView.getId());
                mCreateItemLayoutBtn.setVisible(false);
                mAddToLayoutBtn.setText("Create Adapter Class");*/

               // GlobalSearchScope scope = GlobalSearchScope.allScope(project);
               // PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass("org.myPackage.MyClass", scope);

               /* if ( psiClass != null ) {
                    FileEditorManager fileEditorManager = FileEditorManager.getInstance(PluginProject.mProject);
                    fileEditorManager.openFile()
                    fileEditorManager.openFile(psiClass.getContainingFile().getVirtualFile(), true, true);
                } else {
                    //handle the class not found
                }*/

                JPanel mJPanel2 = new JPanel();

               // mJPanel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
                final JLabel label1 = new JLabel();
                label1.setText("Parent ID");
                mJPanel.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST,
                        GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED, null, null, null,
                        0, false));
                JLabel mParentIDLabel = new JLabel();
                mParentIDLabel.setText(materialRecyclerView.getId());
                mJPanel.add(mParentIDLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
               // final Spacer spacer1 = new Spacer();
                //mJPanel.add(spacer1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
                JButton mCreateAdapterClassBtn = new JButton();
                mCreateAdapterClassBtn.setText("Create Adapter Class");
                mCreateAdapterClassBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        InsertNewThing insertNewThing = new InsertNewThing();
                        insertNewThing.insertNewLayout("ViewAdapter");
                    }
                });
                mJPanel.add(mCreateAdapterClassBtn, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));


                // mJPanel.add(mJPanel2, BorderLayout.CENTER);

            }
        });
    }
    private VirtualFile getSelectedLayoutFile(AnActionEvent e) {
        VirtualFile[] files = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        if (files != null && files.length > 0) {
            return files[0];
        } else {
            throw new GenerateViewPresenterAction.CancellationException("Select android layout file");
        }
    }

    private void OnClickAddToLayout() {
        mAddToLayoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                materialRecyclerView.setId(mNameTextField.getText());
                UtilsEnvironment.insertInEditor(materialRecyclerView.getViewParametrs());
            }
        });
    }


}
