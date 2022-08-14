cmake_minimum_required(VERSION 3.22)
project(JNI_Library)

set(JAVA_HOME /usr/lib/jvm/java-1.11.0-openjdk-amd64)

set(CMAKE_C_STANDARD 11)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS}")
# -lwiringPi
include_directories("${JAVA_HOME}/include" "${JAVA_HOME}/include/linux" "/usr/local/include" "/usr/local/lib")

add_library(JNI_Library SHARED
        test.c
        OneWireUtils.h
        OneWireUtils.c
        ru_funnydwarf_iot_nml_Pin.h
        ru_funnydwarf_iot_nml_Pin.c
        ru_funnydwarf_iot_nml_modules_OutputModule.h
        ru_funnydwarf_iot_nml_modules_OutputModule.c
        ru_funnydwarf_iot_nml_modules_onewire_OneWire.h
        ru_funnydwarf_iot_nml_modules_onewire_OneWire.c
        ru_funnydwarf_iot_nml_modules_onewire_DS18B20OneWireUnit.h
        ru_funnydwarf_iot_nml_modules_onewire_DS18B20OneWireUnit.c
        ru_funnydwarf_iot_nml_JNILibrary.h
        ru_funnydwarf_iot_nml_JNILibrary.c)

#find_library(WIRINGPI_LIBRARY wiringPi)

target_link_libraries(JNI_Library ${JNI_LIBRARIES} wiringPi)
