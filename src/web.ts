import { WebPlugin } from '@capacitor/core';

import type { WifiIpPlugin } from './definitions';

export class WifiIpWeb extends WebPlugin implements WifiIpPlugin {
  async getIP(): Promise<{ ip: string | null }> {
    return { ip: null };
  }
}
