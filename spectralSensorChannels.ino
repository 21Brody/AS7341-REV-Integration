#include <Adafruit_AS7341.h>

Adafruit_AS7341 as7341;


void setup() {
  Serial.begin(115200);

  // Wait for communication with the host computer serial monitor
  while (!Serial) {
    delay(1);
  }
  Serial.println("Setup Started");
  if (!as7341.begin()){
    Serial.println("Could not find AS7341");
    while (1) { delay(10); }
  }

  Serial.println("Sensor found");

  as7341.setATIME(100);
  as7341.setASTEP(999);
  as7341.setGain(AS7341_GAIN_256X);
}

void loop() {
  // Read all channels at the same time and store in as7341 object
  if (!as7341.readAllChannels()){
    Serial.println("Error reading all channels!");
    return;
  }

  // Print out the stored values for each channel
  Serial.print(as7341.getChannel(AS7341_CHANNEL_415nm_F1));
  Serial.print('\t');
  Serial.print(as7341.getChannel(AS7341_CHANNEL_445nm_F2));
  Serial.print('\t');
  Serial.print(as7341.getChannel(AS7341_CHANNEL_480nm_F3));
  Serial.print('\t');
  Serial.print(as7341.getChannel(AS7341_CHANNEL_515nm_F4));
  Serial.print('\t');
  Serial.print(as7341.getChannel(AS7341_CHANNEL_555nm_F5));
  Serial.print('\t');
  Serial.print(as7341.getChannel(AS7341_CHANNEL_590nm_F6));
  Serial.print('\t');
  Serial.print(as7341.getChannel(AS7341_CHANNEL_630nm_F7));
  Serial.print('\t');
  Serial.print(as7341.getChannel(AS7341_CHANNEL_680nm_F8));

  // Comment out if you want to use Serial Plotter
  Serial.print("Clear    : ");
  Serial.println(as7341.getChannel(AS7341_CHANNEL_CLEAR));

  // Serial.print("Near IR  : ");
  // Serial.println(as7341.getChannel(AS7341_CHANNEL_NIR));

  Serial.println("");
}
