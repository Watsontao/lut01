import type { EnvironmentData } from '../model/EnvironmentData';

@Component
export struct EnvironmentCard {
  @Prop data: EnvironmentData

  build() {
    Column({ space: 4 }) {
      Text('大棚：' + this.data.greenhouseName)
      Text('记录日期：' + this.data.recordDate)
      Text('温度：' + this.data.temperature.toFixed(1) + '°C')
      Text('湿度：' + this.data.humidity.toFixed(1) + '%')
      Text('土壤湿度：' + this.data.soilMoisture.toFixed(1) + '%')
      Text('光照强度：' + this.data.lightIntensity.toFixed(1) + ' lx')
    }
    .padding(12)
    .backgroundColor('#e8f5e9')
    .borderRadius(12)
    .margin({ bottom: 8 })
  }
}
