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

}
