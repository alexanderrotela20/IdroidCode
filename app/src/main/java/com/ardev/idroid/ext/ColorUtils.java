package com.ardev.idroid.ext;





public class  ColorUtils {

public static boolean isHexFormat(String color) {
        return color.matches("\\p{XDigit}+");
    }

public static boolean isColorFormat(String color) {
        return color.matches("^#([0-9a-fA-F]{3}|[0-9a-fA-F]{4}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})$");
    }


public static String getColor( String color) {


return isColorAbrr(color) ?  getColorForAbrr(color) : isColor(color) ? color : null;
		
}

private static String getColorForAbrr(String abrr){
           String color = "";
            char[] chars = abrr.toCharArray();
            for(int i = 0; i <chars.length;i++)
                color += i == 0  ? "" +chars[i] :""+chars[i]+chars[i];
            return color;
        }
private static boolean isColor(String color) {
        return color.matches("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{8})$");
    }
    
 private static boolean isColorAbrr(String color) {
 return color.matches("^#([0-9a-fA-F]{3}|[0-9a-fA-F]{4})$");
    }   
    
}