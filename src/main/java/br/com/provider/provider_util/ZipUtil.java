package br.com.provider.provider_util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class ZipUtil {

    public ZipUtil() {
    }

    public void zip(String filename, String filenameTmp) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(filenameTmp))) {
            File file = new File(filename);
            zos.putNextEntry(new ZipEntry(file.getName()));
            
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int read;
            while ((read = bis.read()) != -1) {
                zos.write(read);
            }
            
            zos.closeEntry();
        }
    }

    @SuppressWarnings("unchecked")
    public void unzip(String filename) throws IOException {        
        String dir = "";
        if (filename.lastIndexOf('/') > 0) {
            dir = filename.substring(0, filename.lastIndexOf('/'));
        } else if (filename.lastIndexOf('\\') > 0) {
            dir = filename.substring(0, filename.lastIndexOf('\\'));
        }

        File fileZip = new File(filename);
        try (ZipFile zf = new ZipFile(fileZip)) {
            Enumeration<ZipEntry> zipEnum = (Enumeration<ZipEntry>) zf.entries();
            while (zipEnum.hasMoreElements()) {
                ZipEntry item = zipEnum.nextElement();
                String itemName = item.getName();
                System.out.println("Extracting: " + itemName);
                try (OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(dir, itemName))); InputStream is = zf.getInputStream(item)) {
                    int r;
                    while ((r = is.read()) != -1) {
                        os.write(r);
                    }
                }
            }
        }
        System.out.println("Extraction [OK]");
    }

    public byte[] getBytes(File fZIP) throws IOException {
        ByteArrayOutputStream arrayOutputStream;
        try (FileInputStream fin = new FileInputStream(fZIP)) {
            byte[] buffer = new byte[4096];
            arrayOutputStream = new ByteArrayOutputStream();
            int bytesRead = 0;
            while ((bytesRead = fin.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, bytesRead);
            }   arrayOutputStream.close();
        }
        return arrayOutputStream.toByteArray();
    }
}