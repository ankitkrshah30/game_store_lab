package com.supercell.gaming_store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "recharges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recharges {

    @Id
    private String id;

    @DBRef
    private Members memberId;
    private double amount;

    @CreatedDate
    private LocalDateTime date;
}
