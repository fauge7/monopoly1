package monopoly.core;

public class LuxuryTax extends TaxSpace{
    private final int amount;
    public LuxuryTax(){
        super();
        this.name = "Luxury Tax";
        amount = 75;
    }
    public int getAmount(){
        return this.amount;
    }
}
