package com.crazy;

import java.util.ArrayList;
import java.util.List;

public class Testt {

    public static void main(String[] args) {
        String t = "1571414400000,59.0,aaaaaaaaaa\"\"";
        int first = t.indexOf(",");
        String firstStr = t.substring(0,first);
        int second = t.indexOf(",", first + 1);
        String secondStr = t.substring(first + 1, second);
        String trdStr = t.substring(second + 1);
        System.out.println(firstStr);
        System.out.println(secondStr);
        System.out.println(trdStr);
    }
}
