package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic;



public class Coin extends BoardObject {

    private final int SCORE_BONUS = 3;

    private boolean isDisabled = true;
    @Override
    int getStartingPositionRow() {

        return 0;
    }

    @Override
    int getStartingPositionColumn() {
        return 0;
    }

    @Override
    BoardObjectType getType() {
        return BoardObjectType.COIN;
    }

    public boolean isDisabled() {
        return isDisabled;
    }


    public void enable() {
        isDisabled = false;
    }

    public void disable() {
        isDisabled = true;
    }

    public int getScoreBonus() {
        return SCORE_BONUS;
    }
}
