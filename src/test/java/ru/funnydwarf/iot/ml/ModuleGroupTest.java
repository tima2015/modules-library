package ru.funnydwarf.iot.ml;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ModuleGroupTest.ModuleGroupTestConfiguration.class)
class ModuleGroupTest {

    private static final Logger log = LoggerFactory.getLogger(ModuleGroupTest.class);

    @Configuration
    static class ModuleGroupTestConfiguration {
        @Bean("good")
        ModuleGroup getGoodModuleGroup() {
            return new ModuleGroup("niceTestGroupName",
                    "niceTestGroupDescription") {
                @Override
                protected InitializationState initialize() throws Exception {
                    log.debug("initialize() called with: group1 = [{}]", this);
                    return InitializationState.OK;
                }
            };
        }

        @Bean("bad")
        ModuleGroup getBadModuleGroup() {
            return new ModuleGroup("badTestGroupName", "badTestGroupDescription") {

                @Override
                protected InitializationState initialize() throws Exception {
                    log.debug("initialize() called with: group1 = [{}]", this);
                    throw new RuntimeException("this is a bad test");
                }
            };
        }
    }

    @Autowired
    @Qualifier("good")
    private ModuleGroup good;

    //@Test
    void initialize() {
        assertEquals(good.getInitializationState(), InitializationState.OK);
    }

    @Autowired
    @Qualifier("bad")
    private ModuleGroup bad;

    //@Test
    void initialize_bad() {
        assertEquals(bad.getInitializationState(), InitializationState.INITIALIZATION_ERROR);
    }
}