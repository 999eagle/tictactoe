using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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
			bool printMove = true;
			bool printBoard = true;
			if (args.Length >= 1) p1Name = args[0];
			if (args.Length >= 2) p2Name = args[1];
			if (args.Length >= 3) size = Int32.Parse(args[2]);
			if (args.Length >= 4) printMove = Boolean.Parse(args[3]);
			if (args.Length >= 5) printBoard = Boolean.Parse(args[4]);

			var game = tournament.CreateGame(p1Name, p2Name, size);
			var winner = game.Play(printMove, printBoard);
			Console.WriteLine($"{game.Player1} vs. {game.Player2}");
			if (winner == null) Console.WriteLine("Draw");
			else Console.WriteLine($"Winner: {winner}");
			Console.ReadLine();
		}
	}
}
