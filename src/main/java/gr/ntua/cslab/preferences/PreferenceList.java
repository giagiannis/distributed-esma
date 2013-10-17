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

import java.util.LinkedList;

/**
 * Preference list, used to contain all the preferences ({@link Preference} objects)
 * for an agent.
 * @author Giannis Giannakopoulos
 *
 */

public class PreferenceList {

	public static int BUFFER_SIZE=-1;
	public static int PREFERENCES_COUNT=-1;
	public static String PREFERENCE_FILE="";
	public static int FIRST_AGENT_IN_FILE=1;
	
	private Preference[] buffer;
	private int indexGlobal=1;
	private int indexInBuffer=-1;
	private PreferenceReader reader;
	private int id;
	/**
	 * 
	 */
	public PreferenceList(int id) {
		this.id=id;
		reader = new PreferenceReader();
		this.setNext(1);
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
		return (indexGlobal<=PREFERENCES_COUNT);
	}
	
	private void fillBuffer(){
		int bufferSize=((indexGlobal-1)+BUFFER_SIZE>PREFERENCES_COUNT?PREFERENCES_COUNT-(indexGlobal-1):BUFFER_SIZE);
//		System.out.println("FILL BUFFER EVENT at pref index "+this.indexGlobal+" BUFFER: "+bufferSize);
		buffer = reader.getPreferences(bufferSize);
		indexInBuffer=0;
	}
	
	private long estimateBufferPosition(int preferene){
		return PREFERENCES_COUNT*Preference.length()*(id-1)+((preferene-PreferenceList.FIRST_AGENT_IN_FILE)*Preference.length());
	}
	
	public void setNext(int rank) {
		this.indexGlobal=rank;
		reader.seek(this.estimateBufferPosition(rank));
		this.fillBuffer();
	}
	
	public static void main(String[] args) {
		PreferenceList.PREFERENCE_FILE=args[0];
		PreferenceList.PREFERENCES_COUNT=new Integer(args[1]);
		PreferenceList.BUFFER_SIZE=new Integer(args[2]);
		
		System.out.format("%d\t",BUFFER_SIZE);
		
		LinkedList<PreferenceList> lists = new LinkedList<PreferenceList>();
		long start=System.currentTimeMillis();
		for(int i=1;i<=PREFERENCES_COUNT;i++){
			lists.add(new PreferenceList(i));
		}
		System.out.format("%.3f\t",(System.currentTimeMillis()-start)/1000.0);
		start = System.currentTimeMillis();
		int count=0;
		while(lists.get(0).hasMore()){
			for(PreferenceList l:lists){
				if(l.getNext()!=null){
					count+=1;
				}
			}
		}
		System.out.format("%.3f\n",(System.currentTimeMillis()-start)/1000.0);
		
		if(count!=PREFERENCES_COUNT*PREFERENCES_COUNT)
			System.err.println("Wrong results man!");
		
		lists.clear();
		lists=null;
		System.gc();
//
//		PreferenceList list = new PreferenceList(1000);
//		while(list.hasMore()){
//			list.getNext();
//		}
		
//		PreferenceList list;
//		for(int ind=1;ind<=PREFERENCES_COUNT;ind++){
//			list = new PreferenceList(ind);
//			Random rand = new Random();
//			for(int i=0;i<1; i++){
//				while(list.hasMore()){
//					System.out.print(list.getNext()+" ");
//				}
//				System.out.println();
////				list.setNext(rand.nextInt(PREFERENCES_COUNT)+1);
//			}
//			System.out.println("======================================================");
//		}
	}
}
