/*******************************************************************************
 * Copyright 2012-2013 Trento RISE
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 ******************************************************************************/

package eu.trentorise.smartcampus.territoryservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author raman
 *
 */
public class JSONHelper {

	public static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }
	
	protected static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }
	@SuppressWarnings("unchecked")
	protected static <T> List<T> toList(JSONArray array, Class<T> cls) throws JSONException {
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < array.length(); i++) {
            list.add((T)array.get(i));
        }
        return list;
    }

	@SuppressWarnings("unchecked")
	protected static Map<String, Object> toMap(JSONObject object) throws JSONException {
		JSONHelper.clean(object);
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

	public static void clean(JSONObject o) {
		if (o == null) return;
		String[] names = JSONObject.getNames(o);
		if (names != null) {
			for (String s : names) {
				if (o.isNull(s)) o.remove(s);
			}
		}
	}
}
