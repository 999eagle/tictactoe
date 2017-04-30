package de.hsa.inf.ml.tictactoe.player;

import de.hsa.inf.ml.tictactoe.Board;

public class MinimaxPlayer extends Player
{
	@Override
	public int move(Board board)
	{
		int[] state = MinimaxPlayer_Util.ReadBoard(board, this.getId());
		int depthLimit = 9;
		if (board.getSize() == 4)
			depthLimit = 11;
		if (board.getSize() == 5)
			depthLimit = 8;
		return MinimaxPlayer_Util.StartAlphaBeta(state, depthLimit);
	}
}
