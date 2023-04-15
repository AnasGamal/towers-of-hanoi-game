import java.util.Scanner;
import java.util.Stack;

public class Main {
    // function that takes the requested amount of disks, initializes stacks for rods, and populates the source rod
    // then runs a function that solves the puzzle
    public static void solveComputer(int numDisks) {
        // Initialize three stacks for the three rods
        Stack<Integer> source = new Stack<>();
        Stack<Integer> space = new Stack<>();
        Stack<Integer> target = new Stack<>();

        // Push disks onto the source stack in descending order
        for (int i = numDisks; i >= 1; i--) {
            source.push(i);
        }
        printRods(source, space, target);
        // Call the moveComputer function to solve the puzzle
        moveComputer(numDisks, source, space, target);
    }

    // function that takes the requested number of disks and the three stacks representing the rods
    public static void moveComputer(int numDisks, Stack<Integer> source, Stack<Integer> space, Stack<Integer> target) {
        // Base case: if there is only one disk, move it directly from source to target
        if (numDisks == 1) {
            int disk = source.pop();
            target.push(disk);
            System.out.println("Move disk " + disk);
            printRods(source, space, target);
        }
        // Recursive case: move n-1 disks from source to space, then move the remaining disk from source to target,
        // and finally move the n-1 disks from space to target
        else {
            // Move n-1 disks from source to space, using target as an intermediary
            moveComputer(numDisks - 1, source, target, space);

            // Move the remaining disk from source to target
            int disk = source.pop();
            target.push(disk);
            System.out.println("Move disk " + disk);
            printRods(source, space, target);
            // Move the n-1 disks from space to target, using source as an intermediary
            moveComputer(numDisks - 1, space, source, target);
        }
    }

    public static boolean isLegalMove(Stack<Integer> source, Stack<Integer> target) {
        // Check if source rod is empty
        if (source.empty()) {
            return false;
        }
        // Get the top disk of source rod
        int sourceDisk = source.peek();
        // If target rod is empty, it is a legal move
        if (target.empty()) {
            return true;
        }
        // Get the top disk of target rod
        int targetDisk = target.peek();
        // If the source disk is smaller than the target disk, it is a legal move
        return sourceDisk < targetDisk;
    }

    public static boolean moveDisk(int sourceRod, int targetRod, Stack<Integer> source, Stack<Integer> space, Stack<Integer> target) {
        // Determine which stack corresponds to the source rod
        Stack<Integer> sourceStack;
        if (sourceRod == 1) {
            sourceStack = source;
        } else if (sourceRod == 2) {
            sourceStack = space;
        } else {
            sourceStack = target;
        }
        // Determine which stack corresponds to the target rod
        Stack<Integer> targetStack;
        if (targetRod == 1) {
            targetStack = source;
        } else if (targetRod == 2) {
            targetStack = space;
        } else {
            targetStack = target;
        }
        // Check if it is a legal move
        if (isLegalMove(sourceStack, targetStack)) {
            // Move the top disk from the source stack to the target stack
            int disk = sourceStack.pop();
            targetStack.push(disk);
            return true;
        } else {
            // If it is not a legal move, do not move the disk
            return false;
        }
    }

    // Checks if the game is over by verifying if all the disks have been successfully transferred to the target rod.
    public static boolean isGameOver(int numDisks, Stack<Integer> target) {
        // If the number of disks on the target rod is less than the total number of disks, the game is not over.
        if (target.size() != numDisks) {
            return false;
        }
        int prevDisk = numDisks + 1;
        // make sure disks are in ascending order
        for (int disk : target) {
            if (disk >= prevDisk) {
                return false;
            }
            prevDisk = disk;
        }
        // If all the disks are in the correct order and on the target rod, the game is over.
        return true;
    }
    // Prints the current position of the disks on each rod.
    public static void printRods(Stack<Integer> source, Stack<Integer> space, Stack<Integer> target) {
        System.out.println("1: " + source);
        System.out.println("2: " + space);
        System.out.println("3: " + target);
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // ask for number of discs
        System.out.print("Enter the number of disks: ");
        int numDisks = input.nextInt();
        // accept number of disks between 1 and 8
        while (numDisks < 1 || numDisks > 8) {
            System.out.println("The number of disks must be between 1 and 8.");
            System.out.print("Enter the number of disks: ");
            numDisks = input.nextInt();
        }
        // ask for mode
        System.out.println("1) Let the computer solve it");
        System.out.println("2) Let me solve it");
        System.out.print("Enter the mode: ");
        int mode = input.nextInt();
        // accept only 1 or 2
        while (mode != 1 && mode != 2) {
            System.out.println("Invalid mode.");
            System.out.println("1) Let the computer solve it");
            System.out.println("2) Let me solve it");
            System.out.print("Enter the mode: ");
            mode = input.nextInt();
        }
        // 1: automated mode runs
        if (mode == 1) {
            System.out.println("The program will solve this for you...");
            solveComputer(numDisks);
        } else { // 2: single player mode runs
            System.out.println("I will let you solve the puzzle");
            System.out.println("The end goal is to move all the disks from the 1st rod to the 3rd rod");
            System.out.println("You can move only one disk at a time, you cannot place a larger disk on a smaller disk");
            System.out.println("Good luck!");

            int numMoves = 0;
            Stack<Integer> source = new Stack<>();
            Stack<Integer> space = new Stack<>();
            Stack<Integer> target = new Stack<>();
            for (int i = numDisks; i >= 1; i--) {
                source.push(i);
            }
            printRods(source, space, target);

            // keep running till the left rod has all of the disks
            while (!isGameOver(numDisks, target)) {

                // ask from where to move (only accept numbers from 1 to 3)
                System.out.print("Enter the source rod (1, 2, or 3): ");
                int sourceRod = input.nextInt();
                while (sourceRod < 1 || sourceRod > 3) {
                    System.out.println("Invalid rod.");
                    System.out.print("Enter the source rod (1, 2, or 3): ");
                    sourceRod = input.nextInt();
                }
                // ask to move where (only accept numbers from 1 to 3)
                System.out.print("Enter the target rod (1, 2, or 3): ");
                int targetRod = input.nextInt();
                while (targetRod < 1 || targetRod > 3) {
                    System.out.println("Invalid rod.");
                    System.out.print("Enter the target rod (1, 2, or 3): ");
                    targetRod = input.nextInt();
                }
                // runs a function that , the requested move if it's legal
                if (moveDisk(sourceRod, targetRod, source, space, target)) {
                    numMoves++;
                    printRods(source, space, target);
                } else { // requested move is not legal, prompt the user to try again
                    System.out.println("Invalid move. Make sure you're not putting a large disk over a smaller disk, or you're not trying to move from an empty rod.");
                }
            }

            System.out.println("You won! It took you " + numMoves + " moves to solve the puzzle.");
        }
    }
}