import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Pacjent {

    public enum Sex {
        Kobieta,
        Mężczyzna;
    }

    public enum Ubezpieczenie {
        NFZ,
        Prywatne,
        Brak;
    }

    //Metoda przyporzadkowujaca kolejnym numerom rodzaje ubezpieczenia
    public static String getInsuranceNumber(int mojeUbezpieczenie) {
        switch(mojeUbezpieczenie) {
            case 0:
                return Ubezpieczenie.NFZ.toString();
            case 1:
                return Ubezpieczenie.Prywatne.toString();
            case 2:
                return Ubezpieczenie.Brak.toString();
            default:
                return null;
        }
    }
}


