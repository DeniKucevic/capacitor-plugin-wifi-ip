import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(WifiIpPlugin)
public class WifiIpPlugin: CAPPlugin {
    private let implementation = WifiIp()

     @objc func getIP(_ call: CAPPluginCall) {
        let address = getWiFiAddress()
        guard let myString = address, !myString.isEmpty else {
            call.reject("ERROR_NO_WIFI_IP_AVAILABLE");
            return
        } 
        call.success([
            "ip": address!
        ])
    }
    
     @objc func getWiFiAddress() -> String? {
        var address : String?

        // Get list of all interfaces on the local machine:
        var ifaddr : UnsafeMutablePointer<ifaddrs>?
        guard getifaddrs(&ifaddr) == 0 else { return nil }
        guard let firstAddr = ifaddr else { return nil }

        // For each interface ...
        for ifptr in sequence(first: firstAddr, next: { $0.pointee.ifa_next }) {
            let interface = ifptr.pointee

            // Check for IPv4:
            let addrFamily = interface.ifa_addr.pointee.sa_family
            // addrFamily == UInt8(AF_INET6)
            if addrFamily == UInt8(AF_INET) {
                // Check interface name:
                let name = String(cString: interface.ifa_name)
                if  name == "en0" || name.starts(with: "tap") || name.starts(with: "ppp") || name.starts(with: "ipsec") || name.starts(with: "utun") {
                    // Convert interface address to a human readable string:
                    var hostname = [CChar](repeating: 0, count: Int(NI_MAXHOST))
                    getnameinfo(interface.ifa_addr, socklen_t(interface.ifa_addr.pointee.sa_len),
                                &hostname, socklen_t(hostname.count),
                                nil, socklen_t(0), NI_NUMERICHOST)
                    address = String(cString: hostname)
                }
            }
        }
        freeifaddrs(ifaddr)

        return address
    }
}
