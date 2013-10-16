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
	
	private Preference[] preferences;
	
	
	/**
	 * 
	 */
	public PreferenceList(int id) {
		PreferenceReader reader = new PreferenceReader(PREFERENCE_FILE, PREFERENCES_COUNT*Preference.length()*(id-1));
		preferences = reader.getPreferences(BUFFER_SIZE);
		for(Preference d:preferences){
			System.out.println(d);
		}
	}
	
	
	private void initializePreferenceList(){
		
	}
	
	public static void main(String[] args) {
		PreferenceList.PREFERENCE_FILE=args[0];
		PreferenceList.PREFERENCES_COUNT=new Integer(args[1]);
		PreferenceList.BUFFER_SIZE=new Integer(args[2]);
		PreferenceList list = new PreferenceList(1);
		
	}
}
