package ru.itis.androidplugin.adapters;

import ru.itis.androidplugin.view.MaterialItem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by yasina on 10.02.17.
 */
public class ViewRender extends JLabel implements ListCellRenderer<MaterialItem> {

    public ViewRender(){
        setOpaque(true);
        setIconTextGap(12);
        Border border = getBorder();
        Border margin = new EmptyBorder(10,10,10,10);
        setBorder(new CompoundBorder(border, margin));
    }

    private ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg.getImage(), 0, 0, w, h, null);
        g2.dispose();

        return new ImageIcon(resizedImg);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends MaterialItem> list, MaterialItem materialItem, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        final int size = 100;

        setLayout(new GridBagLayout());
       // setBackground(Color.decode(Constants.BACKGROUND_ITEM));
        setToolTipText(materialItem.mViewName);

        //setIcon(getScaledImage(materialItem.mIcon, 100, 100));
        setIcon(materialItem.mIcon);

        setText(materialItem.mViewName);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);

        /*setMinimumSize(new Dimension(size+10, size+10));
        setMaximumSize(new Dimension(size+10, size+10));
        setPreferredSize(new Dimension(size+10, size+10));*/



        return this;
    }

}
