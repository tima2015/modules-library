package ru.funnydwarf.iot.ml.sensor;

import org.springframework.data.repository.CrudRepository;

public interface MeasurementSessionRepository extends CrudRepository<MeasurementSession, Long> {
}
