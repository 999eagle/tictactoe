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

import java.util.Locale;

import de.hsa.inf.ml.tictactoe.Board;

/**
 * This class represents the main functionality of a player.
 * <p>
 * It is necessary to implement the {@link Player#move(Board) move} method.
 */
public abstract class Player
{
	/**
	 * Retrieves the player's id.
	 *
	 * @return The player's id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Sets the player's id.
	 *
	 * @param id Specifies the player's id
	 * @return The player
	 */
	public Player setId(int id)
	{
		this.id = id;
		return this;
	}

	/**
	 * Computes the player's next move during a game.
	 *
	 * @param board Specifies the game's board
	 * @return The occupied position/field index
	 */
	public abstract int move(Board board);

	/**
	 * Represents basic player information.
	 *
	 * @return The player's string representation
	 */
	@Override
	public String toString()
	{
		return String.format(Locale.US, "Player %d (%s)", id,
			getClass().getSimpleName());
	}

	/**
	 * Specifies the player's id
	 */
	private int id = 0;
}
