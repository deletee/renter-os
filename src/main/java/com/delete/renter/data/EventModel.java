package com.delete.renter.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EventModel
{
    private StringProperty text;

    public EventModel()
    {
        this.text = new SimpleStringProperty();
    }

    public StringProperty textProperty() {
        return text;
    }

    public final String getText() {
        return textProperty().get();
    }

    public final void setText(String text) {
        textProperty().set(text);
    }

    public void onAction(String params){
        setText(params);
    }
}