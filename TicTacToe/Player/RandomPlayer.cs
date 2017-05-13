using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TicTacToe.Player
{
	class RandomPlayer : BasePlayer
	{
		Random rand = new Random();

		public override int Move(Board board)
		{
			int size = board.Size;
			var list = Enumerable.Range(0, size * size).ToList();
			Shuffle(list);
			foreach (var l in list)
			{
				if (board[l] == 0) return l;
			}
			return -1;
		}

		private void Shuffle<T>(IList<T> list)
		{
			int n = list.Count;
			while (n > 1)
			{
				int k = rand.Next(n);
				n--;
				T value = list[k];
				list[k] = list[n];
				list[n] = value;
			}
		}
	}
}
