

#if !defined(Snake_hpp)
#define Snake_hpp

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

class Snake
{

  private:
    uint8_t length;
    std::vector<Point> body_points;
    Direction head_drc;
    Status status;
    // boolean rezise(int8_t length);

  public:
    Snake(uint8_t length);
    boolean start();
    boolean stop();
    boolean turn_to(Direction turn_drc);
    boolean move(uint8_t step);
    boolean grow(uint8_t length);
    // boolean shrink(uint8_t length);
    void show(Arduboy2 *Obj, void (Arduboy2::*p_call)(int16_t, int16_t, uint8_t));
};

#endif // Snake_hpp
