package ru.funnydwarf.iot.nml;

public class Testy {//  "-Djava.library.path=$APP_HOME/lib"

    public native void helloWorld();

    static {
        System.loadLibrary("JNI_Library");
    }

    public static void main(String[] args) {
        new Testy().helloWorld();
        System.out.println("lol!");
    }

}
