package ru.funnydwarf.iot.ml.sensor;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

//@Entity
@Getter
@ToString
@NoArgsConstructor
@Deprecated
public class MeasurementSession {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    private String description;

    private Date start;

    private Date end;

    public MeasurementSession(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementSession that = (MeasurementSession) o;
        return id == that.id && name.equals(that.name) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }


}
