import java.util.Calendar;

public class Zabieg extends Wizyta {

    ZabiegWybor mojZabieg;

    public enum ZabiegWybor {
        zabieg1,
        zabieg2,
        zabieg3;
    }

    //Konstruktor zabiegu, o parametrach: data zabiegu i typ zabiegu
    Zabieg (Calendar dataZabiegu, int typZabiegu) {
        super(dataZabiegu);
        mojRodzaj = rodzajWizyty.zabieg;
        switch (typZabiegu) {
            case 3:
                mojZabieg= ZabiegWybor.zabieg1;
                break;
            case 4:
                mojZabieg = ZabiegWybor.zabieg2;
                break;
            case 5:
                mojZabieg = ZabiegWybor.zabieg3;
                break;
            default:
                break;
        }

    }

    //Metoda przyporzadkowujaca kolejnym cyfrom rodzaje zabiegow
    public static String wezNumerZabiegu(int mojZabieg) {
        switch(mojZabieg) {
            case 3:
                return ZabiegWybor.zabieg1.toString();
            case 4:
                return ZabiegWybor.zabieg2.toString();
            case 5:
                return ZabiegWybor.zabieg3.toString();
            default:
                return null;
        }
    }
}
