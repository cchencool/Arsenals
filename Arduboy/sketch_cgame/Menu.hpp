

#if !defined(Menu_hpp)
#define Menu_hpp

#include <ArduinoSTL.h>
#include "Arduboy.h"
#include "Btn_ctrl.hpp"
#include "Snake.hpp"
#include "Counter.hpp"

extern Btn_ctrl *btn_ctrl;
extern Snake *snake;
extern Arduboy *arduboy;
extern Counter *counter;

// for menu.
PROGMEM const String menu_str = "Please choose: ";
PROGMEM const String menu_game_choice_a = "Press Up: Snake.";
PROGMEM const String menu_game_choice_b = "Press Dn: Counter.";
enum Support_func
{
    SNAKE,
    COUNTER
};

class Menu
{
private:
    boolean has_made_choice = false;
    Support_func func_choice;

public:
    Menu()
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

    // display menu.
    void show_menu()
    {
        arduboy->setCursor(2, 10);
        arduboy->print(menu_str);

        arduboy->setCursor(2, 30);
        arduboy->print(menu_game_choice_a);

        arduboy->setCursor(2, 40);
        arduboy->print(menu_game_choice_b);

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
            Snake *snake_new = new Snake(10);
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

        snake->show(arduboy, &Arduboy::drawPixel);
    }

    // play game b. counter.
    void func_count()
    {
        if (counter == NULL)
        {
            Counter *counter_new = new Counter();
            counter = counter_new;
        }

        counter->draw_square();

        counter->do_count();

        counter->check_lock_free_flag();

        counter->check_lock_flag();
        
        arduboy->setCursor(10, 20);
        arduboy->print(hello_str);

        arduboy->setCursor(10, 30);
        arduboy->print(String("count:  ") + String(counter->get_count()));

        arduboy->setCursor(10, 40);
        arduboy->print(String("count_times:  ") + String(counter->get_count_times()));
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
#endif // Menu_hpp
