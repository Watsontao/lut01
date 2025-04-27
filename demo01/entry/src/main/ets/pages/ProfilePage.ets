import type { User } from '../model/User'
import router from '@ohos.router'
import prompt from '@ohos.prompt'
import call from '@ohos.telephony.call'
import promptAction from '@ohos.promptAction'

@Entry
@Component
export default struct ProfilePage {
  // private user: User = mockUser()
  @State user: User = AppStorage.get('user') as User

  build() {
    Column() {
      Text('个人中心')
        .fontSize(26)
        .fontWeight(FontWeight.Bold)
        .textAlign(TextAlign.Start)
        .margin({ top: 24, bottom: 16 })
        .fontColor($r('app.color.text_primary'))

      Scroll() {
        Column({ space: 20 }) {
          Column() {
            Image($r('app.media.ic_profile'))
              .width(50)
              .height(50)
              .borderRadius(50)
              .aspectRatio(1)
              .objectFit(ImageFit.Contain)

            Text(this.user.userName)
              .fontSize(20)
              .fontWeight(FontWeight.Bold)
              .margin({ top: 8 })
              .fontColor($r('app.color.text_primary'))

            // Text(`用户名：${this.user.userName}`)
            //   .fontSize(14)
            //   .fontColor($r('app.color.text_secondary'))
          }
          .alignItems(HorizontalAlign.Center)

          Column({ space: 6 }) {
            Text(`性别：${this.user.gender}`).fontColor($r('app.color.text_light'))
            Text(`地址：${this.user.address ?? '--'}`).fontColor($r('app.color.text_light'))
            Text(`身份证号：${this.user.idCard ?? '--'}`).fontColor($r('app.color.text_light'))
            Text(`注册时间：${this.user.createDate}`).fontColor($r('app.color.text_light'))
          }
          .alignItems(HorizontalAlign.Start)
          .width('100%')

          MyDivider()

          Column({ space: 12 }) {
            MyEntryItem('修改密码', () => {
              router.pushUrl({ url: 'pages/ChangePasswordPage' })
            }, $r('app.media.ic_updatepwd'))

            MyEntryItem('系统设置', () => {
              router.pushUrl({ url: 'pages/SystemSettingsPage' })
            }, $r('app.media.ic_setting'))

            MyEntryItem('电话反馈', handlePhoneFeedback, $r('app.media.ic_callout'))

            MyEntryItem('退出登录', () => {
              prompt.showToast({ message: '已退出登录' })
              router.clear()
              router.replaceUrl({ url: 'pages/LoginPage' })
            }, $r('app.media.ic_logout'), true)
          }
        }
        .padding({ top: 0, bottom: 40 })
      }.layoutWeight(1)
    }
    .padding({ left: 24, right: 24 })
    .backgroundColor($r('app.color.page_bg'))
    .height('100%')
  }
}

@Builder
function MyEntryItem(title: string, action: () => void, icon: Resource, danger: boolean = false) {
  Row({ space: 12 }) {
    Image(icon)
      .width(24)
      .height(24)
      .aspectRatio(1)
      .objectFit(ImageFit.Contain)

    Text(title)
      .fontSize(16)
      .fontWeight(FontWeight.Medium)
      .fontColor(danger ? '#FF3B30' : $r('app.color.text_primary'))

    Blank()
  }
  .alignItems(VerticalAlign.Top)
  .onClick(action)
  .padding(16)
  .backgroundColor($r('app.color.entry_bg'))
  .borderRadius(10)
  .width('100%')
}

@Builder
function MyDivider() {
  Row()
    .height(1)
    .backgroundColor($r('app.color.divider'))
    .width('100%')
    .margin({ top: 12, bottom: 12 })
}



function handlePhoneFeedback() {
  promptAction.showDialog({
    title: '提示',
    message: '接下来将会启动电话功能，是否继续？',
    buttons: [
      { text: '确定', color: '#007DFF' },
      { text: '取消', color: '#999999' }
    ]
  }).then((result) => {
    if (result.index === 0) {
      try {
        call.makeCall('18369105956')
      } catch (e) {
        prompt.showToast({ message: '无法启动拨号功能' })
      }
    }
  }).catch(() => {
    prompt.showToast({ message: '弹窗显示失败' })
  })
}
