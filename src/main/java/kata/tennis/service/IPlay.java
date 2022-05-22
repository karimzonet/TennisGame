package kata.tennis.service;

import kata.tennis.entity.Player;

/**
 * @author karim.khoule
 *
 * @param <T>
 */
@FunctionalInterface
public interface IPlay <T> {

	/**
	 * @param entity
	 * @return
	 */
	public Player play(T entity);
}
