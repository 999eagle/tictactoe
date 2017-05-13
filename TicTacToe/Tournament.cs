using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using TicTacToe.Player;

namespace TicTacToe
{
	class Tournament
	{
		public Game CreateGame(string player1Name, string player2Name, int size)
		{
			var assembly = Assembly.GetExecutingAssembly();
			var p1 = (BasePlayer)Activator.CreateInstance(assembly.GetTypes().Where(t => t.Name == player1Name).FirstOrDefault());
			p1.Id = 1;
			var p2 = (BasePlayer)Activator.CreateInstance(assembly.GetTypes().Where(t => t.Name == player2Name).FirstOrDefault());
			p2.Id = 2;
			var board = new Board();
			board.SetSize(size);
			return new Game() { Board = board, Player1 = p1, Player2 = p2 };
		}
	}
}
