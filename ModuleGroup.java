package ru.funnydwarf.iot.ml;

public abstract class ModuleGroup {

    enum State {
        NOT_INITIALIZED,
        INITIALIZATION_ERROR,
        OK
    }
  
    private State state = State.NOT_INITIALIZED;

    private final String name;
    private final String description;

    public ModuleGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void initialize() throws Exception;
    
    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
