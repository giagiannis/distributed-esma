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
package gr.ntua.cslab.utils;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * Class used to manage files.
 * @author Giannis Giannakopoulos
 *
 */
public class File {

	/**
	 * Method used to open a file from local disk
	 * @param fileName
	 * @return
	 */
	public static RandomAccessFile openFileFromLocalDisk(String fileName){
		if(fileName==null || fileName.equals(""))
			return null;
		RandomAccessFile file=null;
		try {
			file = new RandomAccessFile(fileName, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file;
	}
}
