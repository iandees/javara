package com.yellowbkpk.termscrape;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * @author Ian Dees
 * 
 */
public class Main {
    public static void main(String[] args) {
        Tidy tidy = new Tidy();
        tidy.setXmlOut(true);
        tidy.setIndentContent(true);
        FileInputStream fileIS;
        try {
            fileIS = new FileInputStream(new File("D:\\Documents and Settings\\212034767\\Desktop\\terms.php.htm"));
            tidy.setQuiet(true);
            tidy.setShowWarnings(false);
            Document parseDOM = tidy.parseDOM(fileIS, null);
            NodeList childNodes = parseDOM.getElementsByTagName("table").item(7).getChildNodes();
            // Each row in the term table
            for(int i = 0; i < childNodes.getLength(); i++) {
                System.out.print(i + " ");
                Node item = childNodes.item(i);
                if (item.getNodeType() > 1) {
                    System.out.println(item.getNodeName() + " " + item.getNodeValue());
                } else {
                    System.out.println(item.getNodeName() + " " + item.getNodeType());
                }
                
                NamedNodeMap attributes = item.getAttributes();
                System.out.print("   Attributes: ");
                for(int j = 0; j < attributes.getLength(); j++) {
                    System.out.print(attributes.item(j).getNodeName() + " = " + attributes.item(j).getNodeValue() + " ");
                }
                System.out.println();
                
                Section theSection = new Section();
                NodeList classdata = item.getChildNodes();
                System.out.println("   Num data elements in this row: " + classdata.getLength());
                // First year only or not
                Node a = classdata.item(0);
                if(a.getNodeName().equals("td") && a.hasChildNodes() && a.getChildNodes().item(0).getNodeValue().equals("*")) {
                    theSection.setFirstYearOnly(true);
                    System.out.println("    This is a FYO class");
                } else {
                    theSection.setFirstYearOnly(false);
                }
                
                // Department
                a = classdata.item(1);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    theSection.setDepartment(data);
                    System.out.println("    Department: " + data);
                } else {
                    theSection.setDepartment(null);
                }
                
                // block and section
                a = classdata.item(2);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    Integer block = Integer.parseInt(data.substring(0, 1));
                    
                    theSection.setBlock(block);
                    System.out.println("    Block: " + block);
                    
                    Node aTag = a.getChildNodes().item(1);
                    String catalogURL = aTag.getAttributes().getNamedItem("href").getNodeValue();
                    String sectionNumber = aTag.getChildNodes().item(0).getNodeValue();
                    theSection.setSectionNumber(sectionNumber);
                    theSection.setCatalogURL(catalogURL);
                } else {
                    theSection.setSectionNumber(null);
                    theSection.setCatalogURL(null);
                }
                
                // credits
                a = classdata.item(3);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    
                    theSection.setCredits(data);
                    System.out.println("    Credits: " + data);
                } else {
                    theSection.setCredits(null);
                }
                
                // division
                a = classdata.item(4);
                if(a.getNodeName().equals("td") && a.hasChildNodes() && a.getChildNodes().item(0).getNodeName().equals("a") && (a.getChildNodes().item(0).getChildNodes().item(0) != null)) {
                    String data = a.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
                    
                    theSection.setDivision(data);
                    System.out.println("    Division: " + data);
                } else {
                    theSection.setDivision(null);
                }
                
                // prerequisite
                a = classdata.item(5);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    
                    theSection.setCredits(data);
                    System.out.println("    Prerequisites: " + data);
                } else {
                    theSection.setCredits(null);
                }
                
                // course title
                a = classdata.item(6);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    
                    theSection.setCredits(data);
                    System.out.println("    Course Title: " + data);
                } else {
                    theSection.setCredits(null);
                }
                
                // faculty member
                a = classdata.item(7);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    
                    theSection.setCredits(data);
                    System.out.println("    Faculty: " + data);
                } else {
                    theSection.setCredits(null);
                }
                
                // enrollment
                a = classdata.item(8);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    
                    theSection.setCredits(data);
                    System.out.println("    Enroll: " + data);
                } else {
                    theSection.setCredits(null);
                }
                
                // capacity
                a = classdata.item(9);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    
                    theSection.setCredits(data);
                    System.out.println("    Cap: " + data);
                } else {
                    theSection.setCredits(null);
                }
                
                // room
                a = classdata.item(10);
                if(a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    
                    theSection.setCredits(data);
                    System.out.println("    Room: " + data);
                } else {
                    theSection.setCredits(null);
                }
                
                System.out.println(a.getNodeName());
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
