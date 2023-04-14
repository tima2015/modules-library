package ru.funnydwarf.iot.ml.sensor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Long> {
    List<Measurement> findByMeasurementDateBetweenOrderByMeasurementDateAsc(Date measurementDateStart, Date measurementDateEnd, Pageable pageable);

    List<Measurement> findByMeasurementDateBetweenAndMeasurementData_IdOrderByMeasurementDateAsc(Date measurementDateStart, Date measurementDateEnd, long id, Pageable pageable);

}
