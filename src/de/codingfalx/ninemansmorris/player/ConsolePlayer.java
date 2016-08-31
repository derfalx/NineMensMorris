package de.codingfalx.ninemansmorris.player;

import de.codingfalx.ninemansmorris.game.FieldNode;
import de.codingfalx.ninemansmorris.game.Game;
import de.codingfalx.ninemansmorris.game.Gameboard;
import de.codingfalx.ninemansmorris.game.Move;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Kristoffer Schneider
 * @created 31.08.2016
 */
public class ConsolePlayer
        extends AbstractPlayer
{
  //<editor-fold desc="Fields">
  private final Scanner consoleScanner;
  private static final Pattern userMoveInputPattern = Pattern.compile( "\\A[A-G][1-7]->[A-G][1-7]\\Z" );
  private static final Pattern userPickSetInputPattern = Pattern.compile( "\\A[A-G][1-7]\\Z" );
  private static final String NUMBER_FORMAT_EXCEPTION_MESSAGE = "Coordinates need to be numbers";

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public ConsolePlayer ( String playerName )
  {
    super( playerName );
    this.consoleScanner = new Scanner( System.in );
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  @Override
  public Move getMove ( Gameboard gameboard )
  {
    return this.getFlyingOrNormalMove( gameboard, false );
  }

  public Move getFlyingOrNormalMove ( Gameboard gameboard, boolean isFyling )
  {
    boolean validUserInput = false;
    String input;
    Move move = null;
    String flying = isFyling ? " flying" : "";
    do
    {
      // Let the user put the coordinates in with x first, since most of the people are used to this way.
      input = this.read( String.format("Please enter you%s next move (Format: YX -> YX", flying) );
      input = input.replace( " ", "" );
      if ( !ConsolePlayer.userMoveInputPattern.matcher( input ).matches() )
      {
        System.out.println( "Invalid input!" );
      }
      else
      {
        String coordsInput[] = input.split( "->" );
        FieldNode from = gameboard.getFieldNodeByName( coordsInput[0] );
        FieldNode to = gameboard.getFieldNodeByName( coordsInput[1] );
        move = new Move (from, to);
        validUserInput = true;
      }
    }
    while ( !validUserInput );

    return move;
  }

  @Override
  public Move getFlyingMove ( Gameboard gameboard )
  {
    return this.getFlyingOrNormalMove( gameboard, true );
  }

  @Override
  public Move getPick ( Gameboard gameboard )
  {
    FieldNode node = this.getPickSet( "Please enter the coordinates of the piece you want to pick. Format: YX", gameboard );
    return new Move( node, null );
  }

  @Override
  public Move getSet ( Gameboard gameboard )
  {
    FieldNode node = this.getPickSet( "Please enter the coordinates where you want to place your piece. Format: YX", gameboard );
    return new Move( null, node );
  }


  private FieldNode getPickSet( String prompt, Gameboard gameboard )
  {
    boolean validUserInput = false;
    String input;
    FieldNode node = null;

    do
    {
      input = this.read( prompt );
      if ( !ConsolePlayer.userPickSetInputPattern.matcher( input ).matches() )
      {
        System.out.println( "Invalid input!" );
      }
      else
      {
        node = gameboard.getFieldNodeByName( input );
        validUserInput = true;
      }
    }
    while ( !validUserInput );

    return node;
  }

  private String read ( String prompt )
  {
    System.out.println( prompt );
    String input = this.consoleScanner.nextLine();
    input = input.trim();
    return input;
  }
  //</editor-fold>
}
