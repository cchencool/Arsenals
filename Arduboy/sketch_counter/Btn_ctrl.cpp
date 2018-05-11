
#include "Btn_ctrl.hpp"
#include "Arduboy.h"
#include "Arduino.h"

extern Arduboy *arduboy;

Btn_ctrl::Btn_ctrl() {
    this->lock = false;
}

boolean Btn_ctrl::up_click()
{
    if (arduboy->pressed(UP_BUTTON) == true && !this->lock)
    {
        this->lock = true;
    }
    return this->check_lock_flag();
}
boolean Btn_ctrl::down_click()
{
    if (arduboy->pressed(DOWN_BUTTON) == true && !this->lock)
    {
        this->lock = true;
    }
    return this->check_lock_flag();
}

boolean Btn_ctrl::right_click()
{
    if (arduboy->pressed(RIGHT_BUTTON) == true && !this->lock)
    {
        this->lock = true;
    }
    return this->check_lock_flag();
}

boolean Btn_ctrl::left_click()
{
    if (arduboy->pressed(LEFT_BUTTON) == true && !this->lock)
    {
        this->lock = true;
    }
    return this->check_lock_flag();
}

boolean Btn_ctrl::a_click()
{
    if (arduboy->pressed(A_BUTTON) == true && !this->lock)
    {
        this->lock = true;
    }
    return this->check_lock_flag();
}

boolean Btn_ctrl::b_click()
{
    if (arduboy->pressed(B_BUTTON) == true && !this->lock)
    {
        this->lock = true;
    }
    return this->check_lock_flag();
}

boolean Btn_ctrl::check_lock_flag()
{
    
    if (this->lock && arduboy->notPressed(A_BUTTON) == true && arduboy->notPressed(B_BUTTON) == true && arduboy->notPressed(UP_BUTTON) == true && arduboy->notPressed(DOWN_BUTTON) == true && arduboy->notPressed(RIGHT_BUTTON) == true && arduboy->notPressed(LEFT_BUTTON) == true)
    {
        this->lock = false;
        return true;
    }
    else
    {
        return false;
    }
}
