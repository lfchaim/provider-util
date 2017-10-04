package br.com.provider.provider_util;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestTiposEventosCtas {

	private static String sdfa = "d/M/yyyy h:mm:ss a";
	private static String sdfb = "yyyy-MM-dd HH:mm:ss";
	
	public static void main(String[] args) {
		String path = "C:\\Users\\luis.chaim\\Downloads\\csv_sync\\";
		Map<String,List<String[]>> map = carregaCSV(path);
		List<String[]> list = map.get("CbdTiposEventosCtas_OLD");
		List<String> listSql = new ArrayList<>();
		for( int i = 0; i < list.size(); i++ ){
			if( i > 0 ){
				StringBuffer sql = new StringBuffer();
				sql.append("insert into {CbdTiposEventosCtas} (IdCbdTiposEventos,DatIniVig,IdIndexadorPremio,TipRessg,TipObjCbd,TipCbd,TipDebCred,IdCbdPlanoConta,IdCbdCentrosCusto,IdCbdHistoricoPadrao,DscHistoricoContabil,IdUserCad,DtHCad,IdUserAtu,DtHAtu) values ( ");
				// Tipos Eventos
				String tipIdOld = list.get(i)[1];
				String[] tipDescOldArray = ArrayUtil.getValue(map.get("CbdTiposEventos_OLD"), tipIdOld, "Id", new String[]{"TipModulo","DscEventoContabil"});
				String tipIdNew = ArrayUtil.getValueArray(map.get("CbdTiposEventos_NEW"), tipDescOldArray, new String[]{"TipModulo","DscEventoContabil"}, "Id");
				sql.append(tipIdNew).append(",");
				Date dt = DateUtil.parse(list.get(i)[2], sdfa);
				String dtStr = DateUtil.format(dt, sdfb);
				sql.append("'").append(dtStr).append("'").append(",");
				// Indexador Premio
				tipIdOld = list.get(i)[3];
				String tipDescOld = ArrayUtil.getValue(map.get("Indexadores_OLD"), tipIdOld, "Id", "CodIdx");
				tipIdNew = ArrayUtil.getValue(map.get("Indexadores_NEW"), tipDescOld, "CodIdx", "Id");
				sql.append(tipIdNew).append(",");
				sql.append("'").append(list.get(i)[4]).append("',");
				sql.append("'").append(list.get(i)[5]).append("',");
				sql.append("'").append(list.get(i)[6]).append("',");
				sql.append("'").append(list.get(i)[7]).append("',");
				// Plano Contas
				tipIdOld = list.get(i)[8];
				tipDescOld = ArrayUtil.getValue(map.get("CbdPlanoContas_OLD"), tipIdOld, "Id", "CodCtaContabil");
				tipIdNew = ArrayUtil.getValue(map.get("CbdPlanoContas_NEW"), tipDescOld, "CodCtaContabil", "Id");
				sql.append(tipIdNew).append(",");
				// Centro de Custo
				tipIdOld = list.get(i)[9];
				tipDescOld = ArrayUtil.getValue(map.get("CbdCentrosCusto_OLD"), tipIdOld, "Id", "CodCentroCusto");
				tipIdNew = ArrayUtil.getValue(map.get("CbdCentrosCusto_NEW"), tipDescOld, "CodCentroCusto", "Id");
				sql.append(tipIdNew).append(",");
				// Histórico Padrãol
				tipIdOld = list.get(i)[10];
				tipDescOld = ArrayUtil.getValue(map.get("CbdHistoricosPadroes_OLD"), tipIdOld, "Id", "CodHistoricoPadrao");
				tipIdNew = ArrayUtil.getValue(map.get("CbdHistoricosPadroes_NEW"), tipDescOld, "CodHistoricoPadrao", "Id");
				sql.append(tipIdNew).append(",");
				sql.append("'").append(list.get(i)[11]).append("',");
				sql.append("").append(list.get(i)[12]).append(",");
				dt = DateUtil.parse(list.get(i)[13], sdfa);
				dtStr = DateUtil.format(dt, sdfb);
				sql.append("'").append(dtStr).append("'").append(",");
				sql.append("").append(list.get(i)[14]).append(",");
				dt = DateUtil.parse(list.get(i)[15], sdfa);
				dtStr = DateUtil.format(dt, sdfb);
				sql.append("'").append(dtStr).append("'").append("");
				sql.append(")");
				listSql.add(sql.toString());
			}
		}
		FileUtil.writeFile(path, "CbdTiposEventosCtas.sql", listSql, false);
	}

	private static Map<String,List<String[]>> carregaCSV( String path ){
		Map<String,List<String[]>> ret = new LinkedHashMap<>();
		List<String> listFile = FileUtil.listFile(path);
		for( int i = 0; i < listFile.size(); i++ ){
			if( listFile.get(i).endsWith(".csv") ){
				ret.put(listFile.get(i).substring(0,listFile.get(i).indexOf(".csv")), FileUtil.listContentDelim(path, listFile.get(i), "\\|"));
			}
		}
		return ret;
	}
}
