#include "ru_funnydwarf_iot_nml_PinDigitalReader.h"
#include <wiringPi.h>

/*
 * Class:     ru_funnydwarf_iot_nml_PinDigitalReader
 * Method:    digitalRead
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_ru_funnydwarf_iot_nml_PinDigitalReader_digitalRead
  (JNIEnv *env, jclass jc, jint pin) {
    return digitalRead(pin);
}

