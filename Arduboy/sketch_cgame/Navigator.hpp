

#if !defined(Navigator_hpp)
#define Navigator_hpp

#include <ArduinoSTL.h>
#include "Arduboy2.h"
#include "Btn_ctrl.hpp"
#include "Snake.hpp"
#include "Counter.hpp"

extern Btn_ctrl *btn_ctrl;
extern Snake *snake;
extern Arduboy2 *arduboy;
extern Counter *counter;

// for navigator.
PROGMEM const String navigator_str = "Please choose: ";
PROGMEM const String navigator_game_choice_a = "Press Up: Snake.";
PROGMEM const String navigator_game_choice_b = "Press Dn: Counter.";

enum Support_func
{
    SNAKE,
    COUNTER
};

class Navigator
{
private:
    boolean has_made_choice = false;
    Support_func func_choice;

public:
    Navigator()
    {
        this->func_choice = COUNTER;
    }

    boolean get_has_make_choice()
    {
        return this->has_made_choice;
    }

    void set_has_made_choice(boolean has_or_not)
    {
        this->has_made_choice = has_or_not;
    }

    Support_func get_func_choice()
    {
        return this->func_choice;
    }

    // display navigator.
    void show_navigator()
    {
        arduboy->setCursor(2, 10);
        arduboy->print(navigator_str);

        arduboy->setCursor(2, 30);
        arduboy->print(navigator_game_choice_a);

        arduboy->setCursor(2, 40);
        arduboy->print(navigator_game_choice_b);

        if (btn_ctrl->down_click() && !has_made_choice)
        {
            func_choice = COUNTER;
            has_made_choice = true;
        }

        if (btn_ctrl->up_click() && !has_made_choice)
        {
            func_choice = SNAKE;
            has_made_choice = true;
        }
    }

    // play game a. snake.
    void func_snake()
    {
        if (snake == NULL)
        {
            Snake *snake_new = new Snake(10);   // read from Settings.
            snake = snake_new;
        }

        if (btn_ctrl->up_click())
        {
            snake->turn_to(UP);
        }
        else if (btn_ctrl->down_click())
        {
            snake->turn_to(DOWN);
        }
        else if (btn_ctrl->left_click())
        {
            snake->turn_to(LEFT);
        }
        else if (btn_ctrl->right_click())
        {
            snake->turn_to(RIGHT);
        }

        snake->start();
        snake->move(1);

        snake->show(arduboy, &Arduboy2::drawPixel);
    }

    // play game b. counter.
    void func_count()
    {
        if (counter == NULL)
        {
            Counter *counter_new = new Counter();
            counter = counter_new;
        }

        counter->show();
    }

    void stop_game()
    {
        if (has_made_choice)
        {
            if (func_choice == SNAKE)
            {
                if (snake != NULL)
                {
                    snake->stop();
                    delete snake;
                    snake = NULL;
                }
            }
            else if (func_choice == COUNTER)
            {
                counter->reset_counter();
                delete counter;
                counter = NULL;
            }
        }
    }
};
#endif // Navigator_hpp
