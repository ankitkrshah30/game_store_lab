package com.supercell.gaming_store.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.supercell.gaming_store.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Document(collection = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Members {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String phoneNumber;
    
    @JsonIgnore
    private String password;
    
    private double balance;
    
    private Set<Role> roles;

    @CreatedDate
    private LocalDateTime joiningDate = LocalDateTime.now();

    @DBRef
    private List<Transactions> transactions;

    @DBRef
    private List<Recharges> recharges;
}
