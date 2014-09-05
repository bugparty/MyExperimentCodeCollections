package com.example.ArpProtector;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by hanbowen on 2014/8/18.
 */
public class IPV4   {
	public byte getB1() {
		return b1;
	}

	public byte getB2() {
		return b2;
	}

	public byte getB3() {
		return b3;
	}

	public byte getB4() {
		return b4;
	}

	public void setB1(byte b1) {
		this.b1 = b1;
	}

	public void setB2(byte b2) {
		this.b2 = b2;
	}

	public void setB3(byte b3) {
		this.b3 = b3;
	}

	public void setB4(byte b4) {
		this.b4 = b4;
	}

	protected byte b1;
	protected byte b2;
	protected byte b3;
	protected byte b4;

	public int getInt32() {
		return int32;
	}

	protected int int32;

	public IPV4(String ipAddress) {
		if (!ipAddress.contains("."))
			throw new IllegalArgumentException(
					"com.baidu.lib.ipdetect.ip address" + ipAddress
							+ "Does not contians a '.'");

		String[] ipParts = StringUtils.split(ipAddress, '.');

		if (ipParts.length != 4) {
			throw new IllegalArgumentException("IP Format Invalid");
		}

		b1 = ((byte) Integer.parseInt(ipParts[0]));
		b2 = (byte) Integer.parseInt(ipParts[1]);
		b3 = (byte) Integer.parseInt(ipParts[2]);
		b4 = (byte) Integer.parseInt(ipParts[3]);
		int[] temp = new int[4];// We can not convert signed number to unsigned
								// directly
		temp[0] = b1 & 0xff;
		temp[1] = b2 & 0xff;
		temp[2] = b3 & 0xff;
		temp[3] = b4 & 0xff;

		int32 = temp[0] << 24 | temp[1] << 16 | temp[2] << 8 | temp[3];
		int test = b1 << 24;
		String val = Integer.toString(int32, 16);

	}

	public static int byteToUnsignedInt(byte b) {
		return 0x00 << 24 | b & 0xff;
	}

	public static byte[] int32toBytes(int hex) {
		byte[] b = new byte[4];
		b[0] = (byte) ((hex & 0xFF000000) >> 24);
		b[1] = (byte) ((hex & 0x00FF0000) >> 16);
		b[2] = (byte) ((hex & 0x0000FF00) >> 8);
		b[3] = (byte) (hex & 0x000000FF);
		return b;

	}
    public static String int2str(int ip){
        byte [] bs= int32toBytes(ip);
        StringBuilder sb = new StringBuilder(15);
        int v;
        for(int i=3; i > 0;i--){
             v= byteToUnsignedInt(bs[i]);
            sb.append(v);
            sb.append(".");
        }
        v= byteToUnsignedInt(bs[0]);
        sb.append(v);
        return sb.toString();

    }

	protected final static String byte2str(byte b) {
		return Integer.toString(byteToUnsignedInt(b));
	}

	public static String hex2IPStr(int ip) {
		byte[] b = int32toBytes(ip);
		String str = null;
		str = byte2str(b[0]) + "." + byte2str(b[1]) + "." + byte2str(b[2])
				+ "." + byte2str(b[3]);
		return str;
	}
}
