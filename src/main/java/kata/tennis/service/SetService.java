package kata.tennis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kata.tennis.entity.Game;
import kata.tennis.entity.Player;
import kata.tennis.entity.Set;
import kata.tennis.entity.score.SetPlayerScore;

/**
 * @author karim.khoule
 *
 */
@Service
public class SetService implements IResolveScorePlayer<Set>, IPlay<Set> {

	private static final Logger LOG = LoggerFactory.getLogger(SetService.class);
	@Autowired
	private GameService gameService;

	@Override
	public Player play(Set currentSet) {
		do {
			Game currentGame = new Game();
			gameService.play(currentGame);
			resolveScorePlayer(currentGame.getWinner(), currentSet);
			currentSet.addGame(currentGame);
		} while (currentSet.getWinner() == null);

		return currentSet.getWinner();
	}

	@Override
	public void resolveScorePlayer(Player player, Set currentSet) {
		if (firstPredicateResolveScorePlayer(player, currentSet)) {
			resolveSetScore(player, currentSet);
			getWinner(player, currentSet);
			// Set Score is ( 6 - 6 ) => activate tie break rule
		} else if (currentSet.getSetScorePlayerTwo().equals(SetPlayerScore.SIX)
				&& currentSet.getSetScorePlayerOne().equals(SetPlayerScore.SIX)) {
			activateTieBreakRule(player, currentSet);

		} else if (secondPredicateResolveScorePlayer(player, currentSet)) {
			resolveSetScore(player, currentSet);
			getWinner(player, currentSet);
			// All other cases => increment set scores
		} else {
			resolveSetScore(player, currentSet);
		}

	}

	/**
	 * @param player
	 * @param currentSet
	 * @return 
	 */
	private boolean firstPredicateResolveScorePlayer(final Player player, final Set currentSet) {
		return currentSet.getSetScorePlayerOne().equals(SetPlayerScore.FIVE)
				&& currentSet.getSetScorePlayerTwo().value() <= SetPlayerScore.FOUR.value()
				&& player.equals(Player.FIRST_PLAYER)
				|| currentSet.getSetScorePlayerTwo().equals(SetPlayerScore.FIVE)
						&& currentSet.getSetScorePlayerOne().value() <= SetPlayerScore.FOUR.value()
						&& player.equals(Player.SECOND_PLAYER);
	}

	/**
	 * @param player
	 * @param currentSet
	 * @return 
	 */
	private boolean secondPredicateResolveScorePlayer(final Player player, final Set currentSet) {
		return currentSet.getSetScorePlayerOne().equals(SetPlayerScore.SIX)
				&& currentSet.getSetScorePlayerTwo().value() <= SetPlayerScore.FIVE.value()
				&& player.equals(Player.FIRST_PLAYER)
				|| currentSet.getSetScorePlayerTwo().equals(SetPlayerScore.SIX)
						&& currentSet.getSetScorePlayerOne().value() <= SetPlayerScore.FIVE.value()
						&& player.equals(Player.SECOND_PLAYER);
	}

	/**
	 * @param player
	 * @param currentSet
	 */
	private void activateTieBreakRule(final Player player, final Set currentSet) {
		LOG.info("Tie Break Rule is activated");
		// Increment Tie break Score
		resolveTieBreakScore(player, currentSet);
		// Tie break score is at least 7 + 2 Tie break points difference 
		if (predicateTieBreak(player, currentSet)) {
			resolveSetScore(player, currentSet);
			getWinner(player, currentSet);
		}
	}

	/**
	 * @param player
	 * @param currentSet
	 * @return
	 */
	private boolean predicateTieBreak(final Player player, final Set currentSet) {
		return currentSet.getTieBreakScorePlayerOne().value() >= SetPlayerScore.SEVEN.value()
				&& currentSet.getTieBreakScorePlayerOne().value() >= currentSet.getTieBreakScorePlayerTwo().value()
						+ SetPlayerScore.TWO.value()
				&& player.equals(Player.FIRST_PLAYER)
				|| currentSet.getTieBreakScorePlayerTwo().value() >= SetPlayerScore.SEVEN.value()
						&& currentSet.getTieBreakScorePlayerTwo()
								.value() >= currentSet.getTieBreakScorePlayerOne().value() + SetPlayerScore.TWO.value()
						&& player.equals(Player.SECOND_PLAYER);
	}

	/**
	 * @param player
	 * @param currentSet
	 */
	private void getWinner(final Player player, final Set currentSet) {
		if (Player.FIRST_PLAYER.equals(player)) {
			currentSet.setWinner(Player.FIRST_PLAYER);
		} else {
			currentSet.setWinner(Player.SECOND_PLAYER);
		}

		LOG.info("Player 1 Score : {} ", currentSet.getSetScorePlayerOne().name());
		LOG.info("Player 2 Score : {}", currentSet.getSetScorePlayerTwo().name());
		LOG.info("The Winner of the current Set is {}", currentSet.getWinner());
	}

	/**
	 * @param player
	 * @param currentSet
	 */
	private void resolveSetScore(final Player player, final Set currentSet) {
		if (player.equals(Player.FIRST_PLAYER)) {
			currentSet.setSetScorePlayerOne(currentSet.getSetScorePlayerOne().getNextScore());
		} else {
			currentSet.setSetScorePlayerTwo(currentSet.getSetScorePlayerTwo().getNextScore());
		}
	}

	/**
	 * @param player
	 * @param currentSet
	 */
	private void resolveTieBreakScore(final Player player, final Set currentSet) {
		if (player.equals(Player.FIRST_PLAYER)) {
			currentSet.setTieBreakScorePlayerOne(currentSet.getTieBreakScorePlayerOne().getNextScore());
		} else {
			currentSet.setTieBreakScorePlayerTwo(currentSet.getTieBreakScorePlayerTwo().getNextScore());
		}
	}

	/**
	 * @param gameService
	 */
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}
}
