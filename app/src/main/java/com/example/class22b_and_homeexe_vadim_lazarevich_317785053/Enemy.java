package com.example.class22b_and_homeexe_vadim_lazarevich_317785053;

import java.util.Random;

public class Enemy extends BoardObject {

    private final int ENEMY_STARTING_POSITION_ROW = 0;
    private final int ENEMY_STARTING_POSITION_COLUMN = 1;


    public Enemy(){
      setToStartingPosition();

    }

    @Override
    int getStartingPositionRow() {
        return ENEMY_STARTING_POSITION_ROW;
    }

    @Override
    int getStartingPositionColumn() {
        return ENEMY_STARTING_POSITION_COLUMN;
    }




    public void randomNextDirection(){

        switch (new Random().nextInt(MoveDirection.values().length-1)){
            case 0:
                setCurrentDirection(MoveDirection.UP);
                break;
            case 1:
                setCurrentDirection(MoveDirection.RIGHT);
                break;

            case 2:
                setCurrentDirection(MoveDirection.LEFT);
                break;
            case 3:
                setCurrentDirection(MoveDirection.DOWN);
                break;

        }

    }
}
