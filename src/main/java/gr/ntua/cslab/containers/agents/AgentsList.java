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

import gr.ntua.cslab.conf.DataFileConfiguration;
import gr.ntua.cslab.containers.preferences.PreferenceList;
import gr.ntua.cslab.utils.File;

/**
 * @author Giannis Giannakopoulos
 *
 */
public class AgentsList {
	
	private Agent[] agents;
	/**
	 * 
	 */
	public AgentsList(DataFileConfiguration conf) {
		this.agents = new Agent[conf.getNumberOfAgents()];
		int i=0;
		for(int id=conf.getFirstAgentInFile();id<=conf.getNumberOfAgents();id++){
			this.agents[i++]=new Agent(id, new PreferenceList(id, conf));
			this.agents[i-1].setType(conf.getAgentsType());
		}
	}
	
	public Agent get(int id){
		int offset=this.agents[0].getId();
		return this.agents[id-offset];
	}
	
	public int size(){
		return this.agents.length;
	}
	
	public Iterator<Agent> getAll(){
		return new AgentIterator(AgentIterator.ALL, this.agents);
	}
	
	public Iterator<Agent> getSingle(){
		return new AgentIterator(AgentIterator.SINGLE, this.agents);
	}
	
	public Iterator<Agent> getContent(){
		return new AgentIterator(AgentIterator.CONTENT, this.agents);
	}
	
	public Iterator<Agent> getMotivated(){
		return new AgentIterator(AgentIterator.MOTIVATED, this.agents);
	}
	
	public Iterator<Agent> getSingleOrMotivated(){
		return new AgentIterator(AgentIterator.ALLMOTIVATED, this.agents);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Agent a:agents){
			str.append(a.toString()+", ");
		}
		return str.substring(0, str.length()-2);
	}
	
	public static void main(String[] args) {
		String file = args[0];
		int numberOfAgents = new Integer(args[1]);
		
		DataFileConfiguration conf = new DataFileConfiguration();
		conf.setAgentsType((file.contains("women")?AgentType.WOMAN:AgentType.MAN));
		conf.setBufferSize(numberOfAgents/2);
		conf.setDataFile(File.openFileFromLocalDisk(file));
		conf.setFirstAgentInFile(1);
		conf.setNumberOfAgents(numberOfAgents);
		conf.setPreferencesCount(numberOfAgents);
		AgentsList list = new AgentsList(conf);
		System.out.println(list);
		list.get(8).setStatus(AgentStatus.MOTIVATED);
		list.get(3).setStatus(AgentStatus.CONTENT);
		Iterator<Agent> it;
		System.out.println("================ ALL");
		it = list.getAll();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println("================ SINGLE");
		it = list.getSingle();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println("================ CONTENT");
		it = list.getContent();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println("================ MOTIVATED");
		it = list.getMotivated();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println("================ SINGLE OR MOTIVATED");
		it = list.getSingleOrMotivated();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
}
