package ru.funnydwarf.iot.ml.sensor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.funnydwarf.iot.ml.ModuleDescription;

@Repository
public interface MeasurementDataRepository extends CrudRepository<MeasurementData, Long> {
    @Nullable
    MeasurementData findByMeasurementDescription_IdAndModuleDescription_Id(long measurementDescriptionId, long moduleDescriptionId);

    static MeasurementData findOrCreate(MeasurementDataRepository mdr, MeasurementDescription measurementDescription, ModuleDescription moduleDescription) {
        MeasurementData md = mdr.findByMeasurementDescription_IdAndModuleDescription_Id(measurementDescription.getId(), moduleDescription.getId());

        if (md == null) {
            md = mdr.save(new MeasurementData(measurementDescription, moduleDescription));
        }

        return md;
    }
}
