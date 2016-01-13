package wargame;

public class Player {
	private static final String TOO_MANY_PLAYERS = "Too many players!";
	
	public final int PLAYING_DECK_ROW;
	public final int PLAYING_DECK_COL;
	public final int CAPTURED_DECK_ROW;
	public final int CAPTURED_DECK_COL;
	public final int HAND_ROW;
	public final int HAND_COL;
	public final int EXTRA_HAND_ROW;
	public final int EXTRA_HAND_COL;
	
	private static int playersCount = 0;
	
	private int playerId;
	private Deck playingDeck;
	private Deck capturedDeck;
	
	public Player() {
		if (Player.playersCount >= Game.MAX_PLAYERS) {
			throw new InstantiationError(TOO_MANY_PLAYERS);
		}
		
		this.playingDeck = new Deck(this);
		this.capturedDeck = new Deck(this);
		Player.playersCount++;
		this.playerId = Player.playersCount;
		this.PLAYING_DECK_ROW = 2 + (this.playerId - 1) * 15;
		this.PLAYING_DECK_COL = 10 + (this.playerId - 1) * 35;
		this.CAPTURED_DECK_ROW = 2 + (this.playerId - 1) * 15;
		this.CAPTURED_DECK_COL = 50 - (this.playerId - 1) * 45;
		this.HAND_ROW = 7 + (this.playerId - 1) * 5;
		this.HAND_COL = 27;
		this.EXTRA_HAND_ROW = 2 + (this.playerId - 1) * 15;
		this.EXTRA_HAND_COL = 17;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public Deck getPlayingDeck() {
		return this.playingDeck;
	}

	public Deck getCapturedDeck() {
		return this.capturedDeck;
	}
}
