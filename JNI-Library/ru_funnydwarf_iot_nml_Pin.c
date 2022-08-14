#include "ru_funnydwarf_iot_nml_Pin.h"
#include <wiringPi.h>

/*
 * Class:     ru_funnydwarf_iot_nml_Pin
 * Method:    setMode
 * Signature: (ILru/funnydwarf/iot/nml/Pin/PinMode;)V
 */
JNIEXPORT void JNICALL Java_ru_funnydwarf_iot_nml_Pin_setMode
        (JNIEnv *env, jclass jc, jint pin, jint modeCode) {
    pinMode(pin, modeCode);
}