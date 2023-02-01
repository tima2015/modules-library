package ru.funnydwarf.iot.ml.sensor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

public interface MeasurementDescriptionRepository extends CrudRepository<MeasurementDescription, Long> {
    @Nullable
    MeasurementDescription findByUnitNameAndNameAndDescription(String unitName, String name, String description);

    static MeasurementDescription findOrCreate(MeasurementDescriptionRepository mdr, String unitName, String name, String description) {
        MeasurementDescription md = mdr.findByUnitNameAndNameAndDescription(unitName, name, description);

        if (md == null) {
            md = mdr.save(new MeasurementDescription(unitName, name, description));
        }

        return md;
    }

}
