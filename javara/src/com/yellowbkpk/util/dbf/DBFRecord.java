package com.yellowbkpk.util.dbf;

import java.io.DataInputStream;
import java.io.IOException;

public class DBFRecord {

    private DBFField field;
    private byte recordDeleted;
    private byte[] data;
    private boolean valid = true;

    /**
     * @param bis
     */
    public DBFRecord(DBFField field, DataInputStream bis) {
        this.field = field;
        
        try {
            recordDeleted = bis.readByte();
            
            if(recordDeleted == 0x1A) {
                valid = false;
                return;
            }
            
            data = new byte[field.getFieldLength()];
            bis.readFully(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid() {
        return valid;
    }

    public DBFField getField() {
        return field;
    }
    
    public String toString() {
        return getData();
    }

    public String getData() {
        return new String(data).trim();
    }
}
