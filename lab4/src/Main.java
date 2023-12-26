import classes.*;
public class Main{
    public static void main(String[] args){
        Person Mumi_mom = new Person("Муми-мама", false);
        Noun bag1 = Noun.FEMALE4;
        Thingroof bag = new Thingroof("Cумка".substring(0, 4) + bag1.getName(), false, 3);

        Noun shkatulka1 = Noun.FEMALE4;
        Thingroof shkatulka = new Thingroof("Шкатулка".substring(0, 7) + shkatulka1.getName(), false, 2);
        House house = new House(Mumi_mom, 45, true);
        System.out.println(house.moveGhost(house.getStatusGhost()));
        Thingroof coffeenik = new Thingroof("Кофейник", false, 3);
        Thingroof family_album = new Thingroof("Семейный альбом", false, 1);
        Guest guests1 = new Guest("Виктор", true, house);
        Guest guests2 = new Guest("Виктор123", true, house);
        Plant bushes = new Plant("Кусты");
        Plant trees = new Plant("Деревья");
        Thingswim cart = new Thingswim("Телега", true, true, 6);
        Thingswim trough = new Thingswim("Корыто", true, true, 5);
        Thingswim strollers = new Thingswim("Детская коляска", false, true, 5);
        Thingswim tanks = new Thingswim("Садки для рыб", false, true, 3);
        Thingswim berths = new Thingswim("Причалы", true, true, 4);
        Thingswim hedges = new Thingswim("Изгороди", false,true, 7);



        System.out.println(Mumi_mom.hug(bag, shkatulka, coffeenik, family_album));
        System.out.println(Mumi_mom.moveAway());

        Guest[] guests = {guests2, guests1};
        Noun Mumi_mom1 = Noun.FEMALE3;
        System.out.println(Mumi_mom.getName().substring(0, 8) + Mumi_mom1.getName() + Mumi_mom.doNotLike());
        System.out.println(house.statusHouse(guests, house));
        System.out.println(bushes.getName() + " и " + trees.getName().toLowerCase() + bushes.swim());

        Adjective cart1 = Adjective.FEMALE;
        Adjective trough1 = Adjective.NEUTER;
        Adjective strollers1 = Adjective.FEMALE;
        Adjective berths1 = Adjective.MN;
        String str = (cart.isStatus(cart.status()).substring(0, 4)+cart1.getName() + " " + cart.getName().toLowerCase() + ", " + trough.isStatus(trough.status()).substring(0, 4)+trough1.getName() + " " + trough.getName().toLowerCase() + ", " + strollers.getName().toLowerCase() + " " + strollers.isStatus(strollers.status()) + ", " + tanks.getName().toLowerCase() + " " + tanks.isStatus(tanks.status())  + ", " + berths.isStatus(berths.status()).substring(0, 4)+berths1.getName() + " " + berths.getName().toLowerCase() + ", " +  hedges.getName().toLowerCase() + " " + hedges.isStatus(hedges.status()) + " " + hedges.swim());
        System.out.println(str.substring(0, 1).toUpperCase() + str.substring(1));
        int size_things = (Thing.sizeThings(bag, shkatulka, coffeenik, family_album, cart, tanks, trough, strollers, berths, hedges));
        Thing[] list = {bag, shkatulka, coffeenik, family_album, cart, tanks, trough, strollers, berths, hedges};
        try {
            for (Thing th : list){
                if (th.getSize() > Thing.MAXSIZE){
                    throw new MaxSizeLimitException();
                }
            }
            if (house.getSize() < size_things){
                throw new SizeHouseException();
            }
            else if (house.getSize() == size_things) {
                System.out.println("Дом полностью обставлен мебелью");
            }
            else if (house.getSize()*0.8  <= size_things) {
                System.out.println("Дом обставлен мебелью");
            }
            else {
                System.out.println("Мебели к сожалению не хватило, чтобы обставить дом");
            }
        }
        catch (SizeHouseException e){
            System.out.println(e.sendMessage());
        }
        catch (MaxSizeLimitException e){
            System.out.println(e.sendMessage());
        }
        Person father = new Person("Папа", false){
            public String moveAway(){
                return super.getName() + " отодвинулся от моря";
            }
            public String hug(Thingroof... thingsroofs){
                return super.getName() + " обнимал на крыше " + (Thingroof.toString(thingsroofs)).replace("[", "").replace("]", "").toLowerCase();
            }
        };
        System.out.println(father.moveAway());

    }
}