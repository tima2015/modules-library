package ru.funnydwarf.iot.ml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Класс создающий вспомогательные бины, для упрощения работы с библиотекой.
 * Настройка некоторых бинов производится через редактирование конфигурационного файла.
 * По умолчанию конфигурационный файл application.properties
 */
@Configuration
public class Utils {

    /**
     * Параметр Utils.timeFormat
     *
     * @param format Формат хранения времени. По умолчанию "HH:mm:ss"
     * @return экземпляр DateFormat форматирования Date для получения строки с временем
     */
    @Bean("timeFormat")
    DateFormat timeFormat(@Value("${Utils.timeFormat:HH:mm:ss}") String format){
        return new SimpleDateFormat(format, Locale.ENGLISH);
    }

    /**
     * Параметр Utils.dateFormat
     *
     * @param format Формат хранения даты. По умолчанию "dd-MM-yyyy"
     * @return экземпляр DateFormat форматирования Date для получения строки с датой
     */
    @Bean("dateFormat")
    DateFormat dateFormat(@Value("${Utils.dateFormat:dd-MM-yyyy}") String format){
        return new SimpleDateFormat(format, Locale.ENGLISH);
    }

    /**
     * Параметр Utils.dateTimeFormat
     *
     * @param format Формат хранения даты и времени. По умолчанию "dd-MM-yyyy HH:mm:ss"
     * @return экземпляр DateFormat форматирования Date для получения строки с датой и временем
     */
    @Bean("dateTimeFormat")
    DateFormat dateTimeFormat(@Value("${Utils.dateTimeFormat:dd-MM-yyyy HH:mm:ss}") String format){
        return new SimpleDateFormat(format, Locale.ENGLISH);
    }

}
