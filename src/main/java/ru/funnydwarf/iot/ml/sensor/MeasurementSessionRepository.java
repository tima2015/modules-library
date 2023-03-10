package ru.funnydwarf.iot.ml.sensor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementSessionRepository extends CrudRepository<MeasurementSession, Long> {
}
