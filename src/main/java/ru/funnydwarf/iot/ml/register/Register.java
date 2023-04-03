package ru.funnydwarf.iot.ml.register;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class Register {

    public enum Size {
        BYTE, WORD
    }

    private final int address;
    private final String hexAddress;
    private final Size size;
    private int value = 0;
    private List<String> hexValues;

    public Register(int address, Size size) {
        this.address = address;
        this.size = size;
        hexAddress = "0x%h".formatted(address);
    }

    private void updateConfigBytePair() {
        String[] hexBytes = new String[] {
                "0x%h".formatted((value >> 8) & 0xff),
                "0x%h".formatted(value & 0xff)
        };
        hexValues = size == Size.BYTE ? List.of(hexBytes[1]) : List.of(hexBytes);
    }

    protected final void setValue(int value) {
        this.value = value;
        updateConfigBytePair();
    }

    protected void updateValueByValueParts() {

    }

    protected final void assertValue(int value) {
        if (value != this.value) {
            throw new RuntimeException("Control register value check fail! Row value = [" + Integer.toBinaryString(value) + "], after parse register value = [" + Integer.toBinaryString(this.value) + "]!");
        }
    }

    @AllArgsConstructor
    @Getter
    public static class ValuePart {
        protected final int bits;

        protected static <T extends ValuePart> T getFromBits(int value, int forBits, T[] valueParts) {
            for (T part : valueParts) {
                if (((value & forBits) ^ part.bits) == 0) {
                    return part;
                }
            }
            throw new RuntimeException("Can't find ValuePart for value = [" + Integer.toBinaryString(value) + "] in forBits = [" + Integer.toBinaryString(forBits) + "]!");
        }
    }
}