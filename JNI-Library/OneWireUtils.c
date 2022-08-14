#include "OneWireUtils.h"
#include <wiringPi.h>
#include <stdexcept>
#include <iostream>

int reset(jint pin) {
    pinMode(pin, OUTPUT);
    digitalWrite(pin, LOW);
    delayMicroseconds(480);

    pinMode(pin, INPUT);
    delayMicroseconds(60);

    int response = digitalRead(pin);
    delayMicroseconds(410);

    return response;
}

void writeBit(jint pin, jbyte bit) {
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

void writeByte(jint pin, jbyte byte) {
    jbyte i = 8;
    while (i--) {
        writeBit(pin, byte & 1);
        byte >>= 1;
    }
}

jbyte readByte() {
    jbyte i = 8, byte = 0;
    while (i--) {
        byte >>= 1;
        byte |= (readBit() << 7);
    }
    return byte;
}

jbyte readBit() {
    jbyte bit = 0;
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

void setDevice(jlong rom) {
    jbyte i = 64;
    reset();
    writeByte (CMD_MATCHROM);
    while (i--) {
        writeBit(rom & 1);
        rom >>= 1;
    }
}

void skipRom() {
    reset();
    writeByte(CMD_SKIPROM);
}