package com.example.class22b_and_homeexe_vadim_lazarevich_317785053;
abstract class BoardObject {


    private int currentPositionRow;
    private int currentPositionColumn;
    private int lastCollisionRowPosition;
    private int lastCollisionColumnPosition;
    private MoveDirection currentDirection;

    abstract int getStartingPositionRow();

    abstract int getStartingPositionColumn();

    public void setToStartingPosition(){
        setCurrentPositionRow(getStartingPositionRow());
        setCurrentPositionColumn(getStartingPositionColumn());
        setCurrentDirection(MoveDirection.NONE);
    }

    public int getCurrentPositionRow() {
        return currentPositionRow;
    }

    public int getCurrentPositionColumn() {
        return currentPositionColumn;
    }

    public MoveDirection getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentPositionRow(int currentPositionRow) {
        this.currentPositionRow = currentPositionRow;

    }

    public void setCurrentPositionColumn(int currentPositionColumn) {
        this.currentPositionColumn = currentPositionColumn;

    }

    public void setCurrentDirection(MoveDirection currentDirection) {
        this.currentDirection = currentDirection;

    }

    public void setLastCollisionPosition() {
        this.lastCollisionColumnPosition = this.currentPositionColumn;
        this.lastCollisionRowPosition = this.currentPositionRow;
    }

    public int getLastCollisionRowPosition() {
        return lastCollisionRowPosition;
    }

    public int getLastCollisionColumnPosition() {
        return lastCollisionColumnPosition;
    }
}
