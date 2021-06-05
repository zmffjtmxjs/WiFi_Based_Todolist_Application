package com.cookandroid.wifi_based_todolist.module;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MacAddress {

    /** 어디서든 MacAddress.getMACAddress("wlan0");를 호출하면 연결된 Wifi의 mac주소를 얻을 수 있습니다. 객체 생성할 필요 없습니다.
     *
     * @param interfaceName
     * @return Currently Connected MacAddress of Wifi (String)
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface intf : interfaces) {

                if (interfaceName != null) {

                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;

                }

                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();

                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));

                if (buf.length()>0)
                    buf.deleteCharAt(buf.length()-1);

                return buf.toString();

            }
        } catch (Exception ex) { }

        return "";
    }


}
