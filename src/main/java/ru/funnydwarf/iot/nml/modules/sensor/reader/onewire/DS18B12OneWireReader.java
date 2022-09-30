package ru.funnydwarf.iot.nml.modules.sensor.reader.onewire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.nml.modules.sensor.MeasurementData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Component()
public class DS18B12OneWireReader extends OneWireReader{

    private static final Logger log = LoggerFactory.getLogger(DS18B12OneWireReader.class);

    @Override
    public MeasurementData[] read(Object address) {
        log.debug("read() called with: address = [{}]", address);
        try {
            String resultString = Files.readString(new File(ONE_WIRE_SLAVES_PATH + address + "/w1_slave").toPath());
            String temperatureString = resultString.substring(resultString.lastIndexOf("t=") + 2).trim();
            MeasurementData result = MeasurementData.createToCurrentDate(Integer.parseInt(temperatureString) / 1000d, "°C", "Температура");
            log.debug("read() returned: {}", result);
            return new MeasurementData[]{result};
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new MeasurementData[] { new MeasurementData(0, "ERROR", e.getMessage(), new Date(0))};
        }
    }
}
