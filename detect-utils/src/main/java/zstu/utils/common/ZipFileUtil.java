package zstu.utils.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * User: Aning
 * Date: 14-11-27
 * Time: 下午8:44
 * To change this template use File | Settings | File Templates.
 */
public class ZipFileUtil {

    /**
     * 解压zip文件中指定的文件夹
     * @param sourceZip
     * @param EntryName
     * @param outFileName
     * @throws IOException
     */
    public static void unZipToFile(String sourceZip, String EntryName, String outFileName)
            throws IOException {
        ZipFile zfile = new ZipFile(sourceZip);
        //System.out.println(zfile.getName());
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            //从ZipFile中得到一个ZipEntry
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory() || !ze.getName().startsWith(EntryName)) {
                continue;
            }
            //以ZipEntry为参数得到一个InputStream，并写到OutputStream中
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(outFileName, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
            //System.out.println("Extracted: " + ze.getName());
        }
        zfile.close();
    }

    public static void unZipToFile(String sourceZip, String outFileName)
            throws IOException {
        ZipFile zfile = new ZipFile(sourceZip);
        //System.out.println(zfile.getName());
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            //从ZipFile中得到一个ZipEntry
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                continue;
            }
            //以ZipEntry为参数得到一个InputStream，并写到OutputStream中
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(outFileName, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
            //System.out.println("Extracted: " + ze.getName());
        }
        zfile.close();
    }

    /**
     * 向zipOutputStream中加入sourceZipFile中的文件
     * @param sourceZipFile
     * @param entryName 文件夹名称
     * @param zipOutputStream
     * @throws IOException
     */
    public static void putFileToZip(String sourceZipFile, String entryName, ZipOutputStream zipOutputStream) throws IOException {
        ZipFile zfile = new ZipFile(sourceZipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            //从ZipFile中得到一个ZipEntry
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                continue;
            }
            //以ZipEntry为参数得到一个InputStream，并写到OutputStream中
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            ZipEntry entry = new ZipEntry(entryName+ze.getName());
            //System.out.println("Extracted: " + entry.getName());
            zipOutputStream.putNextEntry(entry);
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                zipOutputStream.write(buf, 0, readLen);
            }
            is.close();
        }
        zfile.close();
    }


    /**
     * 压缩文件,并删除源文件
     * @param baseDir 所要压缩的目录名
     * @param objFileName 压缩后的文件名
     * @throws Exception
     */
    public static void createZip(String baseDir, String objFileName) throws IOException {
        File folderObject = new File(baseDir);
        if (folderObject.exists()){
            List fileList = getSubFiles(new File(baseDir));
            //压缩文件名
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(objFileName));
            ZipEntry ze = null;
            byte[] buf = new byte[1024];
            int readLen = 0;
            for (int i = 0; i < fileList.size(); i++) {
                File f = (File) fileList.get(i);
                //System.out.println("Adding: " + f.getPath() + f.getName());
                //创建一个ZipEntry，并设置Name和其它的一些属性
                ze = new ZipEntry(getAbsFileName(baseDir, f));
                ze.setSize(f.length());
                ze.setTime(f.lastModified());
                //将ZipEntry加到zos中，再写入实际的文件内容
                zos.putNextEntry(ze);
                InputStream is = new BufferedInputStream(new FileInputStream(f));
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    zos.write(buf, 0, readLen);
                }
                is.close();
                f.delete();
                //System.out.println("done...");
            }
            zos.close();
            if (folderObject.exists()){
                deleteFile(folderObject);
            }
        }else{
            throw new IOException(baseDir+",this folder isnot exist!");
        }
    }



    private static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                ret = new File(ret, dirs[i]);
            }
        }
        if (!ret.exists()) {
            ret.mkdirs();
        }
        ret = new File(ret, dirs[dirs.length - 1]);
        return ret;
    }

    /**
     * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
     * @param baseDir
     * @param realFileName
     * @return
     */
    private static String getAbsFileName(String baseDir, File realFileName) {
        File real = realFileName;
        File base = new File(baseDir);
        String ret = real.getName();
        while (true) {
            real = real.getParentFile();
            if (real == null) {
                break;
            }
            if (real.equals(base)) {
                break;
            } else {
                ret = real.getName() + "/" + ret;
            }
        }
        //System.out.println("TTTTT" + ret);
        return ret;
    }

    /**
     * 取得指定目录下的所有文件列表，包括子目录.
     * @param baseDir
     * @return
     */
    private static List getSubFiles(File baseDir) {
        List ret = new ArrayList();
        File[] tmp = baseDir.listFiles();
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].isFile()) {
                ret.add(tmp[i]);
            }
            if (tmp[i].isDirectory()) {
                ret.addAll(getSubFiles(tmp[i]));
            }
        }
        return ret;
    }

    /**
     * 删除文件夹及其下面的所有子文件夹和文件
     * @param oldPath
     */
    public static void deleteFile(File oldPath) {
        if (oldPath.isDirectory()) {
            File[] files = oldPath.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
            oldPath.delete(); //删除空文件夹
        }else{
            oldPath.delete();
        }
    }

    /**
     * 获取zip文件名，不包含后缀
     * @param zipFile
     * @return
     */
    public static String getZipFileName(File zipFile){
        String zipFileName = zipFile.getName();
        return zipFileName.substring(0,zipFileName.lastIndexOf('.'));
    }



}
