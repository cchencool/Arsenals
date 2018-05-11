

#if !defined(Menu_hpp)
#define Menu_hpp

#include <ArduinoSTL.h>
#include "Arduboy.h"
#include "Btn_ctrl.hpp"
#include "Snake.hpp"
#include "Counter.hpp"

extern Btn_ctrl *btn_ctrl;
extern Snake *snake;

// for menu.
PROGMEM const String menu_str = "Please choose: ";
enum Support_func
{
  SNAKE,
  COUNTER
} func_choice;

boolean has_made_choice = false;

// display menu.
void show_menu()
{
  arduboy->setCursor(2, 10);
  arduboy->print(menu_str);

  arduboy->setCursor(2, 30);
  arduboy->print(String("Press Up: Snake."));

  arduboy->setCursor(2, 40);
  arduboy->print(String("Press Dn: Counter."));

  if (btn_ctrl->up_click() && !has_made_choice)
  {
    func_choice = SNAKE;
    has_made_choice = true;
  }

  if (btn_ctrl->down_click() && !has_made_choice)
  {
    if (snake != NULL)
    {
      delete snake;
    }
    func_choice = COUNTER;
    has_made_choice = true;
  }
}

// play game a. snake.
void func_snake()
{
  if (snake == NULL)
  {
    Snake *snake_new = new Snake(10);
    snake = snake_new;
  }
  snake->start();
  snake->move(1);
  snake->show(arduboy, &Arduboy::drawPixel);
}

// play game b. counter.
void func_count()
{
  draw_square();

  do_count();

  check_lock_free_flag();

  check_lock_flag();

  if (btn_ctrl->b_click())
  {
    count = 5211314;
  }

  arduboy->setCursor(10, 20);
  arduboy->print(hello_str);

  arduboy->setCursor(10, 30);
  arduboy->print(String("count:  ") + String(count));

  arduboy->setCursor(10, 40);
  arduboy->print(String("count_times:  ") + String(count_times));
}


#endif // Menu_hpp
