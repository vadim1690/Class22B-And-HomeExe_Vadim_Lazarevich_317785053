package com.example.class22b_and_homeexe_vadim_lazarevich_317785053;

public class Player extends BoardObject {

    private final int PLAYER_STARTING_POSITION_ROW = 4;
    private final int PLAYER_STARTING_POSITION_COLUMN = 1;

    public Player() {
      setToStartingPosition();
    }

    @Override
    int getStartingPositionRow() {
        return PLAYER_STARTING_POSITION_ROW;
    }

    @Override
    int getStartingPositionColumn() {
        return PLAYER_STARTING_POSITION_COLUMN;
    }


}
