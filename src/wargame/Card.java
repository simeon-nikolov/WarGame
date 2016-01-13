package wargame;

public class Card implements Comparable<Card> {
	private static final char SPADE_CHAR = '\u2660';
	private static final char HEART_CHAR = '\u2665';
	private static final char DIAMOND_CHAR = '\u2666';
	private static final char CLUB_CHAR = '\u2663';
	private static final String KING_RANK_NAME = "K";
	private static final String QUEEN_RANK_NAME = "Q";
	private static final String JACK_RANK_NAME = "J";
	private static final String ACE_RANK_NAME = "A";
	public static final int MAX_RANK = 14;
	public static final int MIN_RANK = 2;
	
	private static char[][] cardBack = {
					 ".---.".toCharArray(),
					 "|///|".toCharArray(),
					 "|///|".toCharArray(),
					 "|///|".toCharArray(),
					 "'---'".toCharArray()};
	
	private char[][] cardFace;
	private int rank;
	private String rankName;
	private Suit suit;
	private char suitChar;
	private Player owner;

	public Card(int rank, Suit suit) {
		this.setSuit(suit);
		this.setRank(rank);
		this.setCardFace();
	}
	
	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		if (owner != null) {
			this.owner = owner;
		}
	}

	private void setCardFace() {
		String rankName = this.rankName;
		
		if (this.rank != 10) {
			rankName += " ";
		}
		
		this.cardFace = new char[][] {
		".---.".toCharArray(),
		("|" + this.suitChar + "  |").toCharArray(),
		("| " + rankName + "|").toCharArray(),
		("|  " + this.suitChar + "|").toCharArray(),
		"'---'".toCharArray()};
	}

	public int getRank() {		
		return this.rank;
	}

	private void setRank(int rank) {
		if (rank < MIN_RANK || rank > MAX_RANK) {
			throw new IllegalArgumentException("Rank is not valid value!");
		}
		
		this.rank = rank;
		
		if (MIN_RANK <= rank & rank <= 10) {
			this.rankName = this.rank + "";
		} else {	
			switch (rank) {
			case 11:
				this.rankName = ACE_RANK_NAME;
				break;
			case 12:
				this.rankName = JACK_RANK_NAME;
				break;
			case 13:
				this.rankName = QUEEN_RANK_NAME;
				break;
			case 14:
				this.rankName = KING_RANK_NAME;
				break;
			}
		}
	}
	
	public String getRankName() {
		return this.rankName;
	}

	public Suit getSuit() {
		return this.suit;
	}

	private void setSuit(Suit suit) {
		this.suit = suit;
		
		switch (suit) {
		case CLUB:
			this.suitChar = CLUB_CHAR;
			break;
		case DIAMOND:
			this.suitChar = DIAMOND_CHAR;
			break;
		case HEART:
			this.suitChar = HEART_CHAR;
			break;
		case SPADE:
			this.suitChar = SPADE_CHAR;
			break;
		}
	}

	public char getSuitChar() {
		return this.suitChar;
	}
	
	public char[][] getCardFace() {
		return this.cardFace;
	}
	
	public static char[][] getCardBack() {
		return Card.cardBack;
	}

	@Override
	public int compareTo(Card otherCard) {
		int result = this.rank - otherCard.rank;
		return result;
	}
	
	
}
