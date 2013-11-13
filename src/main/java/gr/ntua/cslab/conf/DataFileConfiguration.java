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
package gr.ntua.cslab.conf;

import gr.ntua.cslab.containers.agents.AgentType;

import java.io.RandomAccessFile;

/**
 * @author giannis
 *
 */
public class DataFileConfiguration {

	private RandomAccessFile file;
	private int preferencesCount, bufferSize, firstAgentInFile;
	private AgentType agentsType;
	private int numberOfAgents;
	
	/**
	 * 
	 */
	public DataFileConfiguration() {

	}
	
	public int getPreferencesCount() {
		return preferencesCount;
	}
	
	public void setPreferencesCount(int preferencesCount) {
		this.preferencesCount = preferencesCount;
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public int getFirstAgentInFile() {
		return firstAgentInFile;
	}
	
	public void setFirstAgentInFile(int firstAgentInFile) {
		this.firstAgentInFile = firstAgentInFile;
	}
	
	public void setDataFile(RandomAccessFile file){
		this.file = file;
	}
	
	public RandomAccessFile getDataFile(){
		return this.file;
	}

	public AgentType getAgentsType() {
		return agentsType;
	}

        public void setAgentsType(AgentType agentsType) {
		this.agentsType = agentsType;
	}

    	public int getNumberOfAgents() {
		return numberOfAgents;
	}

	public void setNumberOfAgents(int numberOfAgents) {
		this.numberOfAgents = numberOfAgents;
	}
}
