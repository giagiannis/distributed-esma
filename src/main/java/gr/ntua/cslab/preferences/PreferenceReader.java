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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * Class used to load preferences from file.
 * @author Giannis Giannakopoulos
 *
 */
public class PreferenceReader {

	private static RandomAccessFile sharedFile = openFile();
	
	/**
	 * Empty constructor
	 * preference. 
	 */
	public PreferenceReader() {
	
	}
	
	private static RandomAccessFile openFile(){
		if(PreferenceList.PREFERENCE_FILE==null || PreferenceList.PREFERENCE_FILE.equals(""))
			return null;
		RandomAccessFile file=null;
		try {
			file = new RandomAccessFile(PreferenceList.PREFERENCE_FILE, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * Count: number of preferences to return
	 * @param count
	 * @return
	 */
	@Deprecated
	public Preference[] getPreferencesSlow(int count){
		Preference[] res = new Preference[count];
		for(int i=0;i<count;i++){
			res[i] = new Preference();
			try {
				res[i].setRankId(sharedFile.readInt());
				res[i].setForeignRank(sharedFile.readInt());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public Preference[] getPreferences(int count){
		ByteBuffer bf = ByteBuffer.wrap(bulkReadFromFile(count));
		Preference[] results = new Preference[count];
		int index=0;
		for(int i=0;i<count;i++){
			results[i] = new Preference(bf.getInt(index), bf.getInt(index+4));
			index+=8;
		}
		return results;
	}
	
	private byte[] bulkReadFromFile(int count){
		byte[] buffer = new byte[count*Preference.length()];
		try {
			sharedFile.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	/**
	 * Offset measured in bytes to move the current pointer to
	 * @param offset
	 */
	public void seek(long offset){
		try {
			sharedFile.seek(offset);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		PreferenceList.PREFERENCE_FILE=args[0];
		PreferenceReader.sharedFile = openFile();
		System.out.println("Set up preference file... ("+PreferenceList.PREFERENCE_FILE+")");
//		PreferenceReader.sharedFile=new RandomAccessFile(args[0], "r");
		PreferenceReader reader = new PreferenceReader();
		long start=System.currentTimeMillis();
		reader.seek(0);
		Preference[] buffer = reader.getPreferencesSlow(new Integer(args[1]));
		long normal = System.currentTimeMillis()-start;start=System.currentTimeMillis();
		reader.seek(0);
		buffer = reader.getPreferences(new Integer(args[1]));
		long bulk = System.currentTimeMillis()-start;
		
		System.out.format("Normal:\t%.3f s,\tBulk:\t%.3f s\n",normal/1000.0, bulk/1000.0);
	}
	
}
