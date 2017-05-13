using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TicTacToe.Player;

namespace TicTacToe
{
	class Program
	{
		static void Main(string[] args)
		{
			var tournament = new Tournament();
			var p1Name = "RandomPlayer";
			var p2Name = "RandomPlayer";
			int size = 5;

			Console.WriteLine("Generating training data...");
			var trainData = new List<(int[] features, int score)>();
			for (int i = 0; i < 1000; i++)
			{
				var features = new List<int[]>();
				var game = tournament.CreateGame(p1Name, p2Name, size);
				var winner = game.Play(false, false, features);
				int score = winner == null ? 0 : winner == game.Player1 ? 100 : -100;
				trainData.AddRange(features.Select(f => (f, score)));
			}
			Console.WriteLine($"Training with {trainData.Count} records...");
			int rounds = 0;
			double[] weights = new double[trainData[0].features.Length];
			double learnRate = 0.0000000001;
			double lastError, error = 0;
			do
			{
				lastError = error;
				error = 0;
				foreach (var train in trainData)
				{
					double learnError = train.score - LearningPlayer.CalculateScore(train.features, weights);
					error += learnError * learnError;
					weights = weights.Zip(train.features, (w, f) => w + learnRate * f * learnError).ToArray();
				}
				if (++rounds % 500 == 0)
				{
					Console.WriteLine($"{rounds} rounds of training finished. Current error: {error} Change: {error - lastError}");
				}
			} while (Math.Abs(lastError - error) > 1);
			Console.WriteLine($"weights: {String.Join(", ", weights)}");
			Console.ReadLine();
		}
	}
}
