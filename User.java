import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class User {
    String name="";
    Double balance=0.0;
    HashMap<String, Stock> stockHoldings; 

    public User(String name){
        this.name=name;
        this.balance = 500000.0;
        this.stockHoldings = new HashMap<>();
    }

    public boolean buy(String symbol, int qty){ 
        if (qty<=0){ 
            System.out.println("Invalid Quantity. ");
            return false; 
        }
        Stock s = stockHoldings.get(symbol);
        if (s == null){ 
            double price = stockAPI.getPrice(symbol);
            s = new Stock(symbol, price, price, qty);
        }
        else{ 
            s.updatePrice();
        }
        double p = s.getCurPrice();
        double totalcost = p*qty; 
        if (balance < totalcost){ 
            System.out.println("Insufficient funds. ");
            return false; 
        }
        balance -= totalcost; 
        s.addQuantity(qty); 
        stockHoldings.put(symbol, s);
        saveData(); 
        return true; 
    }

    public boolean sell(String symbol, int qty){ 
        if (!stockHoldings.containsKey(symbol)){ 
            System.out.println("Stock not owned. "); 
            return false; 
        }
        Stock s = stockHoldings.get(symbol);
        if (s.getQuantity() < qty){ 
            System.out.println("Not enough quantity. ");
            return false; 
        }
        s.updatePrice(); 
        double p = s.getPrevPrice(); 
        double totalcost = p*qty; 
        balance += totalcost; 
        s.removeQuantity(qty);
        if (s.getQuantity() == 0){ 
            stockHoldings.remove(symbol);
        }
        else{ 
            stockHoldings.put(symbol, s); 
        }
        saveData(); 
        return true;
    }

    void saveData(){ 
        try{ 
            FileWriter myWriter = new FileWriter("Userinfo.txt");
            myWriter.write(name + "\n");
            myWriter.write(Double.toString(balance) + "\n");
            for (Entry<String, Stock> entry: stockHoldings.entrySet()){ 
                String sym = entry.getKey(); 
                Stock s = entry.getValue(); 
                myWriter.write(sym + " " + s.getPrevPrice() + " " + s.getQuantity() + "\n");
            }
            myWriter.close();
        }
        catch(Exception e){ 
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    void loadData(){
        try (BufferedReader br = new BufferedReader(new FileReader("Userinfo.txt"))) {
            name = br.readLine();
            String balLine = br.readLine();
            balance = Double.parseDouble(balLine);
            stockHoldings.clear();

            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(" ");

                String symbol = arr[0];
                double buyPrice = Double.parseDouble(arr[1]);
                int qty = Integer.parseInt(arr[2]);
                double currPrice = stockAPI.getPrice(symbol);
                Stock s = new Stock(symbol, currPrice, buyPrice, qty);

                stockHoldings.put(symbol, s);
            }

        } 
        catch (Exception e) {   
            e.printStackTrace();
        }
    }
    public static void main(String[] args){ 
        
    }
}
