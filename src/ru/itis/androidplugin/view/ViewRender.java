package ru.itis.androidplugin.view;

import ru.itis.androidplugin.elements.MaterialItem;
import ru.itis.androidplugin.settings.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by yasina on 10.02.17.
 */
public class ViewRender extends JLabel implements ListCellRenderer<MaterialItem> {

   /* public ColorRender() {
        try {
          /*  BufferedImage bufImg = ImageIO.read(getClass().getResource("/icons/ic_check_circle.png"));
            icon = new JLabel(new ImageIcon(bufImg));

       } catch (IOException ignored) {
        }

    }*/

    public ViewRender(){
        setOpaque(true);
        setIconTextGap(12);
    }

    /*   @Override
       public Component getListCellRendererComponent(JList<? extends MaterialColor> list, MaterialColor color, int index, boolean isSelected, boolean cellHasFocus) {
           final int size = 100;

           setLayout(new GridBagLayout());


          // setBackground(Color.decode(color.hexCode));
           setToolTipText(color.fixedName);

           setMinimumSize(new Dimension(size, size));
           setMaximumSize(new Dimension(size, size));
           setPreferredSize(new Dimension(size, size));

           if (isSelected) {
               add(icon);
           } else {
               remove(icon);
           }

           return this;
       }*/
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

        setIcon(getScaledImage(materialItem.mIcon, 70, 70));

        setText(materialItem.mViewName);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);

        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setPreferredSize(new Dimension(size, size));



        return this;
    }

}
