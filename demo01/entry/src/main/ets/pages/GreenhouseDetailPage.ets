import router from '@ohos.router'
import prompt from '@ohos.prompt'
import type { GreenhouseWithEnv } from '../model/GreenhouseWithEnv'
import { toggleGreenhouseStatus } from '../utils/api'   // ✅ 引入我们封装的接口方法

@Entry
@Component
export default struct GreenhouseDetailPage {
  @State greenhouse: GreenhouseWithEnv = {} as GreenhouseWithEnv;

  aboutToAppear() {
    const params = router.getParams() as Record<string, string>;
    if (!params.greenhouse) {
      console.error('❌ 缺少 greenhouse 参数');
      router.back();
      return;
    }
    try {
      this.greenhouse = JSON.parse(params.greenhouse) as GreenhouseWithEnv;
    } catch (e) {
      console.error('❌ greenhouse 参数解析失败', e);
      router.back();
    }
  }


  build() {
    Column() {
      Text(this.greenhouse.greenhouseName)
        .fontSize(26)
        .fontWeight(FontWeight.Bold)
        .textAlign(TextAlign.Center)
        .margin({ top: 24, bottom: 16 })

      Video({ src: this.greenhouse.videoUrl })
        .width('100%')
        .height(220)
        .borderRadius(12)
        .margin({ bottom: 20 })

      Row({ space: 16 }) {
        Column() {
          Image(this.greenhouse.imageUrl)
            .width(120)
            .height(120)
            .borderRadius(8)
            .aspectRatio(1)
            .objectFit(ImageFit.Contain)
        }.layoutWeight(1)

        Column({ space: 10 }) {
          Text(`作物：${this.greenhouse.plants}`).fontSize(16)

          if (this.greenhouse.environment) {
            Text(`空气温度：${this.greenhouse.environment.temperature.toFixed(1)} ℃`).fontSize(16)
            Text(`空气湿度：${this.greenhouse.environment.humidity.toFixed(1)} %`).fontSize(16)
            Text(`土壤湿度：${this.greenhouse.environment.soilMoisture.toFixed(1)} %`).fontSize(16)
            Text(`光照：${this.greenhouse.environment.lightIntensity.toFixed(1)} Lux`).fontSize(16)
          } else {
            Text(`暂无环境数据`).fontSize(14).fontColor('#999')
          }


          Text(`成熟阶段：${this.greenhouse.cropMaturity}`).fontSize(16)
          Text(`采收日期：${this.greenhouse.expectedHarvestDate}`).fontSize(16)
          Text(`生长天数：${this.greenhouse.growDays} 天`).fontSize(16)
        }.layoutWeight(2)
      }
      .margin({ bottom: 20 })

      MyDivider()

      Text('远程控制')
        .fontSize(18)
        .fontWeight(FontWeight.Bold)
        .margin({ bottom: 8 })

      Column({ space: 12 }) {

        ControlButton('灌溉', () => this.greenhouse.irrigationStatus, () => {
          toggleGreenhouseStatus(this.greenhouse.greenhouseId, 'irrigation')
            .then(() => {
              this.greenhouse.irrigationStatus = !this.greenhouse.irrigationStatus
              prompt.showToast({ message: this.greenhouse.irrigationStatus ? '✅ 已开启灌溉' : '✅ 已关闭灌溉' })
            })
            .catch((err:Error) => {
              console.error('❌ 灌溉操作失败', JSON.stringify(err))
              prompt.showToast({ message: '操作失败，请稍后重试' })
            })
        })

        ControlButton('通风', () => this.greenhouse.ventilationStatus, () => {
          toggleGreenhouseStatus(this.greenhouse.greenhouseId, 'ventilation')
            .then(() => {
              this.greenhouse.ventilationStatus = !this.greenhouse.ventilationStatus
              prompt.showToast({ message: this.greenhouse.ventilationStatus ? '✅ 已开启通风' : '✅ 已关闭通风' })
            })
            .catch((err:Error) => {
              console.error('❌ 通风操作失败', JSON.stringify(err))
              prompt.showToast({ message: '操作失败，请稍后重试' })
            })
        })

        ControlButton('灯光', () => this.greenhouse.lightingStatus, () => {
          toggleGreenhouseStatus(this.greenhouse.greenhouseId, 'lighting')
            .then(() => {
              this.greenhouse.lightingStatus = !this.greenhouse.lightingStatus
              prompt.showToast({ message: this.greenhouse.lightingStatus ? '✅ 已开启灯光' : '✅ 已关闭灯光' })
            })
            .catch((err:Error) => {
              console.error('❌ 灯光操作失败', JSON.stringify(err))
              prompt.showToast({ message: '操作失败，请稍后重试' })
            })
        })
      }
      .margin({ top: 8, bottom: 24 })

      MyDivider()

      Column({ space: 6 }) {
        Text(`负责人：${this.greenhouse.supervisor}`)
          .fontSize(14)
          .fontColor('#888888')

        Text(`创建时间：${this.greenhouse.createDate}`)
          .fontSize(14)
          .fontColor('#888888')
      }
    }
    .padding(20)
    .backgroundColor('#ffffff')
    .height('100%')
  }
}

@Builder
function MyDivider() {
  Row().height(1).width('100%').backgroundColor('#E0E0E0').margin({ top: 24, bottom: 16 })
}

@Builder
function ControlButton(label: string, getStatus: () => boolean, action: () => void) {
  Row({ space: 12 }) {
    Text(label)
      .fontSize(16)
      .fontColor(getStatus() ? '#4CAF50' : '#333')

    Blank()

    Text(getStatus() ? '关闭' : '开启')
      .fontColor('#ffffff')
      .backgroundColor(getStatus() ? '#FF7043' : '#4CAF50')
      .padding({ left: 12, right: 12, top: 6, bottom: 6 })
      .borderRadius(6)
      .onClick(action)
  }
  .padding(12)
  .backgroundColor('#f7f7f7')
  .borderRadius(10)
  .width('100%')
}


