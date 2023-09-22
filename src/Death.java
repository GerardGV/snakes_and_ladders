public class Death extends Square{
    public Death(int pos, Board b) {
        super(pos, b);
    }

    @Override
    public void enter(Player p){
        p.setDead(true);
    }
}
