package de.hsa.inf.ml.tictactoe.player;

import de.hsa.inf.ml.tictactoe.Board;

/**
 * Created by erik on 2017-04-30.
 */
public class DepressedPlayer extends Player
{
	@Override
	public int move(Board board)
	{
		System.out.println("The only winning move is not to play.");
		while(true)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
		return -1;
	}
}
