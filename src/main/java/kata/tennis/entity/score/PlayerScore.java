package kata.tennis.entity.score;

/**
 * @author karim.khoule
 *
 */
public enum PlayerScore {
	WIN(5, null), ADVANTAGE(4, WIN), FORTY(3, ADVANTAGE), THIRTY(2, FORTY), FIFTEEN(1, THIRTY), ZERO(0, FIFTEEN);

	private Integer intScore;

	private PlayerScore nextScore;

	/**
	 * @param intScore
	 * @param nextScore
	 */
	PlayerScore(final Integer intScore, final PlayerScore nextScore) {
		this.intScore = intScore;
		this.nextScore = nextScore;
	}

	
	/**
	 * @return
	 */
	public PlayerScore getNextScore() {
		return nextScore;
	}

	@Override
	public String toString() {
		return intScore.toString();
	}

	/**
	 * @return
	 */
	public Integer value() {
		return intScore;
	}
}
