import java.io.*;
import java.util.*;

class Node {
    String question;
    String animals;
    Node left;
    Node right;

    public Node(String question, String animals) {
        this.question = question;
        this.animals = animals;
        this.left = null;
        this.right = null;
    }
}

class AnimalGuessingGame {
    Node root;

    public void makeTreeFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            root = conditions(lines);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node conditions(List<String> lines) {
        if (lines.isEmpty()) {
            return null;
        }

        String line = lines.get(0);
        lines.remove(0);
        String[] parts = line.split(" ", 2);
        int address = Integer.parseInt(parts[0]);
        String rest = parts[1];

        if (rest.startsWith("The animal is ")) {
            String animals = rest.replace("The animal is ", "");
            return new Node(null, animals);
        }

        String question = rest;
        Node leftChild = conditions(lines);
        Node rightChild = conditions(lines);
        Node node = new Node(question, null);
        node.left = leftChild;
        node.right = rightChild;

        return node;
    }

    public void play(Node node) {
        if (node == null) {
            node = root;
        }

        while (node.left != null || node.right != null) {
            if (node.question != null) {
                System.out.println(node.question);
            }

            Scanner scanner = new Scanner(System.in);
            String userAnswer = scanner.nextLine();

            if (userAnswer.equalsIgnoreCase("yes")) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        System.out.println("The animal you are thinking of is: " + node.animals);
    }

    public static void main(String[] args) {
        AnimalGuessingGame tree = new AnimalGuessingGame();
        String filePath = "game1.txt";
        tree.makeTreeFromFile(filePath);

        System.out.println("Welcome To the Animal Guessing Game");
        System.out.println("think of one of the animals mentioned below:-");
        System.out.println(
                "Dog\n-Parrot\n-capybara\n-fox\n-eagle\n-penguine\n-deer\n-zebra\n-cow\n-sheep\n-shark\n-whale\n-crocodile\n-bear\n-lion\n-tiger");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 Play the game");
            System.out.println("2 Exit the program");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "1":
                    tree.play(null);
                    break;
                case "2":
                    System.out.println("Thankyou for playing");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}