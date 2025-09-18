package com.supercell.gaming_store.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {

    @Id
    private String id;

    @DBRef
    private Members memberId;

    @JsonIdentityReference(alwaysAsId = true)
    @DBRef
    private Games game;
    private double amount;

    @CreatedDate
    private LocalDateTime date;
}
