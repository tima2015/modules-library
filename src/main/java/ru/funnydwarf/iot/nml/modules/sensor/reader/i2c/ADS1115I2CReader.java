package ru.funnydwarf.iot.nml.modules.sensor.reader.i2c;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.nml.modules.sensor.MeasurementData;

@Component
public class ADS1115I2CReader extends I2CReader{
    @Override
    public MeasurementData[] read(Object address) {
        new ExecutionControl.NotImplementedException("This method not implement yet!").printStackTrace();
        return new MeasurementData[0];
    }
}
