#include "ru_funnydwarf_iot_nml_modules_onewire_OneWire.h"
#include "OneWireUtils.h"

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
    uint64_t newAddress = 0;
    int searchDirection = 0;
    int idBitNumber = 1;
    int lastZero = 0;

    reset(pin);
    writeByte(pin, CMD_SEARCHROM);

    while (idBitNumber < 65) {
        int idBit = readBit();
        int cmpIdBit = readBit();

        // id_bit = cmp_id_bit = 1
        if (idBit == 1 && cmpIdBit == 1) {
            throw std::logic_error("error: id_bit = cmp_id_bit = 1");
        } else if (idBit == 0 && cmpIdBit == 0) {
            // id_bit = cmp_id_bit = 0
            if (idBitNumber == lastDiscrepancy) {
                searchDirection = 1;
            } else if (idBitNumber > lastDiscrepancy) {
                searchDirection = 0;
            } else {
                if ((uint8_t)(lastAddress >> (idBitNumber - 1)) & 1) {
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
        newAddress |= ((uint64_t) searchDirection) << (idBitNumber - 1);
        writeBit(pin, searchDirection);
        idBitNumber++;
    }
    jlongArray result = NewLongArray(env, 2);
    result[0] = newAddress;
    result[1] = lastZero
    return result;
}

/*
 * Class:     ru_funnydwarf_iot_nml_modules_onewire_OneWire
 * Method:    readRom
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_ru_funnydwarf_iot_nml_modules_onewire_OneWire_readRom
  (JNIEnv *env, jclass jc){

    uint64_t oneWireDevice;
        if (reset() == 0) {
            writeByte (CMD_READROM);
            //  код семейства
            oneWireDevice = readByte();
            // серийный номер
            oneWireDevice |= (uint16_t) readByte() << 8 | (uint32_t) readByte() << 16 | (uint32_t) readByte() << 24 | (uint64_t) readByte() << 32 | (uint64_t) readByte() << 40
                             | (uint64_t) readByte() << 48;
            // CRC
            oneWireDevice |= (uint64_t) readByte() << 56;
        } else {
            return 1;
        }
        return oneWireDevice;
}
