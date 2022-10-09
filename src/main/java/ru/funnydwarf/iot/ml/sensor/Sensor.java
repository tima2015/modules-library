package ru.funnydwarf.iot.ml.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.sensor.dataio.DataInput;
import ru.funnydwarf.iot.ml.sensor.dataio.DataOutput;
import ru.funnydwarf.iot.ml.sensor.reader.Reader;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;

public class Sensor extends Module implements SchedulingConfigurer {

    private final static Logger log = LoggerFactory.getLogger(Sensor.class);

    private MeasurementData[] measurementData;
    private final String[] measurementIDs;

    private final Reader reader;
    private final DataInput dataInput;
    private final DataOutput dataOutput;
    private final long timeToRepeatMeasurement;

    public Sensor(Reader reader, DataInput dataInput, DataOutput dataOutput, long timeToRepeatMeasurement, Object address, String name, String description) {
        this(reader, dataInput, dataOutput, timeToRepeatMeasurement, address, name, description, "", "");
    }

    public Sensor(Reader reader, DataInput dataInput, DataOutput dataOutput, long timeToRepeatMeasurement, Object address, String name, String description, String userCustomName, String userCustomDescription) {
        super(address, name, description, userCustomName, userCustomDescription);
        this.reader = reader;
        this.dataInput = dataInput;
        this.dataOutput = dataOutput;
        this.timeToRepeatMeasurement = timeToRepeatMeasurement;
        measurementData = reader.getTemplateRead();
        measurementIDs = new String[measurementData.length];
        for (int i = 0; i < measurementData.length; i++) {
            measurementIDs[i] = name + '_' + measurementData[i].measurementName();
        }
    }

    public MeasurementData[] getMeasurementData() {
        return measurementData;
    }

    public MeasurementData[][] getHistoryMeasurementValue(int offset, int length){
        log.debug("getHistoryMeasurementValue() called with: offset = [{}], length = [{}]", offset, length);
        MeasurementData[][] history = new MeasurementData[measurementData.length][];
        try {
            for (int i = 0; i < history.length; i++) {
                MeasurementData data = measurementData[i];
                history[i] = dataInput.read(measurementIDs[i], data.measurementName(), data.unitName(), offset, length);
            }
            return history;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new MeasurementData[0][0];
    }

    private void updateMeasurement() {
        log.debug("updateMeasurement() called");
        try {
            measurementData = reader.read(getAddress());
            if (dataOutput == null) {
                return;
            }
            for (int i = 0; i < measurementData.length; i++) {
                dataOutput.write(measurementData[i], measurementIDs[i]);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            measurementData = new MeasurementData[0];
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newSingleThreadScheduledExecutor());
        taskRegistrar.addTriggerTask(this::updateMeasurement, triggerContext -> {
            Optional<Date> lastCompletionTime =
                    Optional.ofNullable(triggerContext.lastCompletionTime());
            Instant nextExecutionTime =
                    lastCompletionTime.orElseGet(Date::new).toInstant()
                            .plusMillis(timeToRepeatMeasurement);
            return Date.from(nextExecutionTime);
        });
    }
}
