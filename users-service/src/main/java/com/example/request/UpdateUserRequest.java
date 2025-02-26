package com.example.request;

import com.example.entity.Role;

public class UpdateUserRequest {
    private String userID;
    private String name;
    private String dateOfBirth;
    private int age;
    private String phone;
    private String username;
    private String password;
    private Role role;
    private String createDate;

    public String getUserID() {
        return userID;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setCreateDate(String createDate) {this.createDate = createDate;}

    public String getCreateDate() {return createDate;}
}
