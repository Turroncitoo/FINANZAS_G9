package ob.debitos.simp.model.websocket;

import java.io.IOException;
import java.io.Reader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * 
 * @author Carlos Mirano
 *
 */
public class MessageDecoder implements Decoder.TextStream<Message>
{

	@Override
	public void init(EndpointConfig endpointConfig)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Message decode(Reader reader) throws DecodeException, IOException
	{
		JsonReader jsonReader = Json.createReader(reader);
		JsonObject jsonObject = jsonReader.readObject();
		Message message = new Message((double) jsonObject.getInt("porcentaje"), jsonObject.getBoolean("terminado"),
				jsonObject.getBoolean("cancelado"));
		return message;
	}

}
