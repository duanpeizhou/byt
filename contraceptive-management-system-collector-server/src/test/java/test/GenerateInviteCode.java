package test;

import java.util.Random;

/**
 * Created by duanpeizhou on 2016/9/7/
 */
public class GenerateInviteCode {
    
    public static void main(String[] args){
        System.out.println(generate());
    }

    public static String generate(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<8;i++) {
            int g = random.nextInt(10);
            sb.append((char) (g + 48));
        }
        return sb.toString();
    }
}
