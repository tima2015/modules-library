package ru.funnydwarf.iot.nml.modules.sensor.reader.onewire;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component()
public class DS18B12OneWireReader extends OneWireReader{

    @Override
    public double read(Object address) {
        try {
            String result = Files.readString(new File(ONE_WIRE_SLAVES_PATH + address + "/w1_slave").toPath());
            String temperature = result.substring(result.lastIndexOf("t=") + 2).trim();
            return Integer.parseInt(temperature)/1000d;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
