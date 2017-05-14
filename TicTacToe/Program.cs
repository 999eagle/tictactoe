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
		static double learnRate = 0.0000000001;
		static double winScore = 1;
		static double drawScore = 0;
		static double lossScore = -1;
		static double maxErrorDelta = 0.001;
		static int gameCount = 10000;
		static void Main(string[] args)
		{
			var tournament = new Tournament();
			var p1Name = "RandomPlayer";
			var p2Name = "RandomPlayer";
			int size = 5;

			Console.WriteLine("Generating training data...");
			var trainData = new List<(int[] features, double score)>();
			for (int i = 0; i < gameCount; i++)
			{
				var features = new List<int[]>();
				var game = tournament.CreateGame(p1Name, p2Name, size);
				var winner = game.Play(false, false, features);
				double score = winner == null ? drawScore : winner == game.Player1 ? winScore : lossScore;
				trainData.AddRange(features.Select(f => (f, score)));
			}
			Console.WriteLine($"Training with {trainData.Count} records...");
			int rounds = 0;
			double[] weights = new double[trainData[0].features.Length];
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
			} while (Math.Abs(lastError - error) > maxErrorDelta);
			Console.WriteLine($"total rounds: {rounds} error: {error} change: {error - lastError}\nweights: {String.Join(", ", weights)}");
			using (var file = File.Open("learn_results.txt", FileMode.Append, FileAccess.Write))
			using (var w = new StreamWriter(file))
			{
				w.WriteLine("Parameters:");
				w.WriteLine($"  games: {gameCount}, total records: {trainData.Count}\n  scoring (win/draw/loss): {winScore}/{drawScore}/{lossScore}\n  learn rate: {learnRate}\n  max error change: {maxErrorDelta}\n\nTraining:\n  rounds: {rounds}\n  error: {error}, change: {error - lastError}\n");
				w.WriteLine("Weights:");
				foreach (var weight in weights) { w.WriteLine(weight); }
				w.WriteLine("---------------------------------");
			}
			Console.ReadLine();
		}
	}
}
