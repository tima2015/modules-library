package ru.funnydwarf.iot.nml.modules;

public interface Module {

    String NO_NAME = "No name";

    default String getName(){
        return NO_NAME;
    }

}
