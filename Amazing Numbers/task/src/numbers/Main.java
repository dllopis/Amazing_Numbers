package numbers;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    private final Scanner scanner;
    private final DecimalFormat formatter;
    private final Set<String> properties;
    private Map<String, Boolean> map;

    public Main()   {
        this.scanner = new Scanner(System.in);
        this.formatter = new DecimalFormat("#,###");
        this.properties = new HashSet<>();

        this.properties.add("even");
        this.properties.add("odd");
        this.properties.add("buzz");
        this.properties.add("duck");
        this.properties.add("gapful");
        this.properties.add("palindromic");
        this.properties.add("spy");
        this.properties.add("sunny");
        this.properties.add("square");
    }
    private boolean isEven(long input)  {   return input % 2 == 0;  }
    private boolean isOdd(long input)   {   return input % 2 == 1;  }
    private boolean isBuzz(long input)  {   return input % 7 == 0 || input % 10 == 7;   }
    private boolean isDuck(String input)    {
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
    private boolean isSpy(String input) {
        String[] digits = input.split("");
        long sum = 0;
        long product = 1;

        for (String digit : digits) {;
            long num = Long.parseLong(digit);
            sum += num;
            product *= num;
        }
        return sum == product;
    }
    private boolean isSunny(long num)   {   return isSquare(num + 1);   }
    private boolean isSquare(long num)  {
        return num >= 0 ? (long)Math.sqrt(num) * (long)Math.sqrt(num) == num ? true : false : false;
    }
    /*
        Populate our map with boolean values of each property, so that
        we can have them readily accessible.
     */
    private void setProperties(String input)    {
        long num = Long.parseLong(input);
        this.map.put("even", isEven(num));
        this.map.put("odd", isOdd(num));
        this.map.put("buzz", isBuzz(num));
        this.map.put("duck", isDuck(input));
        this.map.put("gapful", isGapful(input));
        this.map.put("palindromic", isPalindromic(input));
        this.map.put("spy", isSpy(input));
        this.map.put("sunny", isSunny(num));
        this.map.put("square", isSquare(num));
    }
    /*
        Prints out to console whether each property is true of false
     */
    private void displayProperty(String input)  {
        System.out.printf("Properties of %s%n" +
                        "even: %b%n" + "odd: %b%n" +
                        "buzz: %b%n" +
                        "duck: %b%n" +
                        "palindromic: %b%n" +
                        "gapful: %b%n" +
                        "spy: %b%n" +
                        "sunny: %b%n" +
                        "square: %b%n%n",
                this.formatter.format(Long.parseLong(input)),
                this.map.get("even"),
                this.map.get("odd"),
                this.map.get("buzz"),
                this.map.get("duck"),
                this.map.get("palindromic"),
                this.map.get("gapful"),
                this.map.get("spy"),
                this.map.get("sunny"),
                this.map.get("square")
        );
    }
    /*
        Prints out to console the input value with properties equal to true.
     */
    private void displayProperties(String input)    {
        Iterator<Map.Entry<String, Boolean>> itr = this.map.entrySet().iterator();
        StringBuilder sb = new StringBuilder();

        if(itr.hasNext())   {
            sb.append(this.formatter.format(Long.parseLong(input))).append(" is ");
        }
        while(itr.hasNext()) {
            Map.Entry<String, Boolean> e1 = itr.next();
            sb.append(e1.getValue() ? itr.hasNext() ? e1.getKey() + ", " : e1.getKey() : "");
        }
        if(sb.charAt(sb.length() - 2) == ',')   {
            sb.setLength(sb.length() - 2);
        }
        System.out.println(sb);
    }
    /*
        Handles error output.
     */
    private void displayCodeMsg(int code, String userInput) {
        switch(code)    {
            case 0  :   {
                System.out.println("\n\nGoodbye!");
                break;
            }
            case 1  :   {
                System.out.println("The first parameter should be a natural number or zero.");
                break;
            }
            case 2  :   {
                System.out.println("The second parameter should be a natural number.");
                break;
            }
            case 3  :   {
                System.out.printf("The property [%s] is wrong.\n" +
                        "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]%n%n", userInput);
                break;
            }
            case 4  :   {
                System.out.printf("The request contains mutually exclusive properties: %n[%s]%n" +
                        "There are no numbers with these properties.%n%n", userInput);
                break;
            }
            case 5 :    {
                System.out.printf("The properties [%s] are wrong.\n" +
                        "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]%n%n", userInput);
                break;
            }
            default :   break;
        }
    }
    /*
        Criteria to pass challenge
     */
    private void displayInstructions()  {
        System.out.println("Welcome to Amazing Numbers!\n\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of\n" +
                "the list:\n" +
                "* the first parameter represents a starting number;\n" +
                "* the second parameter shows how many consecutive\n" +
                "numbers are to be printed;\n" +
                "two natural numbers and a property to search for;\n" +
                "- two natural numbers and two properties to search for;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");
    }
    private boolean validateInput(String input)    {
        String[] userInput = input.split(" ");
        int len = userInput.length;

        if(len == 1)   {
            if(userInput[0].matches("[-][0-9]+") || input.matches("[^0-9]+") || input.length() > 19)   {
                displayCodeMsg(1, "");
                return false;
            }
        }else if(len == 2) {
            if(userInput[0].matches("[-][0-9]+") || userInput[0].matches("[^0-9]+") || userInput[0].length() > 19)   {
                displayCodeMsg(1, "");
                return false;
            }
            if(userInput[1].matches("[-][0-9]+") || userInput[1].matches("[^0-9]+") || userInput[1].length() > 19)   {
                displayCodeMsg(2, "");
                return false;
            }
        }else if(len == 3) {
            if(userInput[0].matches("[-][0-9]+") || userInput[0].matches("[^0-9]+") || userInput[0].length() > 19)   {
                displayCodeMsg(1, "");
                return false;
            }
            if(userInput[1].matches("[-][0-9]+") || userInput[1].matches("[^0-9]+") || userInput[1].length() > 19)   {
                displayCodeMsg(2, "");
                return false;
            }
            if(!userInput[2].matches("[a-zA-Z]+"))  {
                displayCodeMsg(3, userInput[2]);
                return false;
            }
        }else if(len == 4) {
            if(userInput[0].matches("[-][0-9]+") || userInput[0].matches("[^0-9]+") || userInput[0].length() > 19)   {
                displayCodeMsg(1, "");
                return false;
            }
            if(userInput[1].matches("[-][0-9]+") || userInput[1].matches("[^0-9]+") || userInput[1].length() > 19)   {
                displayCodeMsg(2, "");
                return false;
            }
            if(!userInput[2].matches("[a-zA-Z]+"))  {
                displayCodeMsg(3, userInput[2]);
                return false;
            }
            if(!userInput[3].matches("[a-zA-Z]+"))  {
                displayCodeMsg(3, userInput[3]);
                return false;
            }
            /*
                Error check for mutually exclusive properties and natural numbers
             */
            if(Long.parseLong(userInput[0]) > 0 && Long.parseLong(userInput[1]) > 0
                    && this.properties.contains(userInput[2]) && this.properties.contains(userInput[3])) {
                String exclusiveCheck = userInput[2] + " " + userInput[3];

                if (exclusiveCheck.equals("even odd") || exclusiveCheck.equals("odd even") ||
                        exclusiveCheck.equals("duck spy") || exclusiveCheck.equals("spy duck") ||
                        exclusiveCheck.equals("sunny square") || exclusiveCheck.equals("square sunny")) {
                    displayCodeMsg(4, exclusiveCheck);
                    return false;
                }
            }   else    {
                if(!this.properties.contains(userInput[2]) && !this.properties.contains(userInput[3]))   {
                    displayCodeMsg(5, (userInput[2] + " " + userInput[3]).toUpperCase());
                    return false;
                }   else if(!this.properties.contains(userInput[2]))    {
                    displayCodeMsg(3, userInput[2].toUpperCase());
                    return false;
                }   else    {
                    displayCodeMsg(3, userInput[3] .toUpperCase());
                    return false;
                }
            }
        }
        return true;
    }
    /*
        This method is in need of some refactoring and doesn't cover all the basis other then
        what is needed to complete the challenge.
        Simply, it checks if the input matches a certain structure with regex.
     */
    private boolean parseInput(String input)   {
        if(validateInput(input))    {
            this.map = new HashMap<>();

            if(input.equals(""))   {
                displayInstructions();
            }else if (input.equals("0")) {
                displayCodeMsg(0, "");
                return false;
            }else if(input.matches("\\d+"))   {
                map = new HashMap<>();
                setProperties(input);
                displayProperty(input);
            }else if(input.matches("\\d+\\s\\d+"))  {
                String[] request = input.split(" ");
                int len = Integer.parseInt(request[1]);

                for(int i = 0; i < len; i++) {
                    map = new HashMap<>();
                    long num = Long.parseLong(request[0]) + i;
                    setProperties(num + "");
                    displayProperties(num + "");
                }
            }else if(input.matches("\\d+\\s\\d+\\s[a-zA-Z]+"))   {
                String[] request = input.split(" ");
                if(properties.contains(request[2]))   {
                    long start = 0;
                    int end = Integer.parseInt(request[1]);
                    int count = 0;

                    while(start < Long.MAX_VALUE && count < end)  {
                        this.map = new HashMap<>();
                        long num = Long.parseLong(request[0]) + (start++);
                        setProperties(num + "");

                        if(map.get(request[2]))    {
                            displayProperties(num + "");
                            count++;
                        }
                    }
                }   else    { // remove this!!
                    displayCodeMsg(3, request[2].toUpperCase());
                }
            }else if(input.matches("\\d+\\s\\d+\\s[a-zA-Z]+\\s[a-zA-Z]+"))  {
                String[] request = input.split(" ");
                /*
                    Print 'n' values which satisfy two properties!
                 */
                long start = 0;
                int end = Integer.parseInt(request[1]);
                int count = 0;

                while(start < Long.MAX_VALUE && count < end)  {
                    this.map = new HashMap<>();
                    long num = Long.parseLong(request[0]) + (start++);
                    setProperties(num + "");

                    if(map.get(request[2]) && map.get(request[3]))    {
                        displayProperties(num + "");
                        count++;
                    }
                }
            }
        }
        return true;
    }
    /*
        Driver method to be called in main.
        Helps avoid clutter.
     */
    public void run()   {
        displayInstructions();
        String input;
        do {
            System.out.print("Enter a request: ");
            input = scanner.nextLine();
        } while (parseInput(input.toLowerCase()));
        scanner.close();
    }
    public static void main(String[] args) {
        new Main().run();
    }
}
