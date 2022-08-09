package ru.funnydwarf.iot.nml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс содержащий информацию о пине и его текущем состоянии
 */
public class Pin {

    /**
     * Перечисление типов пинов
     * GND - земля/gnd
     * POWER_3_3V - питание 3.3v
     * POWER_5V - питание 5v
     * GPIO - интерфейс ввода/вывода
     */
    public enum PinType {
        GND,
        POWER_3_3V,
        POWER_5V,
        GPIO
    }


    /**
     * Текущее состояние пинов
     * NONE - Отсутствие состояние, применяется к пинам земли и питания
     * UNKNOWN - Состояние неизвестно, возникает при инициализации пинов ввода/вывода, проведении нативных операций,
     * требующих изменения состояния пина
     * OUTPUT - Состояние вывода
     * INPUT - Состояние ввода
     * Поле value содержит численую интерпритацию перечисления
     */
    public enum PinMode {
        NONE(-2),
        UNKNOWN(-1),
        INPUT(0),
        OUTPUT(1),
        PWM_OUTPUT(2),
        GPIO_CLOCK(3),
        SOFT_PWM_OUTPUT(4),
        SOFT_TONE_OUTPUT(5),
        PWM_TONE_OUTPUT(6);

        public final int code;

        PinMode(int code) {
            this.code = code;
        }
    }

    private final String name;
    private final PinType type;
    private final int physicalNumber;
    private final int wiringPiNumber;
    private final int gpioNumber;
    private PinMode mode;

    Pin(String name, PinType type, int physicalNumber, int wiringPiNumber, int gpioNumber) {
        this.name = name;
        this.type = type;
        this.physicalNumber = physicalNumber;
        this.wiringPiNumber = wiringPiNumber;
        this.gpioNumber = gpioNumber;
        mode = type == PinType.GPIO ? PinMode.UNKNOWN : PinMode.NONE;
    }

    /*public void updateMode() {
        if (type != PinType.GPIO) {
            return;
        }

        int result = updateMode(wiringPiNumber);
        for (PinMode value : PinMode.values()) {
            if (result == value.code) {
                mode = value;
                return;
            }
        }
    }

    private static native int updateMode(int number);*/

    void setMode(PinMode mode) throws WrongPinModeException {
        if (type != PinType.GPIO) {
            if (mode == PinMode.NONE) {
                return;
            } else {
                throw new WrongPinModeException(this, mode);
            }
        }

        this.mode = mode;

        if (mode == PinMode.UNKNOWN) {
            return;
        }
        setMode(wiringPiNumber, mode.code);
    }

    private static native void setMode(int number, int modeCode);

    public String getName() {
        return name;
    }

    public PinType getType() {
        return type;
    }

    public int getPhysicalNumber() {
        return physicalNumber;
    }

    public int getWiringPiNumber() {
        return wiringPiNumber;
    }

    public int getGpioNumber() {
        return gpioNumber;
    }

    public PinMode getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "Pin{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", physicalNumber=" + physicalNumber +
                ", wiringPiNumber=" + wiringPiNumber +
                ", gpioNumber=" + gpioNumber +
                ", mode=" + mode +
                '}';
    }

    private static final ArrayList<Pin> pinsList = new ArrayList<>();

    public static void initialize(File pinDataFile) throws ParserConfigurationException, IOException, SAXException {
        if (!pinsList.isEmpty()){
            pinsList.clear();
            System.out.println("Reinitialization of the pin list. Is everything going right?");
        }

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(pinDataFile);
        Node root = document.getDocumentElement();
        NodeList pins = root.getChildNodes();

        for (int i = 0; i < pins.getLength(); i++) {
            Node pin = pins.item(i);
            NodeList pinDataCollection = pin.getChildNodes();
            pinsList.add(new Pin(pinDataCollection.item(0).getTextContent(),
                    PinType.valueOf(pinDataCollection.item(1).getTextContent()),
                    Integer.parseInt(pinDataCollection.item(2).getTextContent()),
                    Integer.parseInt(pinDataCollection.item(3).getTextContent()),
                    Integer.parseInt(pinDataCollection.item(4).getTextContent())));
        }
    }

    public static Pin findByWiringPiNumber(int wiringPiNumber){
        for (Pin pin : pinsList) {
            if (pin.wiringPiNumber == wiringPiNumber)
                return pin;
        }
        return null;
    }

    public static Pin findByPhysicalNumber(int physicalNumber){
        for (Pin pin : pinsList) {
            if (pin.physicalNumber == physicalNumber)
                return pin;
        }
        return null;
    }

    public static Pin findByGPIONumber(int gpioNumber) {
        for (Pin pin : pinsList) {
            if (pin.gpioNumber == gpioNumber)
                return pin;
        }
        return null;
    }

    public static ArrayList<Pin> findByName(String name){
        ArrayList<Pin> list = new ArrayList<>();
        for (Pin pin : pinsList) {
            if (pin.name.equals(name)){
                list.add(pin);
            }
        }
        return list;
    }
}
