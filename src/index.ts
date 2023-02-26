import { registerPlugin } from '@capacitor/core';

import type { WifiIpPlugin } from './definitions';

const WifiIp = registerPlugin<WifiIpPlugin>('WifiIp', {
  web: () => import('./web').then(m => new m.WifiIpWeb()),
});

export * from './definitions';
export { WifiIp };
