import java.util.Calendar;

public class Wizyta {
    Calendar dataWykonania;
    rodzajWizyty mojRodzaj;

    enum rodzajWizyty {
        badanko,
        zabieg
    }

    Wizyta (Calendar dataWykonania) {
        this.dataWykonania = dataWykonania;
    }

}
