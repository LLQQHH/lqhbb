package com.lqh.jaxlinmaster.bean;

import com.google.gson.annotations.SerializedName;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.GsonExclude;

/**
 * Created by Linqh on 2021/12/21.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class LqhTestBeanNew {
   private String name;
    private int age;
    @GsonExclude
    private  String surname;
    private String familyAddress;
    @SerializedName(value = "email", alternate = {"emailAddress", "email_address"})
    private String emailAddress;

    public LqhTestBeanNew(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public LqhTestBeanNew(String name) {
        this.name = name;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "LqhTestBeanNew{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", surname='" + surname + '\'' +
                ", familyAddress='" + familyAddress + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
