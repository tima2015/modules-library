package ru.funnydwarf.iot.ml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class ModuleGroupTest {

    private static final Logger log = LoggerFactory.getLogger(ModuleGroupTest.class);

    @Test
    void initialize() {
        ModuleGroup group = new ModuleGroup("niceTestGroupName",
                "niceTestGroupDescription") {
            @Override
            protected State initialize() throws Exception {
                log.debug("initialize() called with: group1 = [{}]", this);
                return State.OK;
            }
        };
        assertEquals(group.getState(), ModuleGroup.State.OK);
    }

    @Test
    void initialize_bad() {
        ModuleGroup group = new ModuleGroup("badTestGroupName", "badTestGroupDescription") {

            @Override
            protected State initialize() throws Exception {
                log.debug("initialize() called with: group1 = [{}]", this);
                throw new RuntimeException("this is a bad test");
            }
        };
        assertEquals(group.getState(), ModuleGroup.State.INITIALIZATION_ERROR);
    }
}