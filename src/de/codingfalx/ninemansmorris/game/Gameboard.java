package de.codingfalx.ninemansmorris.game;

import de.codingfalx.ninemansmorris.game.GameState.Player;

import java.util.HashMap;
import java.util.function.Function;

/**
 * @author Kristoffer Schneider
 * @created 31.08.2016
 */
public class Gameboard
{
  //<editor-fold desc="Fields">
  public static final Function<Player, Character> playerToChar= p -> p == null ? 'o' : p == Player.PLAYER_ONE ? 'x' : 'y';

  public static final String[] POSITION_NAMES = { "A1", "A4", "A7",
          "B2", "B4", "B6",
          "C3", "C4", "C5",
          "D1", "D2", "D3",
          "D5", "D6", "D7",
          "E3", "E4", "E5",
          "F2", "F4", "F6",
          "G1", "G4", "G7" };

  public final FieldNode[] nodes;
  private final HashMap<String, FieldNode> nodeNameMap;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public Gameboard ()
  {
    this.nodes = new FieldNode[24];
    this.nodeNameMap = new HashMap<>();
    this.setUpNodes();
  }


  //</editor-fold>

  //<editor-fold desc="Methods">

  /**
   * Makes a move on the Gameboard.
   * The Move must be valid: @code{move.nodeFrom} must be owned by @code{player}
   * and @code{move.nodeTo} must be @code{null}
   *
   * @param move
   *         the move to make
   * @param player
   *         the player who does the move
   *
   * @return true if the move is valid and has been done
   */
  public final boolean makeMove ( Move move, Player player, boolean isFreeMove )
  {
    if ( move.nodeFrom == null || move.nodeTo == null ||
            move.nodeFrom.getPlayerOnThisField() != player ||
            move.nodeTo.getPlayerOnThisField() != null )
      return false;

    boolean valid = move.nodeFrom.isAdjacentTo( move.nodeTo ) || isFreeMove;
    if ( valid )
    {
      move.nodeFrom.setPlayerOnThisField( null );
      move.nodeTo.setPlayerOnThisField( player );
    }
    return valid;
  }

  /**
   * Makes a pick from the Gameboard
   * The move must be valid: @code{move.nodeFrom} must be owned by @code{player}s opponent
   * and @code{move.nodeTo} must be @code{null}
   *
   * @param move
   *         the pick to make
   * @param player
   *         the player who does the pick
   *
   * @return true if the move is valid and has been done
   */
  public final boolean pickPiece ( Move move, Player player )
  {
    if ( move.nodeFrom == null || move.nodeTo != null ||
            move.nodeFrom.getPlayerOnThisField() == player ||
            move.nodeFrom.getPlayerOnThisField() == null )
      return false;

    boolean valid = !this.isPieceInMill( move.nodeFrom );
    if ( valid )
      move.nodeFrom.setPlayerOnThisField ( null );
    return valid;
  }

  public final boolean setPiece ( Move move, Player player )
  {
    if (move.nodeTo == null)
      return false;
    if (move.nodeTo.getPlayerOnThisField() == null)
    {
      move.nodeTo.setPlayerOnThisField( player );
      return true;
    }
    return false;
  }

  /**
   * Checks if the given node is placed in a mill.
   *
   * @param node the node to check
   * @return true if it is in a mill
   */
  public final boolean isPieceInMill ( FieldNode node )
  {
    if (node == null)
      return false;
    Player player = node.getPlayerOnThisField();
    if ( player == null )
      return false;

    boolean isInMill = false;

    FieldNode above = node.getAbove();
    FieldNode below = node.getBelow();
    FieldNode left = node.getLeft();
    FieldNode right = node.getRight();

    FieldNode nodeB = null;
    FieldNode nodeC = null;
    FieldNode nodeD = null;
    FieldNode nodeE = null;

    if ( below == null )
    {
      FieldNode aboveAbove = above.getAbove();
      nodeB = above;
      nodeC = aboveAbove;
    }
    else if ( above == null )
    {
      FieldNode belowBelow = below.getBelow();
      nodeB = below;
      nodeC = belowBelow;
    }

    if ( left == null )
    {
      FieldNode rightRight = right.getRight();
      nodeD = right;
      nodeE = rightRight;
    }
    else if ( right == null )
    {
      FieldNode leftLeft = left.getLeft();
      nodeD = left;
      nodeE = leftLeft;
    }

    if ( nodeB == null && nodeC == null )
    {
      isInMill = this.ownedBySamePlayer( player, node, above, below );
    }

    if( nodeD == null && nodeE == null )
    {
      isInMill |= this.ownedBySamePlayer( player, node, left, right );
    }

    if ( !isInMill )
    {
      isInMill |= this.ownedBySamePlayer( player, node, nodeB, nodeC );
      isInMill |= this.ownedBySamePlayer( player, node, nodeD, nodeE );
    }

    return isInMill;
  }

