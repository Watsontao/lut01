import router from '@ohos.router'

@Entry
@Component
export struct HomePage {
  navigateTo(page: string) {
    router.pushUrl({ url: page });
  }

  build() {
    Column() {
      Column() {
        Text('云端惠农APP')
          .fontSize(24)
          .fontWeight(FontWeight.Bold)
          .fontColor(Color.White)

        Text('助力智慧农业发展')
          .fontSize(14)
          .margin({ top: 4 })
          .fontColor(Color.White)
      }
      .width('100%')
      .height(160)
      .backgroundColor($r('app.color.header_bg'))
      .padding(16)
      .alignItems(HorizontalAlign.Center)
      .justifyContent(FlexAlign.Center)

      Scroll() {
        Grid() {
          this.buildCard('大棚管理', $r('app.media.ic_greenhouse'), 'pages/GreenhousePage')
          this.buildCard('任务管理', $r('app.media.ic_clock'), 'pages/TaskPage')
          this.buildCard('作物信息', $r('app.media.ic_crop'), 'pages/CropInfoPage')
          this.buildCard('环境预测', $r('app.media.ic_environment'), 'pages/EnvironmentPage')
        }
        .columnsTemplate('1fr 1fr')
        .rowsGap(16)
        .columnsGap(16)
        .padding(16)
      }
      .layoutWeight(1)
      .backgroundColor($r('app.color.page_bg'))
    }
    .width('100%')
    .height('100%')
  }

  @Builder
  buildCard(title: string, icon: Resource, route: string) {
    GridItem() {
      Column() {
        Image(icon)
          .width(40)
          .height(40)
          .objectFit(ImageFit.Contain)

        Text(title)
          .fontSize(14)
          .margin({ top: 8 })
          .fontColor($r('app.color.text_primary'))
      }
      .width(160)
      .height(160)
      .backgroundColor($r('app.color.card_bg'))
      .borderRadius(8)
      .alignItems(HorizontalAlign.Center)
      .justifyContent(FlexAlign.Center)
      .onClick(() => this.navigateTo(route))
    }
  }
}

export default HomePage
