#include "ru_funnydwarf_iot_nml_modules_onewire_DS18B20OneWireUnit.h"
#include "OneWireUtils.h"

/*
 * Class:     ru_funnydwarf_iot_nml_modules_onewire_DS18B20OneWireUnit
 * Method:    takeMeasurements
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_ru_funnydwarf_iot_nml_modules_onewire_DS18B20OneWireUnit_takeMeasurements
        (JNIEnv *env, jclass jc, jint pin, jlong rom) {
    setDevice(pin, rom);
    writeByte(pin, CMD_CONVERTTEMP);
}

/*
 * Class:     ru_funnydwarf_iot_nml_modules_onewire_DS18B20OneWireUnit
 * Method:    getTemperatureFromUnit
 * Signature: (I)S
 */
JNIEXPORT jshort JNICALL Java_ru_funnydwarf_iot_nml_modules_onewire_DS18B20OneWireUnit_getTemperatureFromUnit
        (JNIEnv *env, jclass jc, jint pin, jlong rom) {
    setDevice(pin, rom);
    writeByte(pin, CMD_RSCRATCHPAD);

    uint8_t data[9];
    for (int i = 0; i < 9; i++) {
        data[i] = readByte(pin);
    }
    return (jshort)((data[1] << 8) + data[0]);
}
