package de.codingfalx.ninemansmorris.game;

/**
 * Represents the state of the current match.
 * The Basic States are:
 * <ol>
 *   <li>Placing - </li>
 *   <li>Moving - </li>
 *   <li>Flying - </li>
 * </ol>
 * @author Kristoffer Schneider
 * @Created 31.08.2016.
 */
public enum GameState
{
  PLACING,
  MOVING,
  FLYING;

  public enum Player
  {
    PLAYER_ONE,
    PLAYER_TWO;
  }
}
