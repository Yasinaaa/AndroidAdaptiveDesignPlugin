package ru.itis.androidplugin.elements.values;

/**
 * Created by yasina on 24.03.17.
 */
public enum XmlTypes{
    NONE("layout"), SMALL("layout-small"), LARGE("layout-large"),
    XLARGE("layout-xlarge"), MEDIUM(""), MEDIUM_LAND(""),
    SMALL_LAND("layout-small-land"), LARGE_LAND("layout-large-land"), XLARGE_LAND("layout-xlarge-land"),
    SW600("layout-sw600dp"), SW720("layout-sw720dp");

    private final String name;

    XmlTypes(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}