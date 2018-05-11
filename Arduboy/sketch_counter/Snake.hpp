

#if !defined(Snake_hpp)
#define Snake_hpp

#include <arduino.h>
#include <ArduinoSTL.h>
#include "Arduboy.h"

struct Point
{
    int x;
    int y;
};

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
    int length;
    std::vector<Point> body_points;
    Direction head_drc;
    Status status;

    boolean check_direction(Direction &head_drc, Direction turn_drc, Direction check_drc);

public:
    Snake(int length);
    boolean start();
    boolean stop();
    boolean turn_to(Direction turn_drc);
    boolean move(int step);
    void show(Arduboy *Obj, void (Arduboy::*p_call)(int, int, uint8_t));

};


#endif // Snake_hpp
