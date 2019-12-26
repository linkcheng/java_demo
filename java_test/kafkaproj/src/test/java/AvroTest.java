import common.common.user_menu.Value;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AvroTest {

    /**
     * All-args constructor.
     * id The new value for id
     * name The new value for name
     * icon_code The new value for icon_code
     * parent_id The new value for parent_id
     * order The new value for order
     * menu_url The new value for menu_url
     * is_deleted The new value for is_deleted
     * created_time The new value for created_time
     * updated_time The new value for updated_time
     */
    @Test
    public void testSerializeValue() {
        Value value = new Value(1L, "设置", "settting", 0L, 1000, "#", 0, "2019-12-24 12:00:00", "2019-12-24 12:00:00");

        DatumWriter<Value> datumWriter = new SpecificDatumWriter<>(Value.class);
        DataFileWriter<Value> fileWriter = new DataFileWriter<>(datumWriter);

        try {
            fileWriter.create(value.getSchema(), new File("values.avro"));
            fileWriter.append(value);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeserializeValue() {
        DatumReader<Value> datumReader = new SpecificDatumReader<>(Value.class);
        try {
            DataFileReader<Value> fileReader = new DataFileReader<>(new File("values.avro"), datumReader);
            while (fileReader.hasNext()) {
                Value value = fileReader.next();
                System.out.println(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
