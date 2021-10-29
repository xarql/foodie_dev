package com.imooc.enums;

public enum CategoryRoot {
    ONE (1,"一级"),
    TWO (2,"二级"),
    THREE(3,"三级");

    public final int  type;
    public final String name;
    CategoryRoot(int type,String name){
        this.type=type;
        this.name=name;
    }
}
