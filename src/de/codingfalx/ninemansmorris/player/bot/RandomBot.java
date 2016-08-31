package de.codingfalx.ninemansmorris.player.bot;

import de.codingfalx.ninemansmorris.game.FieldNode;
import de.codingfalx.ninemansmorris.game.GameState.Player;
import de.codingfalx.ninemansmorris.game.Gameboard;
import de.codingfalx.ninemansmorris.game.Move;
import de.codingfalx.ninemansmorris.player.AbstractPlayer;

import java.util.ArrayList;

/**
 * @author falx
 * @created 31.08.2016
 */
public class RandomBot
        extends AbstractPlayer
{

  //<editor-fold desc="Constructor">

  public RandomBot ()
  {
    super( "JustARandomizedBot" );
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  @Override public Move getMove ( final Gameboard gameboard )
  {
    ArrayList<FieldNode> ownedNodes = this.scanForOwnedNodes( gameboard );
    boolean moveFound = false;
    FieldNode fromNode = null;
    FieldNode toNode = null;
    do
    {
      fromNode = this.chooseRandomField( ownedNodes );
      int rndDirection = (int) ( Math.random() * 35496587 ) % 4;
      switch ( rndDirection )
      {
        case 0:
          toNode = fromNode.getAbove();
        case 1:
          toNode = fromNode.getBelow();
        case 2:
          toNode = fromNode.getLeft();
        case 3:
          toNode = fromNode.getRight();
      }
      if ( toNode != null && toNode.getPlayerOnThisField() == null )
        moveFound = true;
    }
    while ( !moveFound );

    return new Move( fromNode, toNode );
  }

  @Override public Move getFlyingMove ( final Gameboard gameboard )
  {
    ArrayList<FieldNode> ownedNodes = this.scanForOwnedNodes( gameboard );
    ArrayList<FieldNode> emptyNodes = this.scanForEmptyNodes( gameboard );
    FieldNode fromNode = this.chooseRandomField( ownedNodes );
    FieldNode toNode = this.chooseRandomField( emptyNodes );
    return new Move( fromNode, toNode );
  }

  @Override public Move getPick ( final Gameboard gameboard )
  {
    ArrayList<FieldNode> enemyNodes = this.scanForEnemyPieces( gameboard );
    FieldNode targetNode = this.chooseRandomField( enemyNodes );
    return new Move( targetNode, null );
  }

  @Override public Move getSet ( final Gameboard gameboard )
  {
    ArrayList<FieldNode> emptyNodes = this.scanForEmptyNodes( gameboard );
    FieldNode targetNode = this.chooseRandomField( emptyNodes );
    return new Move( null, targetNode );
  }

  protected ArrayList<FieldNode> scanForOwnedNodes ( final Gameboard gameboard )
  {
    return this.scanForFields( gameboard, this.thisPlayer );
  }

  protected ArrayList<FieldNode> scanForEmptyNodes ( final Gameboard gameboard )
  {
    return this.scanForFields( gameboard, null );
  }

  protected ArrayList<FieldNode> scanForEnemyPieces ( final Gameboard gameboard )
  {
    Player enemy;
    if ( this.thisPlayer == Player.PLAYER_ONE )
      enemy = Player.PLAYER_TWO;
    else
      enemy = Player.PLAYER_ONE;

    return this.scanForFields( gameboard, enemy );
  }

  protected ArrayList<FieldNode> scanForFields ( final Gameboard gameboard, final Player player )
  {
    ArrayList<FieldNode> nodeList = new ArrayList<>();
    for ( FieldNode node : gameboard.nodes )
    {
      if ( node.getPlayerOnThisField() == player )
        nodeList.add( node );
    }

    return nodeList;
  }

  protected FieldNode chooseRandomField ( final ArrayList<FieldNode> nodes )
  {
    int randomIndex = (int) ( Math.random() * 100 ) % nodes.size();
    return nodes.get( randomIndex );
  }
  //</editor-fold>
}
