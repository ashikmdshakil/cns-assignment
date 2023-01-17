package com.cns.project_management.service;

public class Util {
    public static String formatDateString(String date){
        String year = "";
        String month = "";
        String day = "";
        int count = 0;
        for(char letter: date.toCharArray()){
            if(letter == '-'){
                count = count + 1;
            }
            else{
                if(count == 0){
                    year = year + letter;
                }
                else if(count == 1){
                    month = month + letter;
                }
                else{
                    day = day+letter;
                }
            }
        }
        return day+"/"+month+"/"+year;
    }
}
