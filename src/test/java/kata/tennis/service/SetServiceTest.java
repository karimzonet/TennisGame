package kata.tennis.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import kata.tennis.entity.Game;
import kata.tennis.entity.Player;
import kata.tennis.entity.Set;
import kata.tennis.entity.score.SetPlayerScore;

/**
 * @author karim.khoule
 *
 */
@SpringBootTest
class SetServiceTest {

	@Autowired
	private SetService setService;

	@Autowired
	private GameService gameService;

	@Test
	void testScoreSet_6_0_FirstPlayerWin() {
		Set set = new Set();
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 30 - 0 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 40 - 0 )
		listPlayers.add(Player.FIRST_PLAYER); // => Win
		while (set.getWinner() == null) {
			Game currentGame = new Game();	
			listPlayers.forEach(player -> {
				gameService.resolveScorePlayer(player, currentGame);
			});
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			
			set.addGame(currentGame);
		}
		// FIRST_PLAYER wins the set
		assertThat(set.getWinner()).isEqualTo(Player.FIRST_PLAYER);
		// Set Score 6:0
		assertThat(set.getSetScorePlayerOne()).isEqualTo(SetPlayerScore.SIX);
		assertThat(set.getSetScorePlayerTwo()).isEqualTo(SetPlayerScore.ZERO);

	}

	@Test
	void testScoreSet_6_0_SecondPlayerWin() {
		Set set = new Set();
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(Player.SECOND_PLAYER); // ( 15 - 0 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 30 - 0 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - 0 )
		listPlayers.add(Player.SECOND_PLAYER); // => Win
		while (set.getWinner() == null) {
			Game currentGame = new Game();	
			listPlayers.forEach(player -> {
				gameService.resolveScorePlayer(player, currentGame);
			});
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			set.addGame(currentGame);
		}
		// SECOND_PLAYER wins the set
		assertThat(set.getWinner()).isEqualTo(Player.SECOND_PLAYER);
		// Set Score 6:0
		assertThat(set.getSetScorePlayerOne()).isEqualTo(SetPlayerScore.ZERO);
		assertThat(set.getSetScorePlayerTwo()).isEqualTo(SetPlayerScore.SIX);
	}

	@Test
	void testScoreSet_7_5_FirstPlayerWin() {
		Set set = new Set();
		List<Player> listPlayersP1 = new ArrayList<>();
		listPlayersP1.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayersP1.add(Player.FIRST_PLAYER); // ( 30 - 0 )
		listPlayersP1.add(Player.FIRST_PLAYER); // ( 40 - 0 )
		listPlayersP1.add(Player.FIRST_PLAYER); // => Win
		List<Player> listPlayersP2 = new ArrayList<>();
		listPlayersP2.add(Player.SECOND_PLAYER); // ( 0 - 15 )
		listPlayersP2.add(Player.SECOND_PLAYER); // ( 0 - 30 )
		listPlayersP2.add(Player.SECOND_PLAYER); // ( 0 - 40 )
		listPlayersP2.add(Player.SECOND_PLAYER); // => Win
		Integer setIndex = 0;
		while (set.getWinner() == null) {
			Game currentGame = new Game();
			listPlayersP1.forEach(player -> {
				gameService.resolveScorePlayer(player, currentGame);
			});
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			set.addGame(currentGame);
			// We stop incrementing set scores when Player.SECOND_PLAYER reaches
			// score 5
			if (setIndex < 5) {
				Game currentGameDeux = new Game();
				listPlayersP2.forEach(player -> {
					gameService.resolveScorePlayer(player, currentGameDeux);
				});
				setService.resolveScorePlayer(currentGameDeux.getWinner(), set);
				set.addGame(currentGameDeux);
			}
			setIndex++;
		}
		// FIRST_PLAYER wins the set
		assertThat(set.getWinner()).isEqualTo(Player.FIRST_PLAYER);
		// Set Score 7:5
		assertThat(set.getSetScorePlayerOne()).isEqualTo(SetPlayerScore.SEVEN);
		assertThat(set.getSetScorePlayerTwo()).isEqualTo(SetPlayerScore.FIVE);
	}

	@Test
	void testScoreSet_7_5_SecondPlayerWin() {
		Set set = new Set();
		List<Player> listPlayersP1 = new ArrayList<>();
		listPlayersP1.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayersP1.add(Player.FIRST_PLAYER); // ( 30 - 0 )
		listPlayersP1.add(Player.FIRST_PLAYER); // ( 40 - 0 )
		listPlayersP1.add(Player.FIRST_PLAYER); // => Win
		List<Player> listPlayersP2 = new ArrayList<>();
		listPlayersP2.add(Player.SECOND_PLAYER); // ( 0 - 15 )
		listPlayersP2.add(Player.SECOND_PLAYER); // ( 0 - 30 )
		listPlayersP2.add(Player.SECOND_PLAYER); // ( 0 - 40 )
		listPlayersP2.add(Player.SECOND_PLAYER); // => Win
		Integer setIndex = 0;
		while (set.getWinner() == null) {
			// We stop incrementing set scores when Player.FIRST_PLAYER reaches score
			// 5
			if (setIndex < 5) {
				Game currentGame = new Game();
				for (Player player : listPlayersP1) {
					gameService.resolveScorePlayer(player, currentGame);
				}
				setService.resolveScorePlayer(currentGame.getWinner(), set);
			}
			Game currentGame = new Game();
			for (Player player : listPlayersP2) {
				gameService.resolveScorePlayer(player, currentGame);

			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			setIndex++;
		}
		// FIRST_PLAYER wins the set
		assertThat(set.getWinner()).isEqualTo(Player.SECOND_PLAYER);
		// Set Score 7:5
		assertThat(set.getSetScorePlayerOne()).isEqualTo(SetPlayerScore.FIVE);
		assertThat(set.getSetScorePlayerTwo()).isEqualTo(SetPlayerScore.SEVEN);
	}

	@Test
	void testSetWinP1_6_6_tieBreak() {
		Set set = new Set();
		List<Player> testQuickestGameWinP1 = new ArrayList<>();
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 30 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 40 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // => Win

		List<Player> testQuickestGameWinP2 = new ArrayList<>();
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 15 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 30 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 40 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // => Win

		List<Player> testTieBreakP1 = new ArrayList<>();
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 1 - 0 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 2 - 0 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 3 - 0 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 4 - 0 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 5 - 0 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 6 - 0 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 7 - 0 ) => Win

		Integer setIndex = 0;
		// We stop incrementing set scores when scores reach score ( 6 - 6)
		while (set.getWinner() == null && setIndex < 6) {
			Game currentGame = new Game();
			for (Player player : testQuickestGameWinP1) {
				gameService.resolveScorePlayer(player, currentGame);
			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			
			
			
			 currentGame = new Game();
			for (Player player : testQuickestGameWinP2) {
				gameService.resolveScorePlayer(player, currentGame);
			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			setIndex++;
		}

		// Play Tie Break game
		for (Player player : testTieBreakP1) {
			setService.resolveScorePlayer(player, set);
		}
		// FIRST_PLAYER wins the set
		assertThat(set.getWinner()).isEqualTo(Player.FIRST_PLAYER);
	}

	@Test
	void testSetWinP2_6_6_tieBreak() {
		Set set = new Set();
		List<Player> testQuickestGameWinP1 = new ArrayList<>();
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 30 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 40 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // => Win
		List<Player> testQuickestGameWinP2 = new ArrayList<>();
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 15 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 30 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 40 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // => Win
		List<Player> testTieBreakP2 = new ArrayList<>();
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 0 - 1 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 0 - 2 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 0 - 3 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 0 - 4 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 0 - 5 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 0 - 6 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 0 - 7 ) => Win
		Integer setIndex = 0;
		// We stop incrementing set scores when scores reach score ( 6 - 6)
		while (set.getWinner() == null && setIndex < 6) {
			Game currentGame = new Game();
			for (Player player : testQuickestGameWinP1) {
				gameService.resolveScorePlayer(player,currentGame);
			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			 currentGame = new Game();
			for (Player player : testQuickestGameWinP2) {
				gameService.resolveScorePlayer(player, currentGame);
			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			setIndex++;
		}
		// Play Tie Break game until score ( 0 - 7 )
		for (Player player : testTieBreakP2) {
			setService.resolveScorePlayer(player, set);
		}
		// FIRST_PLAYER wins the set
		assertThat(set.getWinner()).isEqualTo(Player.SECOND_PLAYER);
	}

	@Test
	void testSetWinP1_6_6_tieBreak_extended() {
		Set set = new Set();
		List<Player> testQuickestGameWinP1 = new ArrayList<>();
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 30 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 40 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // => Win
		List<Player> testQuickestGameWinP2 = new ArrayList<>();
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 15 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 30 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 40 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // => Win
		List<Player> testTieBreakP1 = new ArrayList<>();
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 1 - 0 )
		testTieBreakP1.add(Player.SECOND_PLAYER); // ( 1 - 1 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 2 - 1 )
		testTieBreakP1.add(Player.SECOND_PLAYER); // ( 2 - 2 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 3 - 2 )
		testTieBreakP1.add(Player.SECOND_PLAYER); // ( 3 - 3 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 4 - 3 )
		testTieBreakP1.add(Player.SECOND_PLAYER); // ( 4 - 4 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 5 - 4 )
		testTieBreakP1.add(Player.SECOND_PLAYER); // ( 5 - 5 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 6 - 5 )
		testTieBreakP1.add(Player.SECOND_PLAYER); // ( 6 - 6 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 7 - 6 )
		testTieBreakP1.add(Player.SECOND_PLAYER); // ( 7 - 7 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 8 - 7 )
		testTieBreakP1.add(Player.FIRST_PLAYER); // ( 9 - 7 ) => Win
		Integer setIndex = 0;
		// We stop incrementing set scores when scores reach score ( 6 - 6)
		while (set.getWinner() == null && setIndex < 6) {
			Game currentGame = new Game();
			for (Player player : testQuickestGameWinP1) {
				gameService.resolveScorePlayer(player, currentGame);
			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			 currentGame = new Game();
			for (Player player : testQuickestGameWinP2) {
				gameService.resolveScorePlayer(player, currentGame);
			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			setIndex++;
		}
		// Play Tie Break game
		for (Player player : testTieBreakP1) {
			setService.resolveScorePlayer(player, set);
		}
		// FIRST_PLAYER wins the set
		assertThat(set.getWinner()).isEqualTo(Player.FIRST_PLAYER);
	}

	@Test
	void testSetWinP2_6_6_tieBreak_extended() {
		Set set = new Set();
		List<Player> testQuickestGameWinP1 = new ArrayList<>();
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 30 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // ( 40 - 0 )
		testQuickestGameWinP1.add(Player.FIRST_PLAYER); // => Win
		List<Player> testQuickestGameWinP2 = new ArrayList<>();
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 15 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 30 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // ( 0 - 40 )
		testQuickestGameWinP2.add(Player.SECOND_PLAYER); // => Win
		List<Player> testTieBreakP2 = new ArrayList<>();
		testTieBreakP2.add(Player.FIRST_PLAYER); // ( 1 - 0 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 1 - 1 )
		testTieBreakP2.add(Player.FIRST_PLAYER); // ( 2 - 1 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 2 - 2 )
		testTieBreakP2.add(Player.FIRST_PLAYER); // ( 3 - 2 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 3 - 3 )
		testTieBreakP2.add(Player.FIRST_PLAYER); // ( 4 - 3 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 4 - 4 )
		testTieBreakP2.add(Player.FIRST_PLAYER); // ( 5 - 4 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 5 - 5 )
		testTieBreakP2.add(Player.FIRST_PLAYER); // ( 6 - 5 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 6 - 6 )
		testTieBreakP2.add(Player.FIRST_PLAYER); // ( 7 - 6 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 7 - 7 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 7 - 8 )
		testTieBreakP2.add(Player.SECOND_PLAYER); // ( 7 - 9 ) => Win

		Integer setIndex = 0;
		// We stop incrementing set scores when scores reach score ( 6 - 6)
		while (set.getWinner() == null && setIndex < 6) {
			Game currentGame = new Game();
			for (Player player : testQuickestGameWinP1) {
				gameService.resolveScorePlayer(player, currentGame);
			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			 currentGame = new Game();
			for (Player player : testQuickestGameWinP2) {
				gameService.resolveScorePlayer(player, currentGame);
			}
			setService.resolveScorePlayer(currentGame.getWinner(), set);
			setIndex++;
		}
		// Play Tie Break game until score ( 0 - 7 )
		for (Player player : testTieBreakP2) {
			setService.resolveScorePlayer(player, set);
		}
		// SECOND_PLAYER wins the set
		assertThat(set.getWinner()).isEqualTo(Player.SECOND_PLAYER);
	}
}
