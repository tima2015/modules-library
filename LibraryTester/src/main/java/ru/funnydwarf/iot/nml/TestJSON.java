package ru.funnydwarf.iot.nml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.funnydwarf.iot.nml.modules.Module;
import ru.funnydwarf.iot.nml.modules.OutputModule;
import ru.funnydwarf.iot.nml.modules.onewire.DS18B20OneWireUnit;
import ru.funnydwarf.iot.nml.modules.onewire.OneWire;
import ru.funnydwarf.iot.nml.modules.onewire.OneWireUnit;
import ru.funnydwarf.iot.nml.modules.onewire.UnknownOneWireUnit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TestJSON {

    public static void main(String[] args) {
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(new OutputModule(new Pin(PinType.GPIO, 0),
                "beeper",
                "Тестовая пищалка",
                "какоето имя заданое пользователем или кем нибудь ещё",
                "Какоето описание кем-то заданое. Для реле не хватает напряжения на пинах... вроде"));
        modules.add(new OutputModule(new Pin(PinType.GPIO, 1), "LED", "Тестовый светодиодик =)"));

        Pin oneWirePin = new Pin(PinType.GPIO, 3);
        OneWireUnit[] oneWireUnits = new OneWireUnit[]{
                new DS18B20OneWireUnit(oneWirePin, "temperature0", "Тестовый термометр", 0x280316a006c5ffL),
                new DS18B20OneWireUnit(oneWirePin, "temperature1", "Тестовый термометр", Long.parseLong("28FFC506A01603E2", 16)),
                new UnknownOneWireUnit(oneWirePin, "Unknown", "Something", Long.parseLong("28FFC506A01613E3", 16))
        };
        modules.add(new OneWire(oneWirePin, "oneWire0", "Тестовый oneWire", oneWireUnits));

        JNILibrary.Modules m = new JNILibrary.Modules(modules);
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            FileWriter writer = new FileWriter("modules.json", Charset.defaultCharset());
            mapper.writeValue(writer,m);
            JNILibrary.Modules value = mapper.readValue(new File("modules.json"), JNILibrary.Modules.class);
            System.out.println(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
