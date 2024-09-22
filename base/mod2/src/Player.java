public class Player {
    public enum VARIANTS {
        ROCK,
        PAPER,
        SCISSORS
    }

    private String name;
    private VARIANTS choice;

    public Player() {
        this.name = "Bot";
        this.choice = getRandomVariant();
    }

    public Player(VARIANTS choice, String name) {
        this.choice = choice;
        this.name = name;
    }

    private VARIANTS getRandomVariant() {
        return VARIANTS.values()[(int) (Math.random() * VARIANTS.values().length)];
    }

    public String whoWins(Player player1, Player player2) {
        if (player1.choice == player2.choice) {
            return "Ничья";
        }

        if ((player1.choice == VARIANTS.ROCK && player2.choice == VARIANTS.SCISSORS) ||
                (player1.choice == VARIANTS.PAPER && player2.choice == VARIANTS.ROCK) ||
                (player1.choice == VARIANTS.SCISSORS && player2.choice == VARIANTS.PAPER)) {
            return player1.name + " победил";
        } else {
            return player2.name + " победил";
        }
    }
}
