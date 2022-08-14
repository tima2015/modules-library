#include "ru_funnydwarf_iot_nml_modules_OutputModule.h"
#include <wiringPi.h>

/*
 * Class:     ru_funnydwarf_iot_nml_modules_OutputModule
 * Method:    digitalWrite
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_ru_funnydwarf_iot_nml_modules_OutputModule_digitalWrite
        (JNIEnv *env, jclass js, jint pin, jint value) {
    digitalWrite(pin, value);
}
