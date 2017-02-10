package ru.itis.androidplugin.view;

import ru.itis.androidplugin.elements.MaterialItem;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yasina on 10.02.17.
 */
public class ViewRender extends JPanel implements ListCellRenderer<MaterialItem> {

    private JLabel icon;

   /* public ColorRender() {
        try {
          /*  BufferedImage bufImg = ImageIO.read(getClass().getResource("/icons/ic_check_circle.png"));
            icon = new JLabel(new ImageIcon(bufImg));

       } catch (IOException ignored) {
        }

    }*/

    public ViewRender(){


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
    @Override
    public Component getListCellRendererComponent(JList<? extends MaterialItem> list, MaterialItem materialItem, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        final int size = 100;

        setLayout(new GridBagLayout());

        icon = new JLabel(materialItem.mIcon);
        //icon = new JLabel(color.fixedName);
      //  setBackground(Color.decode(color.hexCode));
        setToolTipText(materialItem.mViewName);

        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setPreferredSize(new Dimension(size, size));

    /* if (isSelected) {
         add(icon);
     } else {
         remove(icon);
     }*/

        add(icon);
        return this;
    }

}
