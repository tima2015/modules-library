package ru.funnydwarf.iot.nml.modules;

public abstract class Module {
    private final Object address;
    private final String name;
    private final String description;
    private String userCustomName;
    private String userCustomDescription;

    public Module(Object address, String name, String description) {
        this(address, name, description, "", "");
    }
    public Module(Object address,
                  String name,
                  String description,
                  String userCustomName,
                  String userCustomDescription) {
        this.address = address;
        this.name = name;
        this.description = description;
        this.userCustomName = userCustomName;
        this.userCustomDescription = userCustomDescription;
    }

    public Object getAddress() {
        return address;
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

    @Override
    public String toString() {
        return "Module{" +
                "address=" + address +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userCustomName='" + userCustomName + '\'' +
                ", userCustomDescription='" + userCustomDescription + '\'' +
                '}';
    }
}
