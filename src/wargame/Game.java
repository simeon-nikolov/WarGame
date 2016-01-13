package wargame;

public class Game {
	private static final String CONSOLE_IS_NULL_MESSAGE = "Console is null!";
	private static final String WINNER_MESSAGE = "Winner is: Player ";
	private static final String NO_WINNER_MESSAGE = "No winner! Game is tie!";
	private static final int TURN_WAIT_TIME = 1000;
	private static final int THREE_CARDS = 3;
	private static final int ONE_CARD = 1;
	private static final int FIELD_HEIGHT = 25;
	private static final int FIELD_WIDTH = 60;
	public static final int MAX_PLAYERS = 2;

	private Player[] players;
	private Player winner;
	private Deck gameDeck;
	private char[][] gameField;
	private IConsole console;
	
	public Game(IConsole console) {
		if (console == null) {
			throw new IllegalArgumentException(CONSOLE_IS_NULL_MESSAGE);
		}
		
		this.console = console;
	}

	public void run() throws InterruptedException {
		this.initializeGame();
		this.printField();
		Thread.sleep(TURN_WAIT_TIME);
		boolean isGameOver = false;

		while (!isGameOver) {
			this.clearField();
			this.printField();
			Thread.sleep(TURN_WAIT_TIME);
			this.playHand();
			Thread.sleep(TURN_WAIT_TIME);

			for (Player player : players) {
				if (player.getPlayingDeck().isEmpty()) {
					isGameOver = true;
					break;
				}
			}
		}

		this.clearField();
		this.printField();
		
		this.winner = this.getWinner();
		this.showEndGameMessage();
	}

	private void showEndGameMessage() {
		String message = "";
		
		if (this.winner == null) {
			message = NO_WINNER_MESSAGE;
		} else {
			message = WINNER_MESSAGE + winner.getPlayerId();
		}
		
		char[][] messageMatrix = new char[5][message.length() + 6];
		char[] messageChars = message.toCharArray();
		
		for (int index = 0; index < messageChars.length; index++) {
			messageMatrix[2][index + 3] = messageChars[index]; 
		}
		
		this.addBorder(messageMatrix);
		this.clearField();
		this.addToField(messageMatrix, 10, 20);
		this.printField();
	}

	private Player getWinner() {
		int maxScore = 0;
		Player winner = null;
		
		for (Player player : this.players) {
			if (maxScore < player.getCapturedDeck().size()) {
				maxScore = player.getCapturedDeck().size();
				winner = player;
			}
		}
		
		int countWinners = 0;
		
		for (Player player : this.players) {
			if (maxScore == player.getCapturedDeck().size()) {
				countWinners++;
			}
		}
		
		if (countWinners == 1) {
			return winner;
		} else {
			return null;
		}
	}

	private void printField() {
		this.clearConsole();
		this.addBorder(gameField);
		this.addDecksToField();

		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[row].length; col++) {
				console.print(gameField[row][col]);
			}

