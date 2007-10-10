package com.yellowbkpk.util.dbf;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class DBFHeader {

    private int versionNumber;
    private GregorianCalendar dateOfLastUpdate;
    private int numberOfRecords;
    private int headerLength;
    private int recordLength;
    private List<DBFField> fields;

    /**
     * @param bis
     */
    public DBFHeader(DataInputStream bis) {
        fields = new ArrayList<DBFField>();

        try {
            versionNumber = bis.readUnsignedByte();

            int dateOfLastUpdateYY = bis.readUnsignedByte();
            int dateOfLastUpdateMM = bis.readUnsignedByte();
            int dateOfLastUpdateDD = bis.readUnsignedByte();
            int yy = 1900 + dateOfLastUpdateYY;
            int mm = dateOfLastUpdateMM;
            int dd = dateOfLastUpdateDD;
            dateOfLastUpdate = new GregorianCalendar(yy, mm, dd);

            numberOfRecords = readUnsignedLittleEndianInt(bis);
            headerLength = readUnsignedLittleEndianShort(bis);
            recordLength = readUnsignedLittleEndianShort(bis);
            bis.skipBytes(2); // Reserved space
            bis.skipBytes(1); // Incomplete Transaction
            bis.skipBytes(1); // Encryption flag
            bis.skipBytes(4); // Reserved for LAN only
            bis.skipBytes(8); // Reserved for multi-user dBase
            bis.skipBytes(1); // MDX flag
            bis.skipBytes(1); // Language driver
            bis.skipBytes(2); // Reserved

            // Parse the field descriptors
            DBFField field = new DBFField(bis);
            while (field.isValid()) {
                fields.add(field);
                field = new DBFField(bis);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param bis
     * @return
     * @throws IOException 
     */
    private int readUnsignedLittleEndianShort(DataInputStream in) throws IOException {
        int ch1 = in.readUnsignedByte();
        int ch2 = in.readUnsignedByte();
        if ((ch2 | ch1) < 0)
            throw new EOFException();
        return (ch2 << 8) + (ch1 << 0);
    }

    /**
     * @param bis
     * @return
     * @throws IOException
     */
    private int readUnsignedLittleEndianInt(DataInputStream in) throws IOException {
        int ch1 = in.readUnsignedByte();
        int ch2 = in.readUnsignedByte();
        int ch3 = in.readUnsignedByte();
        int ch4 = in.readUnsignedByte();
        if ((ch4 | ch3 | ch2 | ch1) < 0)
            throw new EOFException();
        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public GregorianCalendar getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public int getRecordLength() {
        return recordLength;
    }

    /**
     * 
     */
    public List<DBFField> getFields() {
        return fields;
    }

}
