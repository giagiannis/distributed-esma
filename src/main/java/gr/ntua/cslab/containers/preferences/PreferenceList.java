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
package gr.ntua.cslab.preferences;

import java.io.RandomAccessFile;

import org.eclipse.jdt.internal.compiler.impl.IntConstant;

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
	private int bufferSize=-1;
	private int preferenceCount=-1;
	private int firstAgentInFile=1;

	/**
	 * Empty constructor
	 */
	public PreferenceList(){
		
	}
	
	/**
	 * 
	 */
	public PreferenceList(int id, RandomAccessFile file, int preferenceCount, int bufferSize, int firstAgentInFile) {
		this.id=id;
		this.preferenceCount=preferenceCount;
		this.bufferSize = bufferSize;
		this.firstAgentInFile=firstAgentInFile;
		reader = new PreferenceReader(file);
		this.setNext(1);
	}
	
	public int getBufferSize(){
		return this.bufferSize;
	}
	
	public void setBufferSize(int numberOfPreferences){
		this.bufferSize=numberOfPreferences;
	}
	
	public int getPreferenceCount() {
		return preferenceCount;
	}

	public void setPreferenceCount(int preferenceCount) {
		this.preferenceCount = preferenceCount;
	}

	public int getFirstAgentInFile() {
		return firstAgentInFile;
	}

	public void setFirstAgentInFile(int firstAgentInFile) {
		this.firstAgentInFile = firstAgentInFile;
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
		return (indexGlobal<=preferenceCount);
	}
	
	private void fillBuffer(){
		int bufferSize=((indexGlobal-1)+this.bufferSize>preferenceCount?preferenceCount-(indexGlobal-1):this.bufferSize);
//		System.out.println("FILL BUFFER EVENT at pref index "+this.indexGlobal+" BUFFER: "+bufferSize);
		buffer = reader.getPreferences(bufferSize);
		indexInBuffer=0;
	}
	
	private long estimateBufferPosition(int preferene){
		return preferenceCount*Preference.length()*(id-this.firstAgentInFile)+((preferene-1)*Preference.length());
	}
	
	public void setNext(int rank) {
		this.indexGlobal=rank;
		reader.seek(this.estimateBufferPosition(rank));
		this.fillBuffer();
	}
	
	public static void main(String[] args) {
		if(args.length<5){
			System.err.println("I need file input, id, preference count and buffer size");
			System.exit(1);;
		}
		RandomAccessFile file = PreferenceReader.openFile(args[0]);
		int 	id=new Integer(args[1]),
				prefC=new Integer(args[2]),
				buffS=new Integer(args[3]),
				firsA=new Integer(args[4]);
		
		
		PreferenceList list = new PreferenceList(id,file,prefC,buffS,firsA);
		
		while(list.hasMore()){
			System.out.println(list.getNext());
		}
		
		
	}
}
