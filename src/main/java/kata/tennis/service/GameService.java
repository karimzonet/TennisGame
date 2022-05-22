package kata.tennis.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import kata.tennis.entity.Game;
import kata.tennis.entity.Player;
import kata.tennis.entity.score.PlayerScore;

/**
 * @author karim.khoule
 *
 */
@Service
public class GameService implements IResolveScorePlayer<Game>, IPlay<Game> {

	private static final Logger LOG = LoggerFactory.getLogger(GameService.class);

	@Override
	public Player play(Game game) {
		do {
			// Randomly select the next Player who will score a point
			Player player = Math.random() < 0.5 ? Player.FIRST_PLAYER : Player.SECOND_PLAYER;
			resolveScorePlayer(player, game);
		} while (game.getWinner() == null);
		return game.getWinner();
	}
	


	@Override
	public void resolveScorePlayer(Player player, Game game) {
		if (firstPredicateResolveScore(player, game)) {
			getWinner(player, game);
		} else if (secondPredicateResolveScore(game)) {
			activateDeuceRule(player, game);
		} else {
			resolveGameScore(player, game);
		}
	}
	/**
	 * @param player
	 * @param game
	 * @return 
	 * Score (X-40) or (40-X) with X<40 ==> Winner
	 */
	private boolean firstPredicateResolveScore(final Player player, final Game game) {
		return game.getScorePlayerOne().equals(PlayerScore.FORTY)
				&& game.getScorePlayerTwo().value() < PlayerScore.FORTY.value() && player.equals(Player.FIRST_PLAYER)
				|| game.getScorePlayerTwo().equals(PlayerScore.FORTY)
						&& game.getScorePlayerOne().value() < PlayerScore.FORTY.value()
						&& player.equals(Player.SECOND_PLAYER);
	}

	/**
	 * @param game
	 * @return 
	 * Score ( 40 - 40 ) => activate Deuce Rule
	 */
	private boolean secondPredicateResolveScore(final Game game) {
		return game.getScorePlayerOne().value() >= PlayerScore.FORTY.value()
				&& game.getScorePlayerTwo().value() >= PlayerScore.FORTY.value();
	}

	/**
	 * @param player
	 * @param game   All other cases => increment scores
	 */
	private void resolveGameScore(final Player player, final Game game) {
		if (Player.FIRST_PLAYER.equals(player)) {
			LOG.info("FIRST_PLAYER  has won 1 point");
			game.setScorePlayerOne(game.getScorePlayerOne().getNextScore());
		} else {
			LOG.info("SECOND_PLAYER  has won 1 point");
			game.setScorePlayerTwo(game.getScorePlayerTwo().getNextScore());
		}
		LOG.info("SCORE : {} - {}", game.getScorePlayerOne().name(), game.getScorePlayerTwo().name());
	}

	/**
	 * @param player
	 * @param game
	 */
	private void activateDeuceRule(final Player player, final Game game) {
		LOG.info("Deuce Activated");
		// Game Score is ( 40 - 40 ) => increment score to ADV
		if (game.getScorePlayerOne().equals(PlayerScore.FORTY) && game.getScorePlayerTwo().equals(PlayerScore.FORTY)) {
			resolveGameScore(player, game);
			// Game Score is ( ADV - 40 ) or ( 40 - ADV ) leading to score above ADV =>
			// designate a winner
		} else if (firstPredicateDeuceAdvantage(player, game)) {
			getWinner(player, game);
			// Game Score is ( ADV - 40 ) or ( 40 - ADV ) leading to score ( ADV - ADV ) =>
			// increment scores & activate deuce rule
		} else if (secondPredicateDeuceAdvantage(player, game)) {
			resolveGameScore(player, game);
			resetScoresDeuceRule(game);
		}
	}

	/**
	 * @param player
	 * @param game
	 * @return
	 */
	private boolean firstPredicateDeuceAdvantage(final Player player, final Game game) {
		return game.getScorePlayerOne().equals(PlayerScore.ADVANTAGE)
				&& game.getScorePlayerTwo().equals(PlayerScore.FORTY) && player.equals(Player.FIRST_PLAYER)
				|| game.getScorePlayerTwo().equals(PlayerScore.ADVANTAGE)
						&& game.getScorePlayerOne().equals(PlayerScore.FORTY) && player.equals(Player.SECOND_PLAYER);
	}

	/**
	 * @param player
	 * @param game
	 * @return
	 */
	private boolean secondPredicateDeuceAdvantage(final Player player, final Game game) {
		return game.getScorePlayerOne().equals(PlayerScore.FORTY)
				&& game.getScorePlayerTwo().equals(PlayerScore.ADVANTAGE) && player.equals(Player.FIRST_PLAYER)
				|| game.getScorePlayerTwo().equals(PlayerScore.FORTY)
						&& game.getScorePlayerOne().equals(PlayerScore.ADVANTAGE)
						&& player.equals(Player.SECOND_PLAYER);
	}

	/**
	 * @param game
	 */
	private void resetScoresDeuceRule(final Game game) {
		game.setScorePlayerOne(PlayerScore.FORTY);
		game.setScorePlayerTwo(PlayerScore.FORTY);
	}

	/**
	 * @param player
	 * @param game
	 */
	private void getWinner(final Player player, final Game game) {
		if (Player.FIRST_PLAYER.equals(player)) {
			game.setWinner(Player.FIRST_PLAYER);
		} else {
			game.setWinner(Player.SECOND_PLAYER);
		}
		LOG.info("Player 1 Score : {} ", game.getScorePlayerOne().name());
		LOG.info("Player 2 Score : {}", game.getScorePlayerTwo().name());
		LOG.info("The Winner of the game is {}", game.getWinner());
		LOG.info("*********************End of Game*************************");
		resetGameScores(game);
	}

	/**
	 * @param game
	 */
	private void resetGameScores(final Game game) {
		game.setScorePlayerOne(PlayerScore.ZERO);
		game.setScorePlayerTwo(PlayerScore.ZERO);
	}
}
