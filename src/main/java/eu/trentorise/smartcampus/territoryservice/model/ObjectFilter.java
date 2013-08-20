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

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ObjectFilter {

	private boolean myObjects;
	private double[] center;
	private Double radius;
	private List<String> types;
	
	private Long fromTime;
	private Long toTime;
	
	private Integer limit;
	private Integer skip;
	
	private String text;
	
	private Map<String,Object> criteria = null;
	private SortedMap<String,Integer> sort = null;

	public ObjectFilter() {
		super();
	}

	public double[] getCenter() {
		return center;
	}

	public void setCenter(double[] center) {
		this.center = center;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double  radius) {
		this.radius = radius;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Long getFromTime() {
		return fromTime;
	}

	public void setFromTime(Long fromTime) {
		this.fromTime = fromTime;
	}

	public Long getToTime() {
		return toTime;
	}

	public void setToTime(Long toTime) {
		this.toTime = toTime;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public Map<String, Object> getCriteria() {
		return criteria;
	}

	public void setCriteria(Map<String, Object> criteria) {
		this.criteria = criteria;
	}

	public boolean isMyObjects() {
		return myObjects;
	}

	public void setMyObjects(boolean myObjects) {
		this.myObjects = myObjects;
	}

	/**
	 * @return the sort
	 */
	public SortedMap<String, Integer> getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(SortedMap<String, Integer> sort) {
		this.sort = sort;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return
	 * @throws JSONException 
	 */
	public Object toJSON() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("myObjects", myObjects);
		if (center != null) {
			jo.put("center", center);
		}
		if (types != null) {
			jo.put("types", new JSONArray(types));
		}
		jo.put("radius", radius);
		jo.put("fromTime", fromTime);
		jo.put("toTime", toTime);
		jo.put("limit", limit);
		jo.put("skip", skip);
		jo.put("text", text);
		if (criteria != null) {
			JSONObject jsonObject = new JSONObject(criteria);
			JSONHelper.clean(jsonObject);
			jo.put("criteria", jsonObject);
		}
		if (sort != null) {
			JSONObject jsonObject = new JSONObject(sort);
			JSONHelper.clean(jsonObject);
			jo.put("sort", jsonObject);
		}
		JSONHelper.clean(jo);
		return jo.toString();
	}
}
