package br.com.provider.provider_util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static List<String[]> listContentDelim( String path, String fileName, String delim ){
		FileReader fr = null;
		BufferedReader br = null;
		List<String[]> ret = new ArrayList<>();
		try {
			fr = new FileReader(new File(path,fileName));
			br = new BufferedReader(fr);
			String line = null;
			while( (line = br.readLine()) != null ){
				String[] arrLine = line.split(delim);
				ret.add(arrLine);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try{br.close();}catch(Exception e){}
			try{fr.close();}catch(Exception e){}
		}
		return ret;
	}
	
	public static int writeFile( String path, String fileName, List<String> content, boolean append ){
		FileWriter fw = null;
		BufferedWriter bw = null;
		int count = 0;
		try {
			fw = new FileWriter(new File(path,fileName),append);
			bw = new BufferedWriter(fw);
			for( int i = 0; i < content.size(); i++ ){
				bw.write(content.get(i)+"\n");
				bw.flush();
				count++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try{bw.close();}catch(Exception e){}
			try{fw.close();}catch(Exception e){}
		}
		return count;
	}
}
