package controller;

import exceptions.card.InvalidCardException;
import exceptions.hand.InvalidHandException;
import model.Card;
import model.Hand;
import model.Player;
import model.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandAnalyzer {
    // TODO: replace Run/TerciaController logic with their respective HandAnalyzer logic
    // TODO: and use them as objects
    private List<Card> terciaPossibles;
    private List<Card> runPossibles;
    private List<Card> flexCards;

    private List<List<Card>> incompleteTercias;
    private List<List<Card>> perfectTercias;
    private List<List<Card>> overflowTercias;

    private List<List<Card>> incompleteRuns;
    private List<List<Card>> perfectRuns;
    private List<List<Card>> overflowRuns;

    private int jokerCount;
    private int round;

    public HandAnalyzer() {
        initAllLists();
    }

    public void generateHandComponents(Hand hand, int round) throws InvalidHandException, InvalidCardException {
        if (hand == null) {
            throw new InvalidHandException("Hand can't be null");
        }
        this.round = round;
        initAllLists();
        jokerCount = hand.getJokerCount();

        if (roundNeedsOnlyTercias(round)) {
            generateTerciaComponent(hand);
        } else if (roundNeedsOnlyRuns(round)) {
            generateRunComponent(hand);
        } else {
            generateTerciaComponent(hand);
            generateRunComponent(hand);
        }
        generateFlexCardsComponent(hand);
    }

    public void generateTerciaTypes(Hand hand) {
        initTerciaLists();
        List<Integer> distinctRanks = getDistinctRanks(hand);

        for (int rank : distinctRanks) {
            if (rank == -1) {
                continue;
            }
            List<Card> cardsByRank = hand.getCardCollectionByRank(rank);
            if (cardsByRank.size() == 2) {
                incompleteTercias.add(cardsByRank);
            } else if (cardsByRank.size() == 3) {
                perfectTercias.add(cardsByRank);
            } else if (cardsByRank.size() > 3) {
                overflowTercias.add(cardsByRank);
            }
        }
    }

    public void generateRunTypes(Hand hand) {
        initRunLists();
        List<Suit> distinctSuits = getDistinctSuits(hand);

        // for each distinct suit in hand
        for (Suit suit : distinctSuits) {
            if (suit == Suit.JOKER) {
                continue;
            }
            // get all cards in current suit
            List<Card> cardsBySuit = hand.getCardCollectionBySuit(suit);

            processPerfectRuns(cardsBySuit);
        }
    }

    private void processPerfectRuns(List<Card> cardsBySuit) {
        // initial card from sorted hand
        int cardRankPointer = cardsBySuit.get(0).getRank();
        int cardDiff = 0;
        int diffCount = 1;

        for (Card card : cardsBySuit) {
            cardDiff = card.getRank() - cardRankPointer;
            if (cardDiff == 1) {
                diffCount++;
            }
            cardRankPointer = card.getRank();
        }

        if (perfectHighRoyalRunCheck(cardDiff, diffCount, cardsBySuit)) {
            perfectRuns.add(cardsBySuit);
        } else if (perfectRunCheck(diffCount, cardsBySuit.size())) {
            perfectRuns.add(cardsBySuit);
        }
    }

    /**
     *
     * @param cardDiff Difference between last and current card
     * @param diffCount Total difference in ranks
     * @param cardsBySuit Cards by suit
     * @return Whether params pass size checks
     */
    private boolean perfectHighRoyalRunCheck(int cardDiff, int diffCount, List<Card> cardsBySuit) {
        return cardsBySuit.size() == 4 &&
                diffCount == cardsBySuit.size() - 1 &&
                runNeedsAce(diffCount, cardsBySuit);
    }

    private boolean runNeedsAce(int diffCount, List<Card> cardsBySuit) {
        return diffCount < 4 && cardsBySuit.stream()
                .map(Card::getRank)
                .collect(Collectors.toList())
                .contains(1);
    }

    /**
     * If the difference in ranks is equal to the size of the hand (4)
     * then it must be a perfect run.
     * @param diffCount Total difference in ranks
     * @param cardsBySuitSize Number of cards by suit
     * @return Whether params pass size checks
     */
    private boolean perfectRunCheck(int diffCount, int cardsBySuitSize) {
        return diffCount == cardsBySuitSize && cardsBySuitSize == 4;
    }

    private void initTerciaLists() {
        incompleteTercias = new ArrayList<>();
        perfectTercias = new ArrayList<>();
        overflowTercias = new ArrayList<>();
    }

    private void initRunLists() {
        incompleteRuns = new ArrayList<>();
        perfectRuns = new ArrayList<>();
        overflowRuns = new ArrayList<>();
    }

    private void initAllLists() {
        initTerciaLists();
        initRunLists();
        terciaPossibles = new ArrayList<>();
        runPossibles = new ArrayList<>();
        flexCards = new ArrayList<>();
    }

    public boolean cardHelpsPlayer(Player player, Card card, int round) throws InvalidHandException, InvalidCardException {
        List<Card> playerHand = new ArrayList<>(player.getHand().getHand());
        Hand playerHandCopy = new Hand(playerHand);

        generateHandComponents(playerHandCopy, this.round);
        int initTerciaComponentWeight = terciaPossibles.size();
        int initRunsComponentWeight = runPossibles.size();

        playerHandCopy.addToHand(card);
        generateHandComponents(playerHandCopy, this.round);
        int newTerciaComponentWeight = terciaPossibles.size();
        int newRunsComponentWeight = runPossibles.size();

        if (roundNeedsOnlyTercias(round)) {
            return initTerciaComponentWeight < newTerciaComponentWeight;
        } else if (roundNeedsOnlyRuns(round)) {
            return initRunsComponentWeight < newRunsComponentWeight;
        }
        return initTerciaComponentWeight < newTerciaComponentWeight || initRunsComponentWeight < newRunsComponentWeight;
    }

    public boolean roundNeedsOnlyTercias(int round) {
        return round == 6 || round == 9 || round == 12;
    }

    public boolean roundNeedsOnlyRuns(int round) {
        return round == 8 || round == 13;
    }

    private void generateTerciaComponent(Hand hand) {
        for (Card card : hand.getHand()) {
            if (card.getSuit() == Suit.JOKER) {
                continue;
            }
            if (hand.getCardCountByRank(card) >= 2) {
                terciaPossibles.add(card);
            }
        }
    }

    private void generateRunComponent(Hand hand) {
        for (Suit suit : getDistinctSuits(hand)) {
            if (suit == Suit.JOKER) {
                continue;
            }
            List<Card> cards = getCardsBySuit(hand, suit);
            if (cards.size() > 1) {
                filterCardsByWeight(cards);
            }
        }
    }

    private void generateFlexCardsComponent(Hand hand) {
        List<Card> jokers = hand.getHand().stream().
                filter(predicateCard -> predicateCard.getSuit() == Suit.JOKER)
                .collect(Collectors.toList());
        flexCards.addAll(jokers);

        for (Card terciaCard : terciaPossibles) {
            for (Card runCard : runPossibles) {
                if (terciaCard == runCard) {
                    flexCards.add(terciaCard);
                }
            }
        }
    }

    private void filterCardsByWeight(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            Card cardOne = cards.get(i);
            Card cardTwo = cards.get(i + 1);
            if (withinWeight(cardOne, cardTwo)) {
                addRunCardsIfNotPresent(cardOne);
                addRunCardsIfNotPresent(cardTwo);
            }
        }
    }

    private boolean withinWeight(Card cardOne, Card cardTwo) {
        int weight = getWeight(cardOne.getRank(), cardTwo.getRank());
        if (jokerCount > 0) {
            weight--;
        }
        return weight > 0 && weight <= 2;
    }

    private int getWeight(int num, int num2) {
        return Math.abs(num - num2);
    }

    private void addRunCardsIfNotPresent(Card card) {
        if (!runPossibles.contains(card)) {
            runPossibles.add(card);
        }
    }

    public List<Card> getJokers(Hand hand) {
        List<Card> jokers = new ArrayList<>();
        for (Card card : hand.getHand()) {
            if (card.getSuit() == Suit.JOKER) {
                jokers.add(card);
            }
        }
        return jokers;
    }

    // only checking tercia helpfulness
    public boolean cardHelpsHand(Card card, Hand hand) {
        return hand.getHand().get(0).getRank() == card.getRank() || card.getSuit() == Suit.JOKER;
    }

    private List<Suit> getDistinctSuits(Hand hand) {
        return hand.getHand().stream().map(Card::getSuit).distinct().collect(Collectors.toList());
    }

    private List<Integer> getDistinctRanks(Hand hand) {
        return hand.getHand().stream().map(Card::getRank).distinct().collect(Collectors.toList());
    }

    private List<Card> getCardsBySuit(Hand hand, Suit suit) {
        return hand.getHand().stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
    }

    public List<Card> getTerciaPossibles() {
        return terciaPossibles;
    }

    public List<Card> getRunPossibles() {
        return runPossibles;
    }

    public List<Card> getFlexCards() {
        return flexCards;
    }

    public List<List<Card>> getIncompleteTercias() {
        return incompleteTercias;
    }

    public List<List<Card>> getPerfectTercias() {
        return perfectTercias;
    }

    public List<List<Card>> getOverflowTercias() {
        return overflowTercias;
    }

    public List<List<Card>> getIncompleteRuns() {
        return incompleteRuns;
    }

    public List<List<Card>> getPerfectRuns() {
        return perfectRuns;
    }

    public List<List<Card>> getOverflowRuns() {
        return overflowRuns;
    }
}
