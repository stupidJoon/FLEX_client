package com.example.flex.Models;

public class DataSingleton {
    private static DataSingleton instance = new DataSingleton();
    private DataSingleton() { }
    public static DataSingleton getInstance() {
        return instance;
    }
}
