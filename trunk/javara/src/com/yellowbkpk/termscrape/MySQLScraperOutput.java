package com.yellowbkpk.termscrape;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLScraperOutput implements ScraperOutput {

    private Connection con;

    /**
     * @param dbHost
     * @param dbName
     * @param dbUsername
     * @param dbPassword
     */
    public MySQLScraperOutput(String dbHost, String dbName, String dbUsername, String dbPassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://"+dbHost+"/"+dbName, dbUsername, dbPassword);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSection(Section theSection) {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `classes` " +
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
                    " '"+theSection.getRoomnumber()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cleanup() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
