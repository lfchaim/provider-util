package br.com.provider.provider_util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TrataArquivoCSV {

	public static void main(String[] args) {
		String path = "C:\\Users\\luis.chaim\\Downloads";
		path = "d:\\";
		String fileName = "carga.csv";
		String fileNew = "carga-ok.csv";
		//delete(path, fileNew);
		process(path, fileName, fileNew);
		//validate(path, "carga-ok-v0.csv");
	}
	
	private static void validate( String path, String fileName ) {
		List<String> list = listData(path, fileName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<String> listStr = new ArrayList<>();
		for( int i = 1; i < list.size(); i++ ){
			String[] sp = list.get(i).split(";");
			for( int j = 0; j < sp.length; j++ ) {
				if( isDate(sp[j]) ) {
					System.out.print(sp[j]+" ");
					try {
						Date d = sdf.parse(sp[j]);
						String sd = sdf.format(d);
						if( !sd.equals(sp[j]) ) {
							System.err.println("Erro: "+sp[j]+" i: "+i);
							System.exit(0);
						}else {
							if( !listStr.contains(sp[j]) ) {
								listStr.add(sp[j]);
							}
						}
					} catch (Exception e) {
						System.err.println("Erro no parse "+sp[j]+" i: "+i);
						System.exit(0);
					}
				}
			}
			System.out.println("");
		}
		Collections.sort(listStr);
		for( int i = 0; i < listStr.size(); i++ ) {
			System.out.println(listStr.get(i));
		}
		System.out.println("FIM");
	}
	
	private static void delete(String path, String file) {
		File f = new File(path,file);
		f.delete();
	}
	
	private static void appendLine( String path, String fileName, String line ) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(new File(path,fileName),true);
			bw = new BufferedWriter(fw);
			bw.write(line+"\n");
			bw.flush();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {bw.close();}catch(Exception e) {}
			try {fw.close();}catch(Exception e) {}
		}
	}
	
	private static List<String> listData(String path, String fileName){
		FileReader fr = null;
		BufferedReader br = null;
		List<String> ret = new ArrayList<>();
		try {
			fr = new FileReader(new File(path,fileName));
			br = new BufferedReader(fr);
			String line = null;
			while( (line = br.readLine()) != null ) {
				ret.add(line);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {br.close();}catch(Exception e) {}
			try {fr.close();}catch(Exception e) {}
		}
		return ret;
	}
	
	private static boolean isDate( String value ) {
		boolean ret = true;
		if( value != null ){
			if( value.indexOf("/") > -1 ) {
				for( int i = 0; i < value.length(); i++ ) {
					int ch = value.charAt(i);
					if( ch < 47 || ch > 57 ) {
						return false;
					}
				}
			}else {
				ret = false;
			}
		}else {
			ret = false;
		}
		return ret;
	}
	
	private static void process(String path, String fileName, String fileNew){
		ZipUtil zu = new ZipUtil();
		FileReader fr = null;
		BufferedReader br = null;
		String header = "DatCompetencia;NumSUSEPSeguradora;NumGrpRamo;NumRamo;TipModulo;NumEventoContabil;NumPpsta;NumApolice;DatEmissaoApolice;DatIniVigApolice;DatFimVigApolice;NumEndosso;DatEmissaoEndosso;DatIniVigEndosso;DatFimVigEndosso;NumParcela;QtdParcela;DatVctoParcela;DatMovimento;NumSucursal;CodIdxPremio;NumEndossoCanc;SlgUF;TipPessoaSegurado;NumCPFCNPJSegurado;NomPessoaSegurado;FlgCosseguro;FlgResseguro;TipSexo;DtNascFund;DscProcessoSUSEP;CodCobertura;PrcBonus;TipEmissao;CodProdutor;TipPessoaTomador;NumCPFCNPJTomador;VlrPremioLiquido;VlrDescAgravo;VlrCustoApolice;VlrAdicFrac;VlrPremioAssist;VlrVistoria;VlrIOF;PrcComissPremio;PrcComissAssistencia;PrcAgenciamento;PrcProLabore;PrcRepresentacao;FlgPrimParcAntecipada;";
		String[] arrHeader = header.split(";");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			fr = new FileReader(new File(path,fileName));
			br = new BufferedReader(fr);
			String line = null;
			long count = 0;
			long pageCount = 0;
			long limit = 199900;
			int page = 0;
			String nuApolice = "";
			while( (line = br.readLine()) != null ) {
				String[] strLine = line.split(";");
				if( count > 0 ) {
					int colPerComiss = 43;
					double dCom = 0d;
					try{dCom = Double.parseDouble(strLine[colPerComiss]);}catch(Exception e) {}
					if( dCom <= 0d )
						strLine[colPerComiss] = "15.00";
					for( int i = 0; i < strLine.length; i++ ) {
						strLine[i] = strLine[i].trim();
						if( strLine[i].equals("0000/00/00") )
							strLine[i] = "";
						if( isDate(strLine[i]) ) {
                            try {
                                Date dt = sdf.parse(strLine[i]);
                                String strDate = sdf.format(dt);
                                if( !strDate.equals(strLine[i]) ){
                                    System.err.println("Erro de data linha: "+count+" coluna: "+arrHeader[i]+" valor: ["+strLine[i]+"]");
                                }
                            }catch(ParseException pe) {
                                System.out.println("Erro na linha: "+count+" - "+line);
                            }
                        }
					}
					String[] strNewLine = new String[strLine.length+1];
					for( int i = 0; i < 18; i++ ) {
						strNewLine[i] = strLine[i];
					}
					if( count < 1 ) {
						strNewLine[18] = "DatMovimento";
					}else {
						strNewLine[18] = strLine[0];
					}
					for( int i = 18; i < strLine.length; i++ ) {
						strNewLine[i+1] = strLine[i];
					}
					StringBuffer sb = new StringBuffer();
					for( int i = 0; i < strNewLine.length; i++ ) {
						if( arrHeader[i].startsWith("Dat") && !isDate(strNewLine[i]) && strNewLine[i].length() > 0 ) {
							System.err.println("Erro de data linha: "+count+" coluna: "+arrHeader[i]+" valor: ["+strNewLine[i]+"]");
						}
						sb.append(strNewLine[i]).append(";");
					}
					line = sb.toString();
				}else {
					line = header;
				}
				count++;
				pageCount++;
				if( pageCount > limit ) {
					if( "".equals(nuApolice) ) {
						nuApolice = strLine[7];
						appendLine(path, changeName(fileNew,"-v"+page), line);
					}else if( !nuApolice.equals(strLine[7]) ) {
						zu.zip(path+"/"+changeName(fileNew,"-v"+page), path+"/"+changeName(fileNew,"-v"+page)+".zip");
						nuApolice = "";
						page++;
						pageCount = 0;
						appendLine(path, changeName(fileNew,"-v"+page), header);
						appendLine(path, changeName(fileNew,"-v"+page), line);
					}else {
						appendLine(path, changeName(fileNew,"-v"+page), line);
					}
				}else {
					appendLine(path, changeName(fileNew,"-v"+page), line);
				}
				if( count > 0 && count % 10000 == 0 )
					System.out.println(page+"-"+count);
			}
			zu.zip(path+"/"+changeName(fileNew,"-v"+page), path+"/"+changeName(fileNew,"-v"+page)+".zip");
			System.out.println("Processo finalizado - Itens processados: "+count);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {br.close();}catch(Exception e) {}
			try {fr.close();}catch(Exception e) {}
		}
	}
	
	private static String changeName( String fileName, String suffix ) {
		String ret = fileName.substring(0,fileName.lastIndexOf("."))+suffix+fileName.substring(fileName.lastIndexOf("."));
		return ret;
	}
}
