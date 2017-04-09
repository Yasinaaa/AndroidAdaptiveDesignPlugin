package ru.itis.androidplugin.presenters;

import ru.itis.androidplugin.interfaces.TextViewTypes;
import ru.itis.androidplugin.view.MaterialTextView;

/**
 * Created by yasina on 06.04.17.
 */
public class TextViewPresenter extends CommonPresenter {

    private TextViewTypes textViewType;
    private String type;
    private final String TYPE_USUAL_TEXT = "usual_text";
    private final String TYPE_BOTTOM_SHEET_TEXT = "bottom_sheet";

    public TextViewPresenter(TextViewTypes textViewType) {
        super();
        this.textViewType = textViewType;
    }

    @Override
    public void setChildViewParameters() {

    }

    public void setParametersByChoosedStyle(String style){
        switch (style){
            case TYPE_BOTTOM_SHEET_TEXT:
                textViewType.setBottomSheetOptions(new String[]{TYPE_BOTTOM_SHEET_TEXT});
                break;
            default:
                textViewType.setUsualOptions(new String[]{TYPE_USUAL_TEXT});
                break;
        }
    }

    public String setTextByChoosedStyle(String style){
        switch (style){
            case TYPE_BOTTOM_SHEET_TEXT:
                 return textViewType.setBottomSheetTextView();
            default:
                 return textViewType.setUsualTextView();
        }
    }


}
