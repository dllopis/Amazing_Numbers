package numbers;
import java.util.*;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private boolean even;
    private boolean odd;
    private boolean buzz;
    private boolean duck;
    private boolean gapful;
    private boolean palindrome;

    private void displayProperty(String input) {
        System.out.printf("Properties of %s%n" + "even: %b%n" + "odd: %b%n" + "buzz: %b%n" +
                "duck: %b%n" + "palindromic: %b%n" + "gapful: %b%n%n", input,
                even, odd, buzz, duck, palindrome, gapful);
    }
    private void displayProperties(String input)    {
        System.out.println(input + " is " +
                (even ? "even," : "odd, ") +
                (buzz ? "buzz, " : "") +
                (duck ? "duck, " : "") +
                (gapful ? "gapful" : "") +
                (palindrome ? "palindromic" : "")
        );
    }
    public void run()   {
        displayInstructions();
        String input;
        do {
            System.out.print("Enter a request: ");
            input = scanner.nextLine();
        } while (parseInput(input));
    }
    private void displayInstructions()  {
        System.out.println("Welcome to Amazing Numbers!\n\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of\n" +
                "the list:\n" +
                "* the first parameter represents a startng number;\n" +
                "* the second parameter shows how many consecutive\n" +
                "numbers are to be printed;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");
    }
    private boolean parseInput(String input)   {
        if(input.matches(""))   {
            displayInstructions();
        }else if(input.matches("[-][0-9]+") || input.matches("[^0-9]+")
                || input.matches("[-][0-9]+\\s[0-9]+")|| input.matches("[^0-9]+\\s[0-9]+")){
            System.out.println("The first parameter should be a natural number or zero.");
        }else if(input.matches("[0-9]+\\s[^0-9]+") || input.matches("[0-9]+\\s-[0-9]+")
        || input.matches("[0-9]+\\s[^0-9]*")){
            System.out.println("The second parameter should be a natural number or zero.");
        }else if (input.equals("0")) {
            System.out.println("\n\nGoodbye!");
            return false;
        }else if(input.matches("\\d+"))   {
            setProperties(input);
            displayProperty(input);
        }else if(input.matches("\\d+\\s+\\d+"))  {
            String[] request = input.split(" ");
            int len = Integer.parseInt(request[1]);

            for(int i = 0; i < len; i++) {
                long num = Long.parseLong(request[0]) + i;
                setProperties(num + "");
                displayProperties(num + "");
            }
        }
        return true;
    }
    private boolean isEven(long input)  {
        return input % 2 == 0;
    }
    private boolean isOdd(long input)  {
        return input % 2 == 1;
    }
    private boolean isBuzzNumber(Long input)    {
        return input % 7 == 0 || input % 10 == 7;
    }
    private boolean isDuckNumber(String input) {
        return input.charAt(0) == '0' && input.substring(1).contains("0")
                || input.charAt(0) != '0' && input.contains("0");
    }
    private boolean isPalindromic(String input) {
        int i = 0;
        int j = input.length() - 1;

        while(i < j)    {
            if(input.charAt(i++) != input.charAt(j--))    {
                return false;
            }
        }
        return true;
    }
    private boolean isGapful(String input)  {
        return input.length() >= 3 && Long.parseLong(input) % Long.parseLong(input.charAt(0) + ""
                + input.charAt(input.length() - 1)) == 0;
    }
    private void setProperties(String input)    {
        long num = Long.parseLong(input);

        this.even = isEven(num);
        this.odd = isOdd(num);
        this.buzz = isBuzzNumber(num);
        this.duck = isDuckNumber(input);
        this.gapful = isGapful(input);
        this.palindrome = isPalindromic(input);
    }
    public static void main(String[] args) {
        Main buzzNumber = new Main();
        buzzNumber.run();
    }
}
