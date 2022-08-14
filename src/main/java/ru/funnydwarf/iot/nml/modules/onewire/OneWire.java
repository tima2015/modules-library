package ru.funnydwarf.iot.nml.modules.onewire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.funnydwarf.iot.nml.Initializable;
import ru.funnydwarf.iot.nml.Pin;
import ru.funnydwarf.iot.nml.modules.Module;

import java.util.ArrayList;

public class OneWire extends Module implements Initializable {

    private final OneWireUnit[] oneWireUnits;

    public OneWire(Pin pin, String name, String description, OneWireUnit[] oneWireUnits) {
        super(pin, name, description);
        this.oneWireUnits = oneWireUnits;
    }

    @JsonCreator
    public OneWire(@JsonProperty("pin") Pin pin,
                   @JsonProperty("name") String name,
                   @JsonProperty("description") String description,
                   @JsonProperty("userCustomName") String userCustomName,
                   @JsonProperty("userCustomDescription") String userCustomDescription,
                   @JsonProperty("oneWireUnits") OneWireUnit[] oneWireUnits) {
        super(pin, name, description, userCustomName, userCustomDescription);
        this.oneWireUnits = oneWireUnits;
    }

    public OneWireUnit[] getOneWireUnits() {
        return oneWireUnits;
    }

    @Override
    public void initialize(){
        ArrayList<Long> roms = searchRoms();
        OneWireUnit.State state;
        for (OneWireUnit unit : oneWireUnits) {
            state = OneWireUnit.State.NOT_FOUND;
            for (long rom : roms) {
                if (unit.getRom() == rom){
                    state = OneWireUnit.State.OK;
                    break;
                }
            }
            unit.setState(state);
        }
    }

    /**
     * Выполняет поиск следующего подключённого устройства
     * @param pin пин к которому подключена исследуемая сеть oneWire
     * @param lastAddress адрес предыдущего найденого устройства
     * @param lastDiscrepancy
     * @return
     * @throws RuntimeException
     */
    private static native long[] searchNextAddress(int pin, long lastAddress, long lastDiscrepancy) throws RuntimeException;

    /**
     * провеска CRC
     * @param byteArray массив байтов заданый через переменнуб типа long
     * @param len длина массива байтов
     * @return возвращает "0", если нет ошибок
     */
    private int checkingCyclicRedundancyCheck(long byteArray, int len) {
        byte dat, crc = 0, stByte = 0;
        int fb;
        do {
            dat = (byte) (byteArray >> (stByte * 8));
            //счетчик битов в байте
            for (int i = 0; i < 8; i++) {
                fb = crc ^ dat;
                fb &= 1;
                crc >>= 1;
                dat >>= 1;
                if (fb == 1) {
                    crc ^= 0x8c; //полином
                }
            }
            stByte++;
        } while (stByte < len); //счетчик байтов в массиве
        return crc;
    }

    /**
     * Выполняет происк устройств на шине(проводе)
     * @return список адресов устройств
     */
    private ArrayList<Long> searchRoms() {
        long lastAddress = 0;
        long lastDiscrepancy = 0;
        int err = 0;
        ArrayList<Long> unitAddress = new ArrayList<>();

        do {
            do {
                try{
                    long[] searchResult = searchNextAddress(getPin().getNumber(), lastAddress, lastDiscrepancy);
                    lastAddress = searchResult[0];
                    lastDiscrepancy = searchResult[1];
                    int crc = checkingCyclicRedundancyCheck(lastAddress, 8);
                    if (crc == 0) {
                        unitAddress.add(lastAddress);
                        err = 0;
                    } else {
                        err++;
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    err++;
                    if (err > 3) {
                        throw e;
                    }
                }
            } while (err != 0);
        } while (lastDiscrepancy != 0);
        return unitAddress;
    }

    /**
     * Только если к шине подключено одно устройство!
     * @return ROM подчиненного устройства (код 64 бита)
     */
    private static native long readRom();

}
