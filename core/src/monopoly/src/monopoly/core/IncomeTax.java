package monopoly.core;

public class IncomeTax extends TaxSpace{
    public IncomeTax(){
        super();
        this.name = "Income Tax";
    }
    public String getType(){
        return "IncomeTax";
    }

}
