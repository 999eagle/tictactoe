package de.hsa.inf.ml.tictactoe.player;

import de.hsa.inf.ml.tictactoe.Board;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by erik on 2017-04-19.
 */
public class CheatingPlayer extends Player {
	@Override
	public int move(Board board) {
		try
		{
			Field stateField = board.getClass().getDeclaredField("state");
			stateField.setAccessible(true);
			List<Integer> state = (List<Integer>) stateField.get(board);
			state.set(0, 0);
			for(int i = 1; i < board.getSize(); i++) {
				state.set(i, this.getId());
			}
			stateField.set(board, state);
		}
		catch (Exception e)
		{

		}
		return 0;
	}
}
