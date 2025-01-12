package models;

import lombok.Data;

@Data
public class GenerateTokenResponseModel {
    String token,
            expires,
            status,
            result;
}