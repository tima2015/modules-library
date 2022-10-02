package ru.funnydwarf.iot.ml.sensor.datawriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class FileDataIO implements DataIO {

    private static final Logger log = LoggerFactory.getLogger(FileDataIO.class);

    private final String destination;
    private final SimpleDateFormat time;
    private final SimpleDateFormat date;

    public FileDataIO(@Value("${Sensor.dataFoleder:collectedData/}") String destination) {
        this.destination = destination;
        time = new SimpleDateFormat("HH:mm:ss");
        date = new SimpleDateFormat("dd-MM-yyyy");
    }

    @Override
    public void write(MeasurementData[] data, String name) throws IOException {
        Date d = new Date();
        File directory = new File(destination + name);
        if (!directory.exists() || directory.isDirectory()){
            directory.mkdirs();
        }
        Path path = new File(directory + "/" + date.format(d) + ".smd").toPath();
        Files.writeString(path,
                time.format(d) + '\t' + data + '\n',
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND);
    }

    @Override
    public MeasurementData[] read(String name, String unitName, int offset, int length) throws IOException {
        MeasurementData[] measurementData = new MeasurementData[length];

        File directory = new File(destination + name);
        File[] files = directory.listFiles();
        if (files == null){
            log.warn("Measurement history files not found!");
            return new MeasurementData[0];
        }

        Arrays.sort(files, (o1, o2) -> {
            String[] o1Date = o1.getName().split("\\.")[0].split("-");
            GregorianCalendar o1Calendar = new GregorianCalendar(Integer.parseInt(o1Date[2]),
                    Integer.parseInt(o1Date[1]),
                    Integer.parseInt(o1Date[0]));
            String[] o2Date = o2.getName().split("\\.")[0].split("-");
            GregorianCalendar o2Calendar = new GregorianCalendar(Integer.parseInt(o2Date[2]),
                    Integer.parseInt(o2Date[1]),
                    Integer.parseInt(o2Date[0]));

            return Long.compare(o1Calendar.getTimeInMillis(), o2Calendar.getTimeInMillis());
        });

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

        for (int i = 0, of = 0, j = 0; i < files.length; i++) {
            List<String> list = Files.readAllLines(files[i].toPath());
            for (int k = list.size() - 1; k >= 0; k--) {
                if (j >= length){
                    return measurementData;
                }
                if (of < offset){
                    of++;
                    continue;
                }
                String[] split = list.get(k).split("\t");
                try {
                    Date dateValue = formatter.parse(files[i].getName().split("\\.")[0] + ' ' + split[0]);
                    measurementData[i] = new MeasurementData(Double.parseDouble(split[1]), unitName, name, dateValue);
                    j++;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return measurementData;
    }
}