			console.println();
		}

		int rowsForAlign = 10;

		if (rowsForAlign > 0) {
			for (int row = 0; row < rowsForAlign; row++) {
				console.println();
			}
		}
	}

	private void addDecksToField() {
		for (Player player : players) {
			this.addPlayingDecks(player);
			this.addCapturedDecks(player);
		}
	}

	private void addPlayingDecks(Player player) {
		char[][] deckBack = Card.getCardBack();
		int startRow = player.PLAYING_DECK_ROW;
		int startCol = player.PLAYING_DECK_COL;

		for (int row = 0; row <= deckBack.length; row++) {
			for (int col = 0; col <= deckBack.length; col++) {
				this.gameField[startRow + row][startCol + col] = ' ';
			}
		}

		if (!player.getPlayingDeck().isEmpty()) {
			if (player.getPlayingDeck().size() > 1) {
				char[][] oldBack = deckBack;
				deckBack = new char[oldBack.length][oldBack[0].length + 1];

				for (int row = 1; row < deckBack.length - 1; row++) {
					deckBack[row][0] = '|';
				}

				deckBack[0][0] = '.';
				deckBack[4][0] = '\'';

				for (int row = 0; row < oldBack.length; row++) {
					for (int col = 0; col < oldBack.length; col++) {
						deckBack[row][col + 1] = oldBack[row][col];
					}
				}
			}

			for (int row = 0; row < deckBack.length; row++) {
				for (int col = 0; col < deckBack[row].length; col++) {
					this.gameField[startRow + row][startCol + col] = deckBack[row][col];
				}
			}

			char[] numberOfCards = (player.getPlayingDeck().size() + "")
					.toCharArray();

			for (int index = 0; index < numberOfCards.length; index++) {
				this.gameField[startRow + deckBack.length][startCol + index + 2] = numberOfCards[index];
			}
		}
	}

	private void addCapturedDecks(Player player) {
		char[][] deckBack = Card.getCardBack();

		if (!player.getCapturedDeck().isEmpty()) {
			int startRow = player.CAPTURED_DECK_ROW;
			int startCol = player.CAPTURED_DECK_COL;

			for (int row = 0; row < deckBack.length; row++) {
				for (int col = 0; col < deckBack[row].length; col++) {
					this.gameField[startRow + row][startCol + col] = deckBack[row][col];
				}
			}

			char[] capturedText = "Captured".toCharArray();

			for (int index = 0; index < capturedText.length; index++) {
				this.gameField[startRow + deckBack.length][startCol + index - 1] = capturedText[index];
			}

			char[] numberOfCards = (player.getCapturedDeck().size() + "")
					.toCharArray();

			for (int index = 0; index < numberOfCards.length; index++) {
				this.gameField[startRow + deckBack.length + 1][startCol + index
						+ 2] = numberOfCards[index];
			}
		}
	}

	private void addBorder(char[][] matrix) {
		for (int row = 0; row < matrix.length; row++) {
			int col = 0;
			matrix[row][col] = '|';
			matrix[row][col + 1] = '|';
		}

		for (int row = 0; row < matrix.length; row++) {
			int col = matrix[row].length - 1;
			matrix[row][col - 1] = '|';
			matrix[row][col] = '|';
		}

		for (int col = 0; col < matrix[0].length; col++) {
			int row = 0;
			matrix[row][col] = '=';
		}

		for (int col = 0; col < matrix[0].length; col++) {
			int row = matrix.length - 1;
			matrix[row][col] = '=';
		}

		int lastRow = matrix.length - 1;
		int lastCol = matrix[0].length - 1;

		matrix[0][0] = '/';
		matrix[0][1] = '/';
		matrix[0][lastCol] = '\\';
		matrix[0][lastCol - 1] = '\\';
		matrix[lastRow][0] = '\\';
		matrix[lastRow][1] = '\\';
		matrix[lastRow][lastCol] = '/';
		matrix[lastRow][lastCol - 1] = '/';
	}

	private void clearConsole() {
		console.clearScreen();
	}

	private void clearField() {
		this.gameField = new char[FIELD_HEIGHT][FIELD_WIDTH];
	}

	private void initializeGame() {
		this.gameField = new char[FIELD_HEIGHT][FIELD_WIDTH];
		this.players = new Player[Game.MAX_PLAYERS];

		for (int index = 0; index < players.length; index++) {
			players[index] = new Player();
		}

		this.gameDeck = new Deck(Deck.FULL_DECK);
		this.gameDeck.shuffle();
		this.dealCards();
	}

	private void dealCards() {
		while (!this.gameDeck.isEmpty()) {
			for (int index = 0; index < players.length; index++) {
				Card card = this.gameDeck.drawCard();
				players[index].getPlayingDeck().addCard(card);

				if (this.gameDeck.isEmpty()) {
					break;
				}
			}
		}
	}

	private void playHand() throws InterruptedException {
		Deck hand = new Deck(Deck.EMPTY_DECK);
		this.clearField();

		for (Player player : players) {
			if (!player.getPlayingDeck().isEmpty()) {
				Card card = player.getPlayingDeck().drawCard();
				hand.addCard(card);
				this.addToField(card.getCardFace(), player.HAND_ROW, player.HAND_COL);
			}

			Thread.sleep(TURN_WAIT_TIME);
			this.printField();
		}

		Card highestRankCard = new Card(Card.MIN_RANK, Suit.CLUB);

		for (Card card : hand) {
			if (highestRankCard.compareTo(card) < 0) {
				highestRankCard = card;
			}
		}

		Deck highestCards = new Deck(Deck.EMPTY_DECK);

		for (Card card : hand) {
			if (card.compareTo(highestRankCard) == 0) {
				highestCards.addCard(card);
			}
		}

		if (highestCards.size() == 1) {
			Deck winnerCaptureDeck = highestRankCard.getOwner()
					.getCapturedDeck();

			for (Card card : hand) {
				winnerCaptureDeck.addCard(card);
			}
		} else {
			playWhenEquals(highestCards);
		}
	}

	private void playWhenEquals(Deck highestCards) throws InterruptedException {
		Card highestRankCard;
		boolean handWinner = false;
		Deck captureDeck = new Deck(Deck.EMPTY_DECK);
		int cardsToDraw = THREE_CARDS;

		while (!handWinner) {
			Deck[] extraDecks = new Deck[highestCards.size()];
			
			for (int handIndex = 0; handIndex < highestCards.size(); handIndex++) {
				Player owner = highestCards.getCard(handIndex).getOwner();
				extraDecks[handIndex] = new Deck(owner);
				
				for (int cardIndex = 0; cardIndex < cardsToDraw; cardIndex++) {
					Deck playingDeck = owner.getPlayingDeck();
					
					if (!playingDeck.isEmpty()) {
						Card card = playingDeck.drawCard();
						extraDecks[handIndex].addCard(card);
						int padding = 10 * cardIndex;
						this.addToField(card.getCardFace(), owner.EXTRA_HAND_ROW, owner.EXTRA_HAND_COL + padding);
						Thread.sleep(TURN_WAIT_TIME);
						this.printField();
					}	
				}
			}
			
			int compareCardIndex = 0;
			highestRankCard = new Card(Card.MIN_RANK, Suit.CLUB);
			
			for (int deckIndex = 0; deckIndex < extraDecks.length; deckIndex++) {
				compareCardIndex = extraDecks[deckIndex].size() - 1;
				
				if (!extraDecks[deckIndex].isEmpty()) {
					Card card = extraDecks[deckIndex].getCard(compareCardIndex);
					
					if (highestRankCard.compareTo(card) < 0) {
						highestRankCard = card;
					}
				}
			}
			
			int countHighest = 0;
			
			for (int deckIndex = 0; deckIndex < extraDecks.length; deckIndex++) {
				compareCardIndex = extraDecks[deckIndex].size() - 1;
				Card card = extraDecks[deckIndex].getCard(compareCardIndex);
				
				if (highestRankCard.compareTo(card) == 0) {
					countHighest++;
				}
			}
			
			this.addCardsToDeck(extraDecks, captureDeck);
			
			if (countHighest == 0) { // last cards are equal
				break;
			}
			
			if (countHighest == 1) {
				handWinner = true;
				Deck winnerCaptureDeck = highestRankCard.getOwner().getCapturedDeck();
				
				for (Card card : captureDeck) {
					winnerCaptureDeck.addCard(card);
				}
			} else {
				cardsToDraw = ONE_CARD;
			}
			
			Thread.sleep(TURN_WAIT_TIME);
			this.clearField();
			this.printField();
		}
	}

	private void addCardsToDeck(Deck[] decks, Deck captureDeck) {
		for (int hand = 0; hand < decks.length; hand++) {
			while (!decks[hand].isEmpty()) {
				Card card = decks[hand].drawCard();
				captureDeck.addCard(card);
			}
		}
	}

	private void addToField(char[][] charMatrix, int startRow, int startCol) {
		for (int row = 0; row < charMatrix.length; row++) {
			for (int col = 0; col < charMatrix[row].length; col++) {
				this.gameField[startRow + row][startCol + col] = charMatrix[row][col];
			}
		}
	}

}
