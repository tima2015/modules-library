//
// Created by tima2015 on 08.08.22.
//
// Документация используемая для реализации
// https://teplo-energetika.ru/arduino/ds18b20-rus.pdf
//
// Огромное спасибо статье на Micro Pi большая часть реализации библиотеки OneWire взята у них
// https://micro-pi.ru/%d0%bf%d0%be%d0%b4%d0%ba%d0%bb%d1%8e%d1%87%d0%b5%d0%bd%d0%b8%d0%b5-ds18b20-%d0%ba-orange-pi-bpi-rpi/
//
#ifndef JNI_LIBRARY_ONEWIRE_H
#define JNI_LIBRARY_ONEWIRE_H

#define CMD_CONVERTTEMP    0x44
#define CMD_RSCRATCHPAD    0xbe
#define CMD_WSCRATCHPAD    0x4e
#define CMD_CPYSCRATCHPAD  0x48
#define CMD_RECEEPROM      0xb8
#define CMD_RPWRSUPPLY     0xb4
#define CMD_SEARCHROM      0xf0
#define CMD_READROM        0x33
#define CMD_MATCHROM       0x55
#define CMD_SKIPROM        0xcc
#define CMD_ALARMSEARCH    0xec

#include <stdint.h>

/**
 * Операция сброс.
 * В документации процесс сброса описывается на странице 24
 * @return 1 если был ответ от дачтика, 0 если ответа небыло
 */
int reset(int pin);

/**
 * отправить один бит
 */
void writeBit(int pin, uint8_t bit);

/**
 * отправить один байт
 */
void writeByte(int pin, uint8_t byte);

/**
 * поиск устройств
 */
void searchRom(int pin, uint64_t *roms, int &n);

int crcCheck(uint64_t, uint8_t);

uint8_t crc8(uint8_t *, uint8_t);

void oneWireInit();



void setDevice(uint64_t);



void skipRom(void);

uint8_t readByte(void);

uint8_t readBit(void);

uint64_t readRoom(void);

#endif //JNI_LIBRARY_ONEWIRE_H