  @Override
  public String toString()
  {
    String dualEmpty = " - - ";
    String singleHorizontalStart = "| ";
    String singleHorizontalEnd = " |\n";
    String singleEmpty = " - ";
    StringBuilder sb = new StringBuilder();

    sb.append( "  1 2 3 4 5 6 7\n" );

    sb.append('A');
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[0].getPlayerOnThisField() ) );
    sb.append( dualEmpty );
    sb.append( Gameboard.playerToChar.apply( this.nodes[1].getPlayerOnThisField() ) );
    sb.append( dualEmpty );
    sb.append( Gameboard.playerToChar.apply( this.nodes[2].getPlayerOnThisField() ) );
    sb.append( '\n' );

    sb.append( 'B' );
    sb.append( ' ' );
    sb.append( singleHorizontalStart );
    sb.append( Gameboard.playerToChar.apply( this.nodes[3].getPlayerOnThisField() ) );
    sb.append( singleEmpty );
    sb.append( Gameboard.playerToChar.apply( this.nodes[4].getPlayerOnThisField() ) );
    sb.append( singleEmpty );
    sb.append( Gameboard.playerToChar.apply( this.nodes[5].getPlayerOnThisField() ) );
    sb.append( singleHorizontalEnd );

    sb.append( 'C' );
    sb.append( ' ' );
    sb.append( singleHorizontalStart );
    sb.append( singleEmpty.substring( 1 ) );
    sb.append( Gameboard.playerToChar.apply( this.nodes[6].getPlayerOnThisField() ) );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[7].getPlayerOnThisField() ) );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[8].getPlayerOnThisField() ) );
    sb.append( singleEmpty );
    sb.append( singleHorizontalEnd.substring( 1 ) );

    sb.append( 'D' );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[9].getPlayerOnThisField() ) );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[10].getPlayerOnThisField() ) );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[11].getPlayerOnThisField() ) );
    sb.append ( "   " );
    sb.append( Gameboard.playerToChar.apply( this.nodes[12].getPlayerOnThisField() ) );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[13].getPlayerOnThisField() ) );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[14].getPlayerOnThisField() ) );
    sb.append( '\n' );

    sb.append( 'E' );
    sb.append( ' ' );
    sb.append( singleHorizontalStart );
    sb.append( singleEmpty.substring( 1 ) );
    sb.append( Gameboard.playerToChar.apply( this.nodes[15].getPlayerOnThisField() ) );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[16].getPlayerOnThisField() ) );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[17].getPlayerOnThisField() ) );
    sb.append( singleEmpty );
    sb.append( singleHorizontalEnd.substring( 1 ) );

    sb.append( 'F' );
    sb.append( ' ' );
    sb.append( singleHorizontalStart );
    sb.append( Gameboard.playerToChar.apply( this.nodes[18].getPlayerOnThisField() ) );
    sb.append( singleEmpty );
    sb.append( Gameboard.playerToChar.apply( this.nodes[19].getPlayerOnThisField() ) );
    sb.append( singleEmpty );
    sb.append( Gameboard.playerToChar.apply( this.nodes[20].getPlayerOnThisField() ) );
    sb.append( singleHorizontalEnd );

    sb.append( 'G' );
    sb.append( ' ' );
    sb.append( Gameboard.playerToChar.apply( this.nodes[21].getPlayerOnThisField() ) );
    sb.append( dualEmpty );
    sb.append( Gameboard.playerToChar.apply( this.nodes[22].getPlayerOnThisField() ) );
    sb.append( dualEmpty );
    sb.append( Gameboard.playerToChar.apply( this.nodes[23].getPlayerOnThisField() ) );
    sb.append( '\n' );

    return sb.toString();
  }


  public void print()
  {
    System.out.println( this.toString() );
  }

  public final FieldNode getFieldNodeByName ( String name )
  {
    return this.nodeNameMap.get( name );
  }

  /**
   * Checks if all given Nodes are owned by the given player
   *
   * @param player the player which they should owned by
   * @param nodeA
   * @param nodeB
   * @param nodeC
   * @return true if all nodes are owned by player
   */
  protected boolean ownedBySamePlayer ( Player player, FieldNode nodeA, FieldNode nodeB, FieldNode nodeC )
  {
    return nodeA != null && nodeA.getPlayerOnThisField() == player &&
            nodeB != null && nodeB.getPlayerOnThisField() == player &&
            nodeC != null && nodeC.getPlayerOnThisField() == player;
  }

  /**
   * Sets up all nodes needed to play nine man's morris
   */
  private void setUpNodes ()
  {
    final char x = '1';
    int offsetX = 0;
    final char y = 'A';
    int offsetY = 0;
    final int divisor = 3;
    for ( int i = 0; i < this.nodes.length; i++ )
    {
      this.nodes[i] = new FieldNode( Gameboard.POSITION_NAMES[i] );
    }

    this.nodes[0].setNodes( null, this.nodes[9], null, this.nodes[1] );
    this.nodes[1].setNodes( null, this.nodes[4], this.nodes[0], this.nodes[2] );
    this.nodes[2].setNodes( null, this.nodes[14], this.nodes[1], null );
    this.nodes[3].setNodes( null, this.nodes[10], null, this.nodes[4] );
    this.nodes[4].setNodes( this.nodes[1], this.nodes[7], this.nodes[3], this.nodes[5] );
    this.nodes[5].setNodes( null, this.nodes[13], this.nodes[4], null );
    this.nodes[6].setNodes( null, this.nodes[11], null, this.nodes[7] );
    this.nodes[7].setNodes( this.nodes[4], null, this.nodes[6], this.nodes[8] );
    this.nodes[8].setNodes( null, this.nodes[12], this.nodes[7], null );
    this.nodes[9].setNodes( this.nodes[0], this.nodes[21], null, this.nodes[10] );
    this.nodes[10].setNodes( this.nodes[3], this.nodes[21], this.nodes[9], this.nodes[11] );
    this.nodes[11].setNodes( this.nodes[6], this.nodes[15], this.nodes[10], null );
    this.nodes[12].setNodes( this.nodes[8], this.nodes[17], null, this.nodes[13] );
    this.nodes[13].setNodes( this.nodes[5], this.nodes[20], this.nodes[12], this.nodes[14] );
    this.nodes[14].setNodes( this.nodes[2], this.nodes[23], this.nodes[13], null );
    this.nodes[15].setNodes( this.nodes[11], null, null, this.nodes[16] );
    this.nodes[16].setNodes( null, this.nodes[19], this.nodes[15], this.nodes[17] );
    this.nodes[17].setNodes( this.nodes[12], null, this.nodes[16], null );
    this.nodes[18].setNodes( this.nodes[10], null, null, this.nodes[20] );
    this.nodes[19].setNodes( this.nodes[16], this.nodes[22], this.nodes[18], this.nodes[20] );
    this.nodes[20].setNodes( this.nodes[13], null, this.nodes[19], null );
    this.nodes[21].setNodes( this.nodes[9], null, null, this.nodes[22] );
    this.nodes[22].setNodes( this.nodes[19], null, this.nodes[21], this.nodes[23] );
    this.nodes[23].setNodes( this.nodes[14], null, this.nodes[22], null );

    for ( FieldNode node : this.nodes )
      this.nodeNameMap.put( node.positionName, node );
  }

  //</editor-fold>
}
