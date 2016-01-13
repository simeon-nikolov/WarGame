package wargame;

import java.util.ArrayList;
import java.util.Iterator;

public class Deck implements Iterable<Card> {
	private static final String NEGATIVE_CARD_INDEX_MESSAGE = "Card index is negative value!";
	private static final String OWNER_IS_NULL_MESSAGE = "Owner is null!";
	private static final String CARD_IS_NULL_MESSAGE = "Card is null!";
	private static final int TOP_CARD_INDEX = 0;
	public static final boolean EMPTY_DECK = false;
	public static final boolean FULL_DECK = true;
	public static final Player NO_OWNER = null;
	
	private ArrayList<Card> deck;
	private Player owner;
	
	private Deck() {
		this.deck = new ArrayList<Card>();
		this.owner = null;
	}
	
	public Deck(boolean fullDeck) {
		this();
		
		if (fullDeck) {
			this.initializeDeck();
		}
	}
	
	public Deck(Player owner) {
		this(EMPTY_DECK);
		
		if (owner == null) {
			throw new IllegalArgumentException(OWNER_IS_NULL_MESSAGE);
		}
		
		this.owner = owner;
	}
	
	public void addCard(Card card) {
		if (card == null) {
			throw new IllegalArgumentException(CARD_IS_NULL_MESSAGE);
		}
		
		card.setOwner(this.owner);
		this.deck.add(card);
	}
	
	public Card drawCard() {
		Card card = null;
		
		if (!this.deck.isEmpty()) {
			card = this.deck.get(TOP_CARD_INDEX);
			this.deck.remove(TOP_CARD_INDEX);
		}
		
		return card;
	}
	
	public Card getCard(int index) {
		if (index < 0) {
			throw new IllegalArgumentException(NEGATIVE_CARD_INDEX_MESSAGE);
		}
		
		return this.deck.get(index);
	}
	
	public boolean isEmpty() {
		return this.deck.isEmpty();
	}
	
	public int size() {
		return this.deck.size();
	}
	
	private void initializeDeck() {
		for (Suit suit : Suit.values()) {	
			for (int rank = Card.MIN_RANK; rank <= Card.MAX_RANK; rank++) {
				Card card = new Card(rank, suit);
				this.addCard(card);
			}
		}
	}
	
	public void shuffle() {
		if (!this.deck.isEmpty()) {
			for (int index = 0; index < this.deck.size(); index++) {
				int randIndex = (int) (Math.random() * (Card.MAX_RANK - Card.MIN_RANK) + Card.MIN_RANK);
				swapCards(index, randIndex);
			}
		}
	}

	private void swapCards(int index, int randIndex) {
		Card currCard = this.deck.get(index);
		Card randCard = this.deck.get(randIndex);
		this.deck.set(index, randCard);
		this.deck.set(randIndex, currCard);
	}

	@Override
	public Iterator<Card> iterator() {
		Iterator<Card> cardIterator = this.deck.iterator();
		
		return cardIterator;
	}
}
