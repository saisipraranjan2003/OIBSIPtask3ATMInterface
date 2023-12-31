import java.util.ArrayList;
import java.util.Scanner;
public class oasistask3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 if you are a registered user\nEnter 2 if you want to register as a new user");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                registered_user();

            case 2:
                newuser();
        }
    }
    private static ArrayList<Integer> userid = new ArrayList<>();// Declaring arraylist of users
    private static ArrayList<String> passwords = new ArrayList<>();// Declaring arraylist of the passwords
    private static ArrayList<Integer> balance = new ArrayList<>();// Declaring arraylist of the balance amounts
    private static ArrayList<Integer> transaction_history = new ArrayList<>();// Declaring arraylist of transactions
                                                                              // history
    static Scanner sc = new Scanner(System.in);

    private static void newuser() // signing up for a new user
    {
        System.out.println("Hello user please enter " + userid.size() + " as your registration id");
        int enterid = sc.nextInt();
        if (enterid != userid.size())// checking whether the userid entered is overflowed compared to numberofusers
        {
            System.out.println("Enter the recommended id\n");
            newuser();
        } else if (enterid == userid.size())// If valid id is entered for registering
        {
            userid.add(enterid);
            System.out.println("Enter new password having more than 4 characters");
            String password = sc.next();
            if (password.length() < 4)// Validating Password Strength
            {
                newpassword();
                if (newpassword()) {
                    passwords.add(enterid, password);
                } else {
                    newpassword();
                }
            } else// If password validation parameters are satisfied
            {
                passwords.add(enterid, password);// Adding password to corresponding user id
            }
            System.out.println("Registered as a new user Succesfully\n");
            if (balancevalidation(enterid)) {
                System.out.println("Login as a registered user");
            } else {
                balancevalidation(enterid);
            }
            registered_user();// redirecting to login page
        }
    }

    private static boolean balancevalidation(int enterid) {
        System.out.println("Deposit a minimum amount of 100Rs for opening account");
        int minamount = sc.nextInt();
        if (minamount < 100) {
            return false;
        }
        balance.add(enterid, minamount);// Adding amount to corresponding user id's account
        return true;
    }

    private static boolean newpassword() // Checking strength of password based on it's length
    {
        System.out.println("Enter new password having more than 4 characters");
        String password = sc.next();
        if (password.length() >= 4) {
            return true;
        }
        return false;
    }

    private static void checkbalance(int id)// Printing current balance
    {
        System.out.println("Amount in your current account is :- " + (balance.get(id)) + "Rs");
    }

    private static void withdraw_amount(int id)// For withrawing amount from account
    {
        System.out.println("Enter amount to be withdrawed in rupees:- ");
        int withdrawamount = sc.nextInt();
        int currbalance = balance.get(id);
        if (withdrawamount > currbalance) {
            System.out.println("Insufficent amount to withdraw");
        } else {
            balance.set(id, currbalance - withdrawamount);
            transaction_history.add(0 - withdrawamount);
            System.out.println("Updated your balance successfully");
        }
    }

    private static void update_balance(int id)// Updating balance into account
    {
        System.out.println("Enter amount to be deposited in rupees :- ");
        int depositamount = sc.nextInt(); // accepting the amount to be credited
        int updateamount = balance.get(id) + depositamount; // adding amount to our acc
        balance.set(id, updateamount); // updating it in the acc
        System.out.println("Updated your balance successfully");
        if (id == 0) {
            transaction_history.add(depositamount);// adding amount credited to the history
        }
    }

    private static void transer_funds(int id)// transferring amount from one account to another account
    {
        System.out.println("Enter the account id to which you want to transfer funds");
        int depositid = sc.nextInt();
        if (depositid < userid.size() && depositid != id)// checking whether target account for transferring amount is
                                                         // present or not and making sure that current account is not
                                                         // entered as target account for transferring money
        {
            System.out.println("Enter the amount to be transferred");
            int transferamount = sc.nextInt();
            if (transferamount >= balance.get(id)) {
                System.out.println(
                        "Insufficent balance to transfer please try again by adding funds to your account\nIf you want to transfer funds to your account enter 1 else 2");
                int choice = sc.nextInt();
                if (choice == 1) {
                    update_balance(id);
                } else {
                    registered_user();
                }
            } else {
                if (id == 0) {
                    int currentamount = balance.get(depositid);// checking balance in our acc
                    int totaldepositid_amount = currentamount + transferamount;// adding amount to target acc
                    int deduceamount = balance.get(id) - transferamount;// deducing that amount from our acc
                    balance.set(id, deduceamount);// updating amount in our account
                    balance.set(depositid, totaldepositid_amount);// updating amount in target acc
                    transaction_history.add(0 - deduceamount);
                } else if (id != 0) {
                    int currentamount = balance.get(depositid);// checking balance in our acc
                    int totaldepositid_amount = currentamount + transferamount;// adding amount to target acc
                    int deduceamount = balance.get(id) - transferamount;// deducing that amount from our acc
                    balance.set(id, deduceamount);// updating amount in our account
                    balance.set(depositid, totaldepositid_amount);// updating amount in target acc
                    transaction_history.add(deduceamount);
                }
            }
        } else {
            System.out.println("Enter valid userid for depositing amount");
        }

    }

    private static void update_password(int id)// Updating password
    {
        System.out.println("Enter your current password :- ");
        String currpass = sc.next();
        if (currpass.equals(passwords.get(id))) {
            System.out.println("Enter your new password :- ");
            String newpass = sc.next();
            passwords.set(id, newpass);
        } else {
            System.out.println("Invalid password try again\n If you want to try again enter 1 else 2");
            int choice = sc.nextInt();
            if (choice == 1) {
                update_password(id);
            } else {
                registered_user();
            }
        }

    }

    private static void exit_account()// exiting from current account
    {
        System.out.println("If you want to login again enter 1 else enter 2");
        int choice = sc.nextInt();
        if (choice == 1) {
            registered_user();
        } else {
            newuser();
        }

    }

    private static void registered_user() // operations that are performed by a registered user are under this class
    {
        System.out.println("enter your id");
        int id = sc.nextInt();
        System.out.println("enter your password");
        String pass = sc.next();
        String validation = passwords.get(id);
        if (id > userid.size()) {
            System.out.println("Invalid id");
            registered_user();
        } else if (pass.equals(validation) == false) {
            System.out.println("Invalid password !! please try again");
            registered_user();
        } else if (id <= userid.size() && pass.equals(validation) == true) {
            System.out.println("Welcome user please select the operation you want to perform\n");
            int tempid = id;
            while (true) {
                System.out.println("1-Check Balance\n");
                System.out.println("2-Withdraw Amount\n");
                System.out.println("3-Update Balance\n");
                System.out.println("4-Transfer Funds\n");
                System.out.println("5-Update Password\n");
                System.out.println("6-Transaction History\n");
                System.out.println("7-Exit/n");
                int userchoice = sc.nextInt();
                switch (userchoice) {
                    case 1:
                        checkbalance(tempid);
                        break;

                    case 2:
                        withdraw_amount(tempid);
                        break;

                    case 3:
                        update_balance(tempid);
                        break;

                    case 4:
                        transer_funds(tempid);
                        break;

                    case 5:
                        update_password(tempid);
                        break;

                    case 6:
                        transaction_historycheck(tempid);
                        break;

                    case 7:
                        exit_account();

                }
            }
        }
    }

    private static void transaction_historycheck(int id) {
        if (id == 0) {
            System.out.println();
            System.out.println("Welcome user!! This is your transaction history !!");
            System.out.println();
            for (int i = 0; i < transaction_history.size(); i++) {
                if (transaction_history.get(i) < 0) {
                    System.out.println("Rs " + (transaction_history.get(i)) + " have been deducted from your account");
                } else {
                    System.out.println("Rs " + transaction_history.get(i) + " have been credited to your account");
                }
            }
        }
    }

}
