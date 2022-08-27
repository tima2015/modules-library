package ru.funnydwarf.iot.nml.modules.sensor.reader.i2c;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Component;

@Component
public class ADS1115I2CReader extends I2CReader{
    @Override
    public double read(Object address) {
        new ExecutionControl.NotImplementedException("This method not implement yet!").printStackTrace();
        return 0;
    }
}
