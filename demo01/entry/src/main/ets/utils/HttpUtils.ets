import { http } from '@kit.NetworkKit';

import json from '@ohos.util.json';

import { BasicConstants } from "./BasicConstants"



export function myRequest<T, K>(
  url: string,
  method: http.RequestMethod,
  requestData: K
): Promise<T> {
  const httpRequest = http.createHttp()

  return new Promise((resolve, reject) => {



    const token = AppStorage.get<string>('token') ?? ''

    const headers: Record<string, string> = {

      'content-type': 'application/json',
    }

    if (token) {
      headers['token'] = `Bearer ${token}`; // ✅ 注意大小写
    }


    console.log(`🚀 请求 Headers: ${JSON.stringify(headers)}`);
    console.log(`🚀 发送的token: ${JSON.stringify(token)}`);


    httpRequest.request(BasicConstants.BASE_URL + url, {

      method: method,

      header: headers,

      extraData: JSON.stringify(requestData) || '',

      connectTimeout: 9000,

      readTimeout: 9000

    }, (error: Error, data: http.HttpResponse) => {

      if (!error) {

        console.log(`✅ 成功的结果：${JSON.stringify(data)}`)


        resolve(JSON.parse(data.result as string))

        httpRequest.destroy()

      } else {
        console.error(`❌ 请求失败，具体原因：${JSON.stringify(error)}`)
        reject(JSON.stringify(error))
        httpRequest.off('headerReceive')
        httpRequest.destroy()
      }
    })
  })
}
