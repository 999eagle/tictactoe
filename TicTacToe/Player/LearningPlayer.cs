using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TicTacToe.Player
{
	class LearningPlayer : BasePlayer
	{
		public override int Move(Board board)
		{
			return -1;
		}

		public static double CalculateScore(int[] features, double[] weights)
		{
			return weights[0] + weights.Skip(1).Zip(features, (w, f) => w * f).Aggregate((a, b) => a + b);
		}
	}
}
