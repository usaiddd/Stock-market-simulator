import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        User user = new User(name);
        user.loadData();

        while (true) {
            System.out.println("\n========= STOCK TRADING MENU =========");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. View Balance");
            System.out.println("5. Save Data");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter symbol: ");
                String sym = sc.next().toUpperCase();

                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();

                user.buy(sym, qty);
            }

            else if (choice == 2) {
                System.out.print("Enter symbol: ");
                String sym = sc.next().toUpperCase();

                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();

                user.sell(sym, qty);
            }

            else if (choice == 3) {
                System.out.println("\n----- Your Portfolio -----");

                if (user.stockHoldings.isEmpty()) {
                    System.out.println("No stocks owned.");
                } else {
                    user.stockHoldings.forEach((symbol, stock) -> {
                        System.out.println(
                            symbol +
                            " | Qty: " + stock.getQuantity() +
                            " | Buy: " + stock.getPrevPrice() +
                            " | Current: " + stock.getCurPrice()
                        );
                    });
                }
            }

            else if (choice == 4) {
                System.out.println("Balance: " + user.balance);
            }

            else if (choice == 5) {
                user.saveData();
                System.out.println("Data saved.");
            }

            else if (choice == 6) {
                System.out.println("Exiting...");
                user.saveData();
                break;
            }

            else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
