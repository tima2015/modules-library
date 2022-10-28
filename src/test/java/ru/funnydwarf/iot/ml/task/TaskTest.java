package ru.funnydwarf.iot.ml.task;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.funnydwarf.iot.ml.ModuleGroup;
import ru.funnydwarf.iot.ml.task.command.TaskCommand;
import ru.funnydwarf.iot.ml.Module;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private static final Logger log = LoggerFactory.getLogger(TaskTest.class);

    private ModuleGroup group = new ModuleGroup("testGroupname", "testGroupDescription") {
        @Override
        protected ModuleGroup.State initialize() throws Exception {
            return State.OK;
        }
    };

    private TaskCommand<Module> plugCommand = module -> log.debug("onDoTask() called with: module = [{}]", module);

    private List<Module> plugModules = List.of(
            new Module(group, 1, "module 1", "desc 1") {},
            new Module(group, 1, "module 2", "desc 2") {},
            new Module(group, 1, "module 3", "desc 3") {},
            new Module(group, 1, "module 4", "desc 4") {},
            new Module(group, 1, "module 5", "desc 5") {}
    );

    @Test
    void doTask_0() {
        Task<Module> task = new Task<>("testTask1",
                "testTask1Desc",
                true,
                false,
                module -> log.debug("onDoTask() called with: module = [{}]", module),
                plugModules);

        task.doTask();
        assertNotNull(task.getLastDone());
    }

    @Test
    void doTask_1() {
        Task<Module> task = new Task<>("testTask1",
                "testTask1Desc",
                false,
                false,
                module -> log.debug("onDoTask() called with: module = [{}]", module),
                plugModules);

        task.doTask();
        Date lastDone = task.getLastDone();
        assertNotNull(lastDone);
        task.doTask();
        assertEquals(lastDone, task.getLastDone());
    }

    @Test
    void doTask_2() {
        Task<Module> task = new Task<>("testTask1",
                "testTask1Desc",
                false,
                true,
                module -> log.debug("onDoTask() called with: module = [{}]", module),
                plugModules);

        task.doTask();
        assertNull(task.getLastDone());
        task.setDisable(false);
        task.doTask();
        assertNotNull(task.getLastDone());
    }

}