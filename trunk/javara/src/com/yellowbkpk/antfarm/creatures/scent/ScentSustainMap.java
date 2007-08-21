package com.yellowbkpk.antfarm.creatures.scent;

import java.util.HashMap;

public class ScentSustainMap {
    private static HashMap<ScentType, Double> scentToSustainMap = new HashMap<ScentType, Double>();

    static {
        map(ScentType.ALARM, 0.80);
        map(ScentType.FORAGE, 0.90);
        map(ScentType.TRAIL, 0.95);
    }

    private static void map(ScentType trail, double fraction) {
        scentToSustainMap.put(trail, fraction);
    }

    public static double getSustain(ScentType type) {
        return scentToSustainMap.get(type);
    }
}
