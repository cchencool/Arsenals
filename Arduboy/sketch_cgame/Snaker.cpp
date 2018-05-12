
#include <arduino.h>
#include "Arduboy2.h"
#include "Snaker.hpp"

Snaker::Snaker(uint8_t length) : length(length)
{

    this->status = INACTIVE;
    this->head_drc = RIGHT;
    
    // this->p_for = new Point;

    if (length > 0)
    {
        Point *p;
        for (uint8_t i = 0; i < length; i++)
        {
            p = new Point;
            p->x = 5 + i;
            p->y = 5 + i;
            this->body_points.push_back(p);
        }
    }
}

Snaker::~Snaker()
{
    for (uint8_t i = 0; i < this->body_points.size(); i++)
    {
        delete body_points[i];
    }
    body_points.clear();
    // delete this->p_for;
    // this->p_for = NULL;
}

Status Snaker::get_status()
{
    return this->status;
}

boolean Snaker::start()
{
    if (this->status == INACTIVE)
    {
        this->status = ACTIVE;
        return true;
    }
    else
    {
        return false;
    }
}

boolean Snaker::stop()
{
    if (this->status == ACTIVE)
    {
        this->status = INACTIVE;
        return true;
    }
    else
    {
        return false;
    }
}

boolean Snaker::turn_to(Direction turn_drc)
{
    if (this->status == INACTIVE)
    {
        return false;
    }

    boolean trun_result = false;
    Direction reverse_drc;

    switch (turn_drc)
    {
    case UP:
        reverse_drc = DOWN;
        break;
    case DOWN:
        reverse_drc = UP;
        break;
    case LEFT:
        reverse_drc = RIGHT;
        break;
    case RIGHT:
        reverse_drc = LEFT;
        break;

    default:
        break;
    }

    if (this->head_drc != turn_drc && this->head_drc != reverse_drc)
    {
        this->head_drc = turn_drc;
        trun_result = true;
    }
    else
    {
        trun_result = false;
    }

    return trun_result;
}

// boolean Snaker::move_one()
// {
//     if (this->status == INACTIVE)
//     {
//         return false;
//     }

//     boolean has_move = false;

//     Point *p_head = this->body_points[this->body_points.size() - 1];

//     std::vector<Point*>::iterator vec_itr = this->body_points.begin();

//     switch (this->head_drc)
//     {
//     case UP:
//         if (p_head->y > 0)
//         {
//             while (vec_itr != this->body_points.end()) {
//                 (*vec_itr)->y --;
//                 vec_itr++;
//             }
//         }
//         else
//         {
//             this->stop();
//             has_move = false;
//         }
//         break;
//     case DOWN:
//         if (p_head->y < HEIGHT - 1)
//         {
//             while (vec_itr != this->body_points.end()) {
//                 (*vec_itr)->y ++;
//                 vec_itr++;
//             }
//         }
//         else
//         {
//             this->stop();
//             has_move = false;
//         }
//         break;
//     case LEFT:
//         if (p_head->x > 0)
//         {
//             while (vec_itr != this->body_points.end()) {
//                 (*vec_itr)->x --;
//                 vec_itr++;
//             }
//         }
//         else
//         {
//             this->stop();
//             has_move = false;
//         }
//         break;
//     case RIGHT:
//         if (p_head->x < WIDTH - 1)
//         {
//             while (vec_itr != this->body_points.end()) {
//                 (*vec_itr)->x ++;
//                 vec_itr++;
//             }
//         }
//         else
//         {
//             this->stop();
//             has_move = false;
//         }
//         break;
//     default:
//         break;
//     }

//     return has_move;
// }

boolean Snaker::move(uint8_t step)
{
    if (step <= 0 || this->status == INACTIVE)
    {
        return false;
    }

    boolean has_move = false;

    if (step == 1)
    {
        boolean one_mv_result = true;
        Point *p_head = this->body_points[this->body_points.size() - 1];
        Point *p_for = this->body_points[0];
        // Point *p_for = new Point;

        switch (this->head_drc)
        {
        case UP:
            if (p_head->y > 0)
            {
                p_for->x = p_head->x;
                p_for->y = p_head->y - 1;
            }
            else
            {
                this->stop();
                one_mv_result = false;
            }
            break;
        case DOWN:
            if (p_head->y < HEIGHT - 1)
            {
                p_for->x = p_head->x;
                p_for->y = p_head->y + 1;
            }
            else
            {
                this->stop();
                one_mv_result = false;
            }
            break;
        case LEFT:
            if (p_head->x > 0)
            {
                p_for->x = p_head->x - 1;
                p_for->y = p_head->y;
            }
            else
            {
                this->stop();
                one_mv_result = false;
            }
            break;
        case RIGHT:
            if (p_head->x < WIDTH - 1)
            {
                p_for->x = p_head->x + 1;
                p_for->y = p_head->y;
            }
            else
            {
                this->stop();
                one_mv_result = false;
            }
            break;
        default:
            break;
        }

        if (one_mv_result)
        {
            has_move = true;
            // delete body_points[0];
            this->body_points.erase(this->body_points.begin());
            this->body_points.push_back(p_for);
        }
    }
    else
    {
        for (uint8_t i = 0; i < step; i++)
        {
            has_move = this->move(1);
            // has_move = this->move_one();
        }
    }

    return has_move;
}

void Snaker::show(Arduboy2 *Obj, void (Arduboy2::*p_call)(int16_t, int16_t, uint8_t))
{
    for (uint8_t i = 0; i < this->body_points.size(); i++)
    {
        (Obj->*p_call)(this->body_points[i]->x, this->body_points[i]->y, WHITE);
    }

    // print score. (mem cost approximately).
    Obj->setCursor(0, 55);
    Obj->print(String(this->body_points.size() * sizeof(Point) + sizeof(std::vector<Point*>)));
}