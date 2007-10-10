package com.yellowbkpk.util.dbf;

import java.io.DataInputStream;
import java.io.IOException;

public class DBFField {

    private int firstByte;
    private byte[] fieldName = new byte[10];
    private int fieldType;
    private int fieldLength;
    private int decimalCount;
    private boolean valid = true;
    
    /**
     * @param bis
     */
    public DBFField(DataInputStream bis) {
        try {
            firstByte = bis.readUnsignedByte();
            
            if(firstByte == 0x0D) {
                valid = false;
                return;
            }
            
            bis.readFully(fieldName);
            fieldType = bis.readUnsignedByte();
            bis.skipBytes(4); // Field data address
            fieldLength = bis.readUnsignedByte();
            decimalCount = bis.readUnsignedByte();
            bis.skipBytes(2); // Reserved for multi-user
            bis.skipBytes(1); // Work area ID
            bis.skipBytes(2); // Reserved for multi-user
            bis.skipBytes(1); // Flag for set fields
            bis.skipBytes(7); // Reserved
            bis.skipBytes(1); // Index field
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
        return (String.valueOf((char)firstByte) + new String(fieldName)).trim();
    }

    /**
     * @return
     */
    public int getFieldLength() {
        return fieldLength;
    }

}
