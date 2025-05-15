import router from '@ohos.router'
import type { Greenhouse } from '../model/Greenhouse'

import type { EnvironmentData } from '../model/EnvironmentData'
import type { GreenhouseWithEnv } from '../model/GreenhouseWithEnv'
import { getGreenhouseByUser, getEnvironmentHistory } from '../utils/api'   // ✅ 引入我们封装的接口方法
import type { User } from '../model/User'
import prompt from '@system.prompt'
import { BasicConstants } from '../utils/BasicConstants'


@Entry
@Component
export default struct GreenhousePage {
  @State  private greenhouseList: GreenhouseWithEnv[] = []


  aboutToAppear() {
    const user = AppStorage.get('user') as User

    // const token = AppStorage.get<string>('token') ?? ''
    // console.log('当前 token:', token)


    getGreenhouseByUser(user.userId)
      .then(greenhouses => {
        // 并行调用所有大棚的环境历史接口
        return Promise.all(
          greenhouses.map(async gh => {
            // 拉取环境历史并取最新一条
            const history = await getEnvironmentHistory(gh.greenhouseId)
            const latest = history.length > 0
              ? history[history.length - 1]
              : undefined

            return {
              greenhouseId: gh.greenhouseId,
              greenhouseName: gh.greenhouseName,
              userId: gh.userId,
              greenhouseLocation: gh.greenhouseLocation,
              greenhouseType: gh.greenhouseType,
              area: gh.area,
              status: gh.status,
              description: gh.description,
              plants: gh.plants,
              cropMaturity: gh.cropMaturity,
              expectedHarvestDate: gh.expectedHarvestDate,
              growDays: gh.growDays,
              irrigationStatus: gh.irrigationStatus,
              ventilationStatus: gh.ventilationStatus,
              lightingStatus: gh.lightingStatus,
              createDate: gh.createDate,
              supervisor: gh.supervisor,
              imageUrl: `${BasicConstants.BASE_URL}${gh.imageUrl}`,
              videoUrl: `${BasicConstants.BASE_URL}${gh.videoUrl}`,
              environment: latest
            } as GreenhouseWithEnv
          })
        )
      })
      .then(list => {
        this.greenhouseList = list
      })
      .catch((err:Error) => {
        console.error('❌ 加载大棚或环境数据失败', err)
        prompt.showToast({ message: '加载失败，请稍后再试' })
      })
  }






  build() {
    Column() {
      Text('大棚管理')
        .fontSize(24)
        .fontWeight(FontWeight.Bold)
        .margin({ top: 16, bottom: 12 })

      Scroll() {
        Column({ space: 16 }) {
          ForEach(this.greenhouseList, (item: GreenhouseWithEnv) => {

            GreenhouseCard({ greenhouse: item })
          }, (item: GreenhouseWithEnv) => item.greenhouseId.toString())
        }
        // 在这里加上这一行，让子元素水平顶格
        .alignItems(HorizontalAlign.Start)

      }
      .layoutWeight(1)
    }
    .padding(16)
    .backgroundColor('#ffffff')
    .height('100%')
  }
}

// ✅ 单个大棚卡片组件
@Component
struct GreenhouseCard {
  @Prop greenhouse: GreenhouseWithEnv

