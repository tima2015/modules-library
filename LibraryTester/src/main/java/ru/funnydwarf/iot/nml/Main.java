package ru.funnydwarf.iot.nml;

import ru.funnydwarf.iot.nml.modules.Module;
import ru.funnydwarf.iot.nml.modules.onewire.DS18B20OneWireUnit;
import ru.funnydwarf.iot.nml.modules.onewire.OneWire;
import ru.funnydwarf.iot.nml.modules.onewire.OneWireUnit;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main {


    static {
        System.loadLibrary("JNI_Library");
    }

    public static void main(String[] args) {
        //new Testy().helloWorld();
        try {
            JNILibrary.Modules modules = JNILibrary.initialize(new File("modules.json"));
            System.out.println(modules.toString());
            for (Module module : modules.getModules()) {
                if (Objects.equals(module.getName(), "oneWire0")){
                    OneWire oneWire = (OneWire) module;
                    for (OneWireUnit oneWireUnit : oneWire.getOneWireUnits()) {
                        if (oneWireUnit.getId().equals("28-0316a006c5ff")){
                            DS18B20OneWireUnit ds = (DS18B20OneWireUnit) oneWireUnit;
                            for (int i = 0; i < 20; i++) {
                                System.out.println(ds.takeMeasurements());
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("lol!");
    }

}