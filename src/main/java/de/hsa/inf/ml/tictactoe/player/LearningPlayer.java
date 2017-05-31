package de.hsa.inf.ml.tictactoe.player;

import de.hsa.inf.ml.tictactoe.Board;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik on 2017-05-13.
 */
public class LearningPlayer extends Player
{
	private int size;
	private double[] weights = new double[0];
	
	public LearningPlayer()
	{
		ArrayList<Double> weightList = new ArrayList<>();
		try
		{
			Files.lines(FileSystems.getDefault().getPath("weights")).forEach(
				l -> {
					weightList.add(Double.parseDouble(l));
				});
			weights = weightList.stream().mapToDouble(d -> d).toArray();
		}
		catch(IOException e)
		{
			weights = new double[] {
				0.00387253546455683,
				-0.173461061814011,
				0.167004675719873,
				0.854838230037293,
				-0.153951138548644,
				0.198048507294704,
				0.875078047426304,
				-0.176424584450546,
				0.171072033888653,
				0.851234253201307,
				-0.153344797311734,
				0.188519189156144,
				0.867887004190844,
				-0.18759842362319,
				0.197930668341971,
				0.888676729078891,
				-0.181915746250433,
				0.193945360179105,
				0.876175285154777,
				-0.144636506354586,
				0.201499677157002,
				0.857545585692032,
				-0.185212024852708,
				0.201897402912918,
				0.893819302411324,
				-0.179965695671092,
				0.210063305381261,
				0.855680740599396,
				-0.187662564949295,
				0.16515990094617,
				0.879351401282532,
				-0.0279825745809599,
				0.0463051136095829,
				0.763112735080358,
				-0.090112804439409,
				-0.00988626602781218,
				0.768770851174027,
				0.0113292980379931,
				-0.86208627191326,
				0.947570360489475,
			};
		}
	}
	
	@Override
	public int move(Board board)
	{
		int[] state = ReadBoard(board, getId());
		double maxScore = Double.NEGATIVE_INFINITY;
		int bestMove = -1;
		for (int i = 0; i < state.length; i++)
		{
			if (state[i] == 0)
			{
				state[i] = 1;
				double score = CalculateScore(CalcFeatures(state), weights);
				if (score > maxScore)
				{
					maxScore = score;
					bestMove = i;
				}
				state[i] = 0;
			}
		}
		return bestMove;
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
		features.add(1);
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
			features.add(rowP);
			features.add(rowO);
			features.add(rowP == 0 ? -rowO : rowO == 0 ? rowP : 0);
			features.add(colP);
			features.add(colO);
			features.add(colP == 0 ? -colO : colO == 0 ? colP : 0);
		}
		features.add(d1P);
		features.add(d1O);
		features.add(d1P == 0 ? -d1O : d1O == 0 ? d1P : 0);
		features.add(d2P);
		features.add(d2O);
		features.add(d2P == 0 ? -d2O : d2O == 0 ? d2P : 0);
		features.add(size * size - totalO - totalP);
		features.add(totalP);
		features.add(totalO);
		return features.stream().mapToInt(i -> i).toArray();
	}
	
	private double CalculateScore(int[] features, double[] weights)
	{
		double score = 0;
		for (int i = 0; i < features.length; i++)
		{
			score += weights[i] * features[i];
		}
		return score;
	}
}
