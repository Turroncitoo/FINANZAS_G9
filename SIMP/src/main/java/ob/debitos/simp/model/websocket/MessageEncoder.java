package ob.debitos.simp.model.websocket;

import java.io.IOException;
import java.io.Writer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * 
 * @author Carlos Mirano
 *
 */
public class MessageEncoder implements Encoder.TextStream<Message>
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
	public void encode(Message message, Writer writer) throws EncodeException, IOException
	{
		JsonWriter jsonWriter = Json.createWriter(writer);
		JsonObject jsonObject = Json.createObjectBuilder().add("porcentaje", message.getPorcentaje())
				.add("terminado", message.getTerminado()).add("cancelado", message.getCancelado()).build();
		jsonWriter.writeObject(jsonObject);
	}

}
