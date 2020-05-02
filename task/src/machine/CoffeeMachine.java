package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;
    private State currentState;

    public CoffeeMachine() {
        water = 400;
        milk = 540;
        coffeeBeans = 120;
        cups = 9;
        money = 550;
        setIdle();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine cm = new CoffeeMachine();

        while (cm.isOn()) {
            cm.processInput(scanner.next());
        }

        scanner.close();
    }

    public void processInput(String input) {
        switch (currentState) {
            case IDLE:
                switch (input) {
                    case "buy":
                        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                        setState(State.CHOOSING_COFFEE);
                        break;
                    case "fill":
                        System.out.println("\nWrite how many ml of water do you want to add:");
                        setState(State.FILLING_WATER);
                        break;
                    case "take":
                        take();
                        setIdle();
                        break;
                    case "remaining":
                        printQuantities();
                        setIdle();
                        break;
                    case "exit":
                        setState(State.OFF);
                        break;
                }
                break;
            case CHOOSING_COFFEE:
                switch (input) {
                    case "1":
                    case "2":
                    case "3":
                        buy(Integer.parseInt(input));
                        break;
                    case "back":
                        setIdle();
                        break;
                }
                break;
            case FILLING_WATER:
                water += Integer.parseInt(input);
                System.out.println("Write how many ml of milk do you want to add:");
                setState(State.FILLING_MILK);
                break;
            case FILLING_MILK:
                milk += Integer.parseInt(input);
                System.out.println("Write how many grams of coffee beans do you want to add:");
                setState(State.FILLING_COFFEE_BEANS);
                break;
            case FILLING_COFFEE_BEANS:
                coffeeBeans += Integer.parseInt(input);
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                setState(State.FILLING_CUPS);
                break;
            case FILLING_CUPS:
                cups += Integer.parseInt(input);
                setIdle();
                break;
        }
    }

    private void printQuantities() {
        System.out.println("\nThe coffee machine has:\n" +
                            water + " of water\n" +
                            milk + " of milk\n" +
                            coffeeBeans + " of coffee beans\n" +
                            cups + " of disposable cups\n" +
                            "$" + money + " of money");
    }

    private void setIdle() {
        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
        setState(State.IDLE);
    }

    private void buy(int type) {
        switch (type) {
            case 1:
                if (areSuppliesSufficient(250, 0, 16, 1)) {
                    System.out.println("I have enough resources, making you a coffee!");
                    water -= 250;
                    coffeeBeans -= 16;
                    cups -= 1;
                    money += 4;
                    setIdle();
                    return;
                }
                break;
            case 2:
                if (areSuppliesSufficient(350, 75, 20, 1)) {
                    System.out.println("I have enough resources, making you a coffee!");
                    water -= 350;
                    milk -= 75;
                    coffeeBeans -= 20;
                    cups -= 1;
                    money += 7;
                    setIdle();
                    return;
                }
                break;
            case 3:
                if (areSuppliesSufficient(200, 100, 12, 1)) {
                    System.out.println("I have enough resources, making you a coffee!");
                    water -= 200;
                    milk -= 100;
                    coffeeBeans -= 12;
                    cups -= 1;
                    money += 6;
                    setIdle();
                    return;
                }
                break;
        }
        setIdle();
    }

    private boolean areSuppliesSufficient(int water, int milk, int coffeeBeans, int cups) {
        if (this.water < water) {
            System.out.println("Sorry, not enough water!");
            return false;
        } else if (this.milk < milk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        } else if (this.coffeeBeans < coffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        } else if (this.cups < cups) {
            System.out.println("Sorry, not enough cups");
            return false;
        } else {
            return true;
        }
    }

    public boolean isOn() {
        if (currentState != State.OFF) {
            return true;
        } else {
            return false;
        }
    }

    private void take() {
        System.out.println("\nI gave you $" + money);
        money = 0;
    }

    private void setState(State state) {
        currentState = state;
    }
}