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

import java.util.Iterator;

/**
 * @author Giannis Giannakopoulos
 *
 */
public class AgentIterator implements Iterator<Agent> {

	public static final byte ALL=0, SINGLE=1, MOTIVATED=2, ALLMOTIVATED=3, CONTENT=4;
	
	private byte type;
	private Agent[] agents;
	private int index=0;
	
	/**
	 * 
	 */
	public AgentIterator(byte type, Agent[] agents) {
		this.type=type;
		this.agents=agents;
	}
	
	@Override
	public boolean hasNext() {
		while(this.index<this.agents.length){
			if(!this.termination(this.agents[index]))
				this.index++;
			else
				return true;
		}
		return false;
	}

	@Override
	public Agent next() {
		return	this.agents[this.index++];
	}

	@Override
	public void remove() {
		// not supported
	}
	private boolean termination(Agent a){
		switch (this.type) {
		case ALL:
			return true;
		case SINGLE:
			return a.isSingle();
		case CONTENT:
			return a.isContent();
		case MOTIVATED:
			return a.isMotivated();
		case ALLMOTIVATED:
			return a.isSingle()|| a.isMotivated();
		default:
			return false;
		}
	}

}
