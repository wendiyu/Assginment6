	/**************************************************************************
	 *

	 * Created by: Peter Zhu
	 * Created on: Dec 
	 * This is Main class of Black Jack
	 *
	 ****************************************************************************/
	
	import java.util.Scanner;
	
	public class BlackJack {
	
		public static void main(String[] args){
			
			System.out.println("Blackjack!");
			
			Deck playingDeck = new Deck();
			playingDeck.createDeck();
			playingDeck.shuffle();
			
			Deck playerCards = new Deck();
			
			double playerMoney = 100000.0;
			Deck dealerCards = new Deck();
			
			Scanner userInput = new Scanner(System.in);
			
	while(playerMoney>0){
		//Take Bet
		System.out.println("You have $" + playerMoney + ", how much would you like to bet?");
		double playerBet = userInput.nextDouble();
		boolean endRound = false;
		if(playerBet > playerMoney){
			System.out.println("You cannot bet more than you have.");
			break;
		}
		
		playerCards.draw(playingDeck);
		playerCards.draw(playingDeck);
		
		dealerCards.draw(playingDeck);
		dealerCards.draw(playingDeck);
				
				while(true)
				{
					//Show player cards
					System.out.println("Your Hand:" + playerCards.toString());
					
					//Show Value
					System.out.println("Your hand is currently valued at: " + playerCards.cardsValue());
					
					//Show dealer cards
					System.out.println("Dealer Hand: " + dealerCards.getCard(0).toString() + " and [hidden]");
					
					System.out.println("Would you like to (1)Draw another card or (2)Stand");
					int response = userInput.nextInt();	
					//Draw
					if(response == 1){
						playerCards.draw(playingDeck);
						System.out.println("You draw a:" + playerCards.getCard(playerCards.deckSize()-1).toString());
						//over 21
						if(playerCards.cardsValue() > 21){
							System.out.println("Bust. Currently valued at: " + playerCards.cardsValue());
							playerMoney -= playerBet;
							endRound = true;
							break;
						}
					}
					
					//Stand
					if(response == 2){
						break;
					}
					
				}
					
				//Reveal Dealer Cards
				System.out.println("Dealer Cards:" + dealerCards.toString());
				//See if dealer has more points than player
				if((dealerCards.cardsValue() > playerCards.cardsValue())&&endRound == false){
					System.out.println("Dealer beats you " + dealerCards.cardsValue() + " to " + playerCards.cardsValue());
					playerMoney -= playerBet;
					endRound = true;
				}
				//Dealer hits at 16 stands at 17
				while((dealerCards.cardsValue() < 17) && endRound == false){
					dealerCards.draw(playingDeck);
					System.out.println("Dealer draws: " + dealerCards.getCard(dealerCards.deckSize()-1).toString());
				}
				//Display value of dealer
				System.out.println("Dealers hand value: " + dealerCards.cardsValue());
				//Determine if dealer busted
				if((dealerCards.cardsValue()>21)&& endRound == false){
					System.out.println("Dealer Busts. You win!");
					playerMoney += playerBet;
					endRound = true;
				}
				//Determine if push
				if((dealerCards.cardsValue() == playerCards.cardsValue()) && endRound == false){
					System.out.println("Push.");
					endRound = true;
				}
				//Determine if player wins
				if((playerCards.cardsValue() > dealerCards.cardsValue()) && endRound == false){
					System.out.println("You win the hand.");
					playerMoney += playerBet;
					endRound = true;
				}
				else if(endRound == false) //dealer wins
				{
					System.out.println("Dealer wins.");
					playerMoney -= playerBet;
				}
	
				//End of hand - put cards back in deck
				playerCards.moveAllToDeck(playingDeck);
				dealerCards.moveAllToDeck(playingDeck);
				System.out.println("End of Hand.");
				
			}
			//Game over
			System.out.println("Game over! You lost all your money.");
			
			userInput.close();
			
		}
		
		
	}
