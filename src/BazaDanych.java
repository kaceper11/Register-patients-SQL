import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.proteanit.sql.DbUtils;
import javax.swing.table.*;


public class BazaDanych {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:C:\\Users\\Kacperek\\IdeaProjects\\Projekt2\\pacjent.db";
    private static Connection conn = null;
    private static  Statement stat = null;
    private static ResultSet rs = null;

    public static void Baza() {
        try {
            Class.forName(BazaDanych.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
        createTables();
    }

    //Metoda tworzaca tabele z lista pacjentow i lista ich badan/zabiegow
    public static boolean createTables()  {
        String createPacjenci = "CREATE TABLE IF NOT EXISTS pacjenci (Imie TEXT, Nazwisko TEXT, Pesel TEXT PRIMARY KEY UNIQUE NOT NULL, " +
                                "Plec TEXT, Ubezpieczenie TEXT, HospStart INTEGER, HospKoniec INTEGER)";
        String createWizyta = "CREATE TABLE IF NOT EXISTS wizyty (WizytaID INTEGER PRIMARY KEY AUTOINCREMENT, DataWizyty INTEGER, Wizyta TEXT, Pesel TEXT, " +
                                "FOREIGN KEY(Pesel) REFERENCES pacjenci(Pesel))";

        try {
            stat.execute(createPacjenci);
            stat.execute(createWizyta);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Metoda konwertujaca date na liczbe w formacie long
    public static long convertDateToLong(Calendar time) {
        return time.getTimeInMillis()/1000;
    }

    //Metoda konwertujaca liczbe w formacie long na date
    public static Calendar convertLongToDate(long timeInSeconds) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timeInSeconds*1000);
        return date;
    }

    //Metoda konwertujaca liczbe w formacie long na date format
    public static String convertLongToDateFormat(long timeInSeconds) {
        Calendar date = convertLongToDate(timeInSeconds);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date.getTime());
    }

    //Metoda dodajaca pacjenta do tabeli w bazie danych
    public static boolean insertPacjent(String Imie, String Nazwisko , String Pesel, String Plec,
                                        Integer Ubezpieczenie, Calendar HospStart, Calendar HospEnd ) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT OR REPLACE INTO pacjenci VALUES (?,?,?,?,?,?,?);");
            prepStmt.setString(1, Imie );
            prepStmt.setString(2, Nazwisko );
            prepStmt.setString(3, Pesel );
            prepStmt.setString(4, Plec );
            prepStmt.setInt(5, Ubezpieczenie);
            prepStmt.setLong(6, BazaDanych.convertDateToLong(HospStart));
            if(HospEnd!= null)
                prepStmt.setLong(7, BazaDanych.convertDateToLong(HospEnd));
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu pacjenta");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Metoda dodajaca wizyte do pacjenta o danym peselu
    public static boolean insertWizyta(Calendar dataWizyty, Integer wizyta, String Pesel  ) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO wizyty VALUES (?, ?, ?, ?);");
            prepStmt.setLong(2, BazaDanych.convertDateToLong(dataWizyty));
            prepStmt.setInt(3, wizyta);
            if (Pesel != null)
                prepStmt.setString(4, Pesel);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodawaniu wizyty");
            return false;
        }
        return true;
    }

    //Metoda usuwajaca pacjenta i jego wizyty z bazy danych
    public static Pacjent deletePacjent(String pesel) {
        try {
            String usunSQLPacjent = "DELETE FROM pacjenci WHERE Pesel=" + pesel;
            String usunSQLWizyta = "DELETE FROM wizyty WHERE Pesel=" + pesel;
            stat.executeUpdate(usunSQLPacjent);
            stat.executeUpdate(usunSQLWizyta);

        } catch (Exception e) {
            System.out.println("Blad przy usuwaniu pacjenta");
            e.printStackTrace();
        }
        return null;
    }

    //Metoda usuwajaca wizyte danego pacjenta
    public static Wizyta deleteWizyta(String pesel, int wizytaID) {
        try {
            String usunSQLWizyta = "DELETE FROM wizyty WHERE Pesel=" + pesel + " AND WizytaID=" + wizytaID;
            stat.executeUpdate(usunSQLWizyta);

        } catch (Exception e) {
            System.out.println("Blad przy usuwaniu wizyty");
            e.printStackTrace();
        }
        return null;
    }

    //Metoda pokazujaca dane z listy pacjentow w tabeli
    public static void pokazTabelePacjentow() {
        TableModel dtm;
        try {
            String sql = "SELECT * FROM pacjenci";
            PreparedStatement pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            dtm = DbUtils.resultSetToTableModel(rs);

            PacjentTableModel modelPacjent = PacjentTableModel.createModel(dtm);
            Gui.listaPacjentowTable.setModel(modelPacjent);

        } catch (Exception e) {
            System.out.println("Blad przy pokazywaniu tabeli pacjentow");
            e.printStackTrace();
        }
    }

    //Metoda pokazujaca wizyty pacjenta w tabeli
    public static void pokazWizyty(String pesel) {
        TableModel dtm;
        try {
            String sql = "SELECT * FROM wizyty WHERE Pesel=" + pesel;
            PreparedStatement pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            dtm = DbUtils.resultSetToTableModel(rs);

            WizytaTableModel modelWizyta = WizytaTableModel.createModel(dtm);
            Gui.listaWizytTable.setModel(modelWizyta);


        } catch (Exception e) {
            System.out.println("Blad przy pokazywaniu tabeli wizyt");
            e.printStackTrace();
        }

    }

    //Metoda pokazujaca dane pacjenta z tabeli w panelu pacjent
    public static void getPacjent(String pesel) {
       try {
           String sql = "SELECT * FROM pacjenci WHERE Pesel=" + pesel;
           ResultSet rs = stat.executeQuery(sql);
           if(rs.next()) {
                String imiePobrane = rs.getString("Imie");
                Gui.nameText.setText(imiePobrane);

                String nazwiskoPobrane = rs.getString("Nazwisko");
                Gui.surnameText.setText(nazwiskoPobrane);

                String peselPobrany = rs.getString("Pesel");
                Gui.peselText.setText(peselPobrany);

                String plecPobrana = rs.getString("Plec");
                if (plecPobrana.equals(Pacjent.Sex.Kobieta.toString())) {
                    Gui.femaleRadioButton.setSelected(true);
                    Gui.maleRadioButton.setSelected(false);
                } else {
                    Gui.femaleRadioButton.setSelected(false);
                    Gui.maleRadioButton.setSelected(true);
                }
                int ubezpieczeniePobrane = rs.getInt("Ubezpieczenie");
                Gui.insuranceDropdown.setSelectedIndex(ubezpieczeniePobrane);

                long hospStartPobrane = rs.getLong("HospStart");
                Gui.dateHospStart.setCalendar(convertLongToDate(hospStartPobrane));

                long hospEndPobrane = rs.getLong("HospKoniec");
                if (hospEndPobrane != 0)
                    Gui.dateHospEnd.setCalendar(convertLongToDate(hospEndPobrane));
                else
                    Gui.dateHospEnd.setCalendar(null);
           }
       } catch (Exception e) {
           System.out.println("Blad przy pokazywaniu danych pacjenta");
           e.printStackTrace();
       }
    }

}


