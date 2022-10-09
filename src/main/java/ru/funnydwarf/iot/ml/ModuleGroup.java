package ru.funnydwarf.iot.ml;

public abstract class ModuleGroup {

    enum State {
        NOT_INITIALIZED,
        INITIALIZATION_ERROR,
        OK
    }
  
    private State state = State.NOT_INITIALIZED;

    public abstract void initialize() throws Exception;
    
    public State getState() {
        return state;
    }

}
