package ru.itis.androidplugin.presenters;

import ru.itis.androidplugin.interfaces.RecyclerViewInterface;

/**
 * Created by yasina on 13.05.17.
 */
public class RecyclerViewPresenter extends CommonPresenter{

    private RecyclerViewInterface recyclerViewInterface;

    public RecyclerViewPresenter(RecyclerViewInterface recyclerViewInterface) {
        super();
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public String getAttrType(String mType) {
        switch (mType){
            case "item_":
                return "recyclerview_simple_item";
            case "empty_":
                return "recyclerview_empty_item";
            case "loading_":
                return "recyclerview_loading_item";
        }
        return mType;
    }

    public String setNormalType(String text) {
        switch (text){
            case "recyclerview_simple_item":
                return "item_";
            case "recyclerview_empty_item":
                return "empty_";
            case "recyclerview_loading_item":
                return "loading_";
        }
        return null;
    }

    @Override
    public void setChildViewParameters() {

    }
}
