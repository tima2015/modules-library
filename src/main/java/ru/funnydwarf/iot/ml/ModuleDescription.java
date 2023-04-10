package ru.funnydwarf.iot.ml;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class ModuleDescription {

    @Id
    @GeneratedValue
    private long id;

    /**
     * Имя модуля
     */
    private String name;

    /**
     * Описание модуля
     */
    private String description;

    public ModuleDescription(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
