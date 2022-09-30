package ru.funnydwarf.iot.nml.modules.sensor.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.nml.modules.sensor.MeasurementData;

import java.util.Date;

/**
 * @deprecated USE FOR TESTS ONLY!!!
 */
@Deprecated
@Component
public class PlugReader implements Reader{

    private final static Logger log = LoggerFactory.getLogger(PlugReader.class);

    @Override
    public MeasurementData[] read(Object address) {
        log.debug("read() called with: address = [{}]", address);
        MeasurementData data = new MeasurementData(Math.random(), "int", "Random", new Date());
        log.debug("read() returned: {}", data);
        return new MeasurementData[]{data};
    }
}
