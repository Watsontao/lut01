import { Crop } from '../model/Crop'
import router from '@ohos.router'
import { getAllCrops } from '../utils/api';
import { BasicConstants } from "../utils/BasicConstants"


@Entry
@Component
export default struct CropInfoPage {

  @State cropList: Crop[] = [];

//   aboutToAppear() {
//     getAllCrops()
//       .then((list) => {
//         this.cropList = list;
//         AppStorage.set('cropList', list);
//
//       })
//       .catch((e: Error) => {
//         console.error('❌ 作物列表加载失败', e);
//         // prompt.showToast({ message: '加载作物失败，请稍后重试' });
//       });
//
// }


  // 页面即将出现时拉取并处理数据
  aboutToAppear() {
    getAllCrops()
      .then((list: Crop[]) => {
        const processed: Crop[] = list.map((c: Crop) => {
          // 1. 原始后端返回的相对路径
          let path = c.imageUrl ?? ''
          // 2. 保证以 “/” 开头
          if (!path.startsWith('/')) {
            path = '/' + path
          }
          // 3. 返回一个新的 Crop 对象，完整拼接 URL，并保留其它字段
          return {
            cropId:         c.cropId,
            greenhouseName: c.greenhouseName,
            cropName:       c.cropName,
            plantingDate:   c.plantingDate,
            growthCycle:    c.growthCycle,
            imageUrl:       BasicConstants.BASE_URL + path,
            cropContent:    c.cropContent
          } as Crop
        })
        // 更新状态，触发页面重绘
        this.cropList = processed
        AppStorage.setOrCreate('cropList', processed)
      })
      .catch((e: Error) => {
        console.error('❌ 作物列表加载失败', e)
        // prompt.showToast({ message: '加载作物失败，请稍后重试' })
      })
  }



  build() {
    Column() {
      Text('作物信息')
        .fontSize(24)
        .fontWeight(FontWeight.Bold)
        .margin({ top: 20, bottom: 16 })
        .textAlign(TextAlign.Center)

      Scroll() {
        Column({ space: 16 }) {
          ForEach(this.cropList, (item: Crop) => {
            CropCard(item)
          }, (item: Crop) => item.cropId.toString())
        }
      }
    }
    .padding(16)
    .backgroundColor('#ffffff')
    .height('100%')
  }
}

// ✅ 单个 Crop 卡片组件（无状态字段）
@Builder
function CropCard(crop: Crop) {
  Column() {
    Row({ space: 12 }) {
      // 左侧：图片区域
      Column() {
        Image(crop.imageUrl)
          .width(50)
          .height(50)
          .borderRadius(8)
          .objectFit(ImageFit.Contain)
      }
      .layoutWeight(1)

      // 右侧：文字信息
      Column({ space: 4 }) {
        Text(crop.cropName)
          .fontSize(18)
          .fontWeight(FontWeight.Bold)

        Text(`所属大棚：${crop.greenhouseName}`)
          .fontSize(14)
          .fontColor('#666')
      }
      .layoutWeight(2)
      .alignItems(HorizontalAlign.Start)

      Blank()
    }
    .onClick(() => {
      router.pushUrl({
        url: 'pages/CropDetailPage',
        params: { crop: JSON.stringify(crop) } // 👈 直接传 JSON 字符串
      });
    })
  }
  .padding(12)
  .backgroundColor('#f5f5f5')
  .borderRadius(10)
  .width('100%')
}

