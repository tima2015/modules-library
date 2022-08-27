cmake_minimum_required(VERSION 3.22)
project(JNI_Library)

set(JAVA_HOME /usr/lib/jvm/java-1.11.0-openjdk-amd64)

set(CMAKE_C_STANDARD 11)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS}")
# -lwiringPi
include_directories("${JAVA_HOME}/include" "${JAVA_HOME}/include/linux" "/usr/local/include" "/usr/local/lib")

add_library(JNI_Library SHARED
        ru_funnydwarf_iot_nml_JNILibrary.h
        ru_funnydwarf_iot_nml_JNILibrary.c
        ru_funnydwarf_iot_nml_modules_receiver_writer_NativeWriter.h
        ru_funnydwarf_iot_nml_modules_receiver_writer_NativeWriter.c
        ru_funnydwarf_iot_nml_modules_sensor_reader_NativeReader.h
        ru_funnydwarf_iot_nml_modules_sensor_reader_NativeReader.c)

#find_library(WIRINGPI_LIBRARY wiringPi)

target_link_libraries(JNI_Library ${JNI_LIBRARIES} wiringPi)
