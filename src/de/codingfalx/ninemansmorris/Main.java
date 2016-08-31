package de.codingfalx.ninemansmorris;

import de.codingfalx.ninemansmorris.game.FieldNode;
import de.codingfalx.ninemansmorris.game.Game;
import de.codingfalx.ninemansmorris.game.Gameboard;

/**
 * @author falx
 * @created 31.08.2016
 */
public class Main
{
  public static void main(String[] args)
  {
    Game game = Game.instance;
    game.createNewMatch();
    game.startNewMatch();
  }
}
