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
package eu.trentorise.smartcampus.territoryservice;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import eu.trentorise.smartcampus.network.JsonUtils;
import eu.trentorise.smartcampus.network.RemoteConnector;
import eu.trentorise.smartcampus.territoryservice.model.EventObject;
import eu.trentorise.smartcampus.territoryservice.model.ObjectFilter;
import eu.trentorise.smartcampus.territoryservice.model.POIObject;
import eu.trentorise.smartcampus.territoryservice.model.StoryObject;
import eu.trentorise.smartcampus.territoryservice.model.SyncData;


/**
 * Territory information API. Provides a possibility to access local venues (events, places,
 * stories) as well as create new one or personalize (rate, attend) them.
 * 
 * @author raman
 * 
 */
public class TerritoryService {

	private static final String EVENTS = "events";
	private static final String EVENTS_P = "events/%s";
	private static final String POIS = "pois";
	private static final String POIS_P = "pois/%s";
	private static final String STORIES = "stories";
	private static final String STORIES_P = "stories/%s";
	private static final String SYNC = "sync";
	private static final String RATE = "objects/%s/rate";
	private static final String ATTEND = "objects/%s/attend";
	private static final String NOT_ATTEND = "objects/%s/notAttend";
	private String serviceUrl;

	/**
	 * 
	 * @param serviceUrl service address
	 */
	public TerritoryService(String serviceUrl) {
		this.serviceUrl = serviceUrl;
		if (!serviceUrl.endsWith("/")) {
			this.serviceUrl += '/';
		}
	}

