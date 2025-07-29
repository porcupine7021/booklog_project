package com.porcupine.bookLog.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AladinBookResponse {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String pubDate;
    private String cover;
    private String description;
    private String categoryName;
}
