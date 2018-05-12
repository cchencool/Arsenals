
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
Menu *menu = new Menu;
Snake *snake = new Snake(10);
// Counter *counter = new Counter(arduboy);
Counter *counter = new Counter();

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

  if (!menu->get_has_make_choice())
  {
    menu->show_menu();
  }
  else
  {
    if (menu->get_func_choice() == SNAKE)
    {
      menu->func_snake();
    }
    else if (menu->get_func_choice() == COUNTER)
    {
      menu->func_count();
    }
  }

  if ( menu->get_has_make_choice() && btn_ctrl->b_click())
  {
    menu->stop_game();
    menu->set_has_made_choice(false);
  }

  arduboy->display();
}
