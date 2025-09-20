package com.supercell.gaming_store.dto;

import lombok.Data;

@Data
public class MembersRequest {
    private String name;
    private String phoneNumber;
    private String password;
    private String role; // "USER" or "ADMIN"
}
