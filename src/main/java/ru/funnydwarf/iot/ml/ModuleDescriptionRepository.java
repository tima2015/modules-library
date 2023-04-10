package ru.funnydwarf.iot.ml;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleDescriptionRepository extends CrudRepository<ModuleDescription, Long> {
    @Nullable
    ModuleDescription findByNameAndDescription(String name, String description);

    static ModuleDescription findOrCreate(ModuleDescriptionRepository mdr, String name, String description) {
        ModuleDescription md = mdr.findByNameAndDescription(name, description);

        if (md == null) {
            md = mdr.save(new ModuleDescription(name, description));
        }

        return md;
    }
}
