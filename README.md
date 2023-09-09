# Simple-Game-Simon-Race

## Table of contents
- Introduction
- Requirements
- Tests

## Introduction
The game is called Simon’s Race, it is a made up game.
Simon’s Race is a board-based game which has a start line and a finish line, with a predefined number of cells in between.
The goal is easy: the player to get from the start to the end first is the winner.
However, there are some obstructions that make this a little more complicated!

## How to start the game
Run the UserLogin.java file to open it. Input the name of player1 and player2. Click the 'Start' button to start the game.
Roll dices for squares and directions, then click the 'Move' button to make each player move.
When meeting obstruction on the board, it will pop up a dialog box to remind you or let you do some choices.
The game finish when a player gets to the end first.
The 'Score Board' button shows on the UserLogin board or when one player wins the game.
Click the' Score Board' button will jump to Score Board and it shows the top 10 players sorted by the num of wins.

## Tests
There are four tests in the GameTest.java file.
TestDice method: test functions of rolling dices in the DiceGame.java file.
TestGame method: test the logic of players' movement and meet obstructions in the GameBoard.java file.
TestPlayerList method: test the functions of storing players' data (serialised object) in a file in PlayerInfo.java file.
TestScoreBoard method: test the functions of a scoring mechanism in ScoreBoard.java.file.
