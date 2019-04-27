import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class RoverTest {

    class Postion {
        int xPosition;
        int yPosition;

        public int getxPosition() {
            return xPosition;
        }

        public int getyPosition() {
            return yPosition;
        }

        public void setxPosition(int xPosition) {
            this.xPosition = xPosition;
        }

        public void setyPosition(int yPosition) {
            this.yPosition = yPosition;
        }

        public Postion(int x, int y) {

        }
    }

    enum Facing {
        N,E,S,W
    }
/*
    public void test() {
        List<String> commands = new ArrayList();

        String result;

        String[][] map = new String[][]{{"0","1","1"},{"0","1","1"},{"0","1","0"}};
        String facing = "N";

        Postion position = new Postion(1,1);

        commands.add("r");

        facing = "E";

        assertThat(processTurnCommand(commands),is("ok"));

    }
*/
    @Test
    public void testTurnLeft(){
        Facing facing = turnLeft(Facing.E);
        Facing expectedFacing = Facing.N;
        assertThat(facing,is(expectedFacing));

        facing = turnLeft(Facing.S);
        expectedFacing = Facing.E;
        assertThat(facing,is(expectedFacing));

        facing = turnLeft(Facing.W);
        expectedFacing = Facing.S;
        assertThat(facing,is(expectedFacing));

        facing = turnLeft(Facing.N);
        expectedFacing = Facing.W;
        assertThat(facing,is(expectedFacing));
    }

    @Test
    public void testTurnRight(){
        Facing facing = turnRight(Facing.N);
        Facing expectedFacing = Facing.E;
        assertThat(facing,is(expectedFacing));

        facing = turnRight(Facing.E);
        expectedFacing = Facing.S;
        assertThat(facing,is(expectedFacing));

        facing = turnRight(Facing.S);
        expectedFacing = Facing.W;
        assertThat(facing,is(expectedFacing));

        facing = turnRight(Facing.W);
        expectedFacing = Facing.N;
        assertThat(facing,is(expectedFacing));
    }

    @Test void testMoveForward(){
        Postion startPosition = new Postion()

    }

    private Facing turnLeft(Facing startFacing) {
        Facing facing=null;
        switch (startFacing) {
            case E: facing= Facing.N; break;
            case N: facing= Facing.W; break;
            case W: facing= Facing.S; break;
            case S: facing= Facing.E; break;
        }
        return facing;
    }

    private Facing turnRight(Facing startFacing) {
        Facing facing=null;
        switch (startFacing) {
            case N: facing= Facing.E; break;
            case E: facing= Facing.S; break;
            case S: facing= Facing.W; break;
            case W: facing= Facing.N; break;
        }
        return facing;
    }

}
