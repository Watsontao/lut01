import type { Crop } from '../model/Crop'
import router from '@ohos.router'
import { getCropById } from '../utils/api';

@Entry
@Component
export default struct CropDetailPage {
  @State crop: Crop = {} as Crop;

  aboutToAppear() {
    const params = router.getParams() as Record<string, string> || {};
    const cropStr = params.crop;
    if (!cropStr) {
      console.error('❌ 缺少 crop 参数');
      router.back();
      return;
    }

    try {
      this.crop = JSON.parse(cropStr) as Crop;
    } catch (e) {
      console.error('❌ crop 解析失败', e);
      router.back();
    }
  }


  build() {
    Column({ space: 24 }) {
      // 作物名称
      Text(this.crop.cropName)
        .fontSize(26)
        .fontWeight(FontWeight.Bold)
        .textAlign(TextAlign.Start)

      // 作物介绍内容
      Text(this.crop.cropContent)
        .fontSize(16)
        .fontColor('#333333')
        .textAlign(TextAlign.Start)
        .lineHeight(24)

      // 时间相关信息
      Column({ space: 8 }) {
        Text(`种植时间：${this.crop.plantingDate}`)
          .fontSize(14)
          .fontColor('#888')

        Text(`生长周期：${this.crop.growthCycle}`)
          .fontSize(14)
          .fontColor('#888')


        Text(`所属大棚：${this.crop.greenhouseName}`)
          .fontSize(14)
          .fontColor('#888')
      }
      .alignItems(HorizontalAlign.Start)
    }
    .padding({ top: 24, left: 20, right: 20, bottom: 40 })
    .width('100%')
    .height('100%')
    .backgroundColor('#ffffff')
    .alignItems(HorizontalAlign.Start)
  }
}
