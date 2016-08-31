package de.codingfalx.ninemansmorris.game;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import de.codingfalx.ninemansmorris.game.GameState.Player;

/**
 * @author falx
 * @created 31.08.2016
 */
public class FieldNode
{
  //<editor-fold desc="Fields">

  private FieldNode above;
  private FieldNode below;
  private FieldNode left;
  private FieldNode right;
  private boolean nodesHaveBeenSet;
  private Player playerOnThisField;
  public final String positionName;


  //</editor-fold>

  //<editor-fold desc="Constructor">

  public FieldNode ( final FieldNode above, final FieldNode below, final FieldNode left, final FieldNode right,
                     final Player playerOnThisField, String positionName )
  {
    this.positionName = positionName;
    this.above = above;
    this.below = below;
    this.left = left;
    this.right = right;
    this.playerOnThisField = playerOnThisField;
  }

  public FieldNode ( String positionName )
  {
    // 90h
    this.positionName = positionName;
  }

  //</editor-fold>

  //<editor-fold desc="Methods">

  /**
   * Sets the player which is currently on this field
   *
   * @param newPlayer
   *
   * @return The player on this field which is replaced by newPlayer
   */
  public Player setPlayerOnThisField ( Player newPlayer )
  {
    Player oldPlayer = this.playerOnThisField;
    this.playerOnThisField = newPlayer;
    return oldPlayer;
  }

  public Player getPlayerOnThisField ()
  {
    return this.playerOnThisField;
  }

  public boolean setNodes ( final FieldNode above, final FieldNode below, final FieldNode left, final FieldNode right )
  {
    if ( this.nodesHaveBeenSet )
      return false;

    this.above = above;
    this.below = below;
    this.left = left;
    this.right = right;

    this.nodesHaveBeenSet = true;
    return true;
  }

  public final FieldNode getAbove ()
  {
    return this.above;
  }

  public final FieldNode getBelow ()
  {
    return this.below;
  }

  public final FieldNode getLeft ()
  {
    return this.left;
  }

  public final FieldNode getRight ()
  {
    return this.right;
  }

  public final boolean isAdjacentTo ( FieldNode nodeB )
  {
    boolean valid = this.getAbove() == nodeB&& this.getAbove() != null;
    valid |= this.getLeft() == nodeB && this.getLeft() != null;
    valid |= this.getBelow() == nodeB && this.getBelow() != null;
    valid |= this.getRight() == nodeB && this.getRight() != null;
    return valid;
  }
  //</editor-fold>
}
