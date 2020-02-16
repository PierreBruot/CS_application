package com.coutinsociety.kanma.utils;

public class IDRequest {
    public static long idRequest=0;

    public static long add(){
        return ++idRequest;
    }
}
