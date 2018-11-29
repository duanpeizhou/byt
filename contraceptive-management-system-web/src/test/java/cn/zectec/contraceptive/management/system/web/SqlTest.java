package cn.zectec.contraceptive.management.system.web;

import java.io.*;

/**
 * @author duanpeizhou on 2018/11/29 下午6:47.
 */
public class SqlTest {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/shengrui/Desktop/cms.sql");
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = null;

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/shengrui/Desktop/cms_n.sql"));

        while ((line = reader.readLine()) != null) {
            line = new String(line.getBytes("GBK"), "UTF-8");
            System.out.println(line);

            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }

    }
}
