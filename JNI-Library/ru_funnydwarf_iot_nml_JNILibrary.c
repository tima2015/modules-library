#include "ru_funnydwarf_iot_nml_JNILibrary.h"
#include <wiringPi.h>

/*
 * Class:     ru_funnydwarf_iot_nml_JNILibrary
 * Method:    wiringPiSetup
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ru_funnydwarf_iot_nml_JNILibrary_wiringPiSetup
        (JNIEnv *env, jclass js) {
    wiringPiSetup();
}
