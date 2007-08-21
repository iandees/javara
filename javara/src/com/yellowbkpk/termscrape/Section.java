package com.yellowbkpk.termscrape;

/**
 * @author Ian Dees
 *
 */
public class Section {

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
    private String building;
    private String roomNumber;
    private int year;
    private boolean freshmanOnly;

    /**
     * @param b
     */
    public void setFirstYearOnly(boolean b) {
        freshmanOnly = b;
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
    public void setRoomnumber(String data) {
        roomNumber = data;
    }

    /**
     * @param data
     */
    public void setBuilding(String data) {
        building = data;
    }

    /**
     * @param data
     */
    public void setYear(int data) {
        year = data;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @return the block
     */
    public Integer getBlock() {
        return block;
    }

    /**
     * @return the sectionID
     */
    public String getSectionID() {
        return sectionID;
    }

    /**
     * @return the catalogURL
     */
    public String getCatalogURL() {
        return catalogURL;
    }

    /**
     * @return the credits
     */
    public String getCredits() {
        return credits;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @return the prerequisites
     */
    public String getPrerequisites() {
        return prereqs;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the faculty
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * @return the enrollment
     */
    public String getEnrollment() {
        return enrollment;
    }

    /**
     * @return the capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * @return
     */
    public String getBuilding() {
        return building;
    }

    /**
     * @return
     */
    public String getRoomnumber() {
        return roomNumber;
    }

    /**
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * @return
     */
    public boolean getFreshmanOnly() {
        return freshmanOnly;
    }

}
