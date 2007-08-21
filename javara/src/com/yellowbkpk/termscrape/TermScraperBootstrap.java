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
        String dbHost = args[0];
        String dbName = args[1];
        String dbUsername = args[2];
        String dbPassword = args[3];
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
