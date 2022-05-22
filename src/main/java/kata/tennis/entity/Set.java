package kata.tennis.entity;

import java.util.ArrayList;
import java.util.List;
import kata.tennis.entity.score.SetPlayerScore;

/**
 * @author karim.khoule 
 * Class Set
 */
public class Set {

	private SetPlayerScore setScorePlayerOne;

	private SetPlayerScore tieBreakScorePlayerOne;

	private SetPlayerScore setScorePlayerTwo;

	private SetPlayerScore tieBreakScorePlayerTwo;

	private List<Game> listGame;

	private Player winner;

	public Set() {
		setScorePlayerOne = SetPlayerScore.ZERO;
		setScorePlayerTwo = SetPlayerScore.ZERO;
		tieBreakScorePlayerOne = SetPlayerScore.ZERO;
		tieBreakScorePlayerTwo = SetPlayerScore.ZERO;
		winner = null;
		listGame = new ArrayList<>();
	}

	/**
	 * @return
	 */
	public SetPlayerScore getSetScorePlayerOne() {
		return setScorePlayerOne;
	}

	/**
	 * @param setScorePlayerOne
	 */
	public void setSetScorePlayerOne(SetPlayerScore setScorePlayerOne) {
		this.setScorePlayerOne = setScorePlayerOne;
	}

	/**
	 * @return
	 */
	public SetPlayerScore getTieBreakScorePlayerOne() {
		return tieBreakScorePlayerOne;
	}

	/**
	 * @param tieBreakScorePlayerOne
	 */
	public void setTieBreakScorePlayerOne(SetPlayerScore tieBreakScorePlayerOne) {
		this.tieBreakScorePlayerOne = tieBreakScorePlayerOne;
	}

	/**
	 * @return
	 */
	public SetPlayerScore getSetScorePlayerTwo() {
		return setScorePlayerTwo;
	}

	/**
	 * @param setScorePlayerTwo
	 */
	public void setSetScorePlayerTwo(SetPlayerScore setScorePlayerTwo) {
		this.setScorePlayerTwo = setScorePlayerTwo;
	}

	/**
	 * @return
	 */
	public SetPlayerScore getTieBreakScorePlayerTwo() {
		return tieBreakScorePlayerTwo;
	}

	/**
	 * @param tieBreakScorePlayerTwo
	 */
	public void setTieBreakScorePlayerTwo(SetPlayerScore tieBreakScorePlayerTwo) {
		this.tieBreakScorePlayerTwo = tieBreakScorePlayerTwo;
	}

	/**
	 * @return
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * @param winner
	 */
	public void setWinner(Player winner) {
		this.winner = winner;
	}

	/**
	 * @return
	 */
	public List<Game> getListGame() {
		return listGame;
	}

	/**
	 * @param listGame
	 */
	public void setListGame(List<Game> listGame) {
		this.listGame.addAll(listGame);
	}

	/**
	 * @param game
	 */
	public void addGame(Game game) {
		this.listGame.add(game);
	}
}
