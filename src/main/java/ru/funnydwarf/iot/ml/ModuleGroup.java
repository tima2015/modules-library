package ru.funnydwarf.iot.ml;

public abstract class ModuleGroup {

    enum State {
        NOT_INITIALIZED,
        INITIALIZATION_ERROR,
        OK
    }
  
    private State state = State.NOT_INITIALIZED;
    private String stateMessage = "";
  
    public void initialize();
    
    public State getState() {
        return state;
    }
  
    public String getStateMessage() {
        return stateMessage;
    }
}
