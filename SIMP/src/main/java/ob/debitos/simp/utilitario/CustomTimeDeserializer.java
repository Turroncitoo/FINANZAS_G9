package ob.debitos.simp.utilitario;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomTimeDeserializer extends JsonDeserializer<LocalTime>
{

    @Override
    public LocalTime deserialize(JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException
    {
        String cadena = jsonparser.getText();
        return DatesUtils.obtenerLocalTimeHHMMSS(cadena);
    }
}