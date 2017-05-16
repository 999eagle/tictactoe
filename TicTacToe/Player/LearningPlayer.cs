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
			double score = 0;
			for (int i = 0; i < weights.Length; i++)
			{
				score += features[i] * weights[i];
			}
			return score;
		}
	}
}
