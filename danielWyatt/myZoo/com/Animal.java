package danielWyatt.myZoo.com;

public class Animal {

    // Animal Class attributes
    private String animalName;
    private int age;
    private String species;
    private String sex;

    //Create a static attribute that belongs to the Animal class.
    public static int numOfAnimals = 0;

    //Animal Class constructors
    public Animal(String name, int anAge, String aSpecies, String sex) {
        System.out.println("\n A new Animal object was created.\n");
        
        // Create initial values for the class attributes.
        this.animalName = name;
        this.species = aSpecies;
        this.age = anAge;
        this.sex = sex;
        numOfAnimals++;
    }
    
    public Animal() {
        System.out.println("\n A new Animal object was created.\n");
        numOfAnimals++;
    }
    
    // Getters and Setters for each attribute
    public String getName() {
        return animalName;
    }

    public void setName(String name) {
        this.animalName = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSex() { return sex; }

    public void setSex(String sex) { this.sex = sex; }
}
