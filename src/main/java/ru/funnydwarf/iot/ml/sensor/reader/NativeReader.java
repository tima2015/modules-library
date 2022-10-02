package ru.funnydwarf.iot.ml.sensor.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.ml.sensor.MeasurementData;

@Component
public class NativeReader implements Reader{

    private static final Logger log = LoggerFactory.getLogger(NativeReader.class);

    private static native double digitalRead(int pin);
    @Override
    public MeasurementData[] read(Object address) {
        log.debug("read() called with: address = [{}]", address);
        MeasurementData result = MeasurementData.createToCurrentDate(digitalRead((Integer) address), "", "");
        log.debug("read() returned: {}", result);
        return new MeasurementData[]{result};
    }
}
