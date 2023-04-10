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
    private MeasurementData measurementData;

    public Measurement(double value, Date measurementDate, MeasurementData measurementData) {
        this.value = value;
        this.measurementDate = measurementDate;
        this.measurementData = measurementData;
    }

    public Measurement(double value, MeasurementData measurementData) {
        this(value, new Date(), measurementData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return id == that.id && Double.compare(that.value, value) == 0 && measurementDate.equals(that.measurementDate) && measurementData.equals(that.measurementData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, measurementDate, measurementData);
    }
}
