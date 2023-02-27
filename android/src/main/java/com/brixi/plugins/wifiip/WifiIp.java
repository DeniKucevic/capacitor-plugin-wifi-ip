package com.brixi.plugins.wifiip;

import android.Manifest;
import android.os.Build;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(
    name = "WifiIp",
    permissions = { @Permission(alias = "fineLocation", strings = { Manifest.permission.ACCESS_FINE_LOCATION }) }
)
public class WifiIp extends Plugin {

    private static final int API_VERSION = Build.VERSION.SDK_INT;

    WifiIpPlugin wifiPlugin;

    @PluginMethod
    public void getIP(PluginCall call) {
        if (API_VERSION >= 23 && getPermissionState("fineLocation") != PermissionState.GRANTED) {
            requestPermissionForAlias("fineLocation", call, "accessFineLocation");
        } else {
            this.wifiPlugin.getIP(call);
        }
    }

    @PermissionCallback
    private void accessFineLocation(PluginCall call) {
        if (getPermissionState("fineLocation") == PermissionState.GRANTED) {
            if (call.getMethodName().equals("getIP")) {
                this.wifiPlugin.getIP(call);
            }
        } else {
            call.reject("User denied permission");
        }
    }
}
