package ru.funnydwarf.iot.nml.modules.onewire;

import java.io.File;
import java.util.ArrayList;

public class OneWireDriverController {

    static final String ONE_WIRE_ROOT_PATH = "/sys/bus/w1/devices/";

    public static ArrayList<OneWireUnit> getConnectedUnits() {
        File devicesFolder = new File(ONE_WIRE_ROOT_PATH);
        devicesFolder.list()
    }
}
