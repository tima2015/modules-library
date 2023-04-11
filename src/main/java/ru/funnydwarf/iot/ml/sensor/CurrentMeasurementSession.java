package ru.funnydwarf.iot.ml.sensor;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.prefs.Preferences;

//@Component
@Deprecated
public class CurrentMeasurementSession {
    @Getter
    private MeasurementSession session;

    private final Preferences pref = Preferences.userNodeForPackage(CurrentMeasurementSession.class);
    private final MeasurementSessionRepository msr;

    public CurrentMeasurementSession(MeasurementSessionRepository msr) {
        this.msr = msr;
        session = msr.findById(pref.getLong("sessionId", Long.MAX_VALUE)).orElse(null);
        if (session == null) {
            newSession("Default", "Default");
        }
    }

    public void newSession(String sessionName, String sessionDesc) {
        session = msr.save(new MeasurementSession(sessionName, sessionDesc));
        pref.putLong("sessionId", session.getId());
    }
}
