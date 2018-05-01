package monopoly.core;


public class Lot extends Property{
    //Might need: protected int group;
    protected int houses;
    protected int houseCost;
    protected int[] rent;
    private static int MAXLEVEL = 6;
    public Lot(){
        super();
        this.houses = 0;
        this.houseCost = 0;
        this.mortgage = 0;
        this.rent = new int[MAXLEVEL];
    }
    public Lot(String newName){
        super(newName);
        this.houses = 0;
        this.houseCost = 0;
        this.mortgage =0;
        this.rent = new int[MAXLEVEL];
    }
    public Lot(String name,int value,int[] rent,int mortgage,int houseCost){
        //Constant traits of the property
        this.name = name;
        this.value = value;
        this.rent = rent;
        this.mortgage = mortgage;
        this.houseCost = houseCost;
    }

    public void setHouseCost(int newCost){
        this.houseCost = newCost;
    }
    public int getHouseCost(){
        return this.houseCost;
    }

    public void buildHouse(){
        if (this.houses < 5){
            houses ++;
        } else {
            System.out.println("ERROR! Tried to build house with a hotel!!!");
        }
    }

    public void sellHouse(){
        if(houses <= 0){
            System.out.println("ERROR! Tried to sell house when empty!");
        } else {
            this.houses --;
        }
    }

    public void setRent(int level, int newRent){
        if (level > this.rent.length || level < 0){
            System.out.println("ERROR! tried to access beyond rentList limit!");
        } else{
            this.rent[level] = newRent;
        }
    }

    public void setRent(int[] newRent){
        for(int i = 0; i < this.rent.length; i++){
            this.rent[i] = newRent[i];
        }
    }

    public int getRent(){
        return this.rent[houses];
    }
    /* NO LONGER IN USE
    public int getRent(int level){
        if (level > this.rent.length || level < 0){
            System.out.println("ERROR! tried to access beyond rentList limit!");
            return 0;
        } else{
            return this.rent[level];
        }
    }
    */
    public String getType(){
        return "Lot";
    }

    public int getHouses() {
        return this.houses;
    }

    public boolean getHotel() {
        return this.houses == 5;
    }
}
