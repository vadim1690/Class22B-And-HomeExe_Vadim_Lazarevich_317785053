package com.example.class22b_and_homeexe_vadim_lazarevich_317785053.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic.BoardObjectType;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.callbacks.CallBack_TimerUpdate;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic.GameManager;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic.MoveDirection;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.R;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.utilities.SoundEffectManager;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.utilities.TimerManager;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.data.Record;
import com.example.class22b_and_homeexe_vadim_lazarevich_317785053.logic.TimerStatus;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class Activity_Game extends AppCompatActivity {

    private ImageView[][] game_IMG_board;
    private ImageView[] game_IMG_hearts;
    private MaterialTextView game_LBL_score;

    private GameManager gameManager;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorEventListener accelerometerSensorEventListener;

    private TimerManager timerManager;
    private SoundEffectManager soundEffectManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameManager = new GameManager();
        findViews();
        getRecord();
        initializeSensors();
        initializeTimer();
        initializeSoundEffectManager();

        startGame();
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        setAccelerometerSensorListener();
    }

    /**
     * get the record details from bundle that sent from Activity details
     */
    private void getRecord() {
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString(getString(R.string.BUNDLE_RECORD_KEY));
        gameManager.setCurrentRecord(new Gson().fromJson(json,Record.class));
    }


    private void initializeSoundEffectManager() {
        soundEffectManager = new SoundEffectManager(this);
    }

    private void initializeTimer() {
        CallBack_TimerUpdate callBack_Timer_update = () -> runOnUiThread(this::update);
        timerManager = new TimerManager(callBack_Timer_update);

    }


    /**
     * Setting the Sensors Listeners for player direction
     */
    private void setAccelerometerSensorListener() {
        accelerometerSensorEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                changePlayerDirectionBySensorValue(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private void changePlayerDirectionBySensorValue(float x, float y) {
        int thresholdDown = 7;
        int thresholdUp = -4;
        int thresholdRight = -6;
        int thresholdLeft = 6;
        if (y > thresholdDown && x >= thresholdRight && x <= thresholdLeft) playerDirectionChange(MoveDirection.DOWN);
        else if (y < thresholdUp && x >= thresholdRight && x <= thresholdLeft) playerDirectionChange(MoveDirection.UP);

        if (x > thresholdLeft && y >= thresholdUp && y <= thresholdDown) playerDirectionChange(MoveDirection.LEFT);
        else if (x < thresholdRight && y >= thresholdUp && y <= thresholdDown) playerDirectionChange(MoveDirection.RIGHT);
    }

    /**
     * sets the next move direction for the player to move in next update.
     * and updates his picture direction in UI
     *
     * @param direction the Move direction for the player to move
     */
    private void playerDirectionChange(MoveDirection direction) {
        gameManager.updateBoardObjectDirection(BoardObjectType.PLAYER, direction);
        updatePlayerPictureInUI(gameManager.getBoardObjectPositionRow(BoardObjectType.PLAYER), gameManager.getBoardObjectPositionColumn(BoardObjectType.PLAYER));
    }

    /**
     * initialize the UI components
     */
    private void findViews() {


        game_LBL_score = findViewById(R.id.game_LBL_score);

        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3),
        };


        game_IMG_board = new ImageView[gameManager.getBoardRows()][gameManager.getBoardColumns()];

        for (int i = 0; i < gameManager.getBoardRows(); i++) {
            for (int j = 0; j < gameManager.getBoardColumns(); j++) {
                String ImgFileName = "game_IMG_board_" + i + j;
                int imgId = this.getResources().getIdentifier(ImgFileName, "id", this.getPackageName());
                game_IMG_board[i][j] = findViewById(imgId);
            }
        }


    }

    /**
     * game update that happens every second, does the things that should happen every second
     * check if the characters collide and update game data ana UI.
     */
    private void update() {
        updateGame();
        if (gameManager.isTwoBoardObjectsCollide(BoardObjectType.PLAYER, BoardObjectType.COIN)) {
            coinCollision();
        }
        if (gameManager.isTwoBoardObjectsCollide(BoardObjectType.PLAYER, BoardObjectType.ENEMY)) {
            charactersCollision();
        } else {
            updateUI();
            changeEnemyRandomDirection();
        }

    }

    /**
     *  the operations to do when coin is picked.
     */
    private void coinCollision() {
        soundEffectManager.playCoinSound();
        gameManager.pickedCoin();

    }

    /**
     * the operations to do when two characters collide.
     * stop the timer, reduce the life in data and UI and check if the game is over
     */
    private void charactersCollision() {
        timerManager.stopTimer();
        soundEffectManager.playCollisionSound();
        gameManager.reduceLife();
        reduceLifeUI();
        updateCharactersCollisionInUI();
        if (gameManager.isGameOver()) {
            showEndGameAlert();
        } else {
            showCollisionAlert();
            startGame();
        }


    }

    /**
     * Update characters Collision clears the board and sets collision image
     */
    private void updateCharactersCollisionInUI() {
        clearBoard();
        setPlayerEnemyCollisionImage();
    }


    /**
     * the operations to do when the game is over
     */
    private void endGame() {

        saveGameData();
        openLeaderboards();
        finish();

    }

    /**
     * Open the leaderboards activity
     */
    private void openLeaderboards() {
        Intent intent = new Intent (this, Activity_Leaderboards.class);
        Bundle bundle = this.getIntent().getExtras();
        bundle.putBoolean(getString(R.string.BUNDLE_KEY_FROM_GAME),true);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Save the game data
     */
    private void saveGameData() {
        gameManager.saveData();
    }

    /**
     * shows the end game alert that pops to the user.
     */
    private void showEndGameAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.GAME_OVER_MESSAGE))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.CONFIRMATION_BUTTON_TEXT), (dialog, id) -> endGame());
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * starting operations to start the game - happens in the beginning of a game or after a collision
     * between characters.
     * set the characters to their starting positions, and starts the timer
     */
    private void startGame() {

        gameManager.changePositionsToStarting();
        playerDirectionChange(MoveDirection.NONE);
        changeEnemyRandomDirection();
        timerManager.startTimer();
    }

    /**
     * reduces the life in the UI
     */
    private void reduceLifeUI() {
        game_IMG_hearts[gameManager.getLife()].setVisibility(View.INVISIBLE);
    }

    /**
     * sets the collision image the place the collision happened
     */
    private void setPlayerEnemyCollisionImage() {
        game_IMG_board[gameManager.getPlayerEnemyLastCollisionRow()][gameManager.getPlayerEnemyLastCollisionColumn()].setImageResource(R.drawable.ic_explosion);

    }

    /**
     * Shows an alert after a collision has happened
     */
    private void showCollisionAlert() {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), "oops, You have " + gameManager.getLife() + " lives left! ", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    /**
     * Changes the enemy direction randomly.
     */
    private void changeEnemyRandomDirection() {
        gameManager.randomNextEnemyDirection();
        updateEnemyPictureInUI(gameManager.getBoardObjectPositionRow(BoardObjectType.ENEMY), gameManager.getBoardObjectPositionColumn(BoardObjectType.ENEMY));
    }

    /**
     * Updates every second the data in game manager.
     */
    private void updateGame() {
        gameManager.updateScore();
        gameManager.updateCharacterPosition(BoardObjectType.PLAYER);
        gameManager.updateCharacterPosition(BoardObjectType.ENEMY);
        gameManager.placeCoin();

    }

    /**
     * Updates every second the components in UI.
     */
    private void updateUI() {

        clearBoard();
        updatePlayerPictureInUI(gameManager.getBoardObjectPositionRow(BoardObjectType.PLAYER), gameManager.getBoardObjectPositionColumn(BoardObjectType.PLAYER));
        updateEnemyPictureInUI(gameManager.getBoardObjectPositionRow(BoardObjectType.ENEMY), gameManager.getBoardObjectPositionColumn(BoardObjectType.ENEMY));
        if (!gameManager.isCoinDisabled()) {
            updateCoinPictureInUI();
        }
        updateScoreInUI();


    }

    /**
     * set the coin picture in UI
     */
    private void updateCoinPictureInUI() {
        game_IMG_board[gameManager.getBoardObjectPositionRow(BoardObjectType.COIN)][gameManager.getBoardObjectPositionColumn(BoardObjectType.COIN)].setImageResource(R.drawable.ic_coin);
    }

    /**
     * clear all the game board
     */
    private void clearBoard() {

        for (ImageView[] imageViews : game_IMG_board) {
            for (ImageView imageView : imageViews) {
                imageView.setImageResource(0);
            }
        }
    }


    /**
     * Updates Enemy picture in UI.
     *
     * @param currentRow    the Enemy current row position after update
     * @param currentColumn the Enemy current column position after update
     */
    private void updateEnemyPictureInUI(int currentRow, int currentColumn) {
        String ImgFileName = "ic_monster_" + gameManager.getBoardObjectCurrentDirection(BoardObjectType.ENEMY).name().toLowerCase();
        int imgId = this.getResources().getIdentifier(ImgFileName, "drawable", this.getPackageName());
        game_IMG_board[currentRow][currentColumn].setImageResource(imgId);
    }

    /**
     * Updates Player picture in UI.
     *
     * @param currentRow    the Player current row position after update
     * @param currentColumn the Player current column position after update
     */
    private void updatePlayerPictureInUI(int currentRow, int currentColumn) {
        String ImgFileName = "ic_hero_" + gameManager.getBoardObjectCurrentDirection(BoardObjectType.PLAYER).name().toLowerCase();
        int imgId = this.getResources().getIdentifier(ImgFileName, "drawable", this.getPackageName());
        game_IMG_board[currentRow][currentColumn].setImageResource(imgId);
    }

    /**
     * updates the Score in UI
     */
    private void updateScoreInUI() {
        game_LBL_score.setText(String.valueOf(gameManager.getScore()));
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (timerManager.getTimerStatus() == TimerStatus.RUNNING) {
            timerManager.stopTimer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerManager.getTimerStatus() == TimerStatus.PAUSE) {
            timerManager.startTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerometerSensorEventListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
