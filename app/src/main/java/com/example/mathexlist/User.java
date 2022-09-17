package com.example.mathexlist;

import java.util.ArrayList;

public class User {
    public String email;
    public ArrayList<ArrayList<String>> exercisesDone;
    public ArrayList<ArrayList<String>> exercisesTODO;


    public User(String email) {
        this.email = email;
        this.exercisesDone = null;
        this.exercisesTODO = null;
    }
}
