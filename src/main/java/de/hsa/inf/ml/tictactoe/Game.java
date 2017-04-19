/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-present Lars Schütz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.hsa.inf.ml.tictactoe;

import de.hsa.inf.ml.tictactoe.player.Player;

/**
 * This class represents a tic tac toe game.
 */
public class Game {
    /**
     * Runs the game turn by turn until its end.
     *
     * @param printMove Prints the selected move to the standard output, if true
     * @param printBoard Prints every state to the standard output, if true
     * @return The player that has won the game. Null, if the game ended in a
     * tie
     */
    Player play(
        boolean printMove,
        boolean printBoard
    ) {
        Player winningPlayer;
        while (true) {
            winningPlayer = playerTurn(player1, player2, printMove, printBoard);
            if (winningPlayer != null) {
                return winningPlayer;
            }

            if (board.isDrawState()) {
                return null;
            }

            winningPlayer = playerTurn(player2, player1, printMove, printBoard);
            if (winningPlayer != null) {
                return winningPlayer;
            }

            if (board.isDrawState()) {
                return null;
            }
        }
    }

    /**
     * Retrieves the game's board.
     *
     * @return The board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Assigns a board to the game.
     *
     * @param board Specifies the board
     * @return The game
     */
    Game setBoard(Board board) {
        this.board = board;
        return this;
    }

    /**
     * Retrieves the first player.
     *
     * @return The first player
     */
    Player getPlayer1() {
        return player1;
    }

    /**
     * Assigns a player as the first player to the game.
     *
     * @param player Specifies the first player
     * @return The game
     */
    Game setPlayer1(Player player) {
        this.player1 = player;
        return this;
    }

    /**
     * Retrieves the second player.
     *
     * @return The second player
     */
    Player getPlayer2() {
        return player2;
    }

    /**
     * Assigns a player as the second player to the game.
     *
     * @param player Specifies the second layer
     * @return The game
     */
    Game setPlayer2(Player player) {
        this.player2 = player;
        return this;
    }

    /**
     * Computes and checks one turn for one player.
     *
     * @param player Specifies the active player
     * @param printMove Prints the selected move to the standard output if true
     * @param printBoard Prints every state to the standard output if true
     * @return Null, if player has not won yet. Otherwise, the winning player
     */
    private Player playerTurn(
        Player player,
        Player opponent,
        boolean printMove,
        boolean printBoard
    ) {
        int move = player.move(board);
        if (printMove) {
            System.out.println("Move of " + player + ": " + move);
        }
        if (!board.isValidMove(move)) {
            return opponent;
        }
        board.getState().set(move, player.getId());
        if (printBoard) {
            System.out.println(board);
        }
        int playerScore = board.score(player);
        if (board.isWinningState(playerScore)) {
            return player;
        }
        return null;
    }

    /** Specifies the game's board */
    private Board board = null;

    /** Specifies the first player */
    private Player player1 = null;

    /** Specifies the second player */
    private Player player2 = null;
}
