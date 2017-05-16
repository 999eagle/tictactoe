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
				0.00133228505146017,
				-0.0200586179467933,
				-0.0120543450977627,
				0.0487811121698806,
				0.00570718975357381,
				-0.000367957672046977,
				0.0827200147924348,
				0.0195336558846808,
				-0.0131635337461998,
				0.0440355144604242,
				0.0194894724945787,
				-0.00690815156000498,
				0.0569526216840801,
				0.0144412680943809,
				-0.00315235864372616,
				0.0532707804245266,
				0.0124393255557614,
				0.0524487576628881,
				0.0633836308280008,
				0.00925862165922876,
				-0.0115280888320914,
				0.0664700497529664,
				-0.00468849381174149,
				-0.00752730004055796,
				0.0805748869455507,
				0.0055196433580229,
				0.0369498440454469,
				0.0867140552585847,
				-0.00425292294263721,
				-0.0405938306646232,
				0.0796147050190139,
				0.324848422817512,
				-0.353080055776965,
				0.0989240064178579,
				0.345393811676871,
				-0.332447266343606,
				0.0953648966669258,
				0.00756103751129368,
				0.0286945710495639,
				-0.00294848227434734,
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
			features.add(rowP == 0 ? rowO : rowO == 0 ? rowP : 0);
			features.add(colP);
			features.add(colO);
			features.add(colP == 0 ? colO : colO == 0 ? colP : 0);
		}
		features.add(d1P);
		features.add(d1O);
		features.add(d1P == 0 ? d1O : d1O == 0 ? d1P : 0);
		features.add(d2P);
		features.add(d2O);
		features.add(d2P == 0 ? d2O : d2O == 0 ? d2P : 0);
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
