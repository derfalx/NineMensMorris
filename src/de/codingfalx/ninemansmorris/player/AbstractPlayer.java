package de.codingfalx.ninemansmorris.player;


import de.codingfalx.ninemansmorris.game.Game;
import de.codingfalx.ninemansmorris.game.GameState.Player;
import de.codingfalx.ninemansmorris.game.Gameboard;
import de.codingfalx.ninemansmorris.game.Move;

/**
 * @author Kristoffer Schneider
 * @created 31.08.2016
 */
public abstract class AbstractPlayer
{
  //<editor-fold desc="Fields">

  protected Player thisPlayer;
  private String playerName;
  private int leftPieces;
  private int placedPieces;
  private int lostPieces;


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public AbstractPlayer( final String playerName )
  {
    this.setPlayerName( playerName );

    this.leftPieces = 9;
    this.placedPieces = 0;
    this.lostPieces = 0;
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  public abstract Move getMove ( final Gameboard gameboard );

  public abstract Move getFlyingMove ( final Gameboard gameboard );

  public abstract Move getPick ( final Gameboard gameboard );

  public abstract Move getSet( final Gameboard gameboard );

  public final void setPiece()
  {
    this.leftPieces--;
    this.placedPieces++;
  }

  public final void lostPiece()
  {
    this.placedPieces--;
    this.lostPieces++;
  }

  public final int getLeftPieces()
  {
    return this.leftPieces;
  }

  public final int getPlacedPieces()
  {
    return this.placedPieces;
  }

  public final int getLostPieces()
  {
    return this.lostPieces;
  }

  public void setPlayerName ( final String playerName )
  {
    this.playerName = playerName;
  }

  public String getPlayerName ()
  {
    return this.playerName;
  }

  public void setThisPlayer ( final Player player )
  {
    if ( player == null )
      throw new Error( "No No No, player must be not null!" );
    this.thisPlayer = player;
  }
  //</editor-fold>
}
