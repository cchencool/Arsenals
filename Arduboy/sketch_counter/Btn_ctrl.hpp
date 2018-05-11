
#ifndef Btn_ctrl_hpp
#define Btn_ctrl_hpp

#include "Arduino.h"

class Btn_ctrl
{
public:
    Btn_ctrl();
    boolean up_click();
    boolean down_click();
    boolean right_click();
    boolean left_click();
    boolean a_click();
    boolean b_click();

private:
    boolean lock;
    boolean check_lock_flag();

};

#endif // Btn_ctrl_hpp
