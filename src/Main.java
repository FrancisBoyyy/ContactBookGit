import contactBook.Contact;
import contactBook.ContactBook;

import java.util.Scanner;


public class Main {
    //Constantes que definem os comandos
    public static final String ADD_CONTACT    = "AC";
    public static final String REMOVE_CONTACT = "RC";
    public static final String GET_PHONE      = "GP";
    public static final String GET_EMAIL      = "GE";
    public static final String SET_PHONE      = "SP";
    public static final String SET_EMAIL      = "SE";
    public static final String LIST_CONTACTS  = "LC";
    public static final String GET_CONTACT    = "GN";
    public static final String CHECK_PHONE    = "EP";
    public static final String QUIT           = "Q";

    //Constantes que definem as mensagens para o utilizador
    public static final String CONTACT_EXISTS = "contactBook.Contact already exists.";
    public static final String NAME_NOT_EXIST = "contactBook.Contact does not exist.";
    public static final String CONTACT_ADDED = "contactBook.Contact added.";
    public static final String CONTACT_REMOVED = "contactBook.Contact removed.";
    public static final String CONTACT_UPDATED = "contactBook.Contact updated.";
    public static final String BOOK_EMPTY = "contactBook.Contact book empty.";
    public static final String NO_PHONE = "Phone number does not exist.";
    public static final String SHARED_PHONE_NUMBERS = "There are contacts that share phone numbers.";
    public static final String NO_SHARED_PHONE_NUMBERS = "All contacts have different phone numbers.";
    public static final String QUIT_MSG = "Goodbye!";
    public static final String COMMAND_ERROR = "Unknown command.";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ContactBook cBook = new ContactBook();
        String comm = getCommand(in);

        while (!comm.equals(QUIT)){
            switch (comm) {
                case ADD_CONTACT:
                    addContact(in,cBook);
                    break;
                case REMOVE_CONTACT:
                    deleteContact(in,cBook);
                    break;
                case GET_PHONE:
                    getPhone(in,cBook);
                    break;
                case GET_EMAIL:
                    getEmail(in,cBook);
                    break;
                case SET_PHONE:
                    setPhone(in,cBook);
                    break;
                case SET_EMAIL:
                    setEmail(in,cBook);
                    break;
                case LIST_CONTACTS:
                    listAllContacts(cBook);
                    break;
                case GET_CONTACT:
                    FindContactWithNumber(in, cBook);
                    break;
                case CHECK_PHONE:
                    CheckRepeatedPhones(cBook);
                    break;
                default:
                    System.out.println(COMMAND_ERROR);
            }
            System.out.println();
            comm = getCommand(in);
        }
        System.out.println(QUIT_MSG);
        System.out.println();
        in.close();
    }

    private static String getCommand(Scanner in) {
        String input;

        input = in.nextLine().toUpperCase();
        return input;
    }

    private static void addContact(Scanner in, ContactBook cBook) {
        String name, email;
        int phone;

        name = in.nextLine();
        phone = in.nextInt(); in.nextLine();
        email = in.nextLine();
        if (!cBook.hasContact(name)) {
            cBook.addContact(name, phone, email);
            System.out.println(CONTACT_ADDED);
        }
        else System.out.println(CONTACT_EXISTS);
    }

    private static void deleteContact(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.deleteContact(name);
            System.out.println(CONTACT_REMOVED);
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void getPhone(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            System.out.println(cBook.getPhone(name));
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void getEmail(Scanner in, ContactBook cBook) {
        String name;
        name = in.nextLine();
        if (cBook.hasContact(name)) {
            System.out.println(cBook.getEmail(name));
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void setPhone(Scanner in, ContactBook cBook) {
        String name;
        int phone;
        name = in.nextLine();
        phone = in.nextInt(); in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.setPhone(name,phone);
            System.out.println(CONTACT_UPDATED);
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void setEmail(Scanner in, ContactBook cBook) {
        String name;
        String email;
        name = in.nextLine();
        email = in.nextLine();
        if (cBook.hasContact(name)) {
            cBook.setEmail(name,email);
            System.out.println(CONTACT_UPDATED);
        }
        else System.out.println(NAME_NOT_EXIST);
    }

    private static void listAllContacts(ContactBook cBook) {
        if (cBook.getNumberOfContacts() != 0) {
            cBook.initializeIterator();
            while( cBook.hasNext() ) {
                Contact c = cBook.next();
                System.out.println(c.getName() + "; " + c.getEmail() + "; " + c.getPhone());
            }
        }
        else System.out.println(BOOK_EMPTY);
    }


    /**
     * Finds contact given a number, outputs the result to console
     * @param in Scanner, used to read the phone to check
     * @param cBook the system where to check
     */
    private static void FindContactWithNumber(Scanner in, ContactBook cBook) {
        int phone;

        phone = in.nextInt(); in.nextLine();
        Contact contact = cBook.getContactByNumber(phone);
        if (contact != null)
            System.out.println(contact.getName());
        else
            System.out.println(NO_PHONE);
    }

    /**
     * Checks for duplicated phones, and outputs the result in the console.
     * @param cBook the system to check inside
     */
    private static void CheckRepeatedPhones(ContactBook cBook) {
        boolean repeated = false;

        cBook.initializeIterator();
        while( cBook.hasNext() && !repeated ) {
            Contact c = cBook.next();
            ContactBook cBookTmp = new ContactBook(cBook);
            while( cBookTmp.hasNext() && !repeated ) {
                Contact d = cBookTmp.next();
                if (c.sameNumber(d))
                    repeated =true;
            }
        }
        if (repeated)
            System.out.println(SHARED_PHONE_NUMBERS);
        else
            System.out.println(NO_SHARED_PHONE_NUMBERS);
    }
}
