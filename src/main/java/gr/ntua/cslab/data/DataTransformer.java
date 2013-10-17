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
package gr.ntua.cslab.data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Class used to transform the ASCII data to binary data.
 * @author Giannis Giannakopoulos
 *
 */
public class DataTransformer {
	
	public static int[][] men;
	public static int[][] women;
	public static int[][] menRev;
	public static int[][] womenRev;
	
	
	public static int DATASET_SIZE;
	
	public static int[][] getDataset(String filename) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		int[][] result = null;
		int i=0;
		while(reader.ready()){
			String splits[]= reader.readLine().trim().split(" ");
			if(splits.length==1){
				DATASET_SIZE=new Integer(splits[0]);
				result = new int[DATASET_SIZE][DATASET_SIZE];
				i=0;
			}
			else{
				int j=0;
				for(String s:splits){
					result[i][j++] = new Integer(s);
				}
				i++;
			}	
		}
		reader.close();
		
		return result;
	}
	
	public static int[][] getDatasetReversed(String filename) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		int[][] result = null;
		int i=0;
		while(reader.ready()){
			String splits[]= reader.readLine().trim().split(" ");
			if(splits.length==1){
				DATASET_SIZE=new Integer(splits[0]);
				result = new int[DATASET_SIZE][DATASET_SIZE];
				i=0;
			}
			else{
				int j=1;
				for(String s:splits){
					result[i][new Integer(s)-1] = j++;
				}
				i++;
			}	
		}
		reader.close();
		
		return result;
	}
	
	public static int[] calculate(int[][] prefs, int[][]opRevPrefs){
		int[] results = new int[prefs.length*prefs.length*2];
		int index=0;
		for(int i=0;i<prefs.length;i++){
			for(int j=0;j<prefs[i].length;j++){
				int next=prefs[i][j];
				int revnext = opRevPrefs[next-1][i];
				results[index++]=next;
				results[index++]=revnext;
			}
		}
		return results;
	}
		
	public static void writeToFile(int[] array, String filename) throws IOException{
		DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));
		out.write(createByteArray(array));
		out.close();
	}
	
	private static byte[] createByteArray(int[] array){
		byte[] results = new byte[array.length*4];
		int index=0;
		for(int d:array){
			for(byte b:getBytes(d)){
				results[index++]=b;
			}
		}
		return results;
	}
	
	private static byte[] getBytes(Integer number){
		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.putInt(number);
		return buf.array();
	}
	
	private static String getOutputFileName(String inputFile){
		String basename = inputFile.substring(inputFile.lastIndexOf('/')+1);
		return basename.substring(0, basename.lastIndexOf('.'))+".bin";
	}
	
	public static void main(String[] args) throws IOException {
		if(args.length<2){
			System.err.println("Please provide men and women datasets as arguments");
			System.exit(1);
		}
		
		
		System.out.print("Reading from files...\t");
		long start = System.currentTimeMillis();
		men = DataTransformer.getDataset(args[0]);
		women = DataTransformer.getDataset(args[1]);
		menRev = DataTransformer.getDatasetReversed(args[0]);
		womenRev = DataTransformer.getDatasetReversed(args[1]);
		
		System.out.println("Done ("+(System.currentTimeMillis()-start)+" ms)");
		System.out.print("Transforming data...\t");
		start = System.currentTimeMillis();
		int[] menData=calculate(men, womenRev);
		int[] womenData=calculate(women, menRev);
		System.out.println("Done ("+(System.currentTimeMillis()-start)+" ms)");
		
		String menOut=getOutputFileName(args[0]);
		String womenOut=getOutputFileName(args[1]);
		
		System.out.format("Writing file %s...\t", menOut);
		start = System.currentTimeMillis();
		writeToFile(menData, menOut);
		System.out.println("Done ("+(System.currentTimeMillis()-start)+" ms)");
		System.out.format("Writing file %s...\t", womenOut);
		start = System.currentTimeMillis();
		writeToFile(womenData, womenOut);
		System.out.println("Done ("+(System.currentTimeMillis()-start)+" ms)");


	}

}
