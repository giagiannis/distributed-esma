/**
 *    Copyright 2013 Giannis Giannakopoulos
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package gr.ntua.cslab.containers.agents;

import gr.ntua.cslab.containers.preferences.PreferenceList;

/**
 * @author Giannis Giannakopoulos
 *
 */
public class Agent {
	
	private int id;
	private PreferenceList preferences;
	private AgentType type;
	private AgentStatus status=AgentStatus.SINGLE;
	private int currentPartnerId=-1;
	private int currentPartnerRank=Integer.MAX_VALUE;
	
	
	/**
	 * 
	 */
	public Agent() {

	}
	
	public Agent(int id, PreferenceList preferences){
		this.setId(id);
		this.setPreferences(preferences);
	}
			
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PreferenceList getPreferences() {
		return preferences;
	}

	public void setPreferences(PreferenceList preferences) {
		this.preferences = preferences;
	}

	public AgentType getType() {
		return type;
	}

	public void setType(AgentType type) {
		this.type = type;
	}

	public AgentStatus getStatus() {
		return status;
	}

	public void setStatus(AgentStatus status) {
		this.status = status;
	}

	public int getCurrentPartnerId() {
		return currentPartnerId;
	}

	public void setCurrentPartnerId(int currentPartnerId) {
		this.currentPartnerId = currentPartnerId;
	}

	public int getCurrentPartnerRank() {
		return currentPartnerRank;
	}

	public void setCurrentPartnerRank(int currentPartnerRank) {
		this.currentPartnerRank = currentPartnerRank;
	}

	public boolean isContent(){
		return status==AgentStatus.CONTENT;
	}
	
	public boolean isSingle(){
		return status==AgentStatus.SINGLE;
	}
	
	public boolean isMotivated(){
		return status==AgentStatus.MOTIVATED;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (type==AgentType.MAN?"m ":"w ")+this.id;
	}
}
