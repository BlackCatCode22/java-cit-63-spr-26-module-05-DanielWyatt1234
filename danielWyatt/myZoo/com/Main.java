package danielWyatt.myZoo.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static Map<String, ArrayList<String>> loadAllNames(String fileName) {
        Map<String, ArrayList<String>> nameBank = new HashMap<>();

        try (Scanner sc = new Scanner(new File(fileName))) {
            String currentSpecies = "";

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                // Look for any species header (e.g., "Lion Names:")
                if (line.endsWith("Names:")) {
                    currentSpecies = line.split(" ")[0]; // Gets "Lion" from "Lion Names:"
                    nameBank.put(currentSpecies, new ArrayList<>());
                }
                // If the line isn't empty, and we have a species set, it's a name line
                else if (!line.isEmpty() && !currentSpecies.isEmpty()) {
                    String[] namesArray = line.split(",");
                    for (String name : namesArray) {
                        nameBank.get(currentSpecies).add(name.trim());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
        return nameBank;
    }

    public static ArrayList<String> getHyenaNames(String fileName) {
        ArrayList<String> hyenaNames = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                // Look for the specific header
                if (line.startsWith("Hyena Names:")) {
                    // The names are on the next non-empty line
                    while (sc.hasNextLine()) {
                        String namesLine = sc.nextLine().trim();
                        if (!namesLine.isEmpty()) {
                            // Split by comma
                            String[] namesArray = namesLine.split(",");
                            for (String name : namesArray) {
                                // Clean up extra spaces and add to list
                                hyenaNames.add(name.trim());
                            }
                            return hyenaNames;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return hyenaNames;
    }

    public static void createReport(ArrayList<Animal> animals) {
        try (PrintWriter pw = new PrintWriter(new File("C:/Users/wyatt/Spring Semester 26/CIT-63/Notes/newAnimals.txt"))) {
            pw.println("Java Zoo Arrival Report");
            pw.println("========================\n");

            // Define the species we are looking for
            String[] speciesList = {"Hyena", "Lion", "Tiger", "Bear"};

            for (String species : speciesList) {
                pw.println(species + "s:");
                pw.println("--------------------");

                int count = 0;
                for (Animal a : animals) {
                    String detail = "";

                    if (a instanceof Hyena) {
                        // Casting 'a' to Hyena to access its unique method
                        detail = " (Laugh: " + ((Hyena) a).getLaugh() + ")";
                    } else if (a instanceof Lion) {
                        detail = " (Mane: " + ((Lion) a).getManeSize() + ")";
                    } else if (a instanceof Tiger) {
                        detail = " (Stripes: " + ((Tiger) a).getStripePattern() + ")";
                    } else if (a instanceof Bear) {
                        detail = " (Hibernating: " + ((Bear) a).isHibernating() + ")";
                    }

                    // Check if the animal matches the current species
                    if (a.getSpecies().equalsIgnoreCase(species)) {
                        pw.println("Name: " + a.getName() + ", Age: " + a.getAge() + ", Sex: " + a.getSex() + detail);
                        count++;
                    }
                }
                pw.println("Total " + species + "s: " + count + "\n");
            }
            pw.println("Total Animals in Zoo: " + Animal.numOfAnimals);
            System.out.println("Report generated successfully in \"C:/Users/wyatt/Spring Semester 26/CIT-63/Notes/newAnimals.txt\"");

        } catch (FileNotFoundException e) {
            System.out.println("Error creating report file.");
        }
    }

    public static void main(String[] args) {
        // 1. Load everything into the map
        String path1 = "C:/Users/wyatt/Spring Semester 26/CIT-63/Notes/animalNames.txt";
        Map<String, ArrayList<String>> nameBank = loadAllNames(path1);

        ArrayList<Animal> animals = new ArrayList<>();
        String path2 = "C:/Users/wyatt/Spring Semester 26/CIT-63/Notes/arrivingAnimals.txt";
        File file1 = new File(path2);

        try (Scanner sc = new Scanner(file1)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) continue; // Skip blank lines

                String[] parts = line.split(", ");
                if (parts.length >= 1) {
                    String ageAndSpecies = parts[0];
                    String[] theParts = ageAndSpecies.split(" ");

                    // Extract age (index 0) and species (index 4)
                    int anAge = Integer.parseInt(theParts[0]);
                    String aSex = theParts[3]; // Gets "female" or "male"
                    String rawSpecies = theParts[4].toLowerCase(); // e.g., "hyena"

                    // 1. Format the species to match the Map keys (e.g., "hyena" -> "Hyena")
                    String speciesKey = rawSpecies.substring(0, 1).toUpperCase() + rawSpecies.substring(1).toLowerCase();

                    // 2. Safely pull a name from the nameBank
                    String animalName = "Unknown Name";
                    if (nameBank.containsKey(speciesKey) && !nameBank.get(speciesKey).isEmpty()) {
                        // Get the ArrayList for this species, then remove the first name
                        animalName = nameBank.get(speciesKey).remove(0);
                    }

                    // 3. Create the specific object (Polymorphism)
                    Animal myAnimal;
                    switch (speciesKey) {
                        case "Hyena":
                            myAnimal = new Hyena(animalName, anAge, aSex);
                            break;
                        case "Lion":
                            myAnimal = new Lion(animalName, anAge, aSex);
                            break;
                        case "Tiger":
                            myAnimal = new Tiger(animalName, anAge, aSex);
                            break;
                        case "Bear":
                            myAnimal = new Bear(animalName, anAge, aSex);
                            break;
                        default:
                            myAnimal = new Animal(animalName, anAge, speciesKey, aSex);
                    }
                    animals.add(myAnimal);
                    System.out.println(parts[0]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Animal file not found: " + path2);
        }

        // 4. Final Output
        System.out.println("--- New Zoo Arrivals ---");
        for (Animal animal : animals) {
            System.out.println("Name: " + animal.getName() + " | Age: " + animal.getAge() + " | Sex: " + animal.getSex() + " | Species: " + animal.getSpecies());
        }

        // This relies on your Animal class having the static counter
        System.out.println("\nTotal number of animals created: " + Animal.numOfAnimals);

        createReport(animals);
    }
}