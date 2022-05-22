package kata.tennis.service;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kata.tennis.entity.Game;
import kata.tennis.entity.Player;
import kata.tennis.entity.score.PlayerScore;

/**
 * @author karim.khoule
 *
 */
@SpringBootTest
class GameServiceTest {
	@Autowired
	private GameService service;
	@Test
	void testScore_40_0_FirstPlayerWin() { 
		Game game = new Game();
		List<Player> listPlayer = new ArrayList<>();
		listPlayer.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayer.add(Player.FIRST_PLAYER); // ( 30 - 0 )
		listPlayer.add(Player.FIRST_PLAYER); // ( 40 - 0 )
		listPlayer.add(Player.FIRST_PLAYER); // => Win
		listPlayer.forEach(player->
		{
			service.resolveScorePlayer(player, game);
		});
		// FIRST_PLAYER wins the game
		assertThat(game.getWinner()).isEqualTo(Player.FIRST_PLAYER);
	}
	
	
	@Test
	 void testScore_0_40_SecondPlayerWin() { 
		Game game = new Game();
		List<Player> listPlayer = new ArrayList<>();
		listPlayer.add(Player.SECOND_PLAYER); // ( 0 - 15 )
		listPlayer.add(Player.SECOND_PLAYER); // ( 0 - 30 )
		listPlayer.add(Player.SECOND_PLAYER); // ( 0 - 40 )
		listPlayer.add(Player.SECOND_PLAYER); // => Win
		listPlayer.forEach(player->
		{
			service.resolveScorePlayer(player, game);
		});
		// SECOND_PLAYER wins the game
		assertThat(game.getWinner()).isEqualTo(Player.SECOND_PLAYER);
	}

	@Test
	 void testScore_40_30_FirstPlayerWin() {
		Game game = new Game();
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 15 - 15 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 30 - 15 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 30 - 30 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 40 - 30 )
		listPlayers.add(Player.FIRST_PLAYER); // => Player1 Wins
		listPlayers.forEach(player->
		{
			service.resolveScorePlayer(player, game);
		});
		// FIRST_PLAYER wins the game
		assertThat(game.getWinner()).isEqualTo(Player.FIRST_PLAYER);
	}

	@Test
	 void testScore_30_40_SecondPlayerWin() {
		Game game = new Game();
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(Player.SECOND_PLAYER); // ( 0 - 15 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 15 - 15 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 15 - 30 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 30 - 30 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 30 - 40 )
		listPlayers.add(Player.SECOND_PLAYER); // => Player2 Wins
		listPlayers.forEach(player->
		{
			service.resolveScorePlayer(player, game);
		});
		// SECOND_PLAYER wins the game
		assertThat(game.getWinner()).isEqualTo(Player.SECOND_PLAYER);
	}

	@Test
	 void testScore_40_40_Deuce_FirstPlayerWin() { 
		Game game = new Game();
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 15 - 15 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 30 - 15 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 30 - 30 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 40 - 30 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - 40 )
		listPlayers.add(Player.FIRST_PLAYER); // ( ADV - 40 )
		listPlayers.add(Player.FIRST_PLAYER); // => Player1 Wins
		listPlayers.forEach(player->
		{
			service.resolveScorePlayer(player, game);
		});
		// FIRST_PLAYER wins the game
		assertThat(game.getWinner()).isEqualTo(Player.FIRST_PLAYER);
	}

	@Test
	 void testScore_40_40_Deuce_SecondPlayerWin() { 
		Game game = new Game();
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 15 - 15 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 30 - 15 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 30 - 30 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 40 - 30 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - 40 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - ADV )
		listPlayers.add(Player.SECOND_PLAYER); // => Player2 Wins
		listPlayers.forEach(player->
		{
			service.resolveScorePlayer(player, game);
		});
		// SECOND_PLAYER wins the game
		assertThat(game.getWinner()).isEqualTo(Player.SECOND_PLAYER);
	}

	@Test
	 void testScore_40_40_DeuceExtended_FirstPlayerWin() {
		Game game = new Game();
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 15 - 15 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 30 - 15 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 30 - 30 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 40 - 30 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - 40 )
		listPlayers.add(Player.FIRST_PLAYER); // ( ADV - 40 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - 40 )
		listPlayers.add(Player.FIRST_PLAYER); // ( ADV - 40 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - 40 )
		listPlayers.add(Player.FIRST_PLAYER); // ( ADV - 40 )
		listPlayers.add(Player.FIRST_PLAYER); // => Player1 Wins
		listPlayers.forEach(player->
		{
			service.resolveScorePlayer(player, game);
		});
		// FIRST_PLAYER wins the game
		assertThat(game.getWinner()).isEqualTo(Player.FIRST_PLAYER);
	}
	@Test
	 void testScore_40_40_DeuceExtended_SecondPlayerWin() { 
		Game game = new Game();
		List<Player> listPlayers = new ArrayList<>();
		listPlayers.add(Player.FIRST_PLAYER); // ( 15 - 0 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 15 - 15 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 30 - 15 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 30 - 30 )
		listPlayers.add(Player.FIRST_PLAYER); // ( 40 - 30 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - 40 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - ADV )
		listPlayers.add(Player.FIRST_PLAYER); // ( 40 - 40 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - ADV )
		listPlayers.add(Player.FIRST_PLAYER); // ( 40 - 40 )
		listPlayers.add(Player.SECOND_PLAYER); // ( 40 - ADV )
		listPlayers.add(Player.SECOND_PLAYER); // => Player2 Wins
		listPlayers.forEach(player->
		{
			service.resolveScorePlayer(player, game);
		});
		// SECOND_PLAYER wins the game
		assertThat(game.getWinner()).isEqualTo(Player.SECOND_PLAYER);
	}
	
	@Test
	void testScore_0_0_StartGame() { 
		Game game = new Game();
		assertThat(game.getScorePlayerOne()).isEqualTo(PlayerScore.ZERO);
		assertThat(game.getScorePlayerTwo()).isEqualTo(PlayerScore.ZERO);
	}

}