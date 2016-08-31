package de.codingfalx.ninemansmorris.game;

import de.codingfalx.ninemansmorris.game.GameState.Player;
import de.codingfalx.ninemansmorris.player.AbstractPlayer;

/**
 * @author falx
 * @created 31.08.2016
 */
public class Match
{
  //<editor-fold desc="Fields">

  private static final String DRAW_BANNER =


 "  ______ ______ ______ ______ ______ ______ ______ ______  \n" +
 " |______|______|______|______|______|______|______|______| \n" +
 "  ______ ______ ______ ______ ______ ______ ______ ______  \n" +
 " |______|______|______|______|______|______|______|______| \n" +
 "         _____  _____      __          __                  \n" +
 "        |  __ \\|  __ \\    /\\ \\        / /                  \n" +
 "        | |  | | |__) |  /  \\ \\  /\\  / /                   \n" +
 "        | |  | |  _  /  / /\\ \\ \\/  \\/ /                    \n" +
 "        | |__| | | \\ \\ / ____ \\  /\\  /                     \n" +
 "        |_____/|_|  \\_/_/    \\_\\/  \\/                      \n" +
 "  ______ ______ ______ ______ ______ ______ ______ ______  \n" +
 " |______|______|______|______|______|______|______|______| \n" +
 "  ______ ______ ______ ______ ______ ______ ______ ______  \n" +
 " |______|______|______|______|______|______|______|______| \n";

  private static final String PLAYER_ONE_WIN =  " ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +

          "|______|______|______|______|______|______|______|______|______|______|______|\n" +

          " ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +

          "|______|______|______|______|______|______|______|______|______|______|______|\n" +

          "           __     ______  _    _  __          ______  _   _ _                 \n" +

          "           \\ \\   / / __ \\| |  | | \\ \\        / / __ \\| \\ | | |                \n" +

          "            \\ \\_/ | |  | | |  | |  \\ \\  /\\  / | |  | |  \\| | |                \n" +

          "             \\   /| |  | | |  | |   \\ \\/  \\/ /| |  | | . ` | |                \n" +

          "              | | | |__| | |__| |    \\  /\\  / | |__| | |\\  |_|                \n" +

          "              |_|  \\____/ \\____/      \\/  \\/   \\____/|_| \\_(_)                \n" +

          " ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +

          "|______|______|______|______|______|______|______|______|______|______|______|\n" +

          " ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +

          "|______|______|______|______|______|______|______|______|______|______|______|\n";

  private static final String PLAYER_TWO_WIN = "  ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +
          " |______|______|______|______|______|______|______|______|______|______|______|\n" +
          "  ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +
          " |______|______|______|______|______|______|______|______|______|______|______|\n" +
          "            __     ______  _    _      _      ____   _____ _______ _           \n" +
          "            \\ \\   / / __ \\| |  | |    | |    / __ \\ / ____|__   __| |          \n" +
          "             \\ \\_/ | |  | | |  | |    | |   | |  | | (___    | |  | |          \n" +
          "              \\   /| |  | | |  | |    | |   | |  | |\\___ \\   | |  | |          \n" +
          "               | | | |__| | |__| |    | |___| |__| |____) |  | |  |_|          \n" +
          "               |_|  \\____/ \\____/     |______\\____/|_____/   |_|  (_)          \n" +
          "  ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +
          " |______|______|______|______|______|______|______|______|______|______|______|\n" +
          "  ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ \n" +
          " |______|______|______|______|______|______|______|______|______|______|______|\n";






  private GameState currentState;
  private GameState.Player currentPlayer;
  private Gameboard gameboard;
  private int turnsPlayed;

  private final AbstractPlayer playerOne;
  private final AbstractPlayer playerTwo;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  private final  AbstractPlayer toPlayer ( final GameState.Player player )
  {
    if ( player == Player.PLAYER_ONE )
      return this.playerOne;
    else
      return this.playerTwo;
  }

  public Match ( final AbstractPlayer playerOne, final AbstractPlayer playerTwo )
  {
    this.playerOne = playerOne;
    this.playerOne.setThisPlayer( Player.PLAYER_ONE );
    this.playerTwo = playerTwo;
    this.playerTwo.setThisPlayer( Player.PLAYER_TWO );
    this.gameboard = new Gameboard();
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  public void runGame()
  {
    this.currentState = GameState.PLACING;
    this.currentPlayer = Player.PLAYER_ONE;
    this.turnsPlayed = 0;
    boolean validTurn = false;
    Player nextPlayer;
    AbstractPlayer nextPlayerAbstract;
    AbstractPlayer player;

    do
    {
      nextPlayer = ( this.currentPlayer == Player.PLAYER_ONE ) ? Player.PLAYER_TWO : Player.PLAYER_ONE;
      nextPlayerAbstract = ( nextPlayer == Player.PLAYER_ONE ) ? this.playerOne : this.playerTwo;
      player = this.toPlayer ( this.currentPlayer );
      Move move = null;

      System.out.println( player.getPlayerName() + ":" );
      gameboard.print();

      if ( this.currentState == GameState.PLACING )
      {
        move = player.getSet( this.gameboard );
        validTurn = this.gameboard.setPiece( move, this.currentPlayer );
        if ( validTurn )
          player.setPiece();
      }
      if ( this.currentState == GameState.MOVING || this.currentState == GameState.FLYING )
      {
        move = player.getMove( this.gameboard );
        boolean freeMove = this.currentState == GameState.FLYING && player.getPlacedPieces() == 3;
        validTurn = this.gameboard.makeMove( move, this.currentPlayer, freeMove );
      }

      boolean isMill = this.gameboard.isPieceInMill( move.nodeTo );
      if( isMill )
      {
        do
        {
          move = player.getPick( this.gameboard );
          validTurn = this.gameboard.pickPiece( move, this.currentPlayer );
          if ( validTurn )
            nextPlayerAbstract.lostPiece();
        }
        while ( !validTurn );

      }
      if ( validTurn )
      {
        if ( this.currentState == GameState.PLACING &&
                player.getLeftPieces() == 0 &&
                nextPlayerAbstract.getLeftPieces() == 0 )
        {
          this.currentState = GameState.MOVING;
        }
        else if ( this.currentState == GameState.MOVING &&
                ( player.getPlacedPieces() == 3 || nextPlayerAbstract.getPlacedPieces() == 3 ) )
        {
          this.currentState = GameState.FLYING;
        }
        this.turnsPlayed++;
        this.currentPlayer = nextPlayer;
      }
      else
        System.out.println( "Invalid Move!" );

    }while(!hasEnded());

    if (this.turnsPlayed >= 50)
    {
      System.out.println( DRAW_BANNER );
    }
    else if ( this.playerOne.getLostPieces() == 9 )
    {
      System.out.println( PLAYER_TWO_WIN );
    }
    else if ( this.playerTwo.getLostPieces() == 9)
    {
      System.out.println( PLAYER_ONE_WIN );

    }
  }

  public Player getCurrentPlayer()
  {
    return this.currentPlayer;
  }

  public GameState getCurrentState()
  {
    return this.currentState;
  }

  public Gameboard getGameboard()
  {
    return this.gameboard;
  }

  private boolean hasEnded ()
  {
    return this.playerTwo.getLostPieces() == 9 || this.playerOne.getLostPieces() == 9 || this.turnsPlayed == 50;
  }

  //</editor-fold>
}
