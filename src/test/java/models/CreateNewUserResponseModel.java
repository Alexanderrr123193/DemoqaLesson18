package models;

import lombok.Data;

import java.util.HashSet;

@Data
public class CreateNewUserResponseModel {
    String userID, username;
    HashSet<Books> books;
}