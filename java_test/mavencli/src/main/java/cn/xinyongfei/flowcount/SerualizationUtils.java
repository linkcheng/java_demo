package cn.xinyongfei.flowcount;

import java.io.*;

public class SerualizationUtils {
    private static String FILE_NAME = "";

    public static void writeObject(Serializable s) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream((FILE_NAME)));
            oos.writeObject(s);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject() {
        Object obj = null;

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE_NAME));
            obj = input.readObject();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
