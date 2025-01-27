package models;

import lombok.Data;

@Data
public class LoginRequestModel {
    private final String userName;
    private final String password;
}