package ru.funnydwarf.iot.nml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.funnydwarf.iot.nml.modules.Module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JNILibrary {
    @JsonAutoDetect
    public static class Modules {
        private final ArrayList<Module> modules;

        public Modules(@JsonProperty("modules") ArrayList<Module> modules) {
            this.modules = modules;
        }

        public ArrayList<Module> getModules() {
            return modules;
        }
    }

    private static native void wiringPiSetup();

    public static Modules initialize(File moduleDataJsonFile) throws IOException {
        wiringPiSetup();
        Modules modules = new ObjectMapper().readValue(moduleDataJsonFile, Modules.class);
        for (Module module : modules.getModules()) {
            if (module instanceof Initializable) {
                ((Initializable)module).initialize();
            }
        }
        return modules;
    }

}
