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

public class StoryObject extends BaseDTObject {

	private static final long serialVersionUID = -5123355788738143639L;
	private List<StepObject> steps = new ArrayList<StepObject>();
	private List<String> attending = new ArrayList<String>();
	private Integer attendees = 0;
	
	
	public StoryObject() {
		super();
	}

	public List<StepObject> getSteps() {
		return steps;
	}


	public void setSteps(List<StepObject> steps) {
		this.steps = steps;
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
	public static List<StoryObject> toObjectList(String json) throws JSONException {
		JSONArray arr = new JSONArray(json);
		List<StoryObject> list = new ArrayList<StoryObject>();
		for (int i = 0; i < arr.length(); i++) {
			list.add(toObject(arr.getJSONObject(i)));
		}
		return list;
	}

	/**
	 * @param jsonObject
	 * @return
	 * @throws JSONException 
	 */
	public static StoryObject toObject(JSONObject jo) throws JSONException {
		JSONHelper.clean(jo);
		StoryObject o = new StoryObject();
		BaseDTObject.toObject(o,jo);
		o.setAttendees(jo.getInt("attendees"));
		if (!jo.isNull("attending")) {
			o.setAttending(JSONHelper.toList(jo.getJSONArray("attending"),String.class));
		}
		if (!jo.isNull("steps")) {
			JSONArray steps = jo.getJSONArray("steps");
			o.setSteps(new ArrayList<StepObject>());
			for (int i = 0; i < steps.length(); i++) {
				o.getSteps().add(StepObject.toObject(steps.getJSONObject(i)));
			}
		}
		return o;
	}

	/**
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	public static StoryObject toObject(String json) throws JSONException {
		return toObject(new JSONObject(json));
	}
	
	/**
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject jo = super.toJSON();
		if (steps != null) {
			List<JSONObject> list = new ArrayList<JSONObject>();
			for (StepObject step : steps) {
				list.add(step.toJSON());
			}
			jo.put("steps", list);
		}
		if (attending != null) {
			jo.put("attending", attending);
		}
		jo.put("attendees", attendees);
		JSONHelper.clean(jo);
		return jo;
	}

}
