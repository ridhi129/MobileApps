package com.example.ridhirustagi.cs478project4;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Worker thread handlers
    public Handler handlerPlayer1;
    public Handler handlerPlayer2;

    // handle messages
    public final static int SET_PLAYER_STATUS = 0;
    public final static int SET_MOVE_PLAYER1 = 5;
    public final static int SET_MOVE_PLAYER2 = 6;
    public final static int SET_PLAYER1_HANDLER = 3;
    public final static int SET_PLAYER2_HANDLER = 4;
    public final static int SET_GAME_STATUS = 7;

    // id of the two players
    public final static int PLAYER1 = 1;
    public final static int PLAYER2 = 2;

    // variable to keep track of the number of moves
     int moveCount;

    // UI elements to show the player moves and start game
    private TextView showPlayerStatus;
    private Button startButton;

     // creating thread objects for player1 and player2
    private Thread mPlayerThread1 = new Thread(new PlayerThread1());
    private Thread mPlayerThread2 = new Thread(new PlayerThread2());

  // the UI the tic tac toe game board
    private TextView[][] board = new TextView[3][3];

    //UI handler()
    private Handler uiHandler = new Handler() {
        public void handleMessage(Message msg) {
            int val = msg.what;
            switch (val) {

                // update the textview with the current player
                case SET_PLAYER_STATUS: {
                    showPlayerStatus.setText((String) msg.obj);
                    break;
                }

                // get the position from the player 1 thread and update the board
                case SET_MOVE_PLAYER1: {
                    int _row = (int) msg.arg1;
                    int _col = (int) msg.arg2;

                    String winner = updateBoard(PLAYER1,_row,_col);
                    // check and update winner if found
                   if (winner != "") {

                       displayWinner(winner);
                       Message msg3 = handlerPlayer1.obtainMessage(MainActivity.SET_GAME_STATUS);
                       handlerPlayer1.sendMessage(msg3);

                       Message msg4 = handlerPlayer2.obtainMessage(MainActivity.SET_GAME_STATUS);
                       handlerPlayer2.sendMessage(msg3);

                      moveCount = 9;
                   }


                       try {
                           Thread.sleep(3000);
                       } catch (InterruptedException e) {
                           System.out.println("Thread interrupted!");
                       }

                    // inform the second player thread to make a move
                       if (moveCount < 9) {
                           Message msg1 = handlerPlayer2.obtainMessage(SET_PLAYER2_HANDLER);
                           handlerPlayer2.sendMessage(msg1);
                       } else {
                           //playerTwoThread.interrupt();
                       }

                       break;

                }

                // get the position from the player 2 thread and update the board
                case SET_MOVE_PLAYER2: {
                    int _row = (int) msg.arg1;
                    int _col = (int) msg.arg2;

                    String winner = updateBoard(PLAYER2,_row,_col);
                    // check if winner is found and update
                    if (winner != "") {
                        displayWinner(winner);
                        // informing threads to stop when winner is found
                        Message msg3 = handlerPlayer1.obtainMessage(MainActivity.SET_GAME_STATUS);
                        handlerPlayer1.sendMessage(msg3);

                        Message msg4 = handlerPlayer2.obtainMessage(MainActivity.SET_GAME_STATUS);
                        handlerPlayer2.sendMessage(msg3);
                         moveCount = 9;
                    }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted!");
                        }
                        // iinform the first player thread to make a move
                        if (moveCount < 9) {
                            Message msg1 = handlerPlayer1.obtainMessage(SET_PLAYER1_HANDLER);
                            handlerPlayer1.sendMessage(msg1);

                        } else {
                            // playerOneThread.interrupt();
                        }

                        break;

                }

            }
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.start);
        showPlayerStatus = (TextView) findViewById(R.id.playerStatus);

        // oonClick to start the game
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // clear any values on the board set the moves to 0
                   setUp();

                // condition to check if button is clicked while the game is running
                if (mPlayerThread1.isAlive()) {

                    // deleting all messages from the handlers and starting the player 1 thread
                    uiHandler.removeCallbacksAndMessages(null);
                    handlerPlayer1.removeCallbacksAndMessages(null);
                    mPlayerThread1 = new Thread(new PlayerThread1());
                    mPlayerThread1.start();
                }
                // player 1 thread starts to start the game
                else {
                    mPlayerThread1 = new Thread(new PlayerThread1());
                    mPlayerThread1.start();
                }

                // condition to check if button is clicked while the game is running
                if (mPlayerThread2.isAlive()) {

                    //deleting all messages from the handlers and starting the player 2 thread
                    uiHandler.removeCallbacksAndMessages(null);
                    handlerPlayer2.removeCallbacksAndMessages(null);
                    mPlayerThread2 = new Thread(new PlayerThread2());
                    mPlayerThread2.start();
                } else {
                    // player 2 thread starts to start the game
                    mPlayerThread2 = new Thread(new PlayerThread2());
                    mPlayerThread2.start();
                }


            }
        });


    }

    // Worker thread (Player 1)
    public class PlayerThread1 implements Runnable {
        int r,c;
        String cell = "";

        public void run() {
            // preparing to loop messages in message queue
            Looper.prepare();

            // sending message to update the current player
            Message msg1 = uiHandler.obtainMessage(MainActivity.SET_PLAYER_STATUS);
            String val = "Player 1 is starting the game";
            msg1.obj = val;
            uiHandler.sendMessage(msg1);

            // sending message with the position of the move for the first player
            Message msg2 = uiHandler.obtainMessage(SET_MOVE_PLAYER1);
            cell = getRandomCell();
            r = Integer.parseInt(cell.split(",")[0]);
            c = Integer.parseInt(cell.split(",")[1]);
            msg2.arg1 = r;
            msg2.arg2 = c;
            uiHandler.sendMessage(msg2);


          // player 1 thread handler to handle the messages and update UI
            handlerPlayer1 = new Handler() {

                public void handleMessage(Message msg) {
                    int val = msg.what;
                    switch (val) {
                        case SET_PLAYER1_HANDLER: {

                            Message msg1 = uiHandler.obtainMessage(MainActivity.SET_PLAYER_STATUS);
                            String temp = "Player 1(X) is making a move now";
                            msg1.obj = temp;
                            uiHandler.sendMessage(msg1);

                            Message msg2 = uiHandler.obtainMessage(SET_MOVE_PLAYER1);

                            cell = getRandomCell();
                            r = Integer.parseInt(cell.split(",")[0]);
                            c = Integer.parseInt(cell.split(",")[1]);
                            msg2.arg1 = r;
                            msg2.arg2 = c;
                            uiHandler.sendMessage(msg2);
                            break;
                        }

                        case SET_GAME_STATUS:{
                            handlerPlayer1.getLooper().quit();
                            break;
                        }
                    }
                }
            };
             //to loop through the messages in queue
            Looper.loop();
        }
    }

    // Worker thread (Player 2)
    public class PlayerThread2 implements Runnable {

        String cell ="";
        int r,c;

        public void run() {

            // preparing to loop messages in message queue
            Looper.prepare();

            handlerPlayer2 = new Handler() {

                public void handleMessage(Message msg) {
                    int val = msg.what;
                    switch (val) {
                        case SET_PLAYER2_HANDLER: {
                           // sending message to update the current player
                            Message msg1 = uiHandler.obtainMessage(MainActivity.SET_PLAYER_STATUS);
                            String temp = "Player 2(O) is making a move now";
                            msg1.obj = temp;
                            uiHandler.sendMessage(msg1);

                            // sending message with the position of the move for the second player
                            Message msg2 = uiHandler.obtainMessage(SET_MOVE_PLAYER2);
                            cell = getRandomCell();
                            r = Integer.parseInt(cell.split(",")[0]);
                            c = Integer.parseInt(cell.split(",")[1]);
                            msg2.arg1 = r;
                            msg2.arg2 = c;
                            uiHandler.sendMessage(msg2);
                            break;

                        }

                        case SET_GAME_STATUS:{
                            handlerPlayer2.getLooper().quit();
                            break;
                        }

                    }
                }
            };

            Looper.loop();

        }

    }

   // initial board setup
    public void setUp() {

        moveCount = 0;

        board[0][0] = (TextView) findViewById(R.id.textView2);
        board[0][1] = (TextView) findViewById(R.id.textView3);
        board[0][2] = (TextView) findViewById(R.id.textView4);
        board[1][0] = (TextView) findViewById(R.id.textView6);
        board[1][1] = (TextView) findViewById(R.id.textView7);
        board[1][2] = (TextView) findViewById(R.id.textView8);
        board[2][0] = (TextView) findViewById(R.id.textView9);
        board[2][1] = (TextView) findViewById(R.id.textView10);
        board[2][2] = (TextView) findViewById(R.id.textView11);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                board[i][j].setText("");
            }
        }

        showPlayerStatus.setText("");

    }

    //method to get index for a move
    public String getRandomCell(){
     String val = "";
        int row;
        int col;
        Random random = new Random();
    while(true) {

    row = random.nextInt(3 - 0) + 0;
    col = random.nextInt(3 - 0) + 0;

    if (board[row][col].getText() == "") {
        break;
    }
}
        String cell = row + "," + col;

        return cell;

    }

    //method to update the board with X or O for a given player and board position
    public String updateBoard(int player, int row, int col) {

        String result = "";

                if (player == 1) {

                    board[row][col].setText("X");

                    moveCount = moveCount +1;

                    // checking if player 1 has won
                    //check col
                    for(int j = 0;j< 3;j++){
                        if(board[row][j].getText() != "X"){
                            break;

                        }
                        if(j==2){
                            result = "one";
                        }

                    }

                    //check row
                    for(int k = 0;k < 3;k++){

                        if(board[k][col].getText() != "X"){
                            break;
                        }

                        if(k==2){
                            result = "one";
                        }

                    }

                    //check diag
                    if(row == col){
                        //we are in a diag
                        for(int l = 0;l<3;l++){
                            if(board[l][l].getText() != "X"){
                                break;
                            }
                            if(l == 2){
                                result = "one";
                            }
                        }
                    }

                    // check another diag
                    if(row + col == 2){
                        for(int m = 0; m < 3; m++){
                            if(board[m][2-m].getText() != "X"){
                                break;
                            }
                            if(m == 2){
                                result = "one";
                            }
                        }
                    }

                    if(moveCount == 9 && result == ""){
                        result = "draw";

                    }

                }
                else{

                    board[row][col].setText("O");

                    moveCount = moveCount +1;

                    // check if player 2 has won
                    for(int j = 0;j< 3;j++){
                        if(board[row][j].getText() != "O"){
                            break;

                        }
                        if(j==2){
                            result = "two";
                        }

                    }

                    //check row

                    for(int k = 0;k < 3;k++){

                        if(board[k][col].getText() != "O"){
                            break;
                        }

                        if(k==2){
                            result = "two";
                        }

                    }

                    //check diag

                    if(row == col){
                        //we are in a diag
                        for(int l = 0;l<3;l++){
                            if(board[l][l].getText() != "O"){
                                break;
                            }
                            if(l == 2){
                                result = "two";
                            }
                        }
                    }

                    // check another diag
                    if(row + col == 2){
                        for(int m = 0; m < 3; m++){
                            if(board[m][2-m].getText() != "O"){
                                break;
                            }
                            if(m == 2){
                                result = "two";
                            }
                        }
                    }
                    // check if the game has drawn
                    if(moveCount == 9 && result == ""){
                        result = "draw";

                    }

                }

        return result;
    }

 // method to return game winner
    public void displayWinner(String s){
        if(s == "one"){
            showPlayerStatus.setText("Player 1 won!!");
        }
        else if(s == "two"){
            showPlayerStatus.setText("Player 2 won!!");
        }
        else if(s == "draw"){

            showPlayerStatus.setText("Game draw!!");

        }


    }


    }








