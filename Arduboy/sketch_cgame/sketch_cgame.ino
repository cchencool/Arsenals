
#include <avr/pgmspace.h>
#include <ArduinoSTL.h>
#include "Arduboy2.h"
#include "arduino.h"
#include "Navigator.hpp"
#include "Btn_ctrl.hpp"
#include "Snake.hpp"
#include "Counter.hpp"

Arduboy2 *arduboy = new Arduboy2;
Btn_ctrl *btn_ctrl = new Btn_ctrl;
Navigator *navigator = new Navigator;
Snake *snake = new Snake(10);
Counter *counter = new Counter();

void setup()
{
  // put your setup code here, to run once:

  arduboy->begin();

  Serial.begin(9600);

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

  if (!navigator->get_has_make_choice())
  {
    navigator->show_navigator();
  }
  else
  {
    if (navigator->get_func_choice() == SNAKE)
    {
      navigator->func_snake();
    }
    else if (navigator->get_func_choice() == COUNTER)
    {
      navigator->func_count();
    }
  }

  if ( navigator->get_has_make_choice() && btn_ctrl->b_click())
  {
    navigator->stop_game();
    navigator->set_has_made_choice(false);
  }

  arduboy->display();
}
