using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TicTacToe.Player;

namespace TicTacToe
{
	class Game
	{
		public Board Board { get; set; }
		public BasePlayer Player1 { get; set; }
		public BasePlayer Player2 { get; set; }

		public BasePlayer Play(bool printMove, bool printBoard)
		{
			BasePlayer winningPlayer;
			while (true)
			{
				winningPlayer = PlayerTurn(Player1, Player2, printMove, printBoard);
				if (winningPlayer != null) return winningPlayer;
				if (Board.IsDrawState()) return null;

				winningPlayer = PlayerTurn(Player2, Player1, printMove, printBoard);
				if (winningPlayer != null) return winningPlayer;
				if (Board.IsDrawState()) return null;
			}
		}

		BasePlayer PlayerTurn(BasePlayer player, BasePlayer opponent, bool printMove, bool printBoard)
		{
			int move = player.Move(Board);
			if (printMove) Console.WriteLine($"Move of {player}: {move}");
			if (!Board.IsValidMove(move)) return opponent;
			Board[move] = player.Id;
			if (printBoard) Console.WriteLine(Board);
			if (Board.IsWinningState(Board.Score(player))) return player;
			return null;
		}
	}
}
