package ru.funnydwarf.iot.ml;

import java.util.List;

public interface ModuleListReadable<AddressT> {
    List<AddressT> readModuleAdressesList();

}
