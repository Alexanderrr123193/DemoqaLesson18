package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Books {
    String isbn,
            title,
            subTitle,
            author,
            publisher,
            description,
            website;
    @JsonProperty("publish_date")
    String publishDate;
    int pages;

}