package org.firstinspires.ftc.individualprojects;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

public class HardwareSpectralBot {
    LynxModule controlHub;
    LynxModule expansionHub;
    protected I2cDeviceSynch spectralAS7341;

    private final int I2cHardwareAddress = 0x39; // physical hardware address on I2C bus (7-bit)
    private final int spectralRegisterAddressSelector = 0x70; // address of virtual register selector on bus used for commands to select channels
    private final int spectralRegisterAddressData = 0x71; // address on bus of data of virtual registers commands used to actually read the channels

    // Virtual register addresses for channel LSBs (Least Significant Bits (8 right-most))
    // Channel MSBs (Most significant bits (8 left-most)) are the next channel up so 0x95 -> 0x96
    // All 10 channels are in hexadecimal format
    private final int[] virtualLsbAddresses = {
            0x95, // F1 410nm
            0x97, // F2 440nm
            0x99, // F3 475nm
            0x9B, // F4 510nm
            0x9D, // F5 550nm
            0x9F, // F6 583nm
            0xA1, // F7 620nm
            0xA3, // F8 670nm
            0x94, // Clear
            0xA5  // NIR (IR)
    };

    protected HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        // initialize spectral sensor
        spectralAS7341 = hwMap.get(I2cDeviceSynch.class, "spectral sensor");
        // set the physical address 0x39
        spectralAS7341.setI2cAddress(I2cAddr.create7bit(I2cHardwareAddress));
        spectralAS7341.engage();
        // set the read window to ONLY_ONCE (no background reading; ensures fresh data)
        spectralAS7341.setReadWindow(new I2cDeviceSynch.ReadWindow(spectralRegisterAddressData,1, I2cDeviceSynch.ReadMode.ONLY_ONCE));

        for (LynxModule module : hwMap.getAll(LynxModule.class)) {
            if (module.isParent()) {
                controlHub = module;
            } else {
                expansionHub = module;
            }
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }


    }

    public void readBulkData(){
        controlHub.clearBulkCache();
    }

    // Creating higher-level abstraction instead of manually reading and writing to sensor registers every time
    // Read a specific channel
    public int readChannel(int virtualLsbAdr) {
        spectralAS7341.write8(spectralRegisterAddressSelector, virtualLsbAdr); // select virtual register for channel
        int lsb = spectralAS7341.read8(spectralRegisterAddressData); // read lsb data from that channel

        spectralAS7341.write8(spectralRegisterAddressSelector, virtualLsbAdr+1);
        int msb = spectralAS7341.read8(spectralRegisterAddressData); // do the same but read in msb data

        return (msb << 8) | lsb; // combine lsb and msb and return the data;
    }

    // Read all channels
    public int[] readAllChannels(){
        int[] data = new int[10];
        for (int i = 0; i < 10; i++) {
            data[i] = readChannel(virtualLsbAddresses[i]);
        }

        return data;
    }

}
