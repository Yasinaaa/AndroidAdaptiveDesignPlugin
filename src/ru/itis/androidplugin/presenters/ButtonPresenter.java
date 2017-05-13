package ru.itis.androidplugin.presenters;

import ru.itis.androidplugin.interfaces.ButtonInterface;
import ru.itis.androidplugin.settings.Constants;
import ru.itis.androidplugin.view.MaterialButton;

/**
 * Created by yasina on 10.04.17.
 */
public class ButtonPresenter extends CommonPresenter {

    private ButtonInterface buttonTypes;


    public ButtonPresenter(ButtonInterface buttonTypes) {
        super();
        this.buttonTypes = buttonTypes;
    }

    @Override
    public void setChildViewParameters() {

    }

    public String setTextByChoosedStyle(String style){
        switch (style){
            case MaterialButton.FLAT_BUTTON:
                return buttonTypes.setFlatButton();
            case MaterialButton.RAISED_BUTTON:
                return buttonTypes.setRaisedButton();
            default:
                return buttonTypes.setRaisedButton();
        }
    }

    public String setTextColorByChoosedTheme(String theme){
        if(theme.equals(Constants.DARK_THEME)){
            return buttonTypes.setDarkColor();
        }
        if (theme.equals(Constants.LIGHT_THEME)){
            return buttonTypes.setLightColor();
        }
        return buttonTypes.setDarkColor();
    }


}