package com.brixi.plugins.wifiip;

import android.Manifest;
import android.os.Build;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.PermissionState;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(
    name = "WifiIp",
    permissions = { @Permission(alias = "fineLocation", strings = { Manifest.permission.ACCESS_FINE_LOCATION }) }
)
public class WifiIpPlugin extends Plugin {

    private static final int API_VERSION = Build.VERSION.SDK_INT;

    WifiIp wifiIp;

    @Override
    public void load() {
        super.load();
        this.wifiIp = new WifiIp();
        this.wifiIp.load(this.bridge);
    }

    @PluginMethod
    public void getIP(PluginCall call) {
        if (API_VERSION >= 23 && getPermissionState("fineLocation") != PermissionState.GRANTED) {
            requestPermissionForAlias("fineLocation", call, "accessFineLocation");
        } else {
            this.wifiIp.getIP(call);
        }
    }

    @PermissionCallback
    private void accessFineLocation(PluginCall call) {
        if (getPermissionState("fineLocation") == PermissionState.GRANTED) {
            if (call.getMethodName().equals("getIP")) {
                this.wifiIp.getIP(call);
            }
        } else {
            call.reject("User denied permission");
        }
    }
}
