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

public class POIObject extends BaseDTObject {
	private static final long serialVersionUID = 3377022799304541031L;
	
	private POIData poi;

	public POIObject() {
		super();
	}

	public POIData getPoi() {
		return poi;
	}

	public void setPoi(POIData poi) {
		this.poi = poi;
	}

	@Override
	public double[] getLocation() {
		if (super.getLocation() != null) return super.getLocation();
		if (getPoi() != null) return new double[]{getPoi().getLatitude(),getPoi().getLongitude()};
		return null;
	}

	/**
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	public static List<POIObject> toObjectList(String json) throws JSONException {
		JSONArray arr = new JSONArray(json);
		List<POIObject> list = new ArrayList<POIObject>();
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
	public static POIObject toObject(JSONObject jo) throws JSONException {
		JSONHelper.clean(jo);
		POIObject o = new POIObject();
		BaseDTObject.toObject(o,jo);
		if (!jo.isNull("poi")) {
			o.setPoi(POIData.toObject(jo.getJSONObject("poi")));
		}
		return o;
	}

	/**
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	public static POIObject toObject(String json) throws JSONException {
		return toObject(new JSONObject(json));
	}
	
	/**
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject jo = super.toJSON();
		if (poi != null) {
			jo.put("poi", poi.toJSON());
		}
		JSONHelper.clean(jo);
		return jo;
	}

}
