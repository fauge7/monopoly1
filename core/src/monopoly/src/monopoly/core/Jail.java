package monopoly.core;

public class Jail extends Space{
    private final int JAIL_TIME;
    private final int JAIL_FINE;
    public Jail(){
        super();
        this.name = "Jail";
        this.JAIL_TIME = 3;
        this.JAIL_FINE = 50;
    }
    public int getJailTime(){
        return JAIL_TIME;
    }
    public int getJailFine(){
        return JAIL_FINE;
    }


}
