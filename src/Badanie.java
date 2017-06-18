import java.util.Calendar;

public class Badanie extends Wizyta{

    BadanieWybor mojeBadanie;

    public enum BadanieWybor {
        badanie1,
        badanie2,
        badanie3;
    }
    
    //Konstruktor badania, o parametrach: data badania i typ badania
    Badanie(Calendar dateBadanie, int typBadania) {
        super(dateBadanie);
        mojRodzaj = rodzajWizyty.badanko;
        switch (typBadania) {
            case 0:
                mojeBadanie = BadanieWybor.badanie1;
                break;
            case 1:
                mojeBadanie = BadanieWybor.badanie2;
                break;
            case 2:
                mojeBadanie = BadanieWybor.badanie3;
                break;
            default:
                break;
        }
    }

    //Metoda przyporzadkowujaca kolejnym cyfrom rodzaje badan
    public static String wezNumerBadania(int mojeBadanie) {
        switch(mojeBadanie) {
            case 0:
                return BadanieWybor.badanie1.toString();
            case 1:
                return BadanieWybor.badanie2.toString();
            case 2:
                return BadanieWybor.badanie3.toString();
            default:
                return null;
        }
    }

}
