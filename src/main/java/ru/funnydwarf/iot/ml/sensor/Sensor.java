package ru.funnydwarf.iot.ml.sensor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import ru.funnydwarf.iot.ml.InitializationState;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.ModuleDescription;
import ru.funnydwarf.iot.ml.ModuleGroup;
import ru.funnydwarf.iot.ml.sensor.reader.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Сенсор/Датчик
 */
@Getter
@Slf4j
public class Sensor<ModuleGroupT extends ModuleGroup, AddressT> extends Module<ModuleGroupT, AddressT> {

    /**
     * Данные последних замеров
     */
    private Measurement[] measurements = new Measurement[0];

    /**
     * Читающий показания датчика
     */
    @Getter(AccessLevel.NONE)
    private final Reader<AddressT> reader;

    private final MeasurementData[] measurementData;

    /**
     * Аргументы для читателя
     */
    private final Object[] readerArgs;

    private final List<OnTakeMeasurementListener> onTakeMeasurementListeners = new ArrayList<>();

    public Sensor(Reader<AddressT> reader, MeasurementData[] measurementData, ModuleGroupT group, AddressT address, ModuleDescription moduleDescription, @Nullable Initializer initializer, Object ... readerArgs){
        super(group, address, moduleDescription, initializer);
        this.reader = reader;
        this.readerArgs = readerArgs;
        this.measurementData = measurementData;
    }

    private boolean checkSensor() {
        if (getInitializationState() == InitializationState.NOT_INITIALIZED){
            log.debug("[{}] takeMeasurement: module have initialization error! Pass...", getModuleDescription().getName());
            return true;
        }
        if (getInitializationState() == InitializationState.NOT_CONNECTED) {
            log.debug("[{}] takeMeasurement: module not connected! Pass...", getModuleDescription().getName());
            return true;
        }
        return false;
    }

    private void readMeasurements() {
        double[] measurementValues = reader.read(getAddress(), readerArgs);
        measurements = new Measurement[measurementValues.length];

        for (int i = 0; i < measurementValues.length; i++) {
            measurements[i] = new Measurement(measurementValues[i], measurementData[i]);
        }
    }

    /**
     * Выполнить получение новых замеров
     */
    public Measurement[] takeMeasurement() {
        log.debug("[{}] takeMeasurement() called", getModuleDescription().getName());
        if (checkSensor()) {
            return measurements = new Measurement[0];
        }
        try {
            readMeasurements();
            onTakeMeasurementListeners.forEach(onTakeMeasurementListener -> onTakeMeasurementListener.onTakeMeasurement(measurements));
        } catch (Exception e) {
            log.error("[{}] {}", getModuleDescription().getName(), e.getMessage(), e);
        }
        return measurements = new Measurement[0];
    }

    public interface OnTakeMeasurementListener {
        void onTakeMeasurement(Measurement[] measurements);
    }
}
