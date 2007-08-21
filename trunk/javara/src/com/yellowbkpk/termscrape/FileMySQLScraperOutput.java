package com.yellowbkpk.termscrape;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileMySQLScraperOutput implements ScraperOutput {
    private BufferedWriter output;

    public FileMySQLScraperOutput(String filename) {
        try {
            File f = new File(filename);
            output = new BufferedWriter(new FileWriter(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSection(Section theSection) {
        String line = "INSERT INTO `classes` " +
        "(department, block, section, sectionURL, credits, division, professor, title, freshmanOnly, prerequisite, year, enrollment, capacity, building, roomnumber) "+
        "VALUES " +
        "('"+theSection.getDepartment()+"'," +
        " '"+theSection.getBlock()+"'," +
        " '"+theSection.getSectionID()+"'," +
        " '"+theSection.getCatalogURL()+"'," +
        " '"+theSection.getCredits()+"'," +
        " '"+theSection.getDivision()+"'," +
        " '"+theSection.getFaculty()+"'," +
        " '"+theSection.getTitle()+"'," +
        " '"+(theSection.getFreshmanOnly()?1:0)+"'," +
        " '"+theSection.getPrerequisites()+"'," +
        " '"+theSection.getYear()+"'," +
        " '"+theSection.getEnrollment()+"'," +
        " '"+theSection.getCapacity()+"'," +
        " '"+theSection.getBuilding()+"'," +
        " '"+theSection.getRoomnumber()+"')";
        try {
            output.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanup() {
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
