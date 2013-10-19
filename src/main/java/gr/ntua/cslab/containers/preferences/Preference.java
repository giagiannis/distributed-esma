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


/**
 * Class used to hold a preference along with the foreign rank. This class (used as
 * a data structure) will hold a preference id along with the preference rank for the
 * foreign agent. 
 * @author giannis
 *
 */
public class Preference {

	private int rankId;
	private int foreignRank;
	
	/**
	 * Empty constructor
	 */
	public Preference() {
		
	}
	
	/**
	 * Constructor which accepts a rank id and a foreign rank as parameters
	 */
	public Preference(int rankId, int foreignRank) {
		this.rankId=rankId;
		this.foreignRank=foreignRank;
	}

	/**
	 * Returns the rank id.
	 * @return
	 */
	public int getRankId() {
		return rankId;
	}

	/**
	 * Returns the foreign rank (the rank given for this from the foreign agent)
	 * @return
	 */
	public int getForeignRank() {
		return foreignRank;
	}
	
	public void setRankId(int rankId) {
		this.rankId = rankId;
	}

	public void setForeignRank(int foreignRank) {
		this.foreignRank = foreignRank;
	}

	/**
	 * Used to get the size of the structure, measured in bytes.
	 * @return
	 */
	public static short length(){
		return 8;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.rankId+"("+this.foreignRank+")";
	}
	
	
}
