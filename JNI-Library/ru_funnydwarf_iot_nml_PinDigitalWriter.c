#include "ru_funnydwarf_iot_nml_PinDigitalWriter.h"
#include <wiringPi.h>

/*
 * Class:     ru_funnydwarf_iot_nml_PinDigitalWriter
 * Method:    digitalWrite
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_ru_funnydwarf_iot_nml_PinDigitalWriter_digitalWrite
  (JNIEnv *env, jclass jc, jint pin, jint value) {
    digitalWrite(pin, value);
}
