package ru.funnydwarf.iot.nml.modules;

import com.fasterxml.jackson.annotation.*;
import ru.funnydwarf.iot.nml.Pin;
import ru.funnydwarf.iot.nml.modules.onewire.OneWire;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=OutputModule.class, name="Output"),
        @JsonSubTypes.Type(value=OneWire.class, name="OneWire")
})
public abstract class Module {
    private final Pin pin;
    private final String name;
    private final String description;
    private String userCustomName;
    private String userCustomDescription;

    public Module(Pin pin, String name, String description) {
        this(pin, name, description, "", "");
    }

    @JsonCreator
    public Module(@JsonProperty("pin") Pin pin,
                  @JsonProperty("name") String name,
                  @JsonProperty("description") String description,
                  @JsonProperty("userCustomName") String userCustomName,
                  @JsonProperty("userCustomDescription") String userCustomDescription) {
        this.pin = pin;
        this.name = name;
        this.description = description;
        this.userCustomName = userCustomName;
        this.userCustomDescription = userCustomDescription;
    }

    public Pin getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUserCustomName() {
        return userCustomName;
    }

    public void setUserCustomName(String userCustomName) {
        this.userCustomName = userCustomName;
    }

    public String getUserCustomDescription() {
        return userCustomDescription;
    }

    public void setUserCustomDescription(String userCustomDescription) {
        this.userCustomDescription = userCustomDescription;
    }


}
