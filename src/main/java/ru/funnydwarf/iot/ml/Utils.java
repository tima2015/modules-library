package ru.funnydwarf.iot.ml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Configuration
public class Utils {

    @Bean("timeFormat")
    DateFormat timeFormat(@Value("${Utils.timeFormat:HH:mm:ss}") String format){
        return new SimpleDateFormat(format, Locale.ENGLISH);
    }

    @Bean("dateFormat")
    DateFormat dateFormat(@Value("${Utils.dateFormat:dd-MM-yyyy}") String format){
        return new SimpleDateFormat(format, Locale.ENGLISH);
    }

    @Bean("dateTimeFormat")
    DateFormat dateTimeFormat(@Value("${Utils.dateTimeFormat:dd-MM-yyyy HH:mm:ss}") String format){
        return new SimpleDateFormat(format, Locale.ENGLISH);
    }

}
