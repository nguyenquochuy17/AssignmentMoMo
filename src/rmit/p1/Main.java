package rmit.p1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new  Scanner(System.in);
    private static int limitedBudget = 50000;
    private static double winRate = 0.1; //10%
    private static Date firstTimeUse = new Date(); // the date that user first time use the machine

    public static void main(String[] args) {
        while(true){
            // Input note section
            int note = noteInput();

            //Buy product section
            int [] boughtProduct= {0,0,0}; // index 0 is coke, index 1 is pepsi, index 2 is soda. This is used to count product you gonna buy
            note = buyProduct(note,boughtProduct);

            // payment section
            payment(note, boughtProduct);
        }
    }
    // this function is used to implement the winRate chance of winning free product
    private static boolean promotionCheck(double winRate){
        // Example
        // winRate is 0.25 then it means users have 25% chance to win free product,
        return Math.random() <= winRate;
    }


    private static void payment(int note, int[] boughtProduct) {
        while(true){
            System.out.println("--------------------------------------");
            System.out.println("Do you accept to buy selected product");
            System.out.println("1.Yes        2.Cancelled. I want to refund");
            System.out.print("Input number of your option: ");
            int selected = sc.nextInt();
            if(selected == 1) {
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                // compare the current day with the firstTimeUse date
                // if >= 1 then the current day is the next day
                try {
                    currentDate = sdf.parse("12/03/2021");
                    firstTimeUse = sdf.parse("12/01/2021");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // compare the current day with the firstTimeUse date
                // it is not the same day then move on
                if (differenceBetweenDates(firstTimeUse,currentDate) > 0){
                    // if  limitedBudget is not reached, the winrate of the currentday will increase by 50%
                    if( limitedBudget>0 ){
                        // winRate will be calculated depended on the difference dates between the last time date and the current date users use the machine
                        winRate = 0.1 * Math.pow(1.5, differenceBetweenDates(firstTimeUse,currentDate));
                    } else{
                        // Otherwise, reset it to 10%
                        winRate  = 0.1;
                    }
                    // the limitedBudget reset to 50000 when start a new day
                    limitedBudget = 50000;
                    // update the the firstimeUse date to current day
                    firstTimeUse = currentDate;
                }
                // loop through the number of bought product
                for(int i = 0; i < 3; i++){
                    //check if the bought product of Coke or Pepsi or Soda >=3 . If 3 product then get 1 promotion check, 6 product gets 2, 9 products gets 3
                    int numOfProduct = boughtProduct[i]/3;
                    for(int j  = 0 ; j< numOfProduct ;j++){
                        // check if successfully get a free product
                        if(promotionCheck(winRate)) {
                            boughtProduct[i]++; // add 1 free product
                            // decrease limitedBudget if user get a free product
                            if(i == 0){
                                limitedBudget-=10000;
                                System.out.println("Congratulation! You receive 1 free Coke");
                            }else if (i ==1 ){
                                limitedBudget-=10000;
                                System.out.println("Congratulation! You receive 1 free Pepsi");
                            }else{
                                limitedBudget-=20000;
                                System.out.println("Congratulation! You receive 1 free Soda");
                            }

                        }
                    }
                }
                // return the selected product
                System.out.println("Your total selected product: ");
                System.out.print("You got ");
                if (boughtProduct[0] !=0){
                    System.out.print(boughtProduct[0] + " Coke ");
                }
                if (boughtProduct[1] != 0) {
                    System.out.print(boughtProduct[1] + " Pepsi ");
                }
                if (boughtProduct[2] != 0) {
                    System.out.println(boughtProduct[2] + " Soda ");

                }
                if(note != 0){
                    System.out.println("Your change is "+ note);
                }
                break;
            }
            //Refund section
            else if (selected == 2){
                int refund = note + boughtProduct[0] * 10000 + boughtProduct[1] * 10000+ boughtProduct[2]* 20000;
                System.out.println("You got refund "+ refund );
                break;
            }else{
                System.out.println("Wrong input");
            }
        }
    }

    //This method is used to get the difference between dates
    private static long differenceBetweenDates(Date startDate, Date endDate) {
        long difference_In_Time = endDate.getTime() - startDate.getTime();
        return difference_In_Time/ (1000*60*60*24);
    }

    private static int buyProduct(int note,int [] boughtProduct) {
        // show current money
        System.out.println("------------------------------");
        System.out.println("Your current money is "+ note);
        while(true){
            while(true){
                System.out.println("--------------------------------------------------");
                System.out.println("1.Coke(10000)    2.Pepsi(10000)    3.Soda(20000)");
                System.out.print("Please choose the number of the product you want to buy: ");
                int option = sc.nextInt();
                // option1 is Coke
                if(option == 1){
                    // not enough money to buy
                    if(note < 10000){
                        System.out.println("Your money is not enough to buy Coke");

                    }
                    //otherwise, note decrease 10000 and plus 1 at index 0 in boughtproduct
                    else{
                        note-=10000;
                        boughtProduct[0]++; // index O is coke , if buy , then plus 1 at index 0
                        break;
                    }
                }
                else if(option == 2){
                    // not enough money to buy
                    if(note < 10000){
                        System.out.println("Your money is not enough to buy Pepsi");
                    }
                    //otherwise, note decrease 10000 and plus 1 at index 1 in boughtproduct
                    else{
                        note-=10000;
                        boughtProduct[1]++;// index 1 is pepsi , if buy , then plus 1 at index 1
                        break;
                    }
                }else if(option == 3){
                    // not enough money to buy
                    if(note < 20000){
                        System.out.println("Your money is not enough to buy Soda");
                    }
                    //otherwise, note decrease 20000 and plus 1 at index 2  in boughtproduct
                    else{
                        note-=20000;
                        boughtProduct[2]++;// index 2 is soda , if buy , then plus 1 at index 2
                        break;
                    }
                }else{
                    System.out.println("Wrong input");
                }
            }
            // Show currentmoney. If money < 1000 then print not enough to buy any product
            System.out.println("Your current money is: " + note);
            if(note < 1000){
                System.out.println("Your money is not enough to buy any product");
                break;
            }
            //Ask user if they want to continue buying
            while(true) {
                System.out.println("--------------------------------");
                System.out.println("Do you want to continue buying");
                System.out.println("1. Yes    2. No");
                System.out.print("Input number: ");
                int option1 = sc.nextInt();
                if(option1 == 2){
                    return note;
                }else if ( option1 == 1 ){
                    break;
                }else{
                    System.out.println("Wrong input");
                }
            }
        }
        return note;
    }

    public static int noteInput(){
        int note = 0;
        while(true) {
            System.out.println("------------------------");
            System.out.println("Our machine has 3 products: Coke(10000) Pepsi(10000) Soda(20000)");
            System.out.println("We only accepts notes of 10.000, 20.000, 50.000, 100.000, 200.000 VND");
            System.out.println("1. 10.000");
            System.out.println("2. 20.000");
            System.out.println("3. 50.000");
            System.out.println("4. 100.000");
            System.out.println("5. 200.000");
            System.out.print("Please input number of your notes: ");
            int option  = sc.nextInt();
            // increase note variable based on the option the user choose
            if(option == 1){
                note+=10000;
            }else if(option == 2){
                note+=20000;
            }else if(option == 3){
                note+=50000;
            }else if(option == 4){
                note+=100000;
            }else if(option == 5){
                note+=200000;
            }else{
                System.out.println("Wrong input");
            }
            // Ask if they want to continue input note
            while(true) {
                System.out.println("-----------------------------------------");
                System.out.println("Your current money is "+ note);
                System.out.println("Do you want to continue inputting note");
                System.out.println("1. Yes    2. No");
                System.out.print("Choose option: ");
                int option1 = sc.nextInt();
                if(option1 == 1 ){
                    break;
                }else if ( option1 == 2){
                    return note;
                }else{
                    System.out.println("Wrong input");
                }
            }
        }
    }

}
