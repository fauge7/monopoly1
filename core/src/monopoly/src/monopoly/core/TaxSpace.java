package monopoly.core;


public class TaxSpace extends Space{
    public TaxSpace(){
        super();
        this.name = "Unidentified Tax";
    }
    public String getType(){
        return "TaxSpace";
    }
}
