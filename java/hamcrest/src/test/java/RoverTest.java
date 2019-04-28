
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RoverTest {

    enum TurnSide {
        LEFT, RIGHT
    }

    public class CommandResult {

        private Position position;
        private Facing facing;

        public CommandResult(Position position, Facing facing) {
            this.facing = facing;
            this.position = position;
        }

        public Position getPosition() {
            return position;
        }

        public Facing getFacing() {
            return facing;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            CommandResult that = (CommandResult) o;

            return new EqualsBuilder()
                    .append(position, that.position)
                    .append(facing, that.facing)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(position)
                    .append(facing)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return "CommandResult{" +
                    "position=" + position +
                    ", facing=" + facing +
                    '}';
        }
    }

    @Test
    public void testExecuteCommands() {
        List<String> commands = new ArrayList();
        commands.add("f");
        commands.add("r");
        commands.add("f");
        commands.add("l");
        commands.add("b");

        Facing facing = Facing.E;
        Position position = new Position(0, 0);

        String[][] map = new String[][]{{"1", "1", "0"}, {"1", "1", "1"}};
        MarsMap marsMap = new MarsMap(map);
        CommandResult commandResult = null;

        for (String command : commands) {
            commandResult = executeCommand(marsMap, position, facing, command);
            facing = commandResult.facing;
            position = commandResult.position;
        }

        assertThat(commandResult, is(new CommandResult(new Position(0, 1), Facing.E)));
    }


    @Test
    public void testExecuteCommand(){
        MarsMap marsMap = getDefaultMarsMap();
        CommandResult commandResult = executeCommand(marsMap, new Position(0,0), Facing.S, "f");
        assertThat(commandResult, is(new CommandResult(new Position(0, 1), Facing.S)));

        commandResult = executeCommand(marsMap, new Position(0,1), Facing.S, "b");
        assertThat(commandResult, is(new CommandResult(new Position(0, 0), Facing.S)));

        commandResult = executeCommand(marsMap, new Position(0,0), Facing.N, "r");
        assertThat(commandResult, is(new CommandResult(new Position(0, 0), Facing.E)));


        commandResult = executeCommand(marsMap, new Position(0,0), Facing.N, "l");
        assertThat(commandResult, is(new CommandResult(new Position(0, 0), Facing.W)));
    }

    private CommandResult executeCommand(MarsMap marsMap, Position startPosition, Facing facing, String command) {
        Position finalPosition = startPosition;
        Facing finalFacing = facing;
        switch (command) {
            case "f": finalPosition = moveForward(startPosition, facing, marsMap); break;
            case "b": finalPosition = moveBackward(startPosition, facing, marsMap); break;
            case "l": finalFacing = turnLeft(facing); break;
            case "r": finalFacing = turnRight(facing); break;
        }
        return new CommandResult(finalPosition, finalFacing);
    }

    @Test
    public void testGetNextPosition() {
        MarsMap marsMap = getDefaultMarsMap();
        Position startPosition = new Position(0,0);
        Position expectedAfterEastmove = new Position (1,0);
        Facing facing = Facing.E;
        Direction direction = Direction.FORWARD;
        marsMap.printMapWithPositon(startPosition);
        assertThat(marsMap.getNextPosition(startPosition, facing, direction),is(expectedAfterEastmove));
        marsMap.printMapWithPositon(expectedAfterEastmove);

        assertThat(marsMap.getNextPosition(new Position(0,0), Facing.W, direction),is(new Position(1,0)));
        assertThat(marsMap.getNextPosition(new Position(1,0), Facing.E, direction),is(new Position(0,0)));

        assertThat(marsMap.getNextPosition(new Position(0,0), Facing.N, direction),is(new Position(0,1)));
        assertThat(marsMap.getNextPosition(new Position(1,1), Facing.S, direction),is(new Position(1,0)));

        assertThat(marsMap.getNextPosition(new Position(1,1), Facing.E, Direction.BACKWARDS),is(new Position(0,1)));
        assertThat(marsMap.getNextPosition(new Position(0,0), Facing.W, Direction.BACKWARDS),is(new Position(1,0)));
        assertThat(marsMap.getNextPosition(new Position(1,1), Facing.N, Direction.BACKWARDS),is(new Position(1,0)));
        assertThat(marsMap.getNextPosition(new Position(1,1), Facing.S, Direction.BACKWARDS),is(new Position(1,0)));
    }

    @Test
    public void testPrintMapWithOutPositon(){
        MarsMap marsMap = getDefaultMarsMap();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        marsMap.printMapWithPositon(null);

        String expectedResultString = "--MarsMap--\n" +
                "10\n" +
                "10\n";

        assertThat(outContent.toString(), is(expectedResultString));
    }

    private MarsMap getDefaultMarsMap() {
        String[][] map = new String[][]{{"1", "0"}, {"1", "0"}};
        return new MarsMap(map);
    }

    @Test
    public void testPrintMapWithPositon(){
        MarsMap marsMap = getDefaultMarsMap();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        marsMap.printMapWithPositon(new Position(1,1));

        String expectedResultString = "--MarsMap--\n" +
                "10\n" +
                "1X\n";

        assertThat(outContent.toString(), is(expectedResultString));
    }

    @Test
    public void testRoverTurnRight(){
        assertThat(turnRight(Facing.N), is(Facing.E));
        assertThat(turnRight(Facing.E), is(Facing.S));
        assertThat(turnRight(Facing.S), is(Facing.W));
        assertThat(turnRight(Facing.W), is(Facing.N));
    }

    @Test
    public void testRoverTurnLeft(){
        assertThat(turnLeft(Facing.N), is(Facing.W));
        assertThat(turnLeft(Facing.E), is(Facing.N));
        assertThat(turnLeft(Facing.S), is(Facing.E));
        assertThat(turnLeft(Facing.W), is(Facing.S));
    }

    @Test()
    public void testMoveForwardNotPossible() {
        MarsMap marsMap = getDefaultMarsMap();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            moveForward( new Position(0,1), Facing.E, marsMap);
        });
    }

    @Test
    public void testMoveForward() {
        MarsMap marsMap = getDefaultMarsMap();
        Position positionAfterMoving = moveForward( new Position(0,1), Facing.N, marsMap);;
        assertThat(positionAfterMoving,is(new Position(0,0)));
    }

    @Test()
    public void testMoveBackwardNotPossible() {
        MarsMap marsMap = getDefaultMarsMap();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            moveBackward( new Position(0,1), Facing.E, marsMap);
        });
    }

    @Test
    public void testMoveBackward() {
        MarsMap marsMap = getDefaultMarsMap();
        Position positionAfterMoving = moveBackward( new Position(0,1), Facing.N, marsMap);;
        assertThat(positionAfterMoving,is(new Position(0,0)));
    }

    private Position moveForward(Position startPos, Facing facing, MarsMap marsMap){
        return move(startPos, facing, Direction.FORWARD, marsMap);
    }

    private Position moveBackward(Position startPos, Facing facing, MarsMap marsMap){
        return move(startPos, facing, Direction.BACKWARDS, marsMap);
    }

    private Position move(Position startPos, Facing facing, Direction direction, MarsMap marsMap) {
        Position nextPosition;
        Position positionAfterMoving;
        nextPosition = marsMap.getNextPosition(startPos,facing,direction);
        boolean isObstacle = isNextPositionObstacle(marsMap, nextPosition);
        if(isObstacle) {
            System.out.println("Obstacle at " + nextPosition.getX() + "," + nextPosition.getY());
            throw new IllegalArgumentException();
        } else {
            positionAfterMoving = nextPosition;
        }
        return positionAfterMoving;
    }

    private boolean isNextPositionObstacle(MarsMap marsMap, Position nextPosition) {
        String nextPositionContent = marsMap.getContentAtPosition(nextPosition);
        return "0".equals(nextPositionContent);
    }

    private Facing turnLeft(Facing startFacing) {
        return turn(startFacing, TurnSide.LEFT);
    }

    private Facing turnRight(Facing startFacing) {
        return turn(startFacing, TurnSide.RIGHT);
    }

    private Facing turn(Facing startFacing, TurnSide turnSide) {
        Facing newFacing = null;
        switch (startFacing) {
            case N:
              newFacing =  turnSide == TurnSide.RIGHT ? Facing.E : Facing.W;
            break;
            case E:
              newFacing =  turnSide == TurnSide.RIGHT ? Facing.S : Facing.N;
            break;
            case S:
            newFacing =  turnSide == TurnSide.RIGHT ? Facing.W : Facing.E;
            break;
            case W:
            newFacing =  turnSide == TurnSide.RIGHT ? Facing.N : Facing.S;
            break;
        }
        return newFacing;
    }


}
