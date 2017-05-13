using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TicTacToe.Player;

namespace TicTacToe
{
	class Board
	{
		private int[] state;
		private int[] winningScores;

		public int Size { get; private set; }

		public int this[int index] { get => state[index]; set => state[index] = value; }

		public int Score(BasePlayer player) => Enumerable.Range(0, state.Length).Select(i => state[i] == player.Id ? (int)Math.Pow(2, i) : 0).Aggregate((a, b) => a + b);

		public bool IsWinningState(int score) => winningScores.Any(s => (s & score) == s);

		public bool IsDrawState() => state.All(f => f != 0);

		public bool IsValidMove(int move) => move >= 0 && move <= state.Length - 1 && state[move] == 0;

		public void SetSize(int size)
		{
			if (size < 3 || size > 5)
			{
				throw new ArgumentOutOfRangeException(nameof(size));
			}
			this.Size = size;
			state = new int[size * size];
			winningScores = File.ReadAllLines($"scores\\{size}x{size}").Select(Int32.Parse).ToArray();
		}

		public override string ToString()
		{
			var sb = new StringBuilder();
			for (int i = 0; i < 2 * Size + 1; i++)
			{
				sb.Append("-");
			}
			sb.Append("\n|");
			for (int i = 0; i < state.Length; i++)
			{
				sb.Append(state[i]).Append("|");
				if (i % Size == Size - 1 && i < state.Length - 1) sb.Append("\n|");
			}
			sb.Append("\n");
			for (int i = 0; i < 2 * Size + 1; i++)
			{
				sb.Append("-");
			}
			return sb.ToString();
		}
	}
}
