package com.thermostate.roomtemperature.infrastructure.bmp280;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;

public class BMP280 {

    private static final int BMP280_ADDRESS = 0x76; // Dirección I2C del BMP280
    private static final int BMP280_REGISTER_TEMP_MSB = 0xFA; // Registro de temperatura MSB
    private static final int BMP280_REGISTER_PRESSURE_MSB = 0xF7; // Registro de presión MSB

    private I2C device;

    public BMP280(Context pi4j) {
        I2CProvider i2cProvider = pi4j.provider("i2c");

        I2CConfig config = I2C.newConfigBuilder(pi4j)
                .id("BMP280")
                .bus(1)
                .device(BMP280_ADDRESS)
                .build();

        this.device = i2cProvider.create(config);

        init();
    }

    private void init() {
        // Configuración básica del BMP280
        device.writeRegister(0xF4, (byte) 0x27); // Configura el sensor en modo normal
        device.writeRegister(0xF5, (byte) 0xA0); // Configura el filtro y la tasa de muestreo
    }

    public double readTemperature() {
        // BMP280 gives 3 temperature measures: most significat, less and extended in 3 registries
        int msb = device.readRegister(BMP280_REGISTER_TEMP_MSB);
        int lsb = device.readRegister(BMP280_REGISTER_TEMP_MSB + 1);
        int xlsb = device.readRegister(BMP280_REGISTER_TEMP_MSB + 2);

        // last 4 bits are useless (mostly noise) so we remove them
        int rawTemperature = (msb << 12) | (lsb << 4) | (xlsb >> 4);

        double var1 = (((double) rawTemperature) / 16384.0 - 5120.0) / 5120.0;
        double temperature = var1 * 50.0; // To be adjusted

        return temperature;
    }

    public double readPressure() {
        int msb = device.readRegister(BMP280_REGISTER_PRESSURE_MSB);
        int lsb = device.readRegister(BMP280_REGISTER_PRESSURE_MSB + 1);
        int xlsb = device.readRegister(BMP280_REGISTER_PRESSURE_MSB + 2);

        int rawPressure = (msb << 12) | (lsb << 4) | (xlsb >> 4);

        // Cálculo de la presión en Pa
        double pressure = (double) rawPressure / 25600.0; // Ajusta según la calibración de tu sensor

        return pressure;
    }

    public static void main(String[] args) {
        // Inicializa Pi4J con contexto automático
        Context pi4j = Pi4J.newAutoContext();

        BMP280 bmp280 = new BMP280(pi4j);

        double temperature = bmp280.readTemperature();
        double pressure = bmp280.readPressure();

        System.out.println("Temperature: " + temperature + " °C");
        System.out.println("Pressure: " + pressure + " Pa");

        // Cerrar el contexto al final del uso
        pi4j.shutdown();
    }
}
