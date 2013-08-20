/**
 *    Copyright 2012-2013 Trento RISE
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
 */

package eu.trentorise.smartcampus.territoryservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SyncData {

	private long version;
	private Map<String,List<Object>> updated = new HashMap<String, List<Object>>();
	private Map<String,List<String>> deleted = new HashMap<String, List<String>>();
	
	private Map<String, Object> exclude;
	private Map<String, Object> include;
	
	public SyncData() {
		super();
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Map<String, List<Object>> getUpdated() {
		return updated;
	}

	public void setUpdated(Map<String, List<Object>> updated) {
		this.updated = updated;
	}

	public Map<String, List<String>> getDeleted() {
		return deleted;
	}

	public void setDeleted(Map<String, List<String>> deleted) {
		this.deleted = deleted;
	}

	public Map<String, Object> getExclude() {
		return exclude;
	}

	public void setExclude(Map<String, Object> exclude) {
		this.exclude = exclude;
	}

	public Map<String, Object> getInclude() {
		return include;
	}

	public void setInclude(Map<String, Object> include) {
		this.include = include;
	}

	/**
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	@SuppressWarnings("unchecked")
	public static SyncData toObject(String json) throws JSONException {
		JSONObject jo = new JSONObject(json);
		JSONHelper.clean(jo);
		SyncData o = new SyncData();
		o.setVersion(jo.getLong("version"));
		if (!jo.isNull("exclude")) {
			o.setExclude(JSONHelper.toMap(jo.getJSONObject("exclude")));
		}
		if (!jo.isNull("include")) {
			o.setInclude(JSONHelper.toMap(jo.getJSONObject("include")));
		}
		if (!jo.isNull("deleted")) {
			o.setDeleted(new HashMap<String, List<String>>());
			JSONObject del = jo.getJSONObject("deleted");
			for (Iterator<String> iterator = del.keys(); iterator.hasNext();) {
				String key = iterator.next();
				o.getDeleted().put(key, JSONHelper.toList(del.getJSONArray(key), String.class));
			}
		}
		if (!jo.isNull("updated")) {
			o.setUpdated(new HashMap<String, List<Object>>());
			JSONObject upd = jo.getJSONObject("updated");
			for (Iterator<String> iterator = upd.keys(); iterator.hasNext();) {
				String key = iterator.next();
				List<Object> list = new ArrayList<Object>();
				JSONArray arr = upd.getJSONArray(key);
				for (int i = 0; i < arr.length(); i++) {
					list.add(elemToObject(key,arr.getJSONObject(i)));
				}
				o.getUpdated().put(key, list);
			}
		}

		return o;
	}

	/**
	 * @param key 
	 * @param jsonObject
	 * @return
	 * @throws JSONException 
	 */
	private static Object elemToObject(String key, JSONObject jsonObject) throws JSONException {
		if (key.endsWith(EventObject.class.getSimpleName())) return EventObject.toObject(jsonObject);
		if (key.endsWith(POIObject.class.getSimpleName())) return POIObject.toObject(jsonObject);
		if (key.endsWith(StoryObject.class.getSimpleName())) return StoryObject.toObject(jsonObject);
		return null;
	}
	
}
