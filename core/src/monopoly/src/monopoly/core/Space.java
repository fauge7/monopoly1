package monopoly.core;

public class Space {
    protected String name;
    public Space(){
        this.name = "MissingNo";
    }
    public Space(String newName){
        this.name = newName;
    }


    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }
    public String getType(){
        return "Space";
    }


}
