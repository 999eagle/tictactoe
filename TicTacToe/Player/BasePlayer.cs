using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TicTacToe.Player
{
	abstract class BasePlayer
	{
		public int Id { get; set; }

		public abstract int Move(Board board);

		public override string ToString()
		{
			return $"Player {Id} ({GetType().Name})";
		}
	}
}
