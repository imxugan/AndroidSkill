package com.android.skill.bean;

import com.android.skill.mypluglibrary.IBean;

public class Person implements IBean {
    private String name = "zma";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
