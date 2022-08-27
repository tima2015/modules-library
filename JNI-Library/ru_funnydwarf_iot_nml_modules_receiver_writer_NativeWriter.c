#include "ru_funnydwarf_iot_nml_modules_receiver_writer_NativeWriter.h"
#include <wiringPi.h>

/*
 * Class:     ru_funnydwarf_iot_nml_modules_receiver_writer_NativeWriter
 * Method:    digitalWrite
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_ru_funnydwarf_iot_nml_modules_receiver_writer_NativeWriter_digitalWrite
  (JNIEnv *env, jclass jc, jint pin, jint value) {
    pinMode(pin, OUTPUT)
    digitalWrite(pin, value);
}
