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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventObject extends BaseDTObject {
	private static final long serialVersionUID = 388550207183035548L;

	private String poiId;

	private List<String> attending = new ArrayList<String>();
	private Integer attendees = 0;
	
	public EventObject() {
		super();
	}

	public String getPoiId() {
		return poiId;
	}

	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}

	public List<String> getAttending() {
		return attending;
	}

	public void setAttending(List<String> attending) {
		this.attending = attending;
	}

	public Integer getAttendees() {
		return attendees;
	}

	public void setAttendees(Integer attendees) {
		this.attendees = attendees;
	}

	/**
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	public static List<EventObject> toObjectList(String json) throws JSONException {
		JSONArray arr = new JSONArray(json);
		List<EventObject> list = new ArrayList<EventObject>();
		for (int i = 0; i < arr.length(); i++) {
			list.add(toObject(arr.getJSONObject(i)));
		}
		return list;
	}

	/**
	 * @param jo
	 * @return
	 * @throws JSONException 
	 */
	public static EventObject toObject(JSONObject jo) throws JSONException {
		JSONHelper.clean(jo);
		EventObject o = new EventObject();
		BaseDTObject.toObject(o,jo);
		o.setPoiId(jo.optString("poiId", null));
		o.setAttendees(jo.getInt("attendees"));
		if (!jo.isNull("attending")) {
			o.setAttending(JSONHelper.toList(jo.getJSONArray("attending"),String.class));
		}
		return o;
	}

	/**
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	public static EventObject toObject(String json) throws JSONException {
		return toObject(new JSONObject(json));
	}

	/**
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject jo = super.toJSON();
		jo.put("poiId", poiId);
		if (attending != null) {
			jo.put("attending", attending);
		}
		jo.put("attendees", attendees);
		JSONHelper.clean(jo);
		return jo;
	}

}
