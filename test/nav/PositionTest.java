package nav;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PositionTest {


  @Test
  public void testEqual() {
    Position pos1 = new Position(10, 32);
    Position pos2 = new Position(10, 32);

    assertTrue(pos1.equals(pos2));
    assertTrue(pos2.equals(pos1));

    Position pos3 = new Position(4, 2);
    assertFalse(pos3.equals(pos2));
    assertFalse(pos2.equals(pos3));
  }

  @Test
  public void testNeighbour() {
    Position pos = new Position(10, 32);
    Position upPos = new Position(10, 33);
    Position downPos = new Position(10, 31);
    Position leftPos = new Position(9, 32);
    Position rightPos = new Position(11, 32);
    Position upLeftPos = new Position(9, 33);

    assertTrue(pos.isNeighbough(upPos));
    assertTrue(pos.isNeighbough(downPos));
    assertTrue(pos.isNeighbough(leftPos));
    assertTrue(pos.isNeighbough(rightPos));
    assertFalse(pos.isNeighbough(upLeftPos));
  }

  @Test
  public void testMovement(){
    Position pos = new Position(10, 45);

    pos.moveDown();
    assertTrue(pos.equals(new Position(10, 46)));

    pos.moveLeft();
    assertTrue(pos.equals(new Position(9, 46)));

    pos.moveUp();
    assertTrue(pos.equals(new Position(9, 45)));

    pos.moveRight();
    assertTrue(pos.equals(new Position(10, 45)));
  }
}
