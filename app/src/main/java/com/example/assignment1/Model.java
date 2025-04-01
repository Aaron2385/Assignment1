package com.example.assignment1;

public class Model {
    private String text;
    private int imgID;
    private boolean isSelected = false;

    public Model(String text, int imageID, boolean selected) {
        this.text = text;
        this.imgID = imageID;
        isSelected = selected;
    }

    public String getText() {
        return text;
    }

    public int getImgID() {
        return imgID;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
