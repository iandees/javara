package com.yellowbkpk.util.dbf;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DBFReader {

    private DBFHeader header;
    private List<DBFRecord> records;

    public DBFReader(InputStream stream) {
        DataInputStream bis = new DataInputStream(stream);
        
        // Parse the header information
        header = new DBFHeader(bis);
        List<DBFField> fields = header.getFields();
        
        // Parse the records
        int numberOfRecords = header.getNumberOfRecords();
        int curField = 0;
        records = new ArrayList<DBFRecord>(numberOfRecords);
        DBFField recordField = fields.get(curField++);
        DBFRecord record = new DBFRecord(recordField, bis);
        while(record.isValid()) {
            records.add(record);
            if(curField >= numberOfRecords) {
                curField = 0;
            }
            recordField = fields.get(curField++);
            record = new DBFRecord(recordField, bis);
        }
    }
    
    /**
     * @param filename
     * @throws FileNotFoundException 
     */
    public DBFReader(String filename) throws FileNotFoundException {
        this(new FileInputStream(filename));
    }

    /**
     * @return
     */
    public int getFieldCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @param i
     * @return
     */
    public DBFField getField(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public boolean hasNextRecord() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @return
     */
    public Object[] nextRecord() {
        // TODO Auto-generated method stub
        return null;
    }

}
