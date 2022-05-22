package kata.tennis.entity.score;

/**
 * @author karim.khoule
 *
 */
public enum SetPlayerScore {

	NINE(9, null), HEIGHT(8, NINE), SEVEN(7, HEIGHT), SIX(6, SEVEN), FIVE(5, SIX), FOUR(4, FIVE), THREE(3, FOUR), TWO(2, THREE), ONE(1, TWO), ZERO(0,
			ONE);

	private Integer intScore;

	private SetPlayerScore nextScore;

	/**
	 * @param intScore
	 * @param nextScore
	 */
	SetPlayerScore(final Integer intScore, final SetPlayerScore nextScore) {
		this.intScore = intScore;
		this.nextScore = nextScore;
	}

	
	/**
	 * @return
	 */
	public SetPlayerScore getNextScore() {
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
