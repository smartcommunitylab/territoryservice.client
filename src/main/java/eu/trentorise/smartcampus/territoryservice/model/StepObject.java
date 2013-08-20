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

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


public class StepObject implements Serializable {

	private static final long serialVersionUID = 8517257945277793403L;

	private String poiId;
	private String note;

	
	public StepObject(String poiId, String note) {
		super();
		this.poiId = poiId;
		this.note = note;
	}
	public StepObject() {
		super();
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPoiId() {
		return poiId;
	}
	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}
	/**
	 * @param jsonObject
	 * @return
	 * @throws JSONException 
	 */
	public static StepObject toObject(JSONObject jo) throws JSONException {
		JSONHelper.clean(jo);
		StepObject o = new StepObject();
		o.setNote(jo.optString("note", null));
		o.setPoiId(jo.optString("poiId", null));
		return o;
	}
	/**
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("poiId", poiId);
		jo.put("note", note);
		JSONHelper.clean(jo);
		return jo;
	}
}
