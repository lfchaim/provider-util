package br.com.provider.provider_util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestCSVtoSQLInsert {

	private static String delim = "\\|";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "C:\\Users\\luis.chaim\\Downloads\\csv_sync\\";
		
		String fileToFilter = null;//"Indexadores_OLD";
		
		Map<String,String> map = new LinkedHashMap<>();
		List<String> listFile = FileUtil.listFile(path);
		for( int i = 0; i < listFile.size(); i++ ){
			if( listFile.get(i).indexOf("_OLD") > -1 ){
				if( fileToFilter != null && listFile.get(i).indexOf(fileToFilter) < 0 )
					continue;
				map.put(listFile.get(i).substring(0,listFile.get(i).indexOf("_OLD")), listFile.get(i));
			}
		}
		
		Iterator<String> it = map.keySet().iterator();
		while( it.hasNext() ){
			String key = it.next();
			System.out.println("Tratando "+key);
			List<String> listSql = listSQL(path, map.get(key), key);
			FileUtil.writeFile(path, key+".sql", listSql, false);
		}
	}

	public static List<String> listSQL(String path, String csv, String table){
		List<String[]> listCsv1 = FileUtil.listContentDelim(path, csv, delim);
		List<String> listSql = new ArrayList<>();
		if( listCsv1.size() <=1 )
			return listSql;
		String sdfa = "d/M/yyyy h:mm:ss a";
		String sdfb = "yyyy-MM-dd HH:mm:ss";
		for( int i = 1; i < listCsv1.size(); i++ ){
			StringBuffer sb = new StringBuffer();
			try{
				sb.append("insert into {").append(table).append("} ");
				sb.append("(");
				for( int j = 1; j < listCsv1.get(0).length; j++ ){
					if( j > 1 )
						sb.append(",");
					sb.append(listCsv1.get(0)[j]);
				}
				sb.append(") values (");
				for( int j = 1; j < listCsv1.get(i).length; j++ ){
					if( j > 1 )
						sb.append(",");
					if( listCsv1.get(0)[j].startsWith("Id") || listCsv1.get(0)[j].startsWith("Cod") || listCsv1.get(0)[j].startsWith("Seq") || listCsv1.get(0)[j].startsWith("Num") ){
						if( isNum(listCsv1.get(i)[j]) )
							sb.append(listCsv1.get(i)[j]);
						else
							sb.append("'").append(listCsv1.get(i)[j]).append("'");
					} else if( listCsv1.get(0)[j].startsWith("Dt") || listCsv1.get(0)[j].startsWith("Dat") ){
						Date dt = DateUtil.parse(listCsv1.get(i)[j], sdfa);
						String dtStr = DateUtil.format(dt, sdfb);
						sb.append("'").append(dtStr).append("'");
					} else {
						sb.append("'").append(listCsv1.get(i)[j]).append("'");
					}
				}
				sb.append(")");
				listSql.add(sb.toString());
			}catch(Exception e){
				System.err.println("Erro processando "+sb.toString());
			}
		}
		return listSql;
	}
	
	private static boolean isNum( String val ){
		boolean ret = true;
		if( val == null )
			return ret;
		for( int i = 0; i < val.length(); i++ ){
			if( !Character.isDigit(val.charAt(i)) )
				return false;
		}
		return ret;
	}
}
