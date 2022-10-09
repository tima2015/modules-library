package ru.funnydwarf.iot.ml.sensor.dataio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.funnydwarf.iot.ml.sensor.MeasurementData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.util.Date;

/**
 * Вывод данных замеров в хранилище в виде файла
 */
@Component
public class FileDataOutput implements DataOutput{

    private static final Logger log = LoggerFactory.getLogger(FileDataOutput.class);

    private final String destination;
    private final DateFormat timeFormat;
    private final DateFormat dateFormat;

    @Autowired
    public FileDataOutput(@Value("${Sensor.dataFoleder:collectedData}") String destination,
                          @Qualifier("timeFormat") DateFormat timeFormat,
                          @Qualifier("dateFormat") DateFormat dateFormat) {
        this.destination = destination;
        this.timeFormat = timeFormat;
        this.dateFormat = dateFormat;
    }

    @Override
    public void write(MeasurementData data, String measurementId) throws IOException {
        log.debug("write() called with: data = [{}], measurementId = [{}]", data, measurementId);
        File directory = prepareDirectory(measurementId);
        Date date = new Date();
        Path path = new File(directory + "/" + dateFormat.format(date) + ".smd").toPath();
        Files.writeString(path,
                timeFormat.format(date) + '\t' + data + '\n',
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND);
    }

    /**
     * Проверяет создана ли директория для сохранения данных замеров.
     * В случае если директория ещё не создана, создаст её, включае все вложеные подпапки
     * @param measurementId идентификатор замера
     * @return подготовленую директорию
     */
    private File prepareDirectory(String measurementId) {
        log.trace("prepareDirectory() called with: measurementId = [{}]", measurementId);
        File directory = new File(destination + '/' + measurementId);
        if (!directory.exists() || directory.isDirectory()){
            if (directory.mkdirs()) {
                log.info("directory for {} was created", measurementId);
            } else {
                log.warn("something is wrong with the directory {}!", measurementId);
            }
        }
        return directory;
    }
}
