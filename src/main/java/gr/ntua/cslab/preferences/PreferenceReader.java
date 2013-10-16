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

/**
 * Class used to load preferences from file.
 * @author Giannis Giannakopoulos
 *
 */
public class PreferenceReader {

	private RandomAccessFile file;
	
	/**
	 * The name of the file containing the preferences and the offset who points to the first
	 * preference. 
	 */
	public PreferenceReader(String fileName, long startOffset) {
		try{
			file = new RandomAccessFile(fileName, "r");
			file.seek(startOffset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Count: number of preferences to return
	 * @param count
	 * @return
	 */
	public Preference[] getPreferences(int count){
		Preference[] res = new Preference[count];
		for(int i=0;i<count;i++){
			res[i] = new Preference();
			try {
			res[i].setRankId(file.readInt());
				res[i].setForeignRank(file.readInt());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	/**
	 * Offset measured in bytes to move the current pointer to
	 * @param offset
	 */
	public void seek(long offset){
		try {
			file.seek(offset);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
