import { WebPlugin } from '@capacitor/core';

import type { WifiIpPlugin } from './definitions';

export class WifiIpWeb extends WebPlugin implements WifiIpPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
