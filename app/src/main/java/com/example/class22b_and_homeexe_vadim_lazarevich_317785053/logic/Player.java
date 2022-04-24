package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic;

public class Player extends BoardObject {

    private final int PLAYER_STARTING_POSITION_ROW = 6;
    private final int PLAYER_STARTING_POSITION_COLUMN = 2;

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

    @Override
    BoardObjectType getType() {
        return BoardObjectType.PLAYER;
    }


}
