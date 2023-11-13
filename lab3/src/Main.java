import classes.*;

public class Main{
    public static void main(String[] args){
        Mumi_mom Mumi_mom = new Mumi_mom("Муми-мама");

        Nouns bag1 = Nouns.FEMALE4;
        Thingsroof bag = new Thingsroof("Cумка".substring(0, 4) + bag1.getName(), false);

        Nouns shkatulka1 = Nouns.FEMALE4;
        Thingsroof shkatulka = new Thingsroof("Шкатулка".substring(0, 7) + shkatulka1.getName(), false);

        Thingsroof coffeenik = new Thingsroof("Кофейник", false);
        Thingsroof family_album = new Thingsroof("Семейный альбом", false);
        Guests guests1 = new Guests("Виктор", true);
        Plants bushes = new Plants("Кусты");
        Plants trees = new Plants("Деревья");
        Thingsswim cart = new Thingsswim("Телега", true, true);
        Thingsswim trough = new Thingsswim("Корыто", true, true);
        Thingsswim strollers = new Thingsswim("Детская коляска", false, true);
        Thingsswim tanks = new Thingsswim("Садки для рыб", false, true);
        Thingsswim berths = new Thingsswim("Причалы", true, true);
        Thingsswim hedges = new Thingsswim("Изгороди", false,true);
        House house = new House(Mumi_mom.get_name());


        System.out.println(Mumi_mom.hug(bag, shkatulka, coffeenik, family_album));
        System.out.println(Mumi_mom.move_away());

        Nouns Mumi_mom1 = Nouns.FEMALE3;
        System.out.println(Mumi_mom.get_name().substring(0, 8) + Mumi_mom1.getName() + Mumi_mom.do_not_like());
        System.out.println(house.status_house(guests1));
        System.out.println(bushes.getName() + " и " + trees.getName().toLowerCase() + bushes.swim());

        Adjective cart1 = Adjective.FEMALE;
        Adjective trough1 = Adjective.NEUTER;
        Adjective strollers1 = Adjective.FEMALE;
        Adjective berths1 = Adjective.MN;
        String str = (cart.is_status(cart.status()).substring(0, 4)+cart1.getName() + " " + cart.get_name().toLowerCase() + ", " + trough.is_status(trough.status()).substring(0, 4)+trough1.getName() + " " + trough.get_name().toLowerCase() + ", " + strollers.get_name().toLowerCase() + " " + strollers.is_status(strollers.status()) + ", " + tanks.get_name().toLowerCase() + " " + tanks.is_status(tanks.status())  + ", " + berths.is_status(berths.status()).substring(0, 4)+berths1.getName() + " " + berths.get_name().toLowerCase() + ", " +  hedges.get_name().toLowerCase() + " " + hedges.is_status(hedges.status()) + " " + hedges.swim());
        System.out.println(str.substring(0, 1).toUpperCase() + str.substring(1));
        System.out.println(house.is_furnishings(bag, shkatulka, coffeenik, family_album, cart, tanks, trough, strollers, berths, hedges));






    }
}