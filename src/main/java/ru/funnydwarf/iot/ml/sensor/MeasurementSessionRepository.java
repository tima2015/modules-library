package ru.funnydwarf.iot.ml.sensor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementSessionRepository extends CrudRepository<MeasurementSession, Long> {
    @Nullable
    MeasurementSession findFirstByOrderByIdDesc();

    public static MeasurementSession findFirstOrCreate(MeasurementSessionRepository msr) {
        MeasurementSession session = msr.findFirstByOrderByIdDesc();
        if (session == null) {
            session = new MeasurementSession();
            msr.save(session);
        }
        return session;
    }
}
