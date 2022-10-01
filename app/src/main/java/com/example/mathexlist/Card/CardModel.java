package com.example.mathexlist.Card;

public class CardModel {
    private String title;
    private String value;

    public CardModel(String title, Integer value) {
        this.title = title;
        this.value = String.valueOf(value);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = String.valueOf(value);
    }
}
