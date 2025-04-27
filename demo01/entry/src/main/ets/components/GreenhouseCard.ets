import type { Greenhouse } from '../model/Greenhouse';
import type { GreenhouseWithEnv } from '../model/GreenhouseWithEnv';

@Component
export struct GreenhouseCard {
  @Prop greenhouse: GreenhouseWithEnv



  build() {
    Column({ space: 4 }) {
      Text('名称：' + this.greenhouse.greenhouseName)
      Text('位置：' + this.greenhouse.greenhouseLocation)
      Text('类型：' + this.greenhouse.greenhouseType)
      Text('面积：' + this.greenhouse.area.toFixed(2) + '㎡')
      Text('状态：' + this.greenhouse.status)
      Text('备注：' + this.greenhouse.description)
    }

    .padding(12)
    .backgroundColor('#fef7e0')
    .borderRadius(12)
    .margin({ bottom: 8 })
  }
}
