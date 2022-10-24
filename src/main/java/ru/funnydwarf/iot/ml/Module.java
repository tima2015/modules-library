package ru.funnydwarf.iot.ml;

public abstract class Module {

    private final ModuleGroup group;
    private final Object address;
    private final String name;
    private final String description;

    public Module(ModuleGroup group,
                  Object address,
                  String name,
                  String description) {
        this.group = group;
        this.address = address;
        this.name = name;
        this.description = description;
    }

    public ModuleGroup getGroup() {
        return group;
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

    @Override
    public String toString() {
        return "Module{" +
                "address=" + address +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
