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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import de.hsa.inf.ml.tictactoe.player.Player;

/**
 * This class represents a tic tac toe game board.
 */
public class Board {
    /**
     * Computes the score of a player.
     *
     * @param player Specifies the player
     * @return The current score of the specified player
     */
    int score(Player player) {
        return IntStream.range(0, state.size())
            .map(i -> state.get(i) == player.getId() ? (int) Math.pow(2, i) : 0)
            .reduce(0, (a, b) -> a + b);
    }

    /**
     * Retrieves the value at the specified position/field index.
     *
     * @param field Specifies the position/field index of interest
     * @return The value at the position/field index
     */
    public int value(int field) {
        if (field < 0 || field > state.size() - 1) {
            throw new IllegalArgumentException();
        }
        return state.get(field);
    }

    /**
     * Checks whether the board's state is a winning state or not.
     *
     * @param score Specifies the board's state as a score value
     * @return True, if the score is a winning score. False, otherwise
     */
    boolean isWinningState(int score) {
        return winningScores.stream()
            .anyMatch(winningScore -> (winningScore & score) == winningScore);
    }

    /**
     * Checks whether the board's state is a tie or not.
     *
     * @return True, if the board's state is a tie. False, otherwise
     */
    boolean isDrawState() {
        return !state.contains(0);
    }

    /**
     * Checks whether the specified move is valid or not.
     *
     * @param move Specifies the move as a position/field index
     * @return True, if the move is valid. False, otherwise
     */
    boolean isValidMove(int move) {
        return move >= 0 && move <= state.size() - 1 && state.get(move) == 0;
    }

    /**
     * Retrieves the board's size of each dimension.
     *
     * @return The board's size of each dimension
     */
    public int getSize() {
        return size;
    }

    /**
     * Initializes the board.
     *
     * @param size Specifies the board's size of each dimension
     * @return The board
     */
    Board setSize(int size) {
        if (size < 3 || size > 5) {
            throw new IllegalArgumentException();
        }

        this.size = size;
        state = new ArrayList<>(size * size);
        for (int i = 0; i < size * size; i++) {
            state.add(0);
        }
        winningScores = new ArrayList<>(2 * size + 2);

        ClassLoader classLoader = getClass().getClassLoader();
        String name = String.format(Locale.US, "scores/%dx%d", size, size);
        URI uri = null;
        try {
            uri = classLoader.getResource(name).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try (Stream<String> stream = Files.lines(Paths.get(uri))) {
            stream.forEach(line -> winningScores.add(Integer.valueOf(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Retrieves the board's state as a list of positions/field indexes.
     *
     * @return The board's state
     */
    List<Integer> getState() {
        return state;
    }

    /**
     * Computes the board's state string representation.
     *
     * @return The board's state string representation
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2 * size + 1; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\n|");
        for (int i = 0; i < state.size(); i++) {
            stringBuilder.append(state.get(i)).append("|");
            if (i % size == size - 1 && i < state.size() - 1) {
                stringBuilder.append("\n|");
            }
        }
        stringBuilder.append("\n");
        for (int i = 0; i < 2 * size + 1; i++) {
            stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }

    /** Specifies the board's size of each dimension */
    private int size = 0;

    /** Specifies the board's state as a list of positions/field indexes */
    private List<Integer> state = null;

    /** Specifies all final scores for winning a game */
    private List<Integer> winningScores = null;
}
