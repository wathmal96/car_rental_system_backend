package com.example.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerLoginDTO {
    private int id;
    private String name;
    private String password;
    private String address;
    private String contact;
    private String eMail;
    private String roles = "customer";
    private String token;
}
