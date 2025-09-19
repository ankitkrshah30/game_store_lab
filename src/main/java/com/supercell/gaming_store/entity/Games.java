package com.supercell.gaming_store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Games {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private double price;
    private String description;
    private int minPlayer;
    private int maxPlayer;
    private int multipleOf;
    private int duration;
    private String gifURL;
}
