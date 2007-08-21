package com.yellowbkpk.termscrape;

/**
 * @author Ian Dees
 *
 */
public class TermScraperBootstrap {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String dbHost = System.getProperty("db.host");
        String dbName = System.getProperty("db.name");
        String dbUsername = System.getProperty("db.username");
        String dbPassword = System.getProperty("db.password");
        ScraperOutput mysqlOutput = new MySQLScraperOutput(dbHost, dbName, dbUsername, dbPassword);
        
        for(int year = 2006; year < 2009; year++) {
            for(int block = 1; block <= 9; block++) {
                TermScraper a = new TermScraper(block, year, mysqlOutput);
                a.scrape();
            }
        }
        
        mysqlOutput.cleanup();
    }

}
