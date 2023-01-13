package com.ardev.builder.completion.xml.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public enum Format {
    
    STRING,

    
    BOOLEAN,

    INTEGER,

    FRACTION,

    FLOAT,

    COLOR,

   
    REFERENCE,

    
    DIMENSION,

   
    ENUM,

    
    FLAG;

    
    public static List<Format> fromString(String declaration) {
        String[] split = declaration.split("\\|");
        if (split.length == 0) {
            return Collections.singletonList(fromSingleString(declaration));
        }
        List<Format> formats = new ArrayList<>();
        for (String s : split) {
            Format format = fromSingleString(s);
            if (format != null) {
                formats.add(format);
            }
        }
        return formats;
    }

    private static Format fromSingleString(String string) {
        switch (string.toLowerCase()) {
            case "string": return Format.STRING;
            case "boolean": return Format.BOOLEAN;
            case "dimension": return Format.DIMENSION;
            case "integer": return Format.INTEGER;
            case "float": return Format.FLOAT;
            case "fraction": return Format.FRACTION;
            case "enum": return Format.ENUM;
            case "color": return Format.COLOR;
            case "flag":
            case "flags": return Format.FLAG;
            case "reference": return Format.REFERENCE;
        }
        return null;
    }
}
