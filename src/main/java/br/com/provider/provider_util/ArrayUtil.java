package br.com.provider.provider_util;

import java.util.List;

public class ArrayUtil {

	public static String getColName( List<String[]> list, int idx ){
		return list.get(0)[idx];
	}
	
	public static int getColIndex( List<String[]> list, String colName ){
		int ret = -1;
		for( int i = 0; i < list.get(0).length; i++ ){
			if( list.get(0)[i].equals(colName) ){
				ret = i;
				break;
			}
		}
		return ret;
	}
	
	public static String getValue( List<String[]> list, String val, String colSearch, String colToGet ){
		if( list == null || list.size() < 2 ){
			return null;
		}
		String ret = null;
		for( int i = 0; i < list.size(); i++ ){
			if( list.get(i)[getColIndex(list, colSearch)].equals(val) ){
				ret = list.get(i)[getColIndex(list, colToGet)];
				break;
			}
		}
		return ret;
	}
	
	public static String[] getValue( List<String[]> list, String val, String colSearch, String[] colToGet ){
		if( list == null || list.size() < 2 ){
			return null;
		}
		String[] ret = null;
		for( int i = 0; i < list.size(); i++ ){
			if( list.get(i)[getColIndex(list, colSearch)].equals(val) ){
				ret = new String[colToGet.length];
				for( int j = 0; j < colToGet.length; j++ ){
					ret[j] = list.get(i)[getColIndex(list, colToGet[j])];
				}
				break;
			}
		}
		return ret;
	}
	
	public static String getValueArray( List<String[]> list, String[] val, String[] colSearch, String colToGet ){
		if( list == null || list.size() < 2 ){
			return null;
		}
		String ret = null;
		for( int i = 0; i < list.size(); i++ ){
			int countEqual = 0;
			for( int j = 0; j < val.length; j++ ){
				if( list.get(i)[getColIndex(list, colSearch[j])].equals(val[j]) ){
					countEqual++;
				}
			}
			if( countEqual == val.length ){
				ret = list.get(i)[getColIndex(list, colToGet)];
				break;
			}
		}
		return ret;
	}
	
}
