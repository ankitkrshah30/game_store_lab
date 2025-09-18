package com.supercell.gaming_store.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    @ToString.Exclude
    @JsonIdentityReference(alwaysAsId = true)
    @DBRef
    private Members memberId;

    private double amount;

    @CreatedDate
    private LocalDateTime date= LocalDateTime.now();
}
