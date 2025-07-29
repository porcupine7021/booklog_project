package com.porcupine.bookLog.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AladinApiWrapper {
    private AladinBookResponse[] item;

    public AladinBookResponse[] getItem() {
        return item;
    }

    public void setItem(AladinBookResponse[] item) {
        this.item = item;
    }
}
