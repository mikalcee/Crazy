package com.igaaft.mickalc.crazyeights;

import java.util.List;

/**
 * Created by Michael Canfield on 10/7/2015.
 * class ComputerPlayer keep track of computer's hand
 */
public class ComputerPlayer {

    /**
     * makePlay takes in the computer player's hand and the valid
     * suit an/or rank to play and returns the id for a valid play
     * from its hand
     * @param hand computer's hand
     * @param suit computer's card suit
     * @param rank computer's card rank
     * @return id
     */
    public int makePlay(List<Card> hand, int suit, int rank){
        int play = 0;
        /**
         * Loops through each card in the computer player's hand
         * Determine the rank and suit of each card
         */
        for (int i = 0; i < hand.size(); i++){
            int tempId = hand.get(i).getId();
            int tempRank = hand.get(i).getRank();
            int tempSuit = hand.get(i).getSuit();
            /**
             * check to see whether the rank of the top card of the
             * top card of the discard pile is an 9
             */
            if (rank == 8){
                /**
                 * if rank = 8, then checks the suit to be played
                 * If a card in the computer players hand matches
                 * the valid suit, the id of that card is set as
                 * the card to be played
                 */
                if(suit == tempSuit){
                    play = tempId;
                }// end if
            }// end if
            /**
             * if the top card isn't an 8, check to see whether each card
             * in the computer's hand matches either the rank or suit of the
             * card that's played. If it matches, that card's id is set to
             * return as the one to play
             */
            else if (suit == tempSuit || rank == tempRank ||
                     tempId == 108 || tempId == 208 ||
                     tempId == 308 || tempId == 408 ){
                play = tempId;
            }// end else if
        }// end for
        /**
         * if none of the conditions of either loop are met, then there's no valid
         * play among the cards in the computer opponent's hand. In that case 0
         * is returned, which indicates that a card must be drawn
         */
        return play;
    }// end method makePlay

    /**
     * chooseSuit is called only if the computer opponent plays an 8
     * @param hand computer's hand
     * @return suit
     */
    public  int chooseSuit(List<Card> hand){
         int suit = 100;
        return suit;
    }// end method chooseSuit

}// end class ComputerPlayer
