package com.example.charlie.excelexample;

/**
 * Created by charlie on 2017. 12. 7
 */

class Item {
    private String mName;
    private int mAge;

    Item(String name, int age){
        mName = name;
        mAge = age;
    }

    String getName() {
        return mName;
    }

    int getAge() {
        return mAge;
    }
}
