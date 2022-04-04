package com.example.class22b_and_homeexe_vadim_lazarevich_317785053;

public class GameManager {

    private final int BOARD_ROWS = 5;
    private final int BOARD_COLUMNS = 3;

    private int score = 0;
    private int life = 3;


    private BoardObject player;
    private BoardObject enemy;

    public GameManager() {
        player = new Player();
        enemy = new Enemy();

    }

    public int getLife() {
        return life;
    }

    public void reduceLife() {
        life--;
    }

    public int getScore() {
        return score;
    }

    public MoveDirection getPlayerCurrentDirection() {
        return player.getCurrentDirection();
    }

    public MoveDirection getEnemyCurrentDirection() {
        return enemy.getCurrentDirection();
    }

    public int getBoardRows() {
        return BOARD_ROWS;
    }

    public int getBoardColumns() {
        return BOARD_COLUMNS;
    }


    public int getPlayerPositionRow() {
        return player.getCurrentPositionRow();
    }


    public int getPlayerPositionColumn() {
        return player.getCurrentPositionColumn();
    }

    public int getPlayerEnemyLastCollisionRow() {
        return player.getLastCollisionRowPosition();
    }



    public int getPlayerEnemyLastCollisionColumn() {
        return player.getLastCollisionColumnPosition();
    }



    private void updateCharacterPosition(BoardObject boardObject) {
        switch (boardObject.getCurrentDirection()) {

            case UP:
                if (boardObject.getCurrentPositionRow() - 1 >= 0) {
                    boardObject.setCurrentPositionRow(boardObject.getCurrentPositionRow() - 1);
                }
                break;

            case DOWN:
                if (boardObject.getCurrentPositionRow() + 1 < BOARD_ROWS) {
                    boardObject.setCurrentPositionRow(boardObject.getCurrentPositionRow() + 1);
                }
                break;

            case LEFT:
                if (boardObject.getCurrentPositionColumn() - 1 >= 0) {
                    boardObject.setCurrentPositionColumn(boardObject.getCurrentPositionColumn() - 1);
                }
                break;

            case RIGHT:
                if (boardObject.getCurrentPositionColumn() + 1 < BOARD_COLUMNS) {
                    boardObject.setCurrentPositionColumn(boardObject.getCurrentPositionColumn() + 1);
                }
                break;

            case NONE:
                break;
        }
    }


    public void updateScore() {
        score++;
    }

    public void updatePlayerDirection(MoveDirection direction) {
        player.setCurrentDirection(direction);
    }


    public void updatePlayerPosition() {
        updateCharacterPosition(player);
    }

    public void updateEnemyPosition() {
        updateCharacterPosition(enemy);
    }

    public int getEnemyPositionRow() {
        return enemy.getCurrentPositionRow();
    }

    public int getEnemyPositionColumn() {
        return enemy.getCurrentPositionColumn();
    }

    public void randomNextEnemyDirection() {
        ((Enemy)enemy).randomNextDirection();
    }


    public boolean isCharactersCollide() {
        return isTwoBoardObjectsCollide(player,enemy);
    }

    public boolean isTwoBoardObjectsCollide(BoardObject boardObject1, BoardObject boardObject2){
        if (boardObject1.getCurrentPositionRow() == boardObject2.getCurrentPositionRow()
                && boardObject1.getCurrentPositionColumn() == boardObject2.getCurrentPositionColumn()) {
            boardObject1.setLastCollisionPosition();
            boardObject2.setLastCollisionPosition();
            return true;
        }
        return false;
    }




    public void changePositionsToStarting() {
        player.setToStartingPosition();
        enemy.setToStartingPosition();
    }

    public boolean isGameOver() {
        return life <= 0;
    }


}
