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
import de.hsa.inf.ml.tictactoe.player.RandomPlayer;

/**
 * This class represents the main tournament environment with different modes
 */
public class Tournament
{
	/**
	 * Represents the application's main entry.
	 *
	 * @param args Specifies the command line arguments:
	 *             First - Name of player 1,
	 *             Second - Name of player 2,
	 *             Third - Board's size (per dimension),
	 *             Fourth - Print moves to standard output, if true
	 *             Fifth - Print moves to standard output, if true
	 */
	public static void main(String[] args)
	{
		String player1Name = "RandomPlayer";
		String player2Name = "RandomPlayer";
		int size = 5;
		boolean printMove = true;
		boolean printBoard = true;
		if (args.length > 0)
		{
			player1Name = args[0];
		}
		if (args.length > 1)
		{
			player2Name = args[1];
		}
		if (args.length > 2)
		{
			size = Integer.parseInt(args[2]);
		}
		if (args.length > 3)
		{
			printMove = Boolean.parseBoolean(args[3]);
		}
		if (args.length > 4)
		{
			printBoard = Boolean.parseBoolean(args[4]);
		}

		// Play a game
		Game game = createGame(player1Name, player2Name, size);
		Player winner = game.play(printMove, printBoard);

		// Show game's result
		System.out.println(game.getPlayer1() + " vs. " + game.getPlayer2());
		if (winner == null)
		{
			System.out.println("Draw");
		} else
		{
			System.out.println("Winner: " + winner);
		}
	}

	/**
	 * Creates a game to play.
	 *
	 * @param player1Name Specifies the name of the first player
	 * @param player2Name Specifies the name of the second player
	 * @param size        Specifies the board's size per dimension
	 * @return A game
	 */
	private static Game createGame(
		String player1Name,
		String player2Name,
		int size
	)
	{
		Player player1 = new RandomPlayer()
			.setId(1);
		Player player2 = new RandomPlayer()
			.setId(2);
		try
		{
			String packageName = "de.hsa.inf.ml.tictactoe.player.";
			if (!player1Name.equals("RandomPlayer"))
			{
				Class<?> theClassPlayer1 =
					Class.forName(packageName + player1Name);
				player1 = ((Player) theClassPlayer1.newInstance())
					.setId(1);
			}
			if (!player2Name.equals("RandomPlayer"))
			{
				Class<?> theClassPlayer2 =
					Class.forName(packageName + player2Name);
				player2 = ((Player) theClassPlayer2.newInstance())
					.setId(2);
			}
		} catch (ClassNotFoundException | InstantiationException |
			IllegalAccessException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		Board board = new Board()
			.setSize(size);
		return new Game()
			.setPlayer1(player1)
			.setPlayer2(player2)
			.setBoard(board);
	}
}
