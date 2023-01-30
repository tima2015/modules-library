package ru.funnydwarf.iot.ml.sensor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Measurement {

    @Id
    @GeneratedValue
    private long id;
    private double value;
    private Date measurementDate;
    @ManyToOne
    private MeasurementDescription description;

    @ManyToOne
    private MeasurementSession session;

    public Measurement(double value, MeasurementDescription description, MeasurementSession session, Date measurementDate) {
        this.value = value;
        this.description = description;
        this.measurementDate = measurementDate;
        this.session = session;
    }

    public Measurement(double value, MeasurementDescription description, MeasurementSession session) {
        this(value, description, session, new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return id == that.id && Double.compare(that.value, value) == 0 && measurementDate.equals(that.measurementDate) && description.equals(that.description) && session.equals(that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, measurementDate, description, session);
    }
}
