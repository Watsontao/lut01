// common/environment.ts
export enum Env {
  DEV,  // 0
  LAN,  // 1
  PROD, // 2
}

const CUR_ENV: Env = Env.DEV;   // 发布前改成 Env.PROD

const URLS: string[] = [
  'http://10.0.2.2:9090',       // DEV
  'http://192.168.9.56:9090',   // LAN
  'https://api.myfarm.cn',      // PROD
];

// ArkTS 允许数组下标
export const BASE_URL: string = URLS[CUR_ENV];
