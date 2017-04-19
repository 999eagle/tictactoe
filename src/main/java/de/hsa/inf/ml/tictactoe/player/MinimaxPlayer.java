package de.hsa.inf.ml.tictactoe.player;

import de.hsa.inf.ml.tictactoe.Board;

import java.lang.reflect.Array;

/**
 * Created by erik on 2017-04-19.
 */
public class MinimaxPlayer extends Player
{
	private int size;
	private int optimalMove;
	@Override
	public int move(Board board)
	{
		size = board.getSize();
		int player = this.getId();
		int opponent = (player == 1 ? 2 : 1);
		int[][] state = readState(board);
		minimax(state, player, opponent);
		return optimalMove;
	}

	private int[][] readState(Board board)
	{
		int[][] state = new int[size][size];
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				state[x][y] = board.value(y * size + x);
			}
		}
		return state;
	}

	private int minimax(int[][] state, int player, int opponent)
	{
		int simpleScore = scoreBoardSimple(state, player, opponent);
		if (simpleScore != 0) return simpleScore;
		int[][] nextState = new int[size][size];
		int freeFields = 0;
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				nextState[x][y] = state[x][y];
				if (state[x][y] == 0) freeFields++;
			}
		}
		if (freeFields == 0) return 0;
		if (freeFields == size * size) { optimalMove = (size * size - 1) / 2; return 0; }
		int move = -1;
		int maxScore = -1;
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				if (state[x][y] != 0) continue;
				freeFields++;
				nextState[x][y] = player;
				int score = -minimax(nextState, opponent, player);
				if (score > maxScore)
				{
					maxScore = score;
					move = y * size + x;
				}
				nextState[x][y] = 0;
			}
		}
		optimalMove = move;
		return maxScore;
	}

	private int scoreBoardSimple(int[][] state, int player, int opponent)
	{
		int diagonal1P = 0, diagonal1O = 0;
		int diagonal2P = 0, diagonal2O = 0;
		for(int x = 0; x < size; x++)
		{
			int inColP = 0, inColO = 0;
			int inRowP = 0, inRowO = 0;
			if (state[x][x] == player) diagonal1P++;
			if (state[x][x] == opponent) diagonal1O++;
			if (state[x][size - 1 - x] == player) diagonal2P++;
			if (state[x][size - 1 - x] == opponent) diagonal2O++;
			for(int y = 0; y < size; y++)
			{
				if (state[x][y] == player) inColP++;
				if (state[x][y] == opponent) inColO++;
				if (state[y][x] == player) inRowP++;
				if (state[y][x] == opponent) inRowO++;
			}
			if (inColP == size || inRowP == size) return 1;
			if (inColO == size || inRowO == size) return -1;
		}
		if (diagonal1P == size || diagonal2P == size) return 1;
		if (diagonal1O == size || diagonal2O == size) return -1;
		return 0;
	}
}
