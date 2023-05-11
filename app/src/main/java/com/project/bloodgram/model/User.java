package com.project.bloodgram.model;

public class User {
    String mail, name, uid, address, bloodgrp;
    long phNumber;

    User(){}

    public User(String mail, String name, String uid, String address, String bloodgrp, long phNumber) {
        this.mail = mail;
        this.name = name;
        this.uid = uid;
        this.address = address;
        this.bloodgrp = bloodgrp;
        this.phNumber = phNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodgrp() {
        return bloodgrp;
    }

    public void setBloodgrp(String bloodgrp) {
        this.bloodgrp = bloodgrp;
    }

    public long getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(long phNumber) {
        this.phNumber = phNumber;
    }
}
