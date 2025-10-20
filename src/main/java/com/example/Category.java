package com.example;

import java.util.HashMap;
import java.util.Map;

public class Category {

    private static final Map<String, Category> categories = new HashMap<>();
    private final String name;

    private Category(String name){
        this.name = name;
    }

    public static Category of(String name){
        if (name == null){
            throw new IllegalArgumentException("Category name can't be null");
        }
        else if (name.isBlank()){
            throw new IllegalArgumentException("Category name can't be blank");
        }
        else {
            return setAndOrGet(name.trim().substring(0,1).toUpperCase() + name.substring(1));
        }
    }

    private static Category setAndOrGet(String name){
        categories.putIfAbsent(name, new Category(name));
        return categories.get(name);
    }

    public String getName() {
        return this.name;
    }
}
