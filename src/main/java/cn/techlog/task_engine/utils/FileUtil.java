package cn.techlog.task_engine.utils;

import java.io.*;
import java.net.URL;

public class FileUtil {
    public static String getFileString(String fileName) {
        StringBuilder fileStringBuilder = new StringBuilder();
        try {
            URL url = FileUtil.class.getClassLoader().getResource(fileName);
            if (url == null) {
                System.err.println("file dose not exist: " + fileName);
                return null;
            }
            File file = new File(url.getFile());
            Reader reader = new InputStreamReader(new FileInputStream(file));
            char[] buffer = new char[1024 * 1024];
            int a = reader.read(buffer);
            for (int i = 0; i < a; ++i) {
                fileStringBuilder.append(buffer[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStringBuilder.toString();
    }
}
