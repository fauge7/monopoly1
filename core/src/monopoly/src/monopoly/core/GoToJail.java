package monopoly.core;

public class GoToJail extends Space{
    public GoToJail(){
        super();
        this.name = "Go to Jail";
    }
    public String getType(){
        return "GoToJail";
    }
}
