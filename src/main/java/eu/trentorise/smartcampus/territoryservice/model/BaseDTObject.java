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
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseDTObject implements Serializable {
	private static final long serialVersionUID = 3589900794339644582L;
	// common fields
	private String id;
	private long updateTime = -1L;
	private long version;
	private String domainType;
	private String domainId;
	private String description = null;
	private String title = null;
	private String source = null; // service 'source' of the object

	// semantic entity
	private String entityId = null;
	
	// only for user-created objects
	private String creatorId = null;
	private String creatorName = null;

	// community data
	private CommunityData communityData = null;
	
	// categorization
	private String type = null;

	// common data
	private double[] location;
	private Long fromTime;
	private Long toTime;
	private String timing;

	private Map<String,Object> customData;
	
	public BaseDTObject() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
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

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	} 

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public CommunityData getCommunityData() {
		return communityData;
	}

	public void setCommunityData(CommunityData communityData) {
		this.communityData = communityData;
	}

	public Map<String, Object> getCustomData() {
		return customData;
	}

	public void setCustomData(Map<String, Object> customData) {
		this.customData = customData;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	/**
	 * @param o
	 * @param jsonObject
	 * @throws JSONException 
	 */
	public static void toObject(BaseDTObject o, JSONObject jo) throws JSONException {
		o.setCommunityData(CommunityData.toObject(jo.getJSONObject("communityData")));
		o.setCreatorId(jo.optString("creatorId", null));
		o.setCreatorName(jo.optString("creatorName", null));
		if (!jo.isNull("customData")) { 
			o.setCustomData(JSONHelper.toMap(jo.getJSONObject("customData")));
		}
		o.setDescription(jo.optString("description", null));
		o.setDomainId(jo.optString("domainId", null));
		o.setDomainType(jo.optString("domainType", null));
		o.setEntityId(jo.optString("entityId", null));
		if (!jo.isNull("fromTime")) {
			o.setFromTime(jo.getLong("fromTime"));
		}
		o.setId(jo.optString("id", null));
		if (!jo.isNull("location")) {
			JSONArray loc = jo.getJSONArray("location");
			if (loc.length() > 0) {
				o.setLocation(new double[]{loc.getDouble(0),loc.getDouble(1)});
			}
		}
		o.setSource(jo.optString("source", null));
		o.setTiming(jo.optString("timing", null));
		o.setTitle(jo.optString("title", null));
		if (!jo.isNull("toTime")) {
			o.setToTime(jo.getLong("toTime"));
		} else {
			o.setToTime(o.getFromTime());
		}
		o.setType(jo.optString("type", null));
		o.setUpdateTime(jo.getLong("updateTime"));
		o.setVersion(jo.getLong("version"));
	}

	protected JSONObject toJSON() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("updateTime", updateTime);
		jo.put("version", version);
		jo.put("domainType", domainType);
		jo.put("domainId", domainId);
		jo.put("description", description);
		jo.put("title", title);
		jo.put("source", source);
		jo.put("entityId", entityId);
		jo.put("creatorId", creatorId);
		jo.put("creatorName", creatorName);
		jo.put("type", type);
		jo.put("fromTime", fromTime);
		jo.put("toTime", toTime);
		jo.put("timing", timing);
		if (customData != null) {
			JSONObject cd = new JSONObject(customData);
			JSONHelper.clean(cd);
			jo.put("customData", cd);
		}
		if (location != null) {
			jo.put("location", location);
		}
		if (communityData != null) {
			jo.put("communityData", communityData.toJSON());
		}
		
		return jo;
	}
}
