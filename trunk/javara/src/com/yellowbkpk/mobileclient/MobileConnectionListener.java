package com.yellowbkpk.mobileclient;

public interface MobileConnectionListener {

    void error(String message);

    void connected();

    void bedListUpdated();

}
