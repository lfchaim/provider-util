package br.com.provider.provider_util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestCSVtoSQLInsert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "C:\\Users\\luis.chaim\\Downloads";
		Map<String,String> map = new LinkedHashMap<>();
		map.put("CbdPlanoContas", "CbdPlanoContas03102017 141717.csv");
		map.put("CbdCentrosCusto", "CbdCentrosCusto03102017 141713.csv");
		map.put("CbdHistoricosPadroes", "CbdHistoricosPadroes03102017 141715.csv");
		map.put("CbdTiposEventos", "CbdTiposEventos03102017 141719.csv");
		map.put("CbdTiposEventosCtas", "CbdTiposEventos03102017 141723.csv");

		Iterator<String> it = map.keySet().iterator();
		while( it.hasNext() ){
			String key = it.next();
			System.out.println("Tratando "+key);
			List<String> listSql = listSQL(path, map.get(key), key);
			FileUtil.writeFile(path, key+".csv", listSql, false);
		}
	}

	public static List<String> listSQL(String path, String csv, String table){
		List<String[]> listCsv1 = FileUtil.listContentDelim(path, csv, ",");
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
					if( listCsv1.get(0)[j].startsWith("Id") || listCsv1.get(0)[j].startsWith("Cod") ){
						sb.append(listCsv1.get(i)[j]);
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
}
