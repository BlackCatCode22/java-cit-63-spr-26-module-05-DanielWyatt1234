package danielWyatt.myZoo.com;

public class Bear extends Animal {
    // Unique feature
    private boolean isHibernating;

    public Bear(String name, int anAge, String sex) {
        //Call the parent constructor
        super(name, anAge, "Bear", sex);
        this.isHibernating = false;
    }
    public boolean isHibernating() { return isHibernating; }
}
