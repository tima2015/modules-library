package ru.funnydwarf.iot.ml.sensor.dataio;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Получение данных замеров из хранилища в виде файла
 */
@Component
@Slf4j
public class FileDataInput implements DataInput{

    private final String destination;
    private final DateFormat dateTimeFormat;

    public FileDataInput(@Value("${Sensor.dataFoleder:collectedData}") String destination,
                         @Qualifier("dateTimeFormat") DateFormat dateTimeFormat) {
        this.destination = destination;
        this.dateTimeFormat = dateTimeFormat;
    }

    @Override
    public MeasurementData[] read(String measurementId, String measurementName, String unit, int offset, int length) throws IOException {
        log.debug("read() called with: measurementId = [{}], unit = [{}], offset = [{}], length = [{}]",
                measurementId, unit, offset, length);

        File[] files = getMeasurementFiles(new File(destination + measurementId));
        if (files.length == 0) {
            return new MeasurementData[0];
        }

        Arrays.sort(files, this::compare);

        MeasurementData[] measurementData = new MeasurementData[length];
        int j = 0;
        for (int i = 0, of = 0; i < files.length; i++) {
            List<String> list = Files.readAllLines(files[i].toPath());
            for (int k = list.size() - 1; k >= 0; k--) {
                //проверка заполнен ли выходной массив, если да, то метод завершает работу возвращая этот массив
                if (j == length){
                    return measurementData;
                }
                //проходим отступ
                if (of < offset){
                    of++;
                    continue;
                }
                //обрабатываем строки
                String[] split = list.get(k).split("\t");
                try {
                    Date date = dateTimeFormat.parse(files[i].getName().split("\\.")[0] + ' ' + split[0]);
                    measurementData[j] = new MeasurementData(Double.parseDouble(split[1]), unit, measurementName, date);
                    j++;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        MeasurementData[] result = new MeasurementData[j];
        System.arraycopy(measurementData, 0, result, 0, result.length);
        return result;
    }

    /**
     * @param measurementDirectory директория с файлами данных замеров
     * @return массив файлов данных замеров. Если файлов нет, то и массив будет пуст
     */
    private File[] getMeasurementFiles(File measurementDirectory) {
        log.trace("getMeasurementFiles() called with: measurementDirectory = [{}]", measurementDirectory);
        File[] files = measurementDirectory.listFiles();
        if (files == null){
            log.warn("Measurement history files not found!");
            return new File[0];
        }
        return files;
    }

    /**
     * Сравнивает файлы для сортировки в хронологическом порядке
     * @param o1 первый сравниваемый файл
     * @param o2 второй сравниваемый файл
     * @return значение 0 если o1 == o2, значение меньше 0 если o1 < o2 и значение больше 0 если o1 > o2
     */
    private int compare(File o1, File o2) {
        String[] o1Date = o1.getName().split("\\.")[0].split("-");
        GregorianCalendar o1Calendar = new GregorianCalendar(Integer.parseInt(o1Date[2]),
                Integer.parseInt(o1Date[1]),
                Integer.parseInt(o1Date[0]));
        String[] o2Date = o2.getName().split("\\.")[0].split("-");
        GregorianCalendar o2Calendar = new GregorianCalendar(Integer.parseInt(o2Date[2]),
                Integer.parseInt(o2Date[1]),
                Integer.parseInt(o2Date[0]));

        return Long.compare(o1Calendar.getTimeInMillis(), o2Calendar.getTimeInMillis());
    }

}
