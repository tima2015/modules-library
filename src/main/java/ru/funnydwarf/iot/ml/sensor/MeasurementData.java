package ru.funnydwarf.iot.ml.sensor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import ru.funnydwarf.iot.ml.ModuleDescription;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class MeasurementData {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private MeasurementDescription measurementDescription;

    @ManyToOne
    private MeasurementSession session;

    @ManyToOne
    private ModuleDescription moduleDescription;

    public MeasurementData(MeasurementDescription measurementDescription, MeasurementSession session, ModuleDescription moduleDescription) {
        this.measurementDescription = measurementDescription;
        this.session = session;
        this.moduleDescription = moduleDescription;
    }
}
