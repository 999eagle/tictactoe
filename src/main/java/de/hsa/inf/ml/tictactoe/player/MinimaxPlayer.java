package de.hsa.inf.ml.tictactoe.player;

import de.hsa.inf.ml.tictactoe.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
		MinimaxThread t = new MinimaxThread(state, player, opponent, size);
		t.run();
		return t.optimalMove;
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
}

class MinimaxThread extends Thread
{
	int[][] state;
	int player, opponent, size;
	public int optimalMove, score;
	public int x, y;
	static int numThreads = 0;

	public MinimaxThread(int[][] state, int player, int opponent, int size)
	{
		this.state = state;
		this.player = player;
		this.opponent = opponent;
		this.size = size;
	}

	@Override
	public void run()
	{
		score = minimax(state, player, opponent);
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
		List<MinimaxThread> threads = new ArrayList<MinimaxThread>();
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				if (state[x][y] != 0) continue;
				freeFields++;
				nextState[x][y] = player;
				MinimaxThread t = new MinimaxThread(nextState, opponent, player, size);
				t.x = x;
				t.y = y;
				if (numThreads < 8)
				{
					numThreads++;
					t.start();
				}
				else
				{
					t.run();
				}
				threads.add(t);
				nextState[x][y] = 0;
			}
		}
		for (MinimaxThread t: threads)
		{
			try
			{
				if (t.getState() != State.NEW)
				{
					t.join();
					numThreads--;
				}
				if (t.score > maxScore)
				{
					maxScore = t.score;
					optimalMove = t.y * size + t.x;
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
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
