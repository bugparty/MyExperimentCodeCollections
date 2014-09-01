package com.example.network.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.cookie.SetCookie;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hanbowen on 2014/8/25.
 */
public class BRCookie implements SetCookie{
    private String sysauth;
    private String path;
    private String stok;
    private String rawvalue;
    private static Pattern ptnSplitHeader;
    private String name;
    public BRCookie() {
        if(ptnSplitHeader == null){
            ptnSplitHeader = Pattern.compile("Set\\-Cookie\\:(.*)");
        }
    }

    public String getSysauth() {
        return sysauth;
    }

    public void setSysauth(String sysauth) {
        this.sysauth = sysauth;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return rawvalue;
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public String getCommentURL() {
        return null;
    }

    @Override
    public Date getExpiryDate() {
        return null;
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    public String getDomain() {
        return null;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int[] getPorts() {
        return new int[0];
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public boolean isExpired(Date date) {
        return false;
    }

    @Override
    public void setValue(String s) {
        this.rawvalue = s;
    }
    public  void setNameValue(String s){
        Matcher matcher = ptnSplitHeader.matcher(s);
        if(matcher.find()){
            String c = matcher.group(1);
            String [] vals = StringUtils.split(c, ";");
            for(String v: vals){
                String []kv = StringUtils.split(StringUtils.strip(v), "=");
                if(kv[0].contains("sysauth")){
                    this.setSysauth(kv[1]);
                }else if(kv[0].contains("path")){
                    this.setPath(kv[1]);
                }else if(kv[0].contains("stok")){
                    this.setStok(kv[1]);
                }
            }

        }
    }

    @Override
    public void setComment(String s) {

    }

    @Override
    public void setExpiryDate(Date date) {

    }

    @Override
    public void setDomain(String s) {

    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setSecure(boolean b) {

    }

    @Override
    public void setVersion(int i) {

    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }
}
