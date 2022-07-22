#include <stdio.h>
#include <jni.h>
#include <wiringPi.h>

JNIEXPORT void JNICALL Java_ru_funnydwarf_iot_nml_Testy_helloWorld
  (JNIEnv *env, jobject obj){
      printf("Hello, World!\n");
      printf("begin!");
      wiringPiSetup () ; //инициализируем библиотеку
        pinMode (3, OUTPUT) ;
        for (int i = 0; i < 10; i++)
        {
          printf("HIGH!\n");
          digitalWrite (3, HIGH) ; delay (500) ;
          printf("LOW!\n");
          digitalWrite (3,  LOW) ; delay (500) ;
        }
      printf("end!\n");
}
