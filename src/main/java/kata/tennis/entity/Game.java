package kata.tennis.entity;

import kata.tennis.entity.score.PlayerScore;

/**
 * @author karim.khoule 
 * Class Game
 */
public class Game {

	private PlayerScore scorePlayerOne;

	private PlayerScore scorePlayerTwo;

	private Player winner;

	public Game() {
		scorePlayerOne = PlayerScore.ZERO;
		scorePlayerTwo = PlayerScore.ZERO;
		winner = null;
	}

	/**
	 * @return
	 */
	public PlayerScore getScorePlayerOne() {
		return scorePlayerOne;
	}

	/**
	 * @param scorePlayerOne
	 */
	public void setScorePlayerOne(PlayerScore scorePlayerOne) {
		this.scorePlayerOne = scorePlayerOne;
	}

	/**
	 * @return
	 */
	public PlayerScore getScorePlayerTwo() {
		return scorePlayerTwo;
	}

	/**
	 * @param scorePlayerTwo
	 */
	public void setScorePlayerTwo(PlayerScore scorePlayerTwo) {
		this.scorePlayerTwo = scorePlayerTwo;
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
}
