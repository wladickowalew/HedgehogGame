package Objects;

import DataClasses.Images;

public class Fire extends GameObject{

    private int price = 5;

    public Fire() {
        super(Images.FIRE);
    }

    public int getPrice(){
        return price;
    }

}