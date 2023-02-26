export interface WifiIpPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
