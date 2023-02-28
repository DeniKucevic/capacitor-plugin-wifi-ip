package com.brixi.plugins.wifiip;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.ConnectivityManager;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;


import java.util.Locale;

public class WifiIp {

    WifiManager wifiManager;
    ConnectivityManager connectivityManager;
    Context context;

    Bridge bridge;

    public void load(Bridge bridge) {
        this.bridge = bridge;
        this.wifiManager = (WifiManager) this.bridge.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.connectivityManager =
            (ConnectivityManager) this.bridge.getActivity()
                .getApplicationContext()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        this.context = this.bridge.getContext();
    }

    public void getIP(PluginCall call) {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipString = formatIP(ip);

        if (ipString != null && !ipString.equals("0.0.0.0")) {
            JSObject result = new JSObject();
            result.put("ip", ipString);
            call.resolve(result);
        } else {
            call.reject("NO_VALID_IP_IDENTIFIED");
        }
    }

    private String formatIP(int ip) {
        return String.format(Locale.ENGLISH, "%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
    }
}
