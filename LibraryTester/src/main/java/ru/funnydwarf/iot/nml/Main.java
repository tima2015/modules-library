package ru.funnydwarf.iot.nml;

public class Main {


    static {
        System.loadLibrary("JNI_Library");
    }

    public static void main(String[] args) {
        new Testy().helloWorld();
        System.out.println("lol!");
    }

}