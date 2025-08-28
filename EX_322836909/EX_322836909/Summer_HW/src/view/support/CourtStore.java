package view.support;

import java.io.*;
import control.Court;

public final class CourtStore {
    private static final String FILE = "Court.ser";

    private CourtStore() {}

    public static Court loadOrCreate() {
        Court c = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            Object o = in.readObject();
            if (o instanceof Court) c = (Court)o;
        } catch (Exception ignore) {}
        if (c == null) c = Court.getInstance(); 
        return c;
    }

    public static void save(Court court) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(court);
        }
    }
}
