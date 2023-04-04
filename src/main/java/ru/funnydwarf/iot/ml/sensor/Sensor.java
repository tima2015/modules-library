package ru.funnydwarf.iot.ml.sensor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import ru.funnydwarf.iot.ml.InitializationState;
import ru.funnydwarf.iot.ml.Module;
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

    /**
     * Свойства замеров проводимых сенсором
     */
    private final MeasurementDescription[] measurementDescription;

    /**
     * Контейнер актуальной информации о текущей сессии
     */
    private final CurrentMeasurementSession session;

    /**
     * Аргументы для читателя
     */
    private final Object[] readerArgs;

    private final List<OnTakeMeasurementListener> onTakeMeasurementListeners = new ArrayList<>();

    public Sensor(Reader<AddressT> reader, MeasurementDescription[] measurementDescription, CurrentMeasurementSession session, ModuleGroupT group, AddressT address, String name, String description, @Nullable Initializer initializer, Object ... readerArgs){
        super(group, address, name, description, initializer);
        this.reader = reader;
        this.measurementDescription = measurementDescription;
        this.session = session;
        this.readerArgs = readerArgs;
    }

    /**
     * Выполнить получение новых замеров
     */
    public Measurement[] takeMeasurement() {
        log.debug("[{}] takeMeasurement() called", getName());
        measurements = new Measurement[0];
        if (getInitializationState() == InitializationState.NOT_INITIALIZED){
            log.debug("[{}] takeMeasurement: module have initialization error! Pass...", getName());
            return measurements;
        }
        if (getInitializationState() == InitializationState.NOT_CONNECTED) {
            log.debug("[{}] takeMeasurement: module not connected! Pass...", getName());
            return measurements;
        }
        try {
            double[] measurementValues = reader.read(getAddress(), readerArgs);
            measurements = new Measurement[measurementValues.length];

            for (int i = 0; i < measurementValues.length; i++) {
                measurements[i] = new Measurement(measurementValues[i], measurementDescription[i], session.getSession());
            }

            onTakeMeasurementListeners
                    .forEach(onTakeMeasurementListener -> onTakeMeasurementListener.onTakeMeasurement(measurements));
        } catch (Exception e) {
            log.error("[{}] {}", getName(), e.getMessage(), e);
        }
        return measurements;
    }

    public interface OnTakeMeasurementListener {
        void onTakeMeasurement(Measurement[] measurements);
    }
}
