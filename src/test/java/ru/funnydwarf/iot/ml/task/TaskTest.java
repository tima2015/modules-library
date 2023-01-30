package ru.funnydwarf.iot.ml.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.funnydwarf.iot.ml.InitializationState;
import ru.funnydwarf.iot.ml.Module;
import ru.funnydwarf.iot.ml.ModuleGroup;

import java.util.List;

class TaskTest {

    private static final Logger log = LoggerFactory.getLogger(TaskTest.class);

    private ModuleGroup group = new ModuleGroup("testGroupName", "testGroupDescription") {
        @Override
        protected InitializationState initialize() throws Exception {
            return InitializationState.OK;
        }
    };


    private List<Module> plugModules = List.of(
            new Module(group, 1, "module 1", "desc 1") {},
            new Module(group, 1, "module 2", "desc 2") {},
            new Module(group, 1, "module 3", "desc 3") {},
            new Module(group, 1, "module 4", "desc 4") {},
            new Module(group, 1, "module 5", "desc 5") {}
    );


}