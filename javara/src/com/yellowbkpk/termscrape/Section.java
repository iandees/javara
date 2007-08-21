package com.yellowbkpk.termscrape;
/*
 * Section.java
 *
 * Copyright 2007 General Electric Company. All Rights Reserved.
 */

/**
 * @author Ian Dees
 *
 */
public class Section {

    private boolean isFirstYearOnly;
    private String department;
    private Integer block;
    private String sectionID;
    private String catalogURL;
    private String credits;
    private String division;
    private String prereqs;
    private String title;
    private String faculty;
    private String enrollment;
    private String capacity;
    private String room;

    /**
     * @param b
     */
    public void setFirstYearOnly(boolean b) {
        isFirstYearOnly = b;
    }

    /**
     * @param data
     */
    public void setDepartment(String data) {
        department = data;
    }

    /**
     * @param block
     */
    public void setBlock(Integer bl) {
        block = bl;
    }

    /**
     * @param parseInt
     */
    public void setSectionNumber(String sectionNum) {
        sectionID = sectionNum;
    }

    /**
     * @param catalogURL
     */
    public void setCatalogURL(String catURL) {
        catalogURL = catURL;
    }

    /**
     * @param data
     */
    public void setCredits(String data) {
        credits = data;
    }

    /**
     * @param data
     */
    public void setDivision(String data) {
        division = data;
    }

    /**
     * @param data
     */
    public void setPrerequisites(String data) {
        prereqs = data;
    }

    /**
     * @param data
     */
    public void setTitle(String data) {
        title = data;
    }

    /**
     * @param data
     */
    public void setFacultyMember(String data) {
        faculty = data;
    }

    /**
     * @param data
     */
    public void setEnrollment(String data) {
        enrollment = data;
    }

    /**
     * @param data
     */
    public void setCapacity(String data) {
        capacity = data;
    }

    /**
     * @param data
     */
    public void setRoom(String data) {
        room = data;
    }

}