  build() {


    Column() {
      Row({ space: 12 }) {
        Image(this.greenhouse.imageUrl)
          .width(80)
          .height(80)
          .borderRadius(8)

        Column({ space: 6 }) {
          Text(this.greenhouse.greenhouseName)
            .fontSize(18)
            .fontWeight(FontWeight.Bold)

          Text('植物：' + this.greenhouse.plants).fontSize(14)


          //只显示小数点后一位。
          Text(`温度：${this.greenhouse.environment ? this.greenhouse.environment.temperature.toFixed(1) : '--'} ℃`)
          Text(`湿度：${this.greenhouse.environment ? this.greenhouse.environment.humidity.toFixed(1) : '--'} %`)
          Text(`光照：${this.greenhouse.environment ? this.greenhouse.environment.lightIntensity.toFixed(1) : '--'} lux`)



        }
        .layoutWeight(1)
        .alignItems(HorizontalAlign.Start)
      }

      Text('位置：' + this.greenhouse.greenhouseLocation)
        .fontSize(14)
        .margin({ top: 8 })
        .fontColor('#666')

      Text('状态：' + this.greenhouse.status)
        .fontColor(this.greenhouse.status === '正常' ? Color.Green : Color.Red)
        .margin({ top: 4 })
    }
    .padding(12)
    .width('100%')
    .backgroundColor('#f5f5f5')
    .borderRadius(12)
    .onClick(() => {
      router.pushUrl({
        url: 'pages/GreenhouseDetailPage',
        params: { greenhouse: JSON.stringify(this.greenhouse) }
      })

    })
  }
}



// function getMockGreenhouses(): Greenhouse[] {
//   return [
//     {
//       greenhouseId: 1,
//       greenhouseName: '一号大棚',
//       userId: 101,
//       greenhouseLocation: '兰州农场A区',
//       greenhouseType: '温室',
//       area: 120,
//       status: '正常',
//       description: '主要种植西红柿',
//       plants: '西红柿',
//       imageUrl: $r('app.media.ic_greenhouse'),
//       videoUrl: 'https://example.com/video/gh1.mp4',
//       cropMaturity: '抽薹期',
//       expectedHarvestDate: '2025-06-28',
//       growDays: 38,
//       irrigationStatus: false,
//       ventilationStatus: true,
//       lightingStatus: true,
//       createDate: '2025-04-01',
//       supervisor: '张三'
//     },
//     {
//       greenhouseId: 2,
//       greenhouseName: '二号大棚',
//       userid: 101,
//       greenhouseLocation: '兰州农场B区',
//       greenhouseType: '阳光房',
//       area: 90,
//       status: '警告',
//       description: '湿度偏高',
//       plants: '黄瓜',
//       imageUrl: $r('app.media.ic_greenhouse'),
//       videoUrl: 'https://example.com/video/gh2.mp4',
//       cropMaturity: '结果期',
//       expectedHarvestDate: '2025-07-02',
//       growDays: 42,
//       irrigationStatus: true,
//       ventilationStatus: false,
//       lightingStatus: true,
//       createDate: '2025-04-02',
//       supervisor: '李四'
//     },
//     {
//       greenhouseId: 3,
//       greenhouseName: '三号大棚',
//       userid: 101,
//       greenhouseLocation: '兰州农场C区',
//       greenhouseType: '冷棚',
//       area: 150,
//       status: '正常',
//       description: '苗圃备用',
//       plants: '草莓',
//       imageUrl: $r('app.media.ic_greenhouse'),
//       videoUrl: 'https://example.com/video/gh3.mp4',
//       cropMaturity: '成熟期',
//       expectedHarvestDate: '2025-06-25',
//       growDays: 50,
//       irrigationStatus: false,
//       ventilationStatus: true,
//       lightingStatus: false,
//       createDate: '2025-04-03',
//       supervisor: '王五'
//     }
//   ]
// }

function getMockEnvironmentData(): EnvironmentData[] {
  return [
    {
      envid: 1,
      greenhouseId: 1,  // ✅ 正确字段
      recordDate: '2025-04-28',
      temperature: 26,
      humidity: 55,
      soilMoisture: 40,
      lightIntensity: 15000
    },
    {
      envid: 2,
      greenhouseId: 2,
      recordDate: '2025-04-28',
      temperature: 30,
      humidity: 70,
      soilMoisture: 50,
      lightIntensity: 17000
    },
    {
      envid: 3,
      greenhouseId: 3,
      recordDate: '2025-04-28',
      temperature: 22,
      humidity: 50,
      soilMoisture: 45,
      lightIntensity: 14000
    }
  ]
}

