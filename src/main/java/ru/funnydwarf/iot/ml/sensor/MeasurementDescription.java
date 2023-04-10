package ru.funnydwarf.iot.ml.sensor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

/**
 * Свойства замеров проводимых сенсором
 */
@Entity
@NoArgsConstructor
@Getter
@ToString
public class MeasurementDescription {

    @Id
    @GeneratedValue
    private long id;

    private String unitName;
    private String name;
    private String description;

    public MeasurementDescription(String unitName, String name, String description) {
        this.unitName = unitName;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementDescription that = (MeasurementDescription) o;
        return id == that.id && unitName.equals(that.unitName) && name.equals(that.name) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unitName, name, description);
    }
}
