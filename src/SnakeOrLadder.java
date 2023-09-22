public class SnakeOrLadder extends Square {

    private final int transport;

    public SnakeOrLadder(int pos, Board b, int trans) {
        super(pos, b);
        //assert trans > 0;
        transport = trans;
    }

    @Override
    protected Square landHereOrGoHome() {
        System.out.println("ladder from " + (position+1) + " to "
                + (destination().getPosition()+1));
        return destination().landHereOrGoHome();
    }

    private Square destination() {
        return findRelativeSquare(transport);
    }

}
