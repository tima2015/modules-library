package ru.funnydwarf.iot.ml.sensor;

import org.springframework.data.repository.CrudRepository;

public interface MeasurementRepository extends CrudRepository<Measurement, Long> {

}
