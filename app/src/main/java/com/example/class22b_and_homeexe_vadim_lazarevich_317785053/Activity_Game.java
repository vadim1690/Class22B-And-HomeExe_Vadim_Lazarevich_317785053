package com.example.class22b_and_homeexe_vadim_lazarevich_317785053;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

public class Activity_Game extends AppCompatActivity {

    private ImageView[][] game_IMG_board;
    private ImageView[] game_IMG_hearts;
    private ImageView game_IMG_upArrow;
    private ImageView game_IMG_rightArrow;
    private ImageView game_IMG_leftArrow;
    private ImageView game_IMG_downArrow;
    private MaterialTextView game_LBL_score;

    private GameManager gameManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameManager = new GameManager();
        findViews();
        setClickListeners();

        startGame();
    }


    /**
     * Setting the OnClickListeners for player direction press
     */
    private void setClickListeners() {
        game_IMG_upArrow.setOnClickListener(view -> playerDirectionChange(MoveDirection.UP));
        game_IMG_downArrow.setOnClickListener(view -> playerDirectionChange(MoveDirection.DOWN));
        game_IMG_rightArrow.setOnClickListener(view -> playerDirectionChange(MoveDirection.RIGHT));
        game_IMG_leftArrow.setOnClickListener(view -> playerDirectionChange(MoveDirection.LEFT));

    }

    /**
     * sets the next move direction for the player to move in next update.
     * and updates his picture direction in UI
     * @param direction the Move direction for the player to move
     */
    private void playerDirectionChange(MoveDirection direction) {
        gameManager.updatePlayerDirection(direction);
        updatePlayerPictureInUI(gameManager.getPlayerPositionRow(), gameManager.getPlayerPositionColumn());
    }

    /**
     * initialize the UI components
     */
    private void findViews() {

        game_IMG_upArrow = findViewById(R.id.game_IMG_upArrow);
        game_IMG_rightArrow = findViewById(R.id.game_IMG_rightArrow);
        game_IMG_leftArrow = findViewById(R.id.game_IMG_leftArrow);
        game_IMG_downArrow = findViewById(R.id.game_IMG_downArrow);

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
        if (gameManager.isCharactersCollide()) {
            charactersCollision();
        } else {
            updateUI();
            changeEnemyRandomDirection();
        }

    }

    /**
     * the operations to do when two characters collide.
     * stop the timer, reduce the life in data and UI and check if the game is over
     */
    private void charactersCollision() {
        stopTimer();
        gameManager.reduceLife();
        reduceLifeUI();
        updateCharactersCollisionInUI();
        if (gameManager.isGameOver()) {
            endGame();
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
        // do other things, save score etc..
        showEndGameAlert();
    }

    /**
     * shows the end game alert that pops to the user.
     */
    private void showEndGameAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GAME OVER !")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
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
        startTimer();
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
        updateEnemyPictureInUI(gameManager.getEnemyPositionRow(), gameManager.getEnemyPositionColumn());
    }

    /**
     * Updates every second the data in game manager.
     */
    private void updateGame() {
        gameManager.updateScore();
        gameManager.updatePlayerPosition();
        gameManager.updateEnemyPosition();

    }

    /**
     * Updates every second the components in UI.
     */
    private void updateUI() {

        clearBoard();
        updatePlayerPictureInUI(gameManager.getPlayerPositionRow(), gameManager.getPlayerPositionColumn());
        updateEnemyPictureInUI(gameManager.getEnemyPositionRow(), gameManager.getEnemyPositionColumn());
        updateScoreInUI();


    }

    private void clearBoard() {

        for (int i = 0; i < game_IMG_board.length; i++) {
            for (int j = 0; j < game_IMG_board[i].length; j++) {
                game_IMG_board[i][j].setImageResource(0);
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
        String ImgFileName = "ic_monster_" + gameManager.getEnemyCurrentDirection().name().toLowerCase();
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
        String ImgFileName = "ic_hero_" + gameManager.getPlayerCurrentDirection().name().toLowerCase();
        int imgId = this.getResources().getIdentifier(ImgFileName, "drawable", this.getPackageName());
        game_IMG_board[currentRow][currentColumn].setImageResource(imgId);
    }

    /**
     * updates the Score in UI
     */
    private void updateScoreInUI() {
        game_LBL_score.setText("" + gameManager.getScore());
    }


    // ----------- ----------- -----------TIMER ----------- ----------- -----------

    private final int DELAY = 1000;

    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }

    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;


    private final Handler handler = new Handler();
    private Runnable r = new Runnable() {
        public void run() {
            handler.postDelayed(r, DELAY);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    update();
                }
            });

        }
    };

    private void startTimer() {
        timerStatus = TIMER_STATUS.RUNNING;
        handler.postDelayed(r, DELAY);
    }

    private void stopTimer() {
        timerStatus = TIMER_STATUS.PAUSE;
        handler.removeCallbacks(r);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerStatus == TIMER_STATUS.PAUSE) {
            startTimer();
        }
    }


}
