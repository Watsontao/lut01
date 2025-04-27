import { ConfigurationConstant } from '@kit.AbilityKit'
import router from '@ohos.router'

@Entry
@Component
export default struct SystemSettingsPage {
  @State isDarkMode: boolean = false

  build() {
    Column({ space: 24 }) {
      // 顶部标题
      Text('系统设置')
        .fontSize(24)
        .fontWeight(FontWeight.Bold)
        .margin({ top: 32 })

      // 深色模式开关项
      Row({ space: 12 }) {
        Text('深色模式')
          .fontSize(16)
          .fontColor('#333333')
          .layoutWeight(1)

        Toggle({ type: ToggleType.Switch, isOn: this.isDarkMode })
          .onChange(val => {
            this.isDarkMode = val
            let context = getContext(this) // ✅ 正确获取 context
            const mode = val
              ? ConfigurationConstant.ColorMode.COLOR_MODE_DARK
              : ConfigurationConstant.ColorMode.COLOR_MODE_LIGHT
            context.getApplicationContext().setColorMode(mode)
          })


      }
      .padding(12)
      .backgroundColor('#f5f5f5')
      .borderRadius(10)
      .width('100%')
    }
    .padding(24)
    .backgroundColor('#ffffff')
    .width('100%')
    .height('100%')
  }
}
