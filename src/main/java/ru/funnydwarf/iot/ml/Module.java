package ru.funnydwarf.iot.ml;

/**
 * Класс модуля
 */
public abstract class Module {

    /**
     * Группа к которой относится данный модуль
     */
    private final ModuleGroup group;

    /**
     * Каждый модуль в каком либо представлении имеет свой адрес
     */
    private final Object address;

    /**
     * Имя модуля
     */
    private final String name;

    /**
     * Описание модуля
     */
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
