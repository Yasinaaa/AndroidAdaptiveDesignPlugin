package ru.itis.androidplugin.adapters;

/**
 * Created by yasina on 29.05.17.
 */
public class ViewModel {
    private String title, picture;

    public ViewModel(String title, String picture) {
        this.title = title;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
