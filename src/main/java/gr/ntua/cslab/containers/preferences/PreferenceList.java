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
package gr.ntua.cslab.containers.preferences;

import gr.ntua.cslab.conf.DataFileConfiguration;
import gr.ntua.cslab.containers.agents.Agent;
import gr.ntua.cslab.utils.File;

import java.io.RandomAccessFile;

/**
 * Preference list, used to contain all the preferences ({@link Preference} objects)
 * for an agent.
 * @author Giannis Giannakopoulos
 *
 */

public class PreferenceList {						
	
	private Preference[] buffer;
	private int indexGlobal=1;
	private int indexInBuffer=-1;
	private PreferenceReader reader;
	private int id;
	
	private DataFileConfiguration conf;
	
	/**
	 * Empty constructor
	 */
	public PreferenceList(){
		
	}
	
	/**
	 * 
	 */
	public PreferenceList(int id, DataFileConfiguration conf) {
		this.id=id;
		this.conf = conf;
		reader = new PreferenceReader(conf.getDataFile());
		this.setNext(1);
	}
	
	public DataFileConfiguration getConf(){
		return this.conf;
	}
	
	public Preference getNext(){
		if(!this.hasMore()){
			return null;
		}
		if(this.bufferIsExhausted())
			this.setNext(this.indexGlobal);
		indexGlobal++;
		return this.buffer[indexInBuffer++];
	}
	
	public boolean bufferIsExhausted(){
		return indexInBuffer>=buffer.length;
	}
	
	public boolean hasMore(){
		return (indexGlobal<=this.conf.getPreferencesCount());
	}
	
	private void fillBuffer(){
		int bufferSize=((indexGlobal-1)+this.conf.getBufferSize()>this.conf.getPreferencesCount()?this.conf.getPreferencesCount()-(indexGlobal-1):conf.getBufferSize());
//		System.out.println("FILL BUFFER EVENT at pref index "+this.indexGlobal+" BUFFER: "+bufferSize);
		buffer = reader.getPreferences(bufferSize);
		indexInBuffer=0;
	}
	
	private long estimateBufferPosition(int preference){
		return conf.getPreferencesCount()*Preference.length()*(id-this.conf.getFirstAgentInFile())+((preference-1)*Preference.length());
	}
	
	public void setNext(int rank) {
		this.indexGlobal=rank;
		reader.seek(this.estimateBufferPosition(rank));
		this.fillBuffer();
	}
	
	public Agent getAgent(){
		return new Agent(this.id,this);
	}
	
	public static void main(String[] args) {
		if(args.length<5){
			System.err.println("I need file input, id, preference count and buffer size");
			System.exit(1);;
		}
		int 	id=new Integer(args[1]);
		
		RandomAccessFile file = File.openFileFromLocalDisk(args[0]);
		DataFileConfiguration conf = new DataFileConfiguration();
		conf.setPreferencesCount(new Integer(args[2]));
		conf.setBufferSize(new Integer(args[3]));
		conf.setFirstAgentInFile(new Integer(args[4]));
		conf.setDataFile(file);
		
		PreferenceList list = new PreferenceList(id,conf);
		
		while(list.hasMore()){
			System.out.println(list.getNext());
		}
		
		
	}
}
