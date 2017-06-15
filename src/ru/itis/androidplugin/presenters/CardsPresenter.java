package ru.itis.androidplugin.presenters;

import ru.itis.androidplugin.interfaces.CardsInterface;

/**
 * Created by yasina on 13.05.17.
 */
public class CardsPresenter extends CommonPresenter{

    private CardsInterface cardsInterface;

    public CardsPresenter(CardsInterface cardsInterface){
        super();
        this.cardsInterface = cardsInterface;
    }

    public void setParameters(String style){
        switch (style){
            case CardsInterface.STANDARD_TYPE:
                cardsInterface.setParametersStandardStyle();
                break;
            case CardsInterface.COMMENT_TYPE:
                cardsInterface.setParametersCommentStyle();
                break;
            case CardsInterface.ADDITIONAL_INFO_TYPE:
                cardsInterface.setParametersAdditionalInfoStyle();
                break;
            default:
                cardsInterface.setParametersStandardStyle();
                break;
        }
    }

    public void getParameters(String style){
        switch (style){
            case CardsInterface.STANDARD_TYPE:
                cardsInterface.getParametersStandardStyle();
                break;
            case CardsInterface.COMMENT_TYPE:
                cardsInterface.getParametersCommentStyle();
                break;
            case CardsInterface.ADDITIONAL_INFO_TYPE:
                cardsInterface.getParametersAdditionalInfoStyle();
                break;
            default:
                cardsInterface.getParametersStandardStyle();
                break;
        }
    }

    /*public void setChoosedType(String style){
        switch (style){
            case CardsInterface.ITEM_TYPE:
                cardsInterface.generateStandardToolbar();
                break;
            case CardsInterface.COMMENT_TYPE:
                cardsInterface.generateCommentToolbar();
                break;
            case CardsInterface.ADDITIONAL_INFO_TYPE:
                cardsInterface.generateAdditionalInfoToolbar();
                break;
            default:
                cardsInterface.generateStandardToolbar();
                break;
        }
    }*/

    @Override
    public void setChildViewParameters() {

    }
}
