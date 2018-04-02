package zstu.utils.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by liupeizhi on 2017/12/9.
 */
public class FileTest {

    private static ArrayList<String> filelist = new ArrayList<String>();

    public static void main(String[] args) throws Exception {

        String filePath = "D:\\GitProjects\\xcloud-commonservice";
        getFiles(filePath);
    }
    /*
     * 通过递归得到某一路径下所有的目录及其文件
     */
    static void getFiles(String filePath){
        File root = new File(filePath);
        File[] files = root.listFiles();
        for(File file:files){
            if(file.isDirectory()){
      /*
       * 递归调用
       */
                getFiles(file.getAbsolutePath());
                filelist.add(file.getAbsolutePath());
//                System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
            }else{
                if(file.getAbsolutePath().endsWith(".java")) {
                    readFileByLines(file.getAbsolutePath());
                    System.out.println("显示" + filePath + "下所有子目录" + file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param fileName
     *            文件名
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;


        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号


                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


}
