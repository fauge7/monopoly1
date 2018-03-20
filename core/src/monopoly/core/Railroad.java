package monopoly.core;

public class Railroad extends Property{
    private int[] rent;
    public Railroad(String name){
        super();
        this.name = name;
        this.value = 200;
        this.mortgage = 100;
        rent = new int[]{25,50,100,200};
    }

    // ** Methods related to the rent variable **

    //Getters and Setters for rent variable
    public int[] getRent(){
    	return rent;
    }
    public void setRent(int[] rent){
    	this.rent = rent;
    }
    //Returns a value in the rent array
    public int getRent(int level){
    	if(level - 1 < rent.length){
    		return rent[level - 1];
    	}
    	else{
    		System.out.println("ERROR: Railroad rent out of bounds");
    		return -1;
    	}
    }

    public String getType(){
        return "Railroad";
    }

}
