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
package de.hsa.inf.ml.tictactoe.player;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.hsa.inf.ml.tictactoe.Board;

/**
 * This class represents a player that randomly chooses a free position.
 */
public class RandomPlayer extends Player {
    @Override
    public int move(Board board) {
        int size = board.getSize();
        // Create a randomly arranged list of all moves
        List<Integer> fields = IntStream.range(0, size * size)
            .boxed()
            .collect(Collectors.toList());
        Collections.shuffle(fields);

        // Only select the move if the related field is free
        int move = -1;
        Iterator<Integer> fieldsIterator = fields.iterator();
        while (fieldsIterator.hasNext()) {
            move = fieldsIterator.next();
            if (board.value(move) == 0) {
                break;
            }
            fieldsIterator.remove();
        }
        return move;
    }
}
