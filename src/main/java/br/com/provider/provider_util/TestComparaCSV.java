package br.com.provider.provider_util;

import java.util.List;

public class TestComparaCSV {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "C:\\Users\\luis.chaim\\Downloads";
		String csv1 = "CbdCentrosCusto03102017 141713.csv";
		String csv2 = "CbdCentrosCusto03102017 145706.csv";
		List<String[]> listCsv1 = FileUtil.listContentDelim(path, csv1, ",");
		List<String[]> listCsv2 = FileUtil.listContentDelim(path, csv2, ",");
		System.out.println(equals(listCsv1, listCsv2));
	}

	private static boolean equals( List<String[]> list1, List<String[]> list2 ){
		if( list1 != null && list2 != null ){
			if( list1.size() != list2.size() )
				return false;
			for( int i = 0; i < list1.size(); i++ ){
				if( !list1.get(i).equals(list2.get(i)) )
					return false;
			}
		} else if( (list1 == null && list2 != null) || (list1 != null && list2 == null) ) {
			return false;
		}
		return true;
	}
}
