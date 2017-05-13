package de.hsa.inf.ml.tictactoe.player;

import de.hsa.inf.ml.tictactoe.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik on 2017-05-13.
 */
public class LearningPlayer extends Player
{
	private int size;
	
	@Override
	public int move(Board board)
	{
		return -1;
	}
	
	private int[] ReadBoard(Board board, int ourPlayer)
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
	
	private int[] CalcFeatures(int[] state)
	{
		ArrayList<Integer> features = new ArrayList<>();
		int rowO, rowP, colO, colP;
		int d1O = 0, d1P = 0, d2O = 0, d2P = 0;
		int totalP = 0, totalO = 0;
		for (int x = 0; x < size; x++)
		{
			rowO = 0; rowP = 0; colO = 0; colP = 0;
			for (int y = 0; y < size; y++)
			{
				if (state[x + y * size] == 1) { rowP++; totalP++; }
				else if (state[x + y * size] != 0) { rowO++; totalO++; }
				if (state[x * size + y] == 1) colP++;
				else if (state[x * size + y] != 0) colO++;
			}
			if (state[x + x * size] == 1) d1P++;
			else if (state[x + x * size] != 0) d1O++;
			if (state[size - 1 - x + x * size] == 1) d2P++;
			else if (state[size - 1 - x + x * size] != 0) d2O++;
			features.add(rowO);
			features.add(rowP);
			features.add(colO);
			features.add(colP);
		}
		features.add(d1O);
		features.add(d1P);
		features.add(d2O);
		features.add(d2P);
		features.add(totalO);
		features.add(totalP);
		features.add(size * size - totalO - totalP);
		features.add(totalP - totalO);
		return features.stream().mapToInt(i -> i).toArray();
	}
	
}
