package com.example.flex.Models;

import java.util.Date;

public class Estate {
    public String id;
    public String type;
    public String title;
    public String money;
    public String created_date;
    public Estate(String id, String type, String title, String money, String created_date) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.money = money;
        this.created_date = created_date;
    }
}
