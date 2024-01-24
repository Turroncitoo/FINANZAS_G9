package ob.debitos.simp.utilitario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil
{

    public static Object convertirAJSON(List<?> elementos) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(elementos);
    }

    public static Map<String, Object> jsonToMap(String jsonInput) throws JSONException
    {
        return jsonToMap(new JSONObject(jsonInput));
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException
    {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL)
        {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException
    {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext())
        {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray)
            {
                value = toList((JSONArray) value);
            }

            else if (value instanceof JSONObject)
            {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException
    {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++)
        {
            Object value = array.get(i);
            if (value instanceof JSONArray)
            {
                value = toList((JSONArray) value);
            }

            else if (value instanceof JSONObject)
            {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static String convertObjectToJSONString(Object object)
    {
        String jsonStr = "";
        ObjectMapper objMapper = new ObjectMapper();
        try
        {
            jsonStr = objMapper.writeValueAsString(object);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return jsonStr;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertJSONStringToMapObject(String jsonString)
    {
        Map<String, Object> response = null;
        try
        {
            response = new ObjectMapper().readValue(jsonString, HashMap.class);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }

}
