export interface WifiIpPlugin {
  getIP(): Promise<{ ip: string | null }>;
}
