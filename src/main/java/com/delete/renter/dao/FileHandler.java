package com.delete.renter.dao;

import java.io.*;

/**
 * 用于写文件
 */
public class FileHandler {
    private InputStream in;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileHandler(String path) throws FileNotFoundException {
        this.path = path;
        File file = new File(path);
        if (file.exists()){
            in = new FileInputStream(file);
        }
    }

    public void close(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(Object object) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(this.in));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null){
            buffer.append(line);
        }
        return buffer.toString();
    }
}
