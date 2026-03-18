package danielWyatt.myZoo.com;

public class Tiger extends Animal{
    // Unique feature
    private String stripePattern;

    public Tiger(String name, int anAge, String sex) {
        // Call the parent constructor
        super(name, anAge, "Tiger", sex);
        this.stripePattern = "Dense";
    }
    public String getStripePattern() { return stripePattern; }
}
