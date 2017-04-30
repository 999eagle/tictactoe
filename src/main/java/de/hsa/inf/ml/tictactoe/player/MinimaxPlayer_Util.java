package de.hsa.inf.ml.tictactoe.player;

import de.hsa.inf.ml.tictactoe.Board;

import java.util.ArrayList;
import java.util.Comparator;

class MinimaxPlayer_Util
{
	public static int size;
	public static int optimalMove;

	public static int GetScoreLimit() { return 1000; }

	public static int GetWinner(int[] boardState)
	{
		int sumRow, sumCol, sumD1 = 0, sumD2 = 0;
		for(int i = 0; i < size; i++)
		{
			sumRow = 0;
			sumCol = 0;
			for (int j = 0; j < size; j++)
			{
				sumRow += boardState[i * size + j];
				sumCol += boardState[i + j * size];
			}
			if (sumCol == size || sumRow == size) return size;
			if (sumCol == -size || sumRow == -size) return -size;
			sumD1 += boardState[i * size + i];
			sumD2 += boardState[i * size + size - 1 - i];
		}
		if (sumD1 == size || sumD2 == size) return size;
		if (sumD1 == -size || sumD2 == -size) return -size;
		return 0;
	}

	public static int EstimateHeuristic(int[] state, int player)
	{
		// heuristic:
		// 1. count minimum number of moves for player/opponent to win
		// 2. lower number of moves means higher score
		// 3. return difference
		//   0 means both players need the same number of moves
		//   >0 means player needs fewer moves
		//   <0 means opponent needs fewer moves
		byte scoreWin = 0;
		byte scoreLose = 0;
		byte scoreD1P = 0, scoreD1O = 0, scoreD2P = 0, scoreD2O = 0;
		for (int i = 0; i < size; i++)
		{
			byte scoreRowP = 0, scoreRowO = 0, scoreColP = 0, scoreColO = 0;
			for (int j = 0; j < size; j++)
			{
				if (state[i * size + j] == player) scoreRowP++;
				else if (state[i * size + j] != 0) scoreRowO++;

				if (state[i + j * size] == player) scoreColP++;
				else if (state[i + j * size] != 0) scoreColO++;
			}
			if (state[i * size + i] == player) scoreD1P++;
			else if (state[i * size + i] != 0) scoreD1O++;

			if (state[i * size + size - 1 - i] == player) scoreD2P++;
			else if (state[i * size + size - 1 - i] != 0) scoreD2O++;

			// current player is alone in row/col
			if (scoreColO == 0 && scoreColP > scoreWin) scoreWin = scoreColP;
			if (scoreRowO == 0 && scoreRowP > scoreWin) scoreWin = scoreRowP;
			// opponent is alone in row/col
			if (scoreColP == 0 && scoreColO > scoreLose) scoreLose = scoreColO;
			if (scoreRowP == 0 && scoreRowO > scoreLose) scoreLose = scoreRowO;
		}
		// player is alone in diagonal
		if (scoreD1O == 0 && scoreD1P > scoreWin) scoreWin = scoreD1P;
		if (scoreD2O == 0 && scoreD2P > scoreWin) scoreWin = scoreD2P;
		// opponent is alone in diagonal
		if (scoreD1P == 0 && scoreD1O > scoreLose) scoreLose = scoreD1O;
		if (scoreD2P == 0 && scoreD2O > scoreLose) scoreLose = scoreD2O;
		// return scores
		return scoreWin - scoreLose;
	}

	public static int[] ReadBoard(Board board, int ourPlayer)
	{
		size = board.getSize();
		int[] state = new int[size * size];
		for (int i = 0; i < state.length; i++)
		{
			if (board.value(i) == ourPlayer)
				state[i] = 1;
			else if (board.value(i) != 0)
				state[i] = -1;
		}
		return state;
	}

	public static ArrayList<Integer> GetPossibleMoves(int[] state, int currentPlayer)
	{
		ArrayList<Integer> moves = new ArrayList<>();
		ArrayList<Integer> scores = new ArrayList<>();
		for (int i = 0; i < state.length; i++)
		{
			if (state[i] != 0) continue;
			state[i] = currentPlayer;
			int score = EstimateHeuristic(state, currentPlayer);
			state[i] = 0;
			int index = 0;
			while(index < scores.size() && scores.get(index) >= score)
			{
				index++;
			}
			scores.add(index, score);
			moves.add(index, i);
		}
		return moves;
	}

	public static int StartAlphaBeta(int[] state, int depth)
	{
		AlphaBeta(state, depth, -GetScoreLimit(), GetScoreLimit(), 1);
		return optimalMove;
	}

	public static int AlphaBeta(int[] state, int depth, int a, int b, int currentPlayer)
	{
		int winner = GetWinner(state);
		if (winner != 0) return winner;
		if (depth == 0) return EstimateHeuristic(state, currentPlayer);
		ArrayList<Integer> possibleMoves = GetPossibleMoves(state, currentPlayer);
		if (possibleMoves.size() == 0) return 0;

		if (currentPlayer == 1)
		{
			// maximize
			int score = -GetScoreLimit();
			for (int move: possibleMoves)
			{
				state[move] = currentPlayer;
				int childScore = AlphaBeta(state, depth - 1, a, b, -currentPlayer);
				state[move] = 0;
				if (childScore > score)
				{
					score = childScore;
					optimalMove = move;
					a = Math.max(a, score);
					if (b <= a)
						break;
				}
			}
			return score;
		}
		else
		{
			// minimize
			int score = GetScoreLimit();
			for (int move: possibleMoves)
			{
				state[move] = currentPlayer;
				int childScore = AlphaBeta(state, depth - 1, a, b, -currentPlayer);
				state[move] = 0;
				if (childScore < score)
				{
					score = childScore;
					optimalMove = move;
					b = Math.min(b, score);
					if (b <= a)
						break;
				}
			}
			return score;
		}
	}
}
