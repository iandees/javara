package com.yellowbkpk.algebracircuit;

public enum CircuitsEnum {
    /** The add circuit. */
    ADD("Add"),

    NEGATE("Negate"),

    MULTIPLY("Multiply"),

    RECIPROCAL("Reciprocal"),
    
    INPUT("Input"),
    
    OUTPUT("Output");

    private String name;

    private CircuitsEnum(String nm) {
        name = nm;
    }

    public String getName() {
        return name;
    }
}
