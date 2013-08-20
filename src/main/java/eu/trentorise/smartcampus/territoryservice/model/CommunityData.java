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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.trentorise.smartcampus.social.model.Concept;

public class CommunityData implements Serializable {
	private static final long serialVersionUID = 5926048335916274968L;

	protected List<Concept> tags;
	protected int averageRating;
	protected Map<String, Integer> rating;
	protected Map<String, String> following = new HashMap<String, String>();
	private int ratingsCount = 0;
	private int followsCount = 0;

	public CommunityData() {
		super();
	}

	public void setFollowing(Map<String, String> following) {
		this.following = following;
		setFollowsCount(following == null? 0 : following.size());
	}

	public void setRating(Map<String, Integer> rating) {
		this.rating = rating;
		setRatingsCount(rating == null? 0 : rating.size());
	}
	
	public static void filterUserData(CommunityData data, String userId) {
		if (data == null) return;
		Map<String, Integer> ratings = data.getRating();
		if (ratings != null && !ratings.isEmpty()) {
			if (ratings.containsKey(userId)) {
				data.setRating(Collections.singletonMap(userId, ratings.get(userId)));
			} else {
				data.setRating(Collections.<String,Integer>emptyMap());
			}
			data.setRatingsCount(ratings.size());
		}
		if (data.getFollowing() != null && ! data.getFollowing().isEmpty()) {
			if (data.getFollowing().containsKey(userId)) {
				data.setFollowing(Collections.singletonMap(userId,data.getFollowing().get(userId)));
			} else {
				data.setFollowing(Collections.<String,String>emptyMap());
			}
			data.setFollowsCount(data.getFollowing().size());
		}
	}

	public static void filterUserData(List<CommunityData> datas, String userId) {
		for (CommunityData data : datas) {
			filterUserData(data, userId);
		}
	}

	public int getRatingsCount() {
		return ratingsCount;
	}

	public void setRatingsCount(int ratingsCount) {
		this.ratingsCount = ratingsCount;
	}

	public int getFollowsCount() {
		return followsCount;
	}

	public void setFollowsCount(int followCount) {
		this.followsCount = followCount;
	}
	
	public Map<String, String> getFollowing() {
		return following;
	}

	public List<Concept> getTags() {
		return tags;
	}

	public void setTags(List<Concept> tags) {
		this.tags = tags;
	}

	public int getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}

	public Map<String, Integer> getRating() {
		return rating;
	}

	/**
	 * @param jsonObject
	 * @return
	 * @throws JSONException 
	 */
	@SuppressWarnings("unchecked")
	public static CommunityData toObject(JSONObject jo) throws JSONException {
		JSONHelper.clean(jo);
		CommunityData o = new CommunityData();
		o.setAverageRating(jo.getInt("averageRating"));
		o.setFollowsCount(jo.getInt("followsCount"));
		o.setRatingsCount(jo.getInt("ratingsCount"));
		if (!jo.isNull("following")) {
			JSONObject following = jo.getJSONObject("following");
			o.setFollowing(new HashMap<String, String>());
			for (Iterator<String> iterator = following.keys(); iterator.hasNext();) {
				String key = iterator.next();
				o.getFollowing().put(key, following.optString(key, null));
			}
		}
		if (!jo.isNull("tags")) {
			o.setTags(Concept.toList(jo.getJSONArray("tags").toString()));
		}
		if (!jo.isNull("rating")) {
			JSONObject ratings = jo.getJSONObject("rating");
			o.setRating(new HashMap<String, Integer>());
			for (Iterator<String> iterator = ratings.keys(); iterator.hasNext();) {
				String key = iterator.next();
				o.getRating().put(key, ratings.getInt(key));
			}
		}
		return o;
	}

	/**
	 * @return
	 * @throws JSONException 
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject jo = new JSONObject();
		if (tags != null) {
			jo.put("tags", new JSONArray(Concept.toJson(tags)));
		}
		jo.put("averageRating", averageRating);
		if (rating != null) {
			jo.put("rating", rating);
		}
		if (following != null) {
			jo.put("following", following);
		}
		jo.put("ratingsCount", ratingsCount);
		jo.put("followsCount", followsCount);
		JSONHelper.clean(jo);
		return jo;
	}
}
