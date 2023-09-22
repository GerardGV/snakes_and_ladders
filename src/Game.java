import java.util.LinkedList;

public final class Game {
	private LinkedList<Player> players = new LinkedList<Player>();
	private Board board = null;
	private Player winner;

	public Game(String[] playerNames, int numSquares,
				int[][] snakes, int[][] ladders, int[] deaths) {

		makeBoard(numSquares, ladders, snakes, deaths);
		makePlayers(playerNames);

	}

	public void play() {
		assert !players.isEmpty() : "No players to play";
		assert board!=null : "No scoreboard to play";

		Die die = new Die();
		initializeGame();

		System.out.println("Initial state : \n" + this);
		int numRounds = 0;
		while (notOver() && playersLive()) {
			int roll = die.roll();
			System.out.println("Current player is " + currentPlayer() + " and rolls " + roll);
			movePlayer(roll);
			System.out.println("State : \n" + this);
			numRounds++;
		}
		if(winner != null) {
			System.out.println(winner + " has won after " + numRounds + " rounds");
		}
		else
		{
			System.out.println("All players are dead :(");
		}

	}

	private void makeBoard(int numSquares, int[][] ladders, int[][] snakes, int[] deaths) {
		board = new Board(numSquares,ladders,snakes, deaths);
	}

	private void makePlayers(String[] playerNames) {
		assert playerNames.length>0 : "There must be some player" ;
		System.out.println("Players are : ");
		int i=1;
		for (String str : playerNames) {
			Player player = new Player(str);
			players.add(player);
			System.out.println(i + ". " + str);
			i++;
		}
	}


	private void initializeGame() {
		placePlayersAtFirstSquare();
		winner = null;
	}

	private void placePlayersAtFirstSquare() {
		for (Player player : players) {
			board.firstSquare().enter(player);
		}
	}

	private boolean notOver() {
		return (winner == null);
	}

	private boolean playersLive(){
		for (Player p:this.players){

			//if some player is still alive, isDead == false 
			if(!p.isDead())
				return true;
		}

		//no one's live
		return false;
	}

	private void movePlayer(int roll) {
		Player currentPlayer = players.remove(); // the first element of the list
		currentPlayer.moveForward(roll);

		//if player isn't dead, it's added to the players list
		if(!currentPlayer.isDead())
		{
			players.add(currentPlayer); // to the end of list, we're using the linked list as a queue

		}else
		{
			System.out.println("This player is dead");
		}

		if (currentPlayer.wins()) {
			winner = currentPlayer;
		}
	}

	@Override
	public String toString() {
		String str = new String();
		for (Player player : players) {
			str += player.getName() + " is at square " + (player.getPosition()+1) + "\n";
		}
		return str;
	}

	private Player currentPlayer() {
		assert players.size()>0;
		return players.peek();
	}
}
