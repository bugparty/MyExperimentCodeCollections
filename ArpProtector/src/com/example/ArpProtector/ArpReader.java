package com.example.ArpProtector;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hanbowen on 2014/8/28.
 */
public class ArpReader {
    public static String readAll(){
        FileReader fr;

        try {
            fr = new FileReader("/proc/net/arp");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        int ch;
        StringBuilder sb= new StringBuilder();
        try {

            while ((ch= fr.read()) != -1){
                sb.append((char)ch);
            }

        }catch(IOException e){
            e.printStackTrace();

        }finally {
            return sb.toString();
        }

    }
    public static String readByIP(String ip){
        Pattern ptnMac = Pattern.compile("([abcdef1234567890]{2}\\:[abcdef1234567890]{2}\\:[abcdef1234567890]{2}\\:[abcdef1234567890]{2}\\:[abcdef1234567890]{2}\\:[abcdef1234567890]{2})");
        FileReader fr;
        ip = ip.trim();
        try {
            fr = new FileReader("/proc/net/arp");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if(line.contains(ip)){
                    Matcher m = ptnMac.matcher(line);
                    if(m.find()){
                        return m.group(1);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return null;

    }
}
