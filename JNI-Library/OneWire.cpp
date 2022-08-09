#include "OneWire.h"
#include <wiringPi.h>
#include <stdexcept>
#include <iostream>

int reset(int pin) {
    pinMode(pin, OUTPUT);
    digitalWrite(pin, LOW);
    delayMicroseconds(480);

    pinMode(pin, INPUT);
    delayMicroseconds(60);

    int response = digitalRead(pin);
    delayMicroseconds(410);

    return response;
}

void writeBit(int pin, uint8_t bit) {
    if (bit & 1) {
        // логический «0» на 10us
        pinMode(pin, OUTPUT);
        digitalWrite(pin, LOW);
        delayMicroseconds(10);
        pinMode(pin, INPUT);
        delayMicroseconds(55);
    } else {
        // логический «0» на 65us
        pinMode(pin, OUTPUT);
        digitalWrite(pin, LOW);
        delayMicroseconds(65);
        pinMode(pin, INPUT);
        delayMicroseconds(5);
    }
}

void writeByte(int pin, uint8_t byte) {
    uint8_t i = 8;
    while (i--) {
        writeBit(pin, byte & 1);
        byte >>= 1;
    }
}

/**
 * Поиск следующего подключенного устройства
 */
uint64_t searchNextAddress(int pin, uint64_t lastAddress, int &lastDiscrepancy) {
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
    lastDiscrepancy = lastZero;
    return newAddress;
}

void searchRom(int pin, uint64_t * roms, int &n) {//todo тут стоит модернизировать код под динамически расширяемый массив
    uint64_t lastAddress = 0;
    int lastDiscrepancy = 0;
    int err = 0;
    int i = 0;
    do {
        do {
            try {
                lastAddress = searchNextAddress(pin, lastAddress, lastDiscrepancy);
                int crc = crcCheck(lastAddress, 8);
                if (crc == 0) {
                    roms[i++] = lastAddress;
                    err = 0;
                } else {
                    err++;
                }
            } catch (std::exception &e) {
                std::cout << e.what() << std::endl;
                err++;
                if (err > 3) {
                    throw e;
                }
            }
        } while (err != 0);
    } while (lastDiscrepancy != 0 && i < n);
    n = i;
}

/*
 * читать ROM подчиненного устройства (код 64 бита)
 */
uint64_t OneWire::readRoom(void) {
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



/*
 * получить один байт
 */
uint8_t OneWire::readByte() {
    uint8_t i = 8, byte = 0;
    while (i--) {
        byte >>= 1;
        byte |= (readBit() << 7);
    }
    return byte;
}

/*
 * получить один бит
 */
uint8_t OneWire::readBit(void) {
    uint8_t bit = 0;
    // логический «0» на 3us
    pinMode(pin, OUTPUT);
    digitalWrite(pin, LOW);
    delayMicroseconds(3);

    // освободить линию и ждать 10us
    pinMode(pin, INPUT);
    delayMicroseconds(10);

    // прочитать значение
    bit = digitalRead(pin);

    // ждать 45us и вернуть значение
    delayMicroseconds(45);
    return bit;
}



/*
 * Команда соответствия ROM, сопровождаемая последовательностью
 * кода ROM на 64 бита позволяет устройству управления шиной
 * обращаться к определенному подчиненному устройству на шине.
 */
void OneWire::setDevice(uint64_t rom) {
    uint8_t i = 64;
    reset();
    writeByte (CMD_MATCHROM);
    while (i--) {
        writeBit(rom & 1);
        rom >>= 1;
    }
}

/*
 * провеска CRC, возвращает "0", если нет ошибок
 * и не "0", если есть ошибки
 */
int OneWire::crcCheck(uint64_t data8x8bit, uint8_t len) {
    uint8_t dat, crc = 0, fb, stByte = 0;
    do {
        dat = (uint8_t)(data8x8bit >> (stByte * 8));
        // счетчик битов в байте
        for (int i = 0; i < 8; i++) {
            fb = crc ^ dat;
            fb &= 1;
            crc >>= 1;
            dat >>= 1;
            if (fb == 1) {
                crc ^= 0x8c; // полином
            }
        }
        stByte++;
    } while (stByte < len);   // счетчик байтов в массиве
    return crc;
}

uint8_t OneWire::crc8(uint8_t addr[], uint8_t len) {
    uint8_t crc = 0;
    while (len--) {
        uint8_t inbyte = *addr++;
        for (uint8_t i = 8; i; i--) {
            uint8_t mix = (crc ^ inbyte) & 0x01;
            crc >>= 1;
            if (mix) {
                crc ^= 0x8c;
            }
            inbyte >>= 1;
        }
    }
    return crc;
}


/**
 * пропустить ROM
 */
void skipRom() {
    reset();
    writeByte(CMD_SKIPROM);
}


