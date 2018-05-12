

#if !defined(Snaker_hpp)
#define Snaker_hpp

#include <arduino.h>
#include <ArduinoSTL.h>
#include "Arduboy2.h"

// struct Point
// {
//     int x;
//     int y;
// };

enum Direction
{
    UP,
    DOWN,
    LEFT,
    RIGHT
};

enum Status
{
    ACTIVE,
    INACTIVE
};

class Snaker
{

  private:
    uint8_t length;
    std::vector<Point*> body_points;    // begin() is the tail. the last element is the head.
    Direction head_drc;
    Status status;
    // Point *p_for;   // temp var to transfer move.
    // boolean rezise(int8_t length);

  public:
    Snaker(uint8_t length);
    ~Snaker();
    Status get_status();
    boolean start();
    boolean stop();
    boolean turn_to(Direction turn_drc);
    // boolean move_one();
    boolean move(uint8_t step);
    boolean grow(uint8_t length);
    // boolean shrink(uint8_t length);
    void show(Arduboy2 *Obj, void (Arduboy2::*p_call)(int16_t, int16_t, uint8_t));
};

#endif // Snaker_hpp
