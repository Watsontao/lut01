import type { Crop } from '../model/Crop';

@Component
export struct CropCard {
  @Prop crop: Crop

  build() {
    Column({ space: 4 }) {
      Text('作物名称：' + this.crop.cropName).fontSize(18)
      Text('大棚：' + this.crop.greenhouseName).fontSize(14).fontColor('#888')
      Text('种植日期：' + this.crop.plantingDate)
      Text('生长周期：' + this.crop.growthCycle)
      Text('预计收获：' + this.crop.estimatedHarvestDate)
      Text('状态：' + (this.crop.status || '未知'))
    }
    .padding(12)
    .backgroundColor('#f7f7f7')
    .borderRadius(12)
    .margin({ bottom: 8 })
  }
}
