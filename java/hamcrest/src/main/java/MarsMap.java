class MarsMap {
    private String[][] map;

    public MarsMap(String[][] map) {
        this.map = map;
    }

    public Position getNextPosition(Position startPosition, Facing facing, Direction direction) {
        int relX=0;
        int relY=0;

        switch (facing) {
            case N:
                relX = 0;
                relY = 1;
                break;
            case E:
                relX = 1;
                relY = 0;
                break;
            case S:
                relX = 0;
                relY = -1;
                break;
            case W:
                relX = -1;
                relY = 0;
                break;
        }

        if(direction == Direction.BACKWARDS) {
            relX*=-1;
            relY*=-1;
        }

        int newX = startPosition.getX() + relX;
        int newY = startPosition.getY()+relY;

        if (newX >= map[0].length) {
            newX = 0;
        } else if (newX < 0) {
            newX = map[0].length - 1;
        }

        if (newY >= map.length) {
            newY = 0;
        } else if (newY < 0) {
            newY = map.length - 1;
        }

        Position newPosition = new Position(newX, newY);


        return newPosition;
    }

    public String getContentAtPosition(Position position) {
        return map[position.y][position.x];
    }

    public void printMapWithPositon(Position position){
        printHeader();
        for (int y = 0; y < map.length; y++){
            String lineString = new String();
            for (int x=0; x < map[y].length; x++){
                if(position != null && x == position.getX() && y == position.getY()) {
                    lineString+="X";
                } else {
                    lineString += map[y][x];
                }
            }
            System.out.println(lineString);
        }
    }

    private void printHeader(){
        System.out.println("--MarsMap--");
    }
}
