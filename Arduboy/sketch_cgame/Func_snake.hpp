

#if !defined(Func_snake_hpp)
#define Func_snake_hpp

#include <arduino.h>
#include "Arduboy2.h"
#include "Snaker.hpp"
#include "Base_func.hpp"
#include "Func_settings.hpp"


extern Arduboy2 *arduboy;
extern Btn_ctrl *btn_ctrl;
extern Base_func *func_settings;

PROGMEM const String pause_str = "Pause";
PROGMEM const String find_egg_str = "Found Egg!";

uint8_t time_counter = 0;

class Func_snake : public Base_func
{

  private:
    Snaker *snaker; // = new Snaker(10);

  public:
    Func_snake()
    {
        // if (func_settings != NULL)
        // {
        //     this->snaker = new Snaker(func_settings->get_config());
        // }
        // else
        // {
        this->snaker = new Snaker(INITIAL_SNAKE_LENGTH);
        // }
    }

    ~Func_snake()
    {
        delete snaker;
        snaker = NULL;
    }

    void play()
    {
        draw_square();

        if (btn_ctrl->up_click())
        {
            snaker->turn_to(UP);
        }
        else if (btn_ctrl->down_click())
        {
            snaker->turn_to(DOWN);
        }
        else if (btn_ctrl->left_click())
        {
            snaker->turn_to(LEFT);
        }
        else if (btn_ctrl->right_click())
        {
            snaker->turn_to(RIGHT);
        }

        if (btn_ctrl->a_click())
        {
            if (snaker->get_status() == ACTIVE)
            {
                snaker->stop();
            }
            else if (snaker->get_status() == INACTIVE)
            {
                snaker->start();
            }
        }

        if (snaker->get_status() == ACTIVE)
        {
            boolean move_result = snaker->move(1);
            if (!move_result) {
                snaker->reset_body();
            }
        }
        else
        {
            arduboy->setCursor(95, 55);
            arduboy->print(pause_str);
        }

        if (snaker->if_find_egg())
        {
            arduboy->setCursor(0, 0);
            arduboy->print(find_egg_str);
            if (++time_counter % 15 == 0)
            {
                snaker->reset_egg();
            }
        }

        snaker->show(arduboy, &Arduboy2::drawPixel);
    }

    void exit(Base_func **p)
    {
        this->snaker->stop();
        /**
         * if we delete the obj to free mem. it may occur some problem when keep enter func_snake and exit.
         * so far, I think it may cause by the 2.5Kb memory cannot afford such frequent new/delete obj.
         * 
         * 2018/05/13
         */
        // Base_func::exit(p);
    }

    void draw_square()
    {
        arduboy->drawLine(0, 0, WIDTH - BODER_WIDTH, 0, WHITE); // -(up)
        arduboy->drawLine(0, 0, 0, HEIGHT - BODER_WIDTH, WHITE);    // |(left)
        arduboy->drawLine(WIDTH - BODER_WIDTH, 0, WIDTH - BODER_WIDTH, HEIGHT - BODER_WIDTH, WHITE);    // |(right)
        arduboy->drawLine(0, HEIGHT - BODER_WIDTH, WIDTH - BODER_WIDTH, HEIGHT - BODER_WIDTH, WHITE);   // -(down)
    }
};

#endif // Func_snake_hpp
