//
// Created by tima2015 on 08.08.22.
//
// Документация используемая для реализации
// https://teplo-energetika.ru/arduino/ds18b20-rus.pdf
//
// Огромное спасибо статье на Micro Pi большая часть реализации библиотеки OneWire взята у них
// https://micro-pi.ru/%d0%bf%d0%be%d0%b4%d0%ba%d0%bb%d1%8e%d1%87%d0%b5%d0%bd%d0%b8%d0%b5-ds18b20-%d0%ba-orange-pi-bpi-rpi/
//


#ifndef JNI_LIBRARY_ONEWIREUTILS_H
#define JNI_LIBRARY_ONEWIREUTILS_H
#include <jni.h>

#define CMD_CONVERTTEMP    0x44
#define CMD_RSCRATCHPAD    0xbe
#define CMD_WSCRATCHPAD    0x4e
#define CMD_CPYSCRATCHPAD  0x48
#define CMD_RECEEPROM      0xb8
#define CMD_RPWRSUPPLY     0xb4
#define CMD_MATCHROM       0x55
#define CMD_SKIPROM        0xcc
#define CMD_ALARMSEARCH    0xec

/**
 * Операция сброс.
 * В документации процесс сброса описывается на странице 24
 * @return 1 если был ответ от дачтика, 0 если ответа небыло
 */
int reset(jint pin);

/**
 * отправить один бит
 */
void writeBit(jint pin, jbyte bit);

/**
 * отправить один байт
 */
void writeByte(jint pin, jbyte byte);

/**
 * получить один байт
 */
jbyte OneWire::readByte()

/**
 * получить один бит
 */
jbyte OneWire::readBit()


/**
 * Команда соответствия ROM, сопровождаемая последовательностью
 * кода ROM на 64 бита позволяет устройству управления шиной
 * обращаться к определенному подчиненному устройству на шине.
 */
void setDevice(jlong rom);

/**
 * пропустить ROM
 * Используется для передачи функциональной команды сразу всем устройствам сети
 */
void skipRom();

#endif