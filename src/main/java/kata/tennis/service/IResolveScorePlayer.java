package kata.tennis.service;
import kata.tennis.entity.Player;
/**
 * @author karim.khoule
 *
 * @param <T>
 */
@FunctionalInterface
public interface IResolveScorePlayer <T>{
	/**
	 * @param player
	 * @param entity
	 */
	public void resolveScorePlayer(final Player player,T entity);
}
