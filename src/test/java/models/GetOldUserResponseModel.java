package models;

import lombok.Data;

import java.util.HashSet;

@Data
public class GetOldUserResponseModel {
    String userId, username;
    HashSet<Books> books;
}