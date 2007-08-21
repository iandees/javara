package com.yellowbkpk.antfarm.creatures.scent;

public enum ScentType {

    ALARM("alarm"),
    TRAIL("trail"),
    FORAGE("forage");
    
    private String value;

    private ScentType(String name) {
        value = name;
    }
}
