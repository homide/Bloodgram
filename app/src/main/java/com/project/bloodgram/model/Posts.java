package com.project.bloodgram.model;

public class Posts {
    String postID, uid;
    String name, address, mail, bloodgrp;
    long phNumber, patientphNumber;
    int patientAge;
    String patientName, patientbloodgrp, patientAddress, patientRelation;

    Posts(){
    }

    public Posts(String uid, String postID, String name, long patientphNumber, int patientAge, String patientName, String patientbloodgrp) {
        this.uid = uid;
        this.postID = postID;
        this.name = name;
        this.patientphNumber = patientphNumber;
        this.patientAge = patientAge;
        this.patientName = patientName;
        this.patientbloodgrp = patientbloodgrp;
    }

    public Posts(String uid,String postID, String name, String address, String mail, String bloodgrp, long phNumber, long patientphNumber, int patientAge, String patientName, String patientbloodgrp, String patientAddress, String patientRelation) {
        this.uid = uid;
        this.postID = postID;
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.bloodgrp = bloodgrp;
        this.phNumber = phNumber;
        this.patientphNumber = patientphNumber;
        this.patientAge = patientAge;
        this.patientName = patientName;
        this.patientbloodgrp = patientbloodgrp;
        this.patientAddress = patientAddress;
        this.patientRelation = patientRelation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public long getPatientphNumber() {
        return patientphNumber;
    }

    public void setPatientphNumber(long patientphNumber) {
        this.patientphNumber = patientphNumber;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientbloodgrp() {
        return patientbloodgrp;
    }

    public void setPatientbloodgrp(String patientbloodgrp) {
        this.patientbloodgrp = patientbloodgrp;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientRelation() {
        return patientRelation;
    }

    public void setPatientRelation(String patientRelation) {
        this.patientRelation = patientRelation;
    }
}
