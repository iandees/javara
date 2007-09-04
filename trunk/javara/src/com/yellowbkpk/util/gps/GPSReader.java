package com.yellowbkpk.util.gps;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

/**
 * @author Ian Dees
 * 
 */
public class GPSReader extends Thread implements SerialPortEventListener {
    
    private static final double MPH_PER_KNOT = 1.1507;
    private static final String CMD_GPRMC = "$GPRMC";
    private static final String CMD_GPVTG = "$GPVTG";
    private static final String CMD_GPGGA = "$GPGGA";
    private static final String CMD_GP = "$GP";
    private CommPortIdentifier portIdentifier;
    private SerialPort serialPort;
    private InputStream inputStream;
    private boolean running;

    private StringBuffer sBuf;
    private List<GPSChangeListener> listeners;

    public GPSReader(String comPort) throws NoSuchPortException, PortInUseException {

        sBuf = new StringBuffer();

        portIdentifier = CommPortIdentifier.getPortIdentifier(comPort);

        serialPort = (SerialPort) portIdentifier.open("SimpleReadApp", 2000);

        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e1) {
            e1.printStackTrace();
        }
        serialPort.notifyOnDataAvailable(true);

        try {
            serialPort.setSerialPortParams(4800, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
        
        listeners = new ArrayList<GPSChangeListener>();
    }

    public void run() {
        running = true;

        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public synchronized void shutdown() {
        running = false;
    }

    public void serialEvent(SerialPortEvent arg0) {
        try {
            if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

                try {
                    byte[] readBuffer = new byte[inputStream.available()];
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                    }

                    sBuf.append(new String(readBuffer));

                    if (sBuf.toString().contains("\n")) {
                        parseNMEAString(sBuf.toString());
                        sBuf = new StringBuffer();
                    }
                } catch (IOException e) {
                }
            }
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            // Do nothing... skip this notification.
        }
    }

    /**
     * @param string
     */
    private void parseNMEAString(String string) {
        if(!isChecksumValid(string)) {
            return;
        }
        
        String[] commands = string.split(",");
        
        String command = commands[0];
        
        if(CMD_GPRMC.equals(command)) {
            System.out.println(string);
            /*for (String string2 : commands) {
                System.out.println(string2);
            }*/
            
            if("V".equals(commands[2])) {
                return;
            }
            
            if(commands[3].length() < 2) {
                return;
            }
            
            String latDegStr = commands[3].substring(0, 2);
            latDegStr.replace("N", "");
            int latDegVal = Integer.parseInt(latDegStr);
            String latMinStr = commands[3].substring(2);
            double latMinVal = Double.parseDouble(latMinStr);
            double lat = latDegVal + (latMinVal / 60.0);
            if("S".equals(commands[4])) {
                lat *= -1;
            }

            if(commands[5].length() < 2) {
                return;
            }
            
            String lngDegStr = commands[5].substring(0, 3);
            int lngDegVal = Integer.parseInt(lngDegStr);
            String lngMinStr = commands[5].substring(3);
            double lngMinVal = Double.parseDouble(lngMinStr);
            double lng = lngDegVal + (lngMinVal / 60.0);
            if("W".equals(commands[6])) {
                lng *= -1;
            }
            
            double speedKnots = Double.parseDouble(commands[7]);
            double speedMPH = speedKnots * MPH_PER_KNOT;
            System.out.println(speedMPH + "mph");
            double courseDegrees = Double.parseDouble(commands[8]);
            
            notifyOfNewLocation(lat, lng, speedMPH, courseDegrees);
            
        } else if(CMD_GPVTG.equals(command)) {
            
        } else if(CMD_GPGGA.equals(command)) {
            
        } else if(CMD_GP.equals(command)) {
            
        }
    }

    /**
     * @param string
     * @return
     */
    private boolean isChecksumValid(String string) {
        // Fetch the checksum
        int csIndex = string.indexOf("*");
        if(csIndex < 0) {
            return false;
        }
        
        String expectedChecksum = string.trim().substring(csIndex + 1);
        
        if(expectedChecksum.length() > 2 || expectedChecksum.length() < 1) {
            return false;
        }
        
        int dsIndex = string.indexOf("$");
        if(dsIndex < 0) {
            return false;
        }
        
        String checksummedArea = string.substring(dsIndex + 1, csIndex);
        byte[] bytes = checksummedArea.getBytes();
        byte checksum = 0;
        for (byte b : bytes) {
            checksum ^= b;
        }
        Byte actualByte = new Byte(checksum);
        Byte expectedByte = Byte.decode("0x" + expectedChecksum);
        
        return actualByte.equals(expectedByte);
    }

    /**
     * @param lat
     * @param lng
     * @param course 
     * @param speed 
     */
    private void notifyOfNewLocation(double lat, double lng, double speed, double course) {
        for (GPSChangeListener listener : listeners) {
            listener.locationUpdated(lat, lng, speed, course);
        }
    }

    /**
     * @param changeListener
     */
    public void addGPSChangeListener(GPSChangeListener changeListener) {
        listeners.add(changeListener);
    }

}
