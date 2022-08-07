package ru.funnydwarf.iot.nml.modules.onewire;

import ru.funnydwarf.iot.nml.modules.Module;

import java.util.ArrayList;

public class OneWire implements Module {

    private final ArrayList<OneWireUnit> oneWireUnits = new ArrayList<>();
    private final ArrayList<Class<? extends OneWireUnit>> classes = new ArrayList<>();

    public void searchDevices() {

    }

    private static native long[] searchDevices(int portNumber);

}
