public class Main {
    public static void main(String[] args) {
        var bot = new Player();
        var alex = new Player(Player.VARIANTS.SCISSORS, "Alex");

        System.out.println(bot.whoWins(bot, alex));
    }
}