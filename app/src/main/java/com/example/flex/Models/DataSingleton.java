package com.example.flex.Models;

import java.util.ArrayList;

public class DataSingleton {
    private static DataSingleton instance = new DataSingleton();
    private DataSingleton() { }

    public String id;
    public ArrayList<Estate> estates = new ArrayList<>();

    public static DataSingleton getInstance() {
        return instance;
    }
}
