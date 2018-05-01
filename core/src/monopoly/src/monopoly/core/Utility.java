package monopoly.core;


public class Utility extends Property{
    public Utility(String name){
        super();
        this.name = name;

        this.value = 150;
        this.mortgage = 75;
    }
    public String getType(){
        return "Utility";
    }
}
