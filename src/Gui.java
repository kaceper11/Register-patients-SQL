import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import javax.swing.JOptionPane;


public class Gui{

    static JFrame frame;
    static Container container;
    static JPanel panelDaneBadanie, panelDane, panelBadanie, panelListaWizyta, panelLista, panelWizyta;

    static JTextField nameText,surnameText, peselText;
    static JDateChooser dateBadanie, dateZabieg, dateHospStart, dateHospEnd;
    static JRadioButton femaleRadioButton, maleRadioButton;
    static JTable listaPacjentowTable, listaWizytTable;
    static DefaultTableModel tableModel;
    static JComboBox insuranceDropdown, badanieDropdown, zabiegDropdown;


    private static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Rejestracja wyników badań");
        int x = 1100;
        int y = 560;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(x, y);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(((int)dimension.getWidth()/2)-x/2, ((int)dimension.getHeight()/2)-y/2);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu aplikacjaMenu = new JMenu("Aplikacja");
        menuBar.add(aplikacjaMenu);
        JMenuItem exitMenu = new JMenuItem("Zamknij ALT + F4");
        aplikacjaMenu.add(exitMenu);

        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        addPanelDaneBadanie();
        addPanelDane();
        addPanelBadanie();
        addPanelListaWizyta();
        addPanelListaPacjentow();
        addPanelWizyta();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        createAndShowGUI();
        BazaDanych.Baza();
        BazaDanych.pokazTabelePacjentow();
        BazaDanych.pokazWizyty("1");

    }

    //Metoda dodajaca panel w ktorym znajduja sie panele z danymi pacjenta oraz z badaniem
    public static void addPanelDaneBadanie() {
        panelDaneBadanie = new JPanel();
        panelDaneBadanie.setBackground(new Color(181, 223, 224));
        panelDaneBadanie.setLayout(new BorderLayout(10,10));
        container.add(panelDaneBadanie, BorderLayout.LINE_START);
    }

    //Metoda dodajaca panel z danymi pacjenta
    public static void addPanelDane() {
        panelDane = new JPanel();
        panelDane.setPreferredSize(new Dimension(460,300));
        panelDane.setBorder(BorderFactory.createTitledBorder("Dane pacjenta"));
        panelDane.setBackground(new Color(181, 223, 224));
        panelDane.setLayout(new GridLayout(8,2, 5, 10));
        panelDane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        panelDane.add(new JLabel("Imię:"));
        nameText = new JTextField();
        panelDane.add(nameText);

        panelDane.add(new JLabel("Nazwisko:"));
        surnameText = new JTextField();
        panelDane.add(surnameText);

        panelDane.add(new JLabel("PESEL:"));
        peselText = new JTextField();
        panelDane.add(peselText);

        panelDane.add(new JLabel("Płeć"));
        Container sexPanel = Box.createHorizontalBox();
        femaleRadioButton = new JRadioButton("Kobieta");
        sexPanel.add(femaleRadioButton);
        femaleRadioButton.setSelected(true);
        maleRadioButton = new JRadioButton("Mężczyzna");
        sexPanel.add(maleRadioButton);

        femaleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(femaleRadioButton.isSelected()) {
                    maleRadioButton.setSelected(false);
                }
                else {
                    maleRadioButton.setSelected(true);
                }
            }
        });

        maleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maleRadioButton.isSelected()) {
                    femaleRadioButton.setSelected(false);
                }
                else {
                    femaleRadioButton.setSelected(true);
                }
            }
        });
        panelDane.add(sexPanel);

        String[] dropdownStringsInsurance = {"NFZ", "Prywatne", "Brak"};
        panelDane.add(new JLabel("Ubezpieczenie:"));
        insuranceDropdown = new JComboBox(dropdownStringsInsurance);
        panelDane.add(insuranceDropdown);

        panelDane.add(new JLabel("Początek hospitalizacji:"));
        dateHospStart = new com.toedter.calendar.JDateChooser();
        panelDane.add(dateHospStart);

        panelDane.add(new JLabel("Koniec hospitalizacji:"));
        dateHospEnd = new com.toedter.calendar.JDateChooser();
        panelDane.add(dateHospEnd);

        Container buttonPanelPacjent = Box.createHorizontalBox();
        JButton zapiszButtonPacjent = new JButton("Zapisz");
        buttonPanelPacjent.add(zapiszButtonPacjent);
        JButton anulujButtonPacjent = new JButton("Anuluj");
        buttonPanelPacjent.add(anulujButtonPacjent);
        panelDane.add(buttonPanelPacjent);

        zapiszButtonPacjent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajPacjenta();
                BazaDanych.pokazTabelePacjentow();
            }
        });

        anulujButtonPacjent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anulujPacjenta();
            }
        });
        panelDaneBadanie.add(panelDane, BorderLayout.NORTH);
    }

    //Metoda dodajaca panel z badaniem
    public static void addPanelBadanie() {

        panelBadanie = new JPanel();
        panelBadanie.setBorder(BorderFactory.createTitledBorder("Badania i zabiegi"));
        panelBadanie.setPreferredSize(new Dimension(460,200));
        panelBadanie.setBackground(new Color(181, 223, 224));
        panelBadanie.setLayout(new GridLayout(5,2, 5, 10));
        panelBadanie.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        String[] dropdownStringsZabieg = {"zabieg1", "zabieg2", "zabieg3"};
        panelBadanie.add(new JLabel("Rodzaj zabiegu:"));
        zabiegDropdown = new JComboBox(dropdownStringsZabieg);
        panelBadanie.add(zabiegDropdown);

        panelBadanie.add(new JLabel("Data zabiegu:"));
        dateZabieg = new com.toedter.calendar.JDateChooser();
        panelBadanie.add(dateZabieg);

        String[] dropdownStringsBadanie = {"badanie1", "badanie2", "badanie3"};
        panelBadanie.add(new JLabel("Rodzaj badania:"));
        badanieDropdown = new JComboBox(dropdownStringsBadanie);
        panelBadanie.add(badanieDropdown);

        panelBadanie.add(new JLabel("Data badania:"));
        dateBadanie = new com.toedter.calendar.JDateChooser();
        panelBadanie.add(dateBadanie);

        Container buttonPanelBadanie = Box.createHorizontalBox();
        JButton dodajZabiegButton = new JButton("Dodaj zabieg");
        buttonPanelBadanie.add(dodajZabiegButton);
        JButton dodajBadanieButton = new JButton("Dodaj badanie");
        buttonPanelBadanie.add(dodajBadanieButton);
        panelBadanie.add(buttonPanelBadanie);
        Container buttonPanelBadanie2 = Box.createHorizontalBox();
        JButton anulujBadanieButton = new JButton("Anuluj");
        buttonPanelBadanie2.add(anulujBadanieButton);
        panelBadanie.add(buttonPanelBadanie2);

        dodajZabiegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajZabieg();
            }

        });

        dodajBadanieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajBadanie();
            }
        });
        panelDaneBadanie.add(panelBadanie, BorderLayout.SOUTH);

        anulujBadanieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anulujBadanie();
            }
        });
    }

    //Metoda dodajaca panel z lista pacjentow i badaniami pacjenta
    public static  void addPanelListaWizyta() {
        panelListaWizyta = new JPanel();
        panelListaWizyta.setBackground(new Color(181, 223, 224));
        panelListaWizyta.setLayout(new BorderLayout(10,10));
        container.add(panelListaWizyta, BorderLayout.LINE_END);
    }


    //Metoda dodajaca panel z lista pacjentow
    public static void addPanelListaPacjentow() {
        panelLista = new JPanel();
        panelLista.setPreferredSize(new Dimension(630,300));
        panelLista.setBackground(new Color(181, 223, 224));
        panelLista.setLayout(new BorderLayout(10,10));
        panelLista.setBorder(BorderFactory.createTitledBorder("Lista pacjentów"));
        panelListaWizyta.add(panelLista, BorderLayout.NORTH);

        listaPacjentowTable = new JTable(tableModel) {

            private static final long serialVersionUID = 1L;

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return String.class;
                    default:
                        return String.class;
                }
            }

        };

        listaPacjentowTable.getTableHeader().setReorderingAllowed(false);

       //Koloruje co drugi wiersz w tabeli z lista pacjentow
        listaPacjentowTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? (new Color(181, 223, 224)): Color.WHITE);
                return this;
            }
        });

        //Po kliknieciu na wiersz w tabeli z lista pacjentow wyswietlaja sie dane pacjenta w panelu Pacjent oraz jego wizyty
        listaPacjentowTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                BazaDanych.getPacjent(wybierzPeselPacjenta());
                BazaDanych.pokazWizyty(wybierzPeselPacjenta());

            }
        });

        JScrollPane scrollPanePacjent = new JScrollPane(listaPacjentowTable);
        listaPacjentowTable.setFillsViewportHeight(true);
        panelLista.add(scrollPanePacjent, BorderLayout.NORTH);
        scrollPanePacjent.setPreferredSize(new Dimension(630, 250));
        Container buttonPanelLista = Box.createHorizontalBox();
        JButton usunPacjentaButton = new JButton("Usuń pacjenta");
        buttonPanelLista.add(usunPacjentaButton);
        panelLista.add(buttonPanelLista, BorderLayout.SOUTH);

        //Listener odpowiadajacy za usuniecie pacjenta z tabeli
        usunPacjentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BazaDanych.deletePacjent(wybierzPeselPacjenta());
                anulujPacjenta();
                anulujBadanie();
                BazaDanych.pokazTabelePacjentow();
                BazaDanych.pokazWizyty("1");
            }
        });
    }

    //Metoda wybierajaca pesel z tabeli pacjentow po klikneciu na dany wiersz
    public static String wybierzPeselPacjenta() {
        try {
            int row = listaPacjentowTable.getSelectedRow();
            String wybranyPesel = listaPacjentowTable.getValueAt(row, 2).toString();
            return wybranyPesel;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

    }

    //Metoda wybierajaca id wizyty z tabeli wizyty po kliknieciu na dany wiersz
    public static int wybierzWizyte() {
        try {
            int row = listaWizytTable.getSelectedRow();
            String wybranaWizyta = listaWizytTable.getValueAt(row, 0).toString();
            int wybraneID = Integer.parseInt(wybranaWizyta);
            return wybraneID;
        }  catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return 1;
        }
    }

    //Metoda wybierajaca pesel w tabeli wizyty po kliknieciu na dany wiersz
    public static String wybierzPeselWizyty() {
        try {
            int row = listaWizytTable.getSelectedRow();
            String wybranyPeselWizyty = listaWizytTable.getValueAt(row, 3).toString();
            return wybranyPeselWizyty;
        }  catch (ArrayIndexOutOfBoundsException e) {
           return null;
        }
    }

    //Metoda dodajaca panel z badaniami i zabiegami danego pacjenta
    public static void addPanelWizyta() {

        panelWizyta = new JPanel();
        panelWizyta.setPreferredSize(new Dimension(630,200));
        panelWizyta.setBackground(new Color(181, 223, 224));
        panelWizyta.setLayout(new BorderLayout(10, 10));
        panelWizyta.setBorder(BorderFactory.createTitledBorder("Badania i zabiegi pacjenta"));

        listaWizytTable = new JTable(tableModel) {

            private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return int.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        listaWizytTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPaneWizyta = new JScrollPane(listaWizytTable);
        scrollPaneWizyta.setPreferredSize(new Dimension(630, 150));
        listaWizytTable.setFillsViewportHeight(true);

        panelWizyta.add(scrollPaneWizyta, BorderLayout.NORTH);

        panelListaWizyta.add(panelWizyta, BorderLayout.SOUTH);
        Container buttonPanelWizyty= Box.createHorizontalBox();

        JButton usunWizyteButton = new JButton("Usuń wizytę");
        buttonPanelWizyty.add(usunWizyteButton);
        panelWizyta.add(buttonPanelWizyty, BorderLayout.SOUTH);

        //Listener odpowiadajacy za usuniecie wizyty pacjenta
        usunWizyteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BazaDanych.deleteWizyta(wybierzPeselWizyty(), wybierzWizyte());
                BazaDanych.pokazWizyty(wybierzPeselPacjenta());
            }
        });
    }



    //Metoda odpowiadająca za czyszczenie formularza "Dane pacjenta"
    public static void anulujPacjenta() {
        nameText.setText(null);
        surnameText.setText(null);
        peselText.setText(null);
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(true);
        insuranceDropdown.setSelectedIndex(0);
        dateHospStart.setCalendar(null);
        dateHospEnd.setCalendar(null);
    }

    //Metoda odpowiadająca za czyszczenie formularza "Badanie i zabiegi"
    public static void anulujBadanie() {
        dateBadanie.setCalendar(null);
        badanieDropdown.setSelectedIndex(0);
        dateZabieg.setCalendar(null);
        zabiegDropdown.setSelectedIndex(0);
    }

    //Metoda sprawdzająca czy dane pacjenta zostały podane
    public static boolean pacjentIsOk() {
        if (nameText.getText().length() == 0 || surnameText.getText().length() == 0 || !peselIsOk() || dateHospStart.getCalendar().toString().length() == 0) {

            JOptionPane.showMessageDialog(null, "Nie podałeś danych pacjenta!", "Uwaga", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }

    //Metoda sprawdzajaca czy w zabiegu zostala podana data
    public static boolean zabiegIsOk() {
        Calendar date = dateZabieg.getCalendar();
        if (date != null) {
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Nie podałeś daty wykonania zabiegu!", "Uwaga", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Metoda sprawdzająca czy w badaniu została podana data
    public static boolean badanieIsOk() {
        Calendar date= dateBadanie.getCalendar();
        if (date != null) {
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Nie podałeś daty wykonania badania!", "Uwaga", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Metoda sprawdzająca czy pole PESEL zostało dobrze wypełnione
    public static boolean peselIsOk() {
        String peselek = peselText.getText();

        if(peselek.length() !=11) {

            JOptionPane.showMessageDialog(null,"PESEL ma nieprawidłową długość lub została podana litera!","Uwaga", JOptionPane.ERROR_MESSAGE);

            peselText.setText(null);
            return false;
        }
        else {
            byte PESEL[] = new byte[11];
            try {
                for (int i = 0; i < 11; i++){
                    PESEL[i] = Byte.parseByte(peselek.substring(i, i+1));
                }
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"Pole PESEL musi być liczbą!","Uwaga", JOptionPane.ERROR_MESSAGE);
                peselText.setText(null);
                return false;
            }
        }
        return true;
    }

    //Metoda dodająca pacjenta do tabeli
    public static void dodajPacjenta() {
        if (dateHospEnd.getCalendar() != null) {

            if(pacjentIsOk() == true && (dateHospStart.getCalendar().compareTo(dateHospEnd.getCalendar()) < 0 || dateHospStart.getCalendar().compareTo(dateHospEnd.getCalendar()) == 0 )) {

                try {
                    BazaDanych.insertPacjent(nameText.getText(), surnameText.getText(), peselText.getText(),
                            femaleRadioButton.isSelected() ? Pacjent.Sex.Kobieta.toString() : Pacjent.Sex.Mężczyzna.toString(),
                            insuranceDropdown.getSelectedIndex(), dateHospStart.getCalendar(), dateHospEnd.getCalendar());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Pacjent nie został dodany, sprawdz czy pola sa poprawnie wypelnione ","Uwaga", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if(pacjentIsOk() == true) {

                try {
                    BazaDanych.insertPacjent(nameText.getText(), surnameText.getText(), peselText.getText(),
                            femaleRadioButton.isSelected() ? Pacjent.Sex.Kobieta.toString() : Pacjent.Sex.Mężczyzna.toString(),
                            insuranceDropdown.getSelectedIndex(), dateHospStart.getCalendar(), dateHospEnd.getCalendar());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Pacjent nie został dodany","Uwaga", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    //Metoda dodajaca zabieg do pacjenta z tabeli
    public static boolean dodajZabieg() {
        if(zabiegIsOk() && wybierzPeselPacjenta() != null) {
            try {
                BazaDanych.insertWizyta(dateZabieg.getCalendar(), zabiegDropdown.getSelectedIndex()+3, wybierzPeselPacjenta());
                BazaDanych.pokazWizyty(wybierzPeselPacjenta());
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"Pola w panelu Badanie i Zabiegi zostały źle podane","Uwaga", JOptionPane.ERROR_MESSAGE);
            }
            return true;
        }
        else
            JOptionPane.showMessageDialog(null,"Zabieg nie został dodany, pamietaj by najpierw wybrac pacjenta!","Uwaga", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    //Metoda dodająca badanie do pacjenta z tabeli
    public static boolean dodajBadanie() {

        if(badanieIsOk() && wybierzPeselPacjenta() != null) {

            try {
                BazaDanych.insertWizyta(dateBadanie.getCalendar(), badanieDropdown.getSelectedIndex(), wybierzPeselPacjenta());
                BazaDanych.pokazWizyty(wybierzPeselPacjenta());
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"Pola w panelu Badanie i Zabiegi zostały źle podane","Uwaga", JOptionPane.ERROR_MESSAGE);
            }
            return true;
        }
        else
            JOptionPane.showMessageDialog(null,"Badanie nie zostalo dodane, pamietaj by najpierw wybrac pacjenta!","Uwaga", JOptionPane.ERROR_MESSAGE);
        return false;
    }

}










