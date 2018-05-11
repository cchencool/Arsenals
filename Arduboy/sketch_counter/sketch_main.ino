
#include <avr/pgmspace.h>
#include <ArduinoSTL.h>
#include "Arduboy.h"
#include "arduino.h"
#include "Menu.hpp"
#include "Btn_ctrl.hpp"
#include "Snake.hpp"
#include "Counter.hpp"

Arduboy *arduboy = new Arduboy;
Btn_ctrl *btn_ctrl = new Btn_ctrl;
Snake *snake;


void setup()
{
  // put your setup code here, to run once:

  arduboy->begin();

  arduboy->setFrameRate(15);
}

void loop()
{
  // put your main code here, to run repeatedly:
  if (!(arduboy->nextFrame()))
  {
    return;
  }

  arduboy->clear();

  if (!has_made_choice)
  {
    show_menu();
  }
  else
  {
    if (func_choice == SNAKE)
    {
      func_snake();
    }
    else if (func_choice == COUNTER)
    {
      func_count();
    }
  }

  if (arduboy->pressed(A_BUTTON) == true && arduboy->pressed(B_BUTTON) == true)
  {
    has_made_choice = false;
  }

  arduboy->display();
}
