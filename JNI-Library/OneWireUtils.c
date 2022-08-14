#include "OneWireUtils.h"
#include <wiringPi.h>

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

void writeBit(int pin, int bit) {
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

void writeByte(int pin, int byte) {
    int i = 8;
    while (i--) {
        writeBit(pin, byte & 1);
        byte >>= 1;
    }
}

int readByte(int pin) {
    int i = 8, byte = 0;
    while (i--) {
        byte >>= 1;
        byte |= (readBit(pin) << 7);
    }
    return byte;
}

int readBit(int pin) {
    int bit = 0;
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

void setDevice(int pin, int64_t rom) {
    int8_t i = 64;
    reset(pin);
    writeByte(pin, CMD_MATCHROM);
    while (i--) {
        writeBit(pin, rom & 1);
        rom >>= 1;
    }
}

void skipRom(int pin) {
    reset(pin);
    writeByte(pin, CMD_SKIPROM);
}