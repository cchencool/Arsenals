
#include "Arduboy.h"

Arduboy arduboy;

String hello_str = "hello, my friend.";

int count = 0;
int count_times = 1;

const int lock_free_frame_limit = 15;
int lock_free_frame_count = 0;

boolean lock = false;
boolean lock_free = false;

void setup() {
  // put your setup code here, to run once:

  arduboy.begin();

  arduboy.setFrameRate(15);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (!(arduboy.nextFrame())) {
    return;
  }

  arduboy.clear();

  do_count();

  check_lock_free_flag();

  check_lock_flag();

  arduboy.setCursor(10, 20);
  arduboy.print(hello_str);

  arduboy.setCursor(10, 30);
  arduboy.print(String("count:  ") + String(count));

  arduboy.setCursor(10, 40);
  arduboy.print(String("count_times:  ") + String(count_times));

  arduboy.display();
}

void check_lock_free_flag() {

  if ( arduboy.pressed(A_BUTTON) == true
       || arduboy.pressed(B_BUTTON) == true
       || arduboy.pressed(UP_BUTTON) == true
       || arduboy.pressed(DOWN_BUTTON) == true
       || arduboy.pressed(RIGHT_BUTTON) == true
       || arduboy.pressed(LEFT_BUTTON) == true) {
    lock_free_frame_count ++;
  }

  if (lock_free_frame_count >= lock_free_frame_limit) {
    lock = false;
    lock_free = true;
  }

}

void do_count() {

  // up
  if ( arduboy.pressed(UP_BUTTON) == true && (!lock || lock_free)) {
    count += count_times ;
    lock = true;
    lock_free_frame_count ++;
  }

  // down
  if ( arduboy.pressed(DOWN_BUTTON) == true && (!lock || lock_free)) {
    count -= count_times ;
    lock = true;
  }

  // right
  if ( arduboy.pressed(RIGHT_BUTTON) == true && (!lock || lock_free)) {
    count_times *= 10 ;
    lock = true;
  }

  // left
  if ( arduboy.pressed(LEFT_BUTTON) == true && (!lock || lock_free)) {
    count_times *= 0.1 ;
    if (count_times == 0) {
      count_times = 1;
    }
    lock = true;
  }

  // func
  if ( (arduboy.pressed(A_BUTTON) == true || arduboy.pressed(B_BUTTON) == true) && (!lock || lock_free)) {
    count = 0;
    count_times = 1;
    lock = true;
  }

}

void check_lock_flag() {

  if ( arduboy.notPressed(A_BUTTON) == true
       && arduboy.notPressed(B_BUTTON) == true
       && arduboy.notPressed(UP_BUTTON) == true
       && arduboy.notPressed(DOWN_BUTTON) == true
       && arduboy.notPressed(RIGHT_BUTTON) == true
       && arduboy.notPressed(LEFT_BUTTON) == true) {
    lock = false;
    lock_free = false;
    lock_free_frame_count = 0;
  }
}


