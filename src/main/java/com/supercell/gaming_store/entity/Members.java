package com.supercell.gaming_store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Members {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String phoneNumber;
    private double balance;

    @CreatedDate
    private LocalDateTime joiningDate= LocalDateTime.now();

    @DBRef
    private List<Transactions> transactions;

    @DBRef
    private List<Recharges> recharges;
}
