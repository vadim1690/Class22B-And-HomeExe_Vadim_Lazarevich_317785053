package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.data.Record;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.data.RecordsDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GameManager {

    private final int BOARD_ROWS = 7;
    private final int BOARD_COLUMNS = 5;

    private int score = 0;
    private int life = 3;

    private Record currentRecord;


    private final Player player;
    private final Enemy enemy;
    private final Coin coin;
    private RecordsDao recordsDao;

    public GameManager() {
        player = new Player();
        enemy = new Enemy();
        coin = new Coin();
        recordsDao = new RecordsDao();

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

    public MoveDirection getBoardObjectCurrentDirection(BoardObjectType type) {
        return getBoardObjectByType(type).getCurrentDirection();
    }

    private BoardObject getBoardObjectByType(BoardObjectType type) {

        switch (type) {
            case PLAYER:
                return player;

            case ENEMY:
                return enemy;

            case COIN:
                return coin;

            default:
                throw new RuntimeException("Unexpected Board Object Type ");
        }

    }

    public int getBoardRows() {
        return BOARD_ROWS;
    }

    public int getBoardColumns() {
        return BOARD_COLUMNS;
    }


    public int getBoardObjectPositionRow(BoardObjectType type) {
        return getBoardObjectByType(type).getCurrentPositionRow();
    }


    public int getBoardObjectPositionColumn(BoardObjectType type) {
        return getBoardObjectByType(type).getCurrentPositionColumn();
    }

    public int getPlayerEnemyLastCollisionRow() {
        return player.getLastCollisionRowPosition();
    }


    public int getPlayerEnemyLastCollisionColumn() {
        return player.getLastCollisionColumnPosition();
    }


    public void updateCharacterPosition(BoardObjectType type) {
        BoardObject boardObject = getBoardObjectByType(type);
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

    public void updateBoardObjectDirection(BoardObjectType type,MoveDirection direction) {
        getBoardObjectByType(type).setCurrentDirection(direction);
    }


    public void randomNextEnemyDirection() {
        enemy.randomNextDirection();
    }


    public boolean isTwoBoardObjectsCollide(BoardObjectType type1 , BoardObjectType type2 ) {
        BoardObject boardObject1 = getBoardObjectByType(type1);
        BoardObject boardObject2 = getBoardObjectByType(type2);
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


    public void placeCoin() {
        if (score % 10 == 0) {
            replaceCoinPosition();
            if (coin.isDisabled()) {
                coin.enable();
            }
        }
    }

    private void replaceCoinPosition() {
        Random random = new Random();
        int nextRow = random.nextInt(BOARD_ROWS - 1);
        int nextColumn = random.nextInt(BOARD_COLUMNS - 1);
        coin.setCurrentPositionColumn(nextColumn);
        coin.setCurrentPositionRow(nextRow);
        coin.setCurrentDirection(MoveDirection.NONE);


    }

    public boolean isCoinDisabled() {
        return coin.isDisabled();
    }

    public void pickedCoin() {
        score += coin.getScoreBonus();
        coin.disable();
    }



    public void setCurrentRecord(Record currentRecord) {
        this.currentRecord = currentRecord;

    }

    public void saveData() {

        currentRecord.setScore(score);
        currentRecord.setTime(getCurrentTime());

        // Read all records
        List<Record> records = recordsDao.readRecords();
        // Add the new record
        records.add(currentRecord);
        // Save all records
        recordsDao.saveRecords(records);
    }


    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(new Date());
    }
}
