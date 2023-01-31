package ru.funnydwarf.iot.ml.sensor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

public interface MeasurementDescriptionRepository extends CrudRepository<MeasurementDescription, Long> {
    @Nullable
    MeasurementDescription findByUnitNameAndNameAndDescription(String unitName, String name, String description);

}
