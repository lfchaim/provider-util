package br.com.provider.provider_util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class TrataCargaCSV {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "d:\\";
		String fileName = "carga.csv";
		String fileNew = "carga-ok.csv";
		trataCSV(path, fileName, fileNew);
	}

	private static void trataCSV(String path, String fileName, String fileNew){
		FileReader fr = null;
		BufferedReader br = null;
		delete(path, fileNew);
		String header = "DatCompetencia;NumSUSEPSeguradora;NumGrpRamo;NumRamo;TipModulo;NumEventoContabil;NumPpsta;NumApolice;DatEmissaoApolice;DatIniVigApolice;DatFimVigApolice;NumEndosso;DatEmissaoEndosso;DatIniVigEndosso;DatFimVigEndosso;NumParcela;QtdParcela;DatVctoParcela;DatMovimento;NumSucursal;CodIdxPremio;NumEndossoCanc;SlgUF;TipPessoaSegurado;NumCPFCNPJSegurado;NomPessoaSegurado;FlgCosseguro;FlgResseguro;TipSexo;DtNascFund;DscProcessoSUSEP;CodCobertura;PrcBonus;TipEmissao;CodProdutor;TipPessoaTomador;NumCPFCNPJTomador;VlrPremioLiquido;VlrDescAgravo;VlrCustoApolice;VlrAdicFrac;VlrPremioAssist;VlrVistoria;VlrIOF;PrcComissPremio;PrcComissAssistencia;PrcAgenciamento;PrcProLabore;PrcRepresentacao;FlgPrimParcAntecipada;";
		try {
			fr = new FileReader(new File(path,fileName));
			br = new BufferedReader(fr);
			String line = null;
			long count = 0;
			while ( (line = br.readLine()) != null ) {
				if( count > 0 ){
					String[] lineSplit = line.split(";");
					for( int i = 0; i < lineSplit.length; i++ ){
						if( lineSplit[i].equals("0000/00/00") )
							lineSplit[i] = "";
					}
					// Inclusao da coluna DatMovimento - igual ao valor da coluna 0
					String[] newArray = new String[lineSplit.length+1];
					for( int i = 0; i < 18; i++ ){
						newArray[i] = lineSplit[i];
					}
					newArray[18] = lineSplit[0];
					for( int i = 18; i < lineSplit.length; i++ ){
						newArray[i+1] = lineSplit[i];
					}
					line = arrayToStr(newArray, ";");
				}else{
					line = header;
				}
				append(path, fileNew, line);
				count++;
				if( count % 10000 == 0 )
					System.out.println("Itens processados: "+count);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static boolean delete(String path, String fileName){
		File f = new File(path,fileName);
		return f.delete();
	}
	
	private static String arrayToStr( String[] array, String delimiter ){
		StringBuffer sb = new StringBuffer();
		for( int i = 0; i < array.length; i++ ){
			sb.append(array[i]).append(delimiter);
		}
		return sb.toString();
	}
	
	private static void append(String path, String fileName, String value){
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(new File(path, fileName),true);
			bw = new BufferedWriter(fw);
			bw.write(value+"\n");
			bw.flush();
		} catch (Exception e) {

		} finally {
			try{bw.close();}catch(Exception e){}
			try{bw.close();}catch(Exception e){}
		}
	}
}
