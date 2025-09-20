package com.supercell.gaming_store.dto;

import com.supercell.gaming_store.entity.Members;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private Members member;
    private String message;

    public LoginResponse(String token, Members member, String message) {
        this.token = token;
        this.member = member;
        this.message = message;
    }
}
