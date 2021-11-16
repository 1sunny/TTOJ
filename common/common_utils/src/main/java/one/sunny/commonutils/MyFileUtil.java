package one.sunny.commonutils;

import cn.hutool.crypto.SecureUtil;

import java.io.*;


public class MyFileUtil {
    public static String readFileByTrimLines( File file) {
        BufferedReader reader = null;
        String result = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            int line = 1;
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                result += tempString.trim();
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
        return result;
    }
    public static String md5FileByTrimLines( File file) {
        return SecureUtil.md5(readFileByTrimLines(file));
    }
    public static String FileToString( File file){
        cn.hutool.core.io.file.FileReader fileReader = new cn.hutool.core.io.file.FileReader(file);
        return fileReader.readString();
    }
    public static boolean stringToFile(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