	/**
	 * Return a list of events matching the specified filter
	 * @param filter search filter (can be null)
	 * @param token user or client access token
	 * @return list of events matching the specified criteria
	 * @throws TerritoryServiceException
	 */
	public List<EventObject> getEvents(ObjectFilter filter, String token) throws TerritoryServiceException {
		try {
			Map<String,Object> params = null;
			if (filter == null) params = Collections.<String,Object>emptyMap();
			else params = Collections.<String,Object>singletonMap("filter", JsonUtils.toJSON(filter));
			String json = RemoteConnector.getJSON(serviceUrl, EVENTS, token, params);
			return JsonUtils.toObjectList(json, EventObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	/**
	 * Get a specific {@link EventObject} instance
	 * @param id of the object
	 * @param token user or client access token
	 * @return
	 * @throws TerritoryServiceException
	 */
	public EventObject getEvent(String id, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			id = URLEncoder.encode(id, "utf8");
			String json = RemoteConnector.getJSON(serviceUrl, String.format(EVENTS_P, id), token);
			return JsonUtils.toObject(json, EventObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	/**
	 * Return a list of {@link POIObject} instances matching the specified filter
	 * @param filter search filter (can be null)
	 * @param token user or client access token
	 * @return List of {@link POIObject} matching the specified criteria
	 * @throws TerritoryServiceException
	 */
	public List<POIObject> getPOIs(ObjectFilter filter, String token) throws TerritoryServiceException {
		try {
			Map<String,Object> params = null;
			if (filter == null) params = Collections.<String,Object>emptyMap();
			else params = Collections.<String,Object>singletonMap("filter", JsonUtils.toJSON(filter));
			String json = RemoteConnector.getJSON(serviceUrl, POIS, token, params);
			return JsonUtils.toObjectList(json, POIObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	/**
	 * Get a specific {@link POIObject} instance
	 * @param id of the object
	 * @param token user or client access token
	 * @return
	 * @throws TerritoryServiceException
	 */
	public POIObject getPOI(String id, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			id = URLEncoder.encode(id, "utf8");
			String json = RemoteConnector.getJSON(serviceUrl, String.format(POIS_P, id), token);
			return JsonUtils.toObject(json, POIObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}

	/**
	 * Return a list of {@link StoryObject} matching the specified criteria
	 * @param filter search filter (can be null)
	 * @param token user or client access token
	 * @return
	 * @throws TerritoryServiceException
	 */
	public List<StoryObject> getStories(ObjectFilter filter, String token) throws TerritoryServiceException {
		try {
			Map<String,Object> params = null;
			if (filter == null) params = Collections.<String,Object>emptyMap();
			else params = Collections.<String,Object>singletonMap("filter", JsonUtils.toJSON(filter));
			String json = RemoteConnector.getJSON(serviceUrl, STORIES, token, params);
			return JsonUtils.toObjectList(json, StoryObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	/**
	 * Get a specific {@link StoryObject} instance
	 * @param id of the object
	 * @param token user or client access token
	 * @return
	 * @throws TerritoryServiceException
	 */
	public StoryObject getStory(String id, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			id = URLEncoder.encode(id, "utf8");
			String json = RemoteConnector.getJSON(serviceUrl, String.format(STORIES_P, id), token);
			return JsonUtils.toObject(json, StoryObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}

	/**
	 * Assign the rating (one per user) to the specific object (event, place, story)
	 * @param id
	 * @param rating
	 * @param token user access token
	 * @return new value for the average rating
	 * @throws TerritoryServiceException
	 */
	public Integer rate(String id, int rating, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			id = URLEncoder.encode(id, "utf8");
			Map<String,Object> params = Collections.<String,Object>singletonMap("rating",rating);
			String json = RemoteConnector.putJSON(serviceUrl, String.format(RATE, id), null, token, params);
			return Integer.parseInt(json);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	
	/**
	 * Add or remove an event from personal ('my') events
	 * @param id of the object to add or remove
	 * @param add true if the object should be added, false to remove
	 * @param token user access token
	 * @return the {@link EventObject} instance added/removed
	 * @throws TerritoryServiceException
	 */
	public EventObject myEvent(String id, boolean add, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			id = URLEncoder.encode(id, "utf8");
			String json = RemoteConnector.putJSON(serviceUrl, String.format(add ? ATTEND : NOT_ATTEND, id), token);
			return JsonUtils.toObject(json,EventObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}

	/**
	 * Add or remove an event from personal ('my') stories
	 * @param id of the object to add or remove
	 * @param add true if the object should be added, false to remove
	 * @param token user access token
	 * @return {@link StoryObject} instance added/removed
	 * @throws TerritoryServiceException
	 */
	public StoryObject myStory(String id, boolean add, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			id = URLEncoder.encode(id, "utf8");
			String json = RemoteConnector.putJSON(serviceUrl, String.format(add ? ATTEND : NOT_ATTEND, id), token);
			return JsonUtils.toObject(json, StoryObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}

	/**
	 * Obtain the list of event/place/story database changes since the modification corresponding to the
	 * specified version or all the objects if no version specified. 
	 * @param version
	 * @param include filter for objects to be included
	 * @param exclude filter for objects to be excluded (mutually exclusive to 'include' set)
	 * @param token user or client access token
	 * @return {@link SyncData} structure containing the objects deleted/modified (or created) since
	 * the specified update
	 * @throws TerritoryServiceException
	 */
	public SyncData synchronize(Long version, Map<String,Object> include, Map<String,Object> exclude, String token) throws TerritoryServiceException {
		try {
			SyncData data = new SyncData();
			data.setExclude(exclude);
			data.setVersion(version);
			data.setInclude(include);
			String json = RemoteConnector.postJSON(serviceUrl, SYNC, JsonUtils.toJSON(data),token, Collections.<String,Object>singletonMap("since",version));
			return JsonUtils.toObject(json, SyncData.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	
	/**
	 * Create a new {@link EventObject}. The object will be visible to all
	 * the users, but can be modified only by the creator
	 * @param in {@link EventObject} to be created
	 * @param token user access token
	 * @return
	 * @throws TerritoryServiceException
	 */
	public EventObject createEvent(EventObject in, String token) throws TerritoryServiceException {
		if (in == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			String json = RemoteConnector.postJSON(serviceUrl, EVENTS, JsonUtils.toJSON(in).toString(), token);
			return JsonUtils.toObject(json, EventObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	
	/**
	 * Modify the specific object. Only the objects created by the current user can be modified.
	 * @param id of the object to be modified
	 * @param in new object data
	 * @param token user access token
	 * @return modified object
	 * @throws TerritoryServiceException
	 */
	public EventObject updateEvent(String id, EventObject in, String token) throws TerritoryServiceException {
		if (id == null || in == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			String json = RemoteConnector.putJSON(serviceUrl, String.format(EVENTS_P, id), JsonUtils.toJSON(in).toString(), token);
			return JsonUtils.toObject(json, EventObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	
	/**
	 * Delete object with the specified ID. Only the objects created by the current user can be deleted.
	 * @param id
	 * @param token user access token
	 * @throws TerritoryServiceException
	 */
	public void deleteEvent(String id, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			RemoteConnector.deleteJSON(serviceUrl, String.format(EVENTS_P, id), token);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}

	/**
	 * Create a new {@link {POIObject}. The object will be visible to all
	 * the users, but can be modified only by the creator
	 * @param in {@link POIObject} to be created
	 * @param token user access token
	 * @return
	 * @throws TerritoryServiceException
	 */
	public POIObject createPOI(POIObject in, String token) throws TerritoryServiceException {
		if (in == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			String json = RemoteConnector.postJSON(serviceUrl, POIS, JsonUtils.toJSON(in).toString(), token);
			return JsonUtils.toObject(json, POIObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	
	/**
	 * Modify the specific object. Only the objects created by the current user can be modified.
	 * @param id of the object to be modified
	 * @param in new object data
	 * @param token user access token
	 * @return modified object
	 * @throws TerritoryServiceException
	 */
	public POIObject updatePOI(String id, POIObject in, String token) throws TerritoryServiceException {
		if (id == null || in == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			String json = RemoteConnector.putJSON(serviceUrl, String.format(POIS_P, id), JsonUtils.toJSON(in).toString(), token);
			return JsonUtils.toObject(json, POIObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	
	/**
	 * Delete object with the specified ID. Only the objects created by the current user can be deleted.
	 * @param id
	 * @param token user access token
	 * @throws TerritoryServiceException
	 */
	public void deletePOI(String id, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			RemoteConnector.deleteJSON(serviceUrl, String.format(POIS_P, id), token);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}

	/**
	 * Create a new {@link StoryObject}. The object will be visible to all
	 * the users, but can be modified only by the creator
	 * @param in {@link StoryObject} to be created
	 * @param token user access token
	 * @return
	 * @throws TerritoryServiceException
	 */
	public StoryObject createStory(StoryObject in, String token) throws TerritoryServiceException {
		if (in == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			String json = RemoteConnector.postJSON(serviceUrl, STORIES, JsonUtils.toJSON(in).toString(), token);
			return JsonUtils.toObject(json, StoryObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	
	/**
	 * Modify the specific object. Only the objects created by the current user can be modified.
	 * @param id of the object to be modified
	 * @param in new object data
	 * @param token user access token
	 * @return modified object
	 * @throws TerritoryServiceException
	 */
	public StoryObject updateStory(String id, StoryObject in, String token) throws TerritoryServiceException {
		if (id == null || in == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			String json = RemoteConnector.putJSON(serviceUrl, String.format(STORIES_P, id), JsonUtils.toJSON(in).toString(), token);
			return JsonUtils.toObject(json,StoryObject.class);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}
	
	/**
	 * Delete object with the specified ID. Only the objects created by the current user can be deleted.
	 * @param id
	 * @param token user access token
	 * @throws TerritoryServiceException
	 */
	public void deleteStory(String id, String token) throws TerritoryServiceException {
		if (id == null)
			throw new TerritoryServiceException("Incomplete request parameters");
		try {
			RemoteConnector.deleteJSON(serviceUrl, String.format(STORIES_P, id), token);
		}catch (SecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new TerritoryServiceException(e);
		}
	}

}
