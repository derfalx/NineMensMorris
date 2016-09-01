package de.codingfalx.ninemansmorris.game;

import de.codingfalx.ninemansmorris.player.AbstractPlayer;
import de.codingfalx.ninemansmorris.player.ConsolePlayer;
import de.codingfalx.ninemansmorris.player.bot.RandomBot;

import java.util.Scanner;

/**
 * @author Kristoffer Schneider
 * @created 31.08.2016
 */
public class Game
{
  //<editor-fold desc="Fields">
  public static final Game instance = new Game();

  private Match currentMatch;
  private final AbstractPlayer playerOne;
  private final AbstractPlayer playerTwo;

  private static final String BANNER = "==========================================================\n" +
                                       " __          ________ _      _____ ____  __  __ ______    \n" +
                                       " \\ \\        / |  ____| |    / ____/ __ \\|  \\/  |  ____|   \n" +
                                       "  \\ \\  /\\  / /| |__  | |   | |   | |  | | \\  / | |__      \n" +
                                       "   \\ \\/  \\/ / |  __| | |   | |   | |  | | |\\/| |  __|     \n" +
                                       "    \\  /\\  /  | |____| |___| |___| |__| | |  | | |____    \n" +
                                       "     \\/  \\/   |______|______\\_____\\____/|_|  |_|______|   \n" +
                                       "                   _______ ____                           \n" +
                                       "                  |__   __/ __ \\                          \n" +
                                       "                     | | | |  | |                          \n" +
                                       "                     | | | |  | |                          \n" +
                                       "                     | | | |__| |                          \n" +
                                       "                     |_|  \\____/                          \n" +
                                       "    ___    __  __            _       __  __                 _   \n" +
                                       "   / _ \\  |  \\/  |          ( )     |  \\/  |               (_) \n" +
                                       "  | (_) | | \\  / | ___ _ __ |/ ___  | \\  / | ___  _ __ _ __ _ ___  \n" +
                                       "   \\__, | | |\\/| |/ _ | '_ \\  / __| | |\\/| |/ _ \\| '__| '__| / __| \n" +
                                       "     / /  | |  | |  __| | | | \\__ \\ | |  | | (_) | |  | |  | \\__ \\ \n" +
                                       "    /_/   |_|  |_|\\___|_| |_| |___/ |_|  |_|\\___/|_|  |_|  |_|___/ \n" +
                                       " written by \n" +
                                       "          Kristoffer 'falx' Schneider \n" +
                                       " within 3 hours\n" +
                                       " and not fully finished \n"+
                                       "==========================================================\n";


  //</editor-fold>

  //<editor-fold desc="Constructor">

  private Game()
  {
    System.out.println( Game.BANNER );
    System.out.println(
            "I'm sorry, but 4 Hours haven't been enough to write an real AI so you're only playing against a bot who " +
                    "makes random actions.. :(" );
    Scanner scanner = new Scanner( System.in );
    System.out.println( "What's you player name? " );
    String name = scanner.nextLine();
    this.playerOne = new ConsolePlayer( name );
    this.playerTwo = new RandomBot( );
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  public void startNewMatch()
  {
    this.createNewMatch();
    this.currentMatch.runGame();
  }

  public void createNewMatch ()
  {

    this.currentMatch = new Match( this.playerOne, this.playerTwo);
  }

  public Match getCurrentMatch()
  {
    return this.currentMatch;
  }

  //</editor-fold>
}
