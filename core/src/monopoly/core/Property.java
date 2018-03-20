package monopoly.core;

public class Property extends Space{
    protected Player owner;
    protected int value;
    protected int mortgage;
    protected boolean mortgaged;
    public Property(){
        super();
        this.name = "MissingNo Ave.";
        this.owner = null;
        this.value = 0;
        this.mortgage = 0;
    }
    public Property(String newName){
        super(newName);
        this.owner = null;
        this.value = 0;
        this.mortgage = 0;
    }
    public void setPrice(int newPrice){
        this.value = newPrice;
    }
    public int getPrice(){
        return this.value;
    }
    public void setOwner(Player newOwner){
        this.owner = newOwner;
    }
    public Player getOwner(){
        return this.owner;
    }
    public void setMortgaged(boolean isMortgaged){
        this.mortgaged = isMortgaged;
    }
    public boolean getMortgaged(){
        return this.mortgaged;
    }
    public void setMortgage(int newMortgage){
        this.mortgage = newMortgage;
    }
    public int getMortgage(){
        return this.mortgage;
    }
    //THis is for the interact function to be able to tell what
    //space it is.
    public String getType(){
        return "Property";
    }
}
