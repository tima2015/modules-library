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
import java.util.function.Consumer;

/**
 * Сенсор/Датчик
 */
@Getter
@Slf4j
public class Sensor extends Module {

    /**
     * Данные последних замеров
     */
    private Measurement[] measurements = new Measurement[0];

    /**
     * Читающий показания датчика
     */
    @Getter(AccessLevel.NONE)
    private final Reader reader;

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

    public Sensor(Reader reader, MeasurementDescription[] measurementDescription, CurrentMeasurementSession session, ModuleGroup group, Object address, String name, String description, @Nullable Initializer initializer, Object ... readerArgs){
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
            log.warn("[{}] takeMeasurement: module have initialization error! Pass...", getName());
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
