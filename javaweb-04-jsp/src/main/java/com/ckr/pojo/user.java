package com.ckr.pojo;

/**
 * @author Shadowckr
 * @create 2021-09-12 10:17
 */

/*
ORM(Object Relational Mapping):对象关系映射
数据库中的一个表--->Java中的一个类
数据库中的一个表字段--->Java中的一个类的属性
数据库中的一个表的行记录--->Java中的一个类的对象
 */

// 实体类，一般都是和数据库中的表结构一一对应！
public class user {
    private int id;
    private String name;
    private int age;
    private String address;

    public user() {
    }

    public user(int id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
