package org.firstinspires.ftc.individualprojects;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp(name = "Adafruit Sensor Test", group = "Sensor")
public class AdafruitAS7341Calibration  extends LinearOpMode{

        HardwareSpectralBot spectral = new HardwareSpectralBot();

        public void init(){

        }

        @Override
        public void runOpMode(){

        }
        /*private I2cDeviceSynch i2cDevice;

        @Override
        public void init() {
            I2cDevice i2c = hardwareMap.get(I2cDevice.class, "colorSensor");
            i2cDevice = new I2cDeviceSynchImpl(i2c, I2cAddr.create7bit(0x29), false);
            i2cDevice.engage(); // Start communication
        }

        @Override
        public void loop() {
            byte[] data = i2cDevice.read(0x14, 8); // Example: read 8 bytes from register 0x14
            int red = data[3] & 0xFF;
            int green = data[5] & 0xFF;
            int blue = data[7] & 0xFF;

            telemetry.addData("Red", red);
            telemetry.addData("Green", green);
            telemetry.addData("Blue", blue);
            telemetry.update();
        }*/

}
