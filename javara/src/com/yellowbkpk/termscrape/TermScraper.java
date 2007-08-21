package com.yellowbkpk.termscrape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * @author Ian Dees
 *
 */
public class TermScraper {

    private Tidy tidy;
    private int block;
    private int year;
    private ScraperOutput output;

    /**
     * @param block
     * @param year
     */
    public TermScraper(int block, int year, ScraperOutput output) {
        // Initialize this object
        this.block = block;
        this.year = year;
        
        // Initialize the Tidy object
        this.tidy = new Tidy();
        this.tidy.setXmlOut(true);
        this.tidy.setIndentContent(true);
        this.tidy.setQuiet(true);
        this.tidy.setShowWarnings(false);
        
        // Initialize the output object
        this.output = output;
    }

    /**
     * 
     */
    public void scrape() {
        InputStream fileIS;
        try {
            Section theSection = new Section();
            
            fileIS = new URL("http://www.cornellcollege.edu/term_table/terms.php?term=" + block + "&year=" + year)
                    .openStream();
            Document parseDOM = tidy.parseDOM(fileIS, null);
            
            // Parse the year
            theSection.setYear(year);

            NodeList childNodes = parseDOM.getElementsByTagName("table").item(7).getChildNodes();

            // Each row in the term table
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                NodeList classdata = item.getChildNodes();
                // First year only or not
                Node a = classdata.item(0);
                if (a.getNodeName().equals("td") && a.hasChildNodes()
                        && a.getChildNodes().item(0).getNodeValue().equals("*")) {
                    theSection.setFirstYearOnly(true);
                    System.out.println("This is a FYO class");
                } else {
                    theSection.setFirstYearOnly(false);
                }

                // Department
                a = classdata.item(1);
                if (a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    theSection.setDepartment(data);
                    System.out.println("Department: " + data);
                } else {
                    theSection.setDepartment("");
                }

                // block and section
                a = classdata.item(2);
                if (a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    Integer block = Integer.parseInt(data.substring(0, 1));

                    theSection.setBlock(block);
                    System.out.println("Block: " + block);

                    Node aTag = a.getChildNodes().item(1);
                    String catalogURL = aTag.getAttributes().getNamedItem("href").getNodeValue();
                    String sectionNumber = aTag.getChildNodes().item(0).getNodeValue();
                    
                    String sectionSuffix = "";
                    if(a.getChildNodes().item(2) != null) {
                        sectionSuffix = a.getChildNodes().item(2).getNodeValue();
                    }
                    theSection.setSectionNumber(sectionNumber + sectionSuffix);
                    theSection.setCatalogURL(catalogURL);
                } else {
                    theSection.setSectionNumber("");
                    theSection.setCatalogURL("");
                }

                // credits
                a = classdata.item(3);
                if (a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();

                    theSection.setCredits(data);
                    System.out.println("Credits: " + data);
                } else {
                    theSection.setCredits("");
                }

                // division
                a = classdata.item(4);
                if (a.getNodeName().equals("td") && a.hasChildNodes()
                        && a.getChildNodes().item(0).getNodeName().equals("a")
                        && (a.getChildNodes().item(0).getChildNodes().item(0) != null)) {
                    String data = a.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();

                    theSection.setDivision(data);
                    System.out.println("Division: " + data);
                } else {
                    theSection.setDivision("");
                }

                // prerequisite
                a = classdata.item(5);
                if (a.getNodeName().equals("td") && a.hasChildNodes()
                        && a.getChildNodes().item(0).getChildNodes().item(0).getNodeName().equals("a")
                        && (a.getChildNodes().item(0).getChildNodes().item(0) != null)) {
                    String data = a.getChildNodes().item(0).getChildNodes().item(0).getAttributes().item(0)
                            .getNodeValue();
                    data = data.substring(23, data.length() - 7);
                    data = fetchPrerequisites(data);

                    theSection.setPrerequisites(data);
                    System.out.println("Prerequisites: " + data);
                } else {
                    theSection.setPrerequisites("");
                }

                // course title
                a = classdata.item(6);
                if (a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    data = escapeLine(data);

                    theSection.setTitle(data);
                    System.out.println("Course Title: " + data);
                } else {
                    theSection.setTitle("");
                }

                // faculty member
                a = classdata.item(7);
                if (a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();
                    data = escapeLine(data);

                    theSection.setFacultyMember(data);
                    System.out.println("Faculty: " + data);
                } else {
                    theSection.setFacultyMember("");
                }

                // enrollment
                a = classdata.item(8);
                if (a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();

                    theSection.setEnrollment(data);
                    System.out.println("Enroll: " + data);
                } else {
                    theSection.setEnrollment("");
                }

                // capacity
                a = classdata.item(9);
                if (a.getNodeName().equals("td") && a.hasChildNodes()) {
                    String data = a.getChildNodes().item(0).getNodeValue();

                    theSection.setCapacity(data);
                    System.out.println("Cap: " + data);
                } else {
                    theSection.setCapacity("");
                }

                // room
                a = classdata.item(10);
                if (a.getNodeName().equals("td") && a.hasChildNodes()
                        && a.getChildNodes().item(0).getNodeName().equals("a")
                        && (a.getChildNodes().item(0).getChildNodes().item(0) != null)) {
                    String data = a.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
                    String[] strings = data.split(" ");
                    if(strings.length == 2) {
                        theSection.setBuilding(strings[0]);
                        theSection.setRoomnumber(strings[1]);

                        System.out.println("Building: " + strings[0]);
                        System.out.println("Room: " + strings[1]);
                    } else {
                        theSection.setBuilding("");
                        theSection.setRoomnumber(strings[0]);

                        System.out.println("Room: " + strings[0]);
                    }
                } else {
                    theSection.setRoomnumber("");
                    theSection.setBuilding("");
                }
                
                output.addSection(theSection);
                System.out.println("=== Added section ===");

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    
    /**
     * @param data
     */
    private String fetchPrerequisites(String data) {
        try {
            InputStream fileIS = new URL("http://www.cornellcollege.edu/term_table/" + data).openStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(fileIS));
            StringBuffer b = new StringBuffer();
            
            String s = null;
            while((s = in.readLine()) != null) {
                b.append(s);
            }
            String whole = b.toString();
            in.close();
            
            int start = whole.indexOf(" Prerequisites</strong><br><br><strong>");
            if(start > 0) {
                start += 39;
                int end = whole.indexOf("<br><script src=\'http://w");
                if(end > 0) {
                    String substring = whole.substring(start, end);
                    substring = substring.replaceAll("<br>", ", ");
                    substring = substring.replaceAll("</?[A-Za-z]+\\b[^>]*>", "");
                    return escapeLine(substring);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    static public String escapeLine(String s) {
        String retvalue = s;
        if (s.indexOf("'") != -1) {
            StringBuffer hold = new StringBuffer();
            char c;
            for (int i = 0; i < s.length(); i++) {
                if ((c = s.charAt(i)) == '\'') {
                    hold.append("''");
                } else {
                    hold.append(c);
                }
            }
            retvalue = hold.toString();
        }
        return retvalue;
    }

}
