#include "ru_funnydwarf_iot_nml_modules_sensor_reader_NativeReader.h"
#include <wiringPi.h>

/*
 * Class:     ru_funnydwarf_iot_nml_modules_sensor_reader_NativeReader
 * Method:    digitalRead
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_ru_funnydwarf_iot_nml_modules_sensor_reader_NativeReader_digitalRead
  (JNIEnv *, jclass, jint) {
    pinMode(pin, INPUT)
    return (double) digitalRead(pin);
}
