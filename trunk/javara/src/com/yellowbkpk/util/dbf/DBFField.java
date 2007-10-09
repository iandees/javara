package com.yellowbkpk.util.dbf;

import java.io.DataInputStream;
import java.io.IOException;

public class DBFField {

    private byte[] firstByte = new byte[1];
    private byte[] fieldName = new byte[10];
    private byte[] fieldType = new byte[1];
    private int fieldLength;
    private int decimalCount;
    private byte[] indexField = new byte[1];
    private boolean valid = true;
    
    /**
     * @param bis
     */
    public DBFField(DataInputStream bis) {
        try {
            bis.read(firstByte);
            
            if(firstByte[0] == 0x0D) {
                valid = false;
                return;
            }
            
            bis.read(fieldName);
            bis.read(fieldType);
            bis.skip(4); // Field data address
            fieldLength = bis.readUnsignedByte();
            decimalCount = bis.readUnsignedByte();
            bis.skip(2); // Reserved for multi-user
            bis.skip(1); // Work area ID
            bis.skip(2); // Reserved for multi-user
            bis.skip(1); // Flag for set fields
            bis.skip(7); // Reserved
            bis.read(indexField);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public boolean isValid() {
        return valid;
    }

    public String toString() {
        return getName();
    }

    /**
     * @return
     */
    public String getName() {
        return (new String(firstByte) + new String(fieldName)).trim();
    }

    /**
     * @return
     */
    public int getFieldLength() {
        return fieldLength;
    }

}
