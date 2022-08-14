#include "ru_funnydwarf_iot_nml_modules_onewire_OneWire.h"
#include "OneWireUtils.h"
#include <stdio.h>
#include <stdint.h>

#define CMD_READROM        0x33
#define CMD_SEARCHROM      0xf0

/*
 * Поиск следующего подключенного устройства
 * Class:     ru_funnydwarf_iot_nml_modules_onewire_OneWire
 * Method:    searchNextAddress
 * Signature: (IJI)[J
 */
JNIEXPORT jlongArray JNICALL Java_ru_funnydwarf_iot_nml_modules_onewire_OneWire_searchNextAddress
        (JNIEnv *env, jclass jc, jint pin, jlong lastAddress, jlong lastDiscrepancy) {
    jlong newAddress = 0;
    jbyte searchDirection = 0;
    jint idBitNumber = 1;
    jint lastZero = 0;

    reset(pin);
    writeByte(pin, CMD_SEARCHROM);

    while (idBitNumber < 65) {
        jbyte idBit = readBit(pin);
        jbyte cmpIdBit = readBit(pin);

        // id_bit = cmp_id_bit = 1
        if (idBit == 1 && cmpIdBit == 1) {
            printf("id_bit = cmp_id_bit = 1");
            return NULL;
        } else if (idBit == 0 && cmpIdBit == 0) {
            // id_bit = cmp_id_bit = 0
            if (idBitNumber == lastDiscrepancy) {
                searchDirection = 1;
            } else if (idBitNumber > lastDiscrepancy) {
                searchDirection = 0;
            } else {
                if ((jbyte) (lastAddress >> (idBitNumber - 1)) & 1) {
                    searchDirection = 1;
                } else {
                    searchDirection = 0;
                }
            }
            if (searchDirection == 0) {
                lastZero = idBitNumber;
            }
        } else {
            // id_bit != cmp_id_bit
            searchDirection = idBit;
        }
        newAddress |= ((jlong) searchDirection) << (idBitNumber - 1);
        writeBit(pin, searchDirection);
        idBitNumber++;
    }
    jlongArray result = (*env)->NewLongArray(env, 2);
    jlong lz = lastZero;
    if (result == NULL) {
        printf("Out of memory!");
        return NULL; //Out of Memory
    }
    (*env)->SetLongArrayRegion(env, result, 0, 1, &newAddress);
    (*env)->SetLongArrayRegion(env, result, 1, 1, &lz);
    return result;
}


/*
 * Class:     ru_funnydwarf_iot_nml_modules_onewire_OneWire
 * Method:    readRom
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_ru_funnydwarf_iot_nml_modules_onewire_OneWire_readRom
        (JNIEnv *env, jclass jc, jint pin) {

    int64_t oneWireDevice = 0;
    if (reset(pin) == 0) {
        writeByte(pin, CMD_READROM);
        //  код семейства
        oneWireDevice = readByte(pin);
        // серийный номер

        oneWireDevice |= (int64_t) readByte(pin) << 8 | (int64_t) readByte(pin) << 16 | (int64_t) readByte(pin) << 24 | (int64_t) readByte(pin) << 32 | (int64_t) readByte(pin) << 40
                         | (int64_t) readByte(pin) << 48;
        // CRC

        oneWireDevice |= (int64_t) readByte(pin) << 56;

    } else {
        printf("cant read =(");
        return 1;
    }
    return oneWireDevice;
}
