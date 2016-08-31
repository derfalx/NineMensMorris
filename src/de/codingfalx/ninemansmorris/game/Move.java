package de.codingfalx.ninemansmorris.game;

import sun.text.resources.cldr.en.FormatData_en_IE;

/**
 * Represents a Move, Pick and Set on the Gameboard.
 * If this is a Move from and to must be filled
 * If this is a Pick from must be filled, but to should be null
 * If this is a Set from should be null and to must be filled
 *
 * @author Kristoffer Schneider
 * @created 31.08.2016
 */
public class Move
{
  //<editor-fold desc="Fields">
  public final FieldNode nodeFrom;
  public final FieldNode nodeTo;

  //</editor-fold>

  //<editor-fold desc="Constructor">

  public Move ( FieldNode nodeFrom, FieldNode nodeTo )
  {
    this.nodeFrom = nodeFrom;
    this.nodeTo = nodeTo;
  }


  //</editor-fold>
}
