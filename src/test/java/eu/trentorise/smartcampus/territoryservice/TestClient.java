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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.trentorise.smartcampus.territoryservice.model.EventObject;
import eu.trentorise.smartcampus.territoryservice.model.ObjectFilter;
import eu.trentorise.smartcampus.territoryservice.model.POIObject;
import eu.trentorise.smartcampus.territoryservice.model.StoryObject;
import eu.trentorise.smartcampus.territoryservice.model.SyncData;

public class TestClient {

	private TerritoryService service;

	@Before
	public void init() {
		service = new TerritoryService(Constants.SERVER_URL);
	}

	@Test
	public void events() throws SecurityException, TerritoryServiceException {
		ObjectFilter filter = new ObjectFilter();
		// get events
		List<EventObject> events = service.getEvents(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertTrue(events.size() > 0);
		System.err.println("Events: "+ events.size());
		
		
		// limit to 10
		filter.setLimit(10);
		events = service.getEvents(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertEquals(10, events.size());
		System.err.println("Events: "+ events.size());

		// Parties only
		filter.setTypes(Arrays.asList(new String[]{"Parties"}));
		events = service.getEvents(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertEquals(10, events.size());
		System.err.println("Events: "+ events.size());
		
		// contains text party
		filter.setText("party");
		events = service.getEvents(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertTrue(events.size() > 0);
		System.err.println("Events: "+ events.size());
		
		// only future events
		filter.setTypes(null);
		filter.setText(null);
		filter.setFromTime(System.currentTimeMillis());
		events = service.getEvents(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertTrue(events.size() > 0);
		System.err.println("Events: "+ events.size());
		
		// limit to zone
		filter.setCenter(new double[]{46.07455, 11.123048});
		filter.setRadius(0.01d);// ~ 1 km
		events = service.getEvents(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertTrue(events.size() > 0);
		System.err.println("Events: "+ events.size());
		
		EventObject o = service.getEvent(events.get(0).getId(), Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		System.err.println(o.getTitle());
	}
	
	@Test
	public void places() throws SecurityException, TerritoryServiceException {
		ObjectFilter filter = new ObjectFilter();
		// get events
		List<POIObject> pois = service.getPOIs(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(pois);
		Assert.assertTrue(pois.size() > 0);
		System.err.println("pois: "+ pois.size());
		
		// limit to 10
		filter.setLimit(10);
		pois = service.getPOIs(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(pois);
		Assert.assertEquals(10, pois.size());
		System.err.println("pois: "+ pois.size());

		// Museums only
		filter.setTypes(Arrays.asList(new String[]{"Museums"}));
		pois = service.getPOIs(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(pois);
		Assert.assertTrue(pois.size() > 0);
		System.err.println("pois: "+ pois.size());
		
		// contains text castello
		filter.setText("castello");
		pois = service.getPOIs(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(pois);
		Assert.assertTrue(pois.size() > 0);
		System.err.println("pois: "+ pois.size());
		
		// limit to zone
		filter.setCenter(new double[]{46.07455, 11.123048});
		filter.setRadius(0.01d);// ~ 1 km
		pois = service.getPOIs(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(pois);
		Assert.assertTrue(pois.size() > 0);
		System.err.println("pois: "+ pois.size());

		POIObject o = service.getPOI(pois.get(0).getId(), Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		System.err.println(o.getTitle());
	}

	@Test
	public void stories() throws SecurityException, TerritoryServiceException {
		ObjectFilter filter = new ObjectFilter();
		// get events
		List<StoryObject> stories = service.getStories(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(stories);
		Assert.assertTrue(stories.size() > 0);
		System.err.println("stories: "+ stories.size());
		
		// limit to 10
		filter.setLimit(10);
		stories = service.getStories(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(stories);
		Assert.assertEquals(10, stories.size());
		System.err.println("stories: "+ stories.size());

		// Museums only
		filter.setTypes(Arrays.asList(new String[]{"Culture"}));
		stories = service.getStories(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(stories);
		Assert.assertTrue(stories.size() > 0);
		System.err.println("stories: "+ stories.size());
		
		// contains text viaggio
		filter.setText("viaggio");
		stories = service.getStories(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(stories);
		Assert.assertTrue(stories.size() > 0);
		System.err.println("stories: "+ stories.size());

		StoryObject o = service.getStory(stories.get(0).getId(), Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		System.err.println(o.getTitle());
	}

	@Test
	public void communityOps() throws TerritoryServiceException {
		ObjectFilter filter = new ObjectFilter();
		filter.setLimit(1);
		// for events
		List<EventObject> events = service.getEvents(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertEquals(1, events.size());
		EventObject o = events.get(0);
		// read new average rating
		int rating = service.rate(o.getId(), 5, Constants.USER_AUTH_TOKEN);
		Assert.assertTrue(rating > 0);
		// check the user rating is as expected
		o = service.getEvent(o.getId(), Constants.USER_AUTH_TOKEN);
		Assert.assertTrue(o.getCommunityData().getRating().get(o.getCommunityData().getRating().keySet().iterator().next()) == 5);
		// add and remove from my events
		o = service.myEvent(o.getId(), true, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		Assert.assertEquals(1,o.getAttending().size());
		o = service.myEvent(o.getId(), false, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		Assert.assertEquals(0,o.getAttending().size());
		
		// for places
		List<POIObject> places = service.getPOIs(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertEquals(1, places.size());
		POIObject poi = places.get(0);
		// read new average rating
		rating = service.rate(poi.getId(), 5, Constants.USER_AUTH_TOKEN);
		Assert.assertTrue(rating > 0);
		// check the user rating is as expected
		poi = service.getPOI(poi.getId(), Constants.USER_AUTH_TOKEN);
		Assert.assertTrue(poi.getCommunityData().getRating().get(poi.getCommunityData().getRating().keySet().iterator().next()) == 5);
		
		// for stories
		filter.setLimit(100);
		List<StoryObject> stories = service.getStories(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertEquals(1, places.size());
		
		StoryObject story = stories.get(0);
		// read new average rating
		rating = service.rate(story.getId(), 5, Constants.USER_AUTH_TOKEN);
		Assert.assertTrue(rating > 0);
		// check the user rating is as expected
		story = service.getStory(story.getId(), Constants.USER_AUTH_TOKEN);
		Assert.assertTrue(story.getCommunityData().getRating().get(story.getCommunityData().getRating().keySet().iterator().next()) == 5);

		story = service.myStory(story.getId(), true, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(story);
		Assert.assertEquals(1,story.getAttending().size());
		story = service.myStory(story.getId(), false, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(story);
		Assert.assertEquals(0,story.getAttending().size());
	}

	@Test
	public void userEvents() throws TerritoryServiceException {
		ObjectFilter filter = new ObjectFilter();
		filter.setLimit(1);
		// for events
		List<EventObject> events = service.getEvents(filter ,Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(events);
		Assert.assertEquals(1, events.size());
		EventObject o = events.get(0);
		o.setId(null);
		o.setDomainId(null);
		o.setDomainType(null);
		o = service.createEvent(o, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		System.err.println("created "+o.getId());
		
		o.setTitle("Title changed");
		o = service.updateEvent(o.getId(), o, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		Assert.assertEquals("Title changed", o.getTitle());
		
		service.deleteEvent(o.getId(), Constants.USER_AUTH_TOKEN);
		boolean deleted = false;
		try {
			o = service.getEvent(o.getId(), Constants.USER_AUTH_TOKEN);
		} catch (Exception e) {
			deleted = true;
		}
		Assert.assertTrue(deleted);
	}
	
	@Test
	public void userPOIs() throws TerritoryServiceException {
		ObjectFilter filter = new ObjectFilter();
		filter.setLimit(1);
		// for stories
		List<POIObject> pois = service.getPOIs(filter, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(pois);
		Assert.assertEquals(1, pois.size());
		POIObject o = pois.get(0);
		o.setId(null);
		o.setDomainId(null);
		o.setDomainType(null);
		o.setTitle(System.currentTimeMillis()+" title");
		o = service.createPOI(o, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		System.err.println("created "+o.getId());
		
		o.setTitle("Title changed");
		o = service.updatePOI(o.getId(), o, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		Assert.assertEquals("Title changed", o.getTitle());
		
		service.deletePOI(o.getId(), Constants.USER_AUTH_TOKEN);
		boolean deleted = false;
		try {
			o = service.getPOI(o.getId(), Constants.USER_AUTH_TOKEN);
		} catch (Exception e) {
			deleted = true;
		}
		Assert.assertTrue(deleted);
	}

	@Test
	public void userStories() throws TerritoryServiceException {
		ObjectFilter filter = new ObjectFilter();
		filter.setLimit(1);
		// for stories
		List<StoryObject> stories = service.getStories(filter, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(stories);
		Assert.assertEquals(1, stories.size());
		StoryObject o = stories.get(0);
		o.setId(null);
		o.setDomainId(null);
		o.setDomainType(null);
		o = service.createStory(o, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		System.err.println("created "+o.getId());
		
		o.setTitle("Title changed");
		o = service.updateStory(o.getId(), o, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(o);
		Assert.assertEquals("Title changed", o.getTitle());
		
		service.deleteStory(o.getId(), Constants.USER_AUTH_TOKEN);
		boolean deleted = false;
		try {
			o = service.getStory(o.getId(), Constants.USER_AUTH_TOKEN);
		} catch (Exception e) {
			deleted = true;
		}
		Assert.assertTrue(deleted);
	}

	@Test
	public void sync() throws TerritoryServiceException {
		SyncData sd = service.synchronize(0L, Collections.<String,Object>singletonMap("type", "Museums"), null, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(sd);
		System.err.println(sd);
	}
}
