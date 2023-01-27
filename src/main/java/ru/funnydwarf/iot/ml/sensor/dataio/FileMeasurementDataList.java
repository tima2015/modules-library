package ru.funnydwarf.iot.ml.sensor.dataio;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

/**
 * @deprecated данный класс является крайне не оптимизирован и стоит применять только для работы с мелкими журналами
 */
@Slf4j
@Getter()
@Deprecated
public class FileMeasurementDataList extends ArrayList<MeasurementData> {

    @Value("${FileMeasurementDataList.dataDir:collectedData}")
    private static String dataDir;
    @Value("${FileMeasurementDataList.dateFormat:yyyy-MM-dd HH:mm:ss}")
    private static String datePattern;

    private final File file;
    @Getter(AccessLevel.NONE)
    private final DateFormat dateFormat = new SimpleDateFormat(datePattern);
    private final MeasurementData prototypeMeasurement;

    public FileMeasurementDataList(MeasurementData template) {
        prototypeMeasurement = template;
        file = new File(prepareDataDir(), template.measurementName() + ".smd");
        load();
    }

    /**
     * Проверяет создана ли директория для сохранения данных замеров.
     * В случае если директория ещё не создана, создаст её
     *
     * @return подготовленную директорию
     */
    private File prepareDataDir() {
        File dataDir = new File(FileMeasurementDataList.dataDir);
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            if (dataDir.mkdirs()) {
                log.info("dataDir was created");
            } else {
                log.warn("something is wrong with the dataDir!");
            }
        }
        return dataDir;
    }

    public void save() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (MeasurementData data : this) {
            writer.printf("%s %f\n", dateFormat.format(data.measurementDate()), data.value());
        }
        writer.close();
    }

    public ArrayList<MeasurementData> load(){
        String tmp;
        ArrayList<MeasurementData> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((tmp = reader.readLine()) != null) {
                String[] split = tmp.split("\t");
                Date date = dateFormat.parse(split[0]);
                double value = Double.parseDouble(split[1]);
                list.add(new MeasurementData(value, prototypeMeasurement.unitName(), prototypeMeasurement.measurementName(), date));
            }
            reader.close();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean add(MeasurementData measurementData) {
        boolean add = super.add(measurementData);
        save();
        return add;
    }

    @Override
    public void add(int index, MeasurementData element) {
        super.add(index, element);
        save();
    }

    @Override
    public boolean addAll(Collection<? extends MeasurementData> c) {
        boolean b = super.addAll(c);
        save();
        return b;
    }

    @Override
    public boolean addAll(int index, Collection<? extends MeasurementData> c) {
        boolean b = super.addAll(index, c);
        save();
        return b;
    }

    @Override
    public boolean remove(Object o) {
        boolean remove = super.remove(o);
        save();
        return remove;
    }

    @Override
    public void clear() {
        super.clear();
        save();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = super.removeAll(c);
        save();
        return b;
    }

    @Override
    public MeasurementData remove(int index) {
        MeasurementData remove = super.remove(index);
        save();
        return remove;
    }

    @Override
    public boolean removeIf(Predicate<? super MeasurementData> filter) {
        boolean b = super.removeIf(filter);
        save();
        return b;
    }
}
