

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

class Func_snake : public Base_func
{

  private:
    Snaker *snaker;// = new Snaker(10);

  public:
    Func_snake()
    {
        // if (func_settings != NULL) 
        // {
        //     this->snaker = new Snaker(func_settings->get_config());
        // }
        // else
        // {
            this->snaker = new Snaker(10);
        // }
    }

    ~Func_snake()
    {
        delete snaker;
        snaker = NULL;
    }

    void play()
    {

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

        snaker->start();
        snaker->move(1);

        snaker->show(arduboy, &Arduboy2::drawPixel);
    }

    void exit(Base_func **p)
    {
        this->snaker->stop();
        Base_func::exit(p);
    }
};

#endif // Func_snake_hpp
