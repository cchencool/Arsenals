
#if !defined(Settings_hpp)
#define Settings_hpp

#include <ArduinoSTL.h>
#include "Arduboy2.h"
#include "arduino.h"

extern Btn_ctrl *btn_ctrl;
extern Arduboy2 *arduboy;

// for func counter.
PROGMEM const String settings_str_title = "settings.";
PROGMEM const String settings_str_snake_initial_length = "snake_length: ";

class Settings
{

  private:
    uint8_t snake_initial_length;   // should store in EEPROM
  
  public:

    Settings()
    {
        this->snake_initial_length = 10;
    }

    void show()
    {
        this->draw_square();

        arduboy->setCursor(10, 20);
        arduboy->print(settings_str_title);

        arduboy->setCursor(10, 30);
        arduboy->print(settings_str_snake_initial_length);

    }

    void draw_square()
    {
        arduboy->drawLine(0, 0, 5, 5, WHITE);
        arduboy->drawLine(5, 5, 122, 5, WHITE);
        arduboy->drawLine(5, 5, 5, 58, WHITE);
        arduboy->drawLine(122, 5, 127, 0, WHITE);
        arduboy->drawLine(122, 5, 122, 58, WHITE);
        arduboy->drawLine(122, 58, 127, 63, WHITE);
        arduboy->drawLine(5, 58, 122, 58, WHITE);
        arduboy->drawLine(5, 58, 0, 63, WHITE);
    }

    boolean store()
    {

    }

    boolean load()
    {

    }
};
#endif // Settings_hpp
