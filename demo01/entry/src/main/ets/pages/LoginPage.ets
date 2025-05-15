import router from '@ohos.router'
// import { loginMock } from '../utils/api'                   //登录API接口
import { loginApi } from '../utils/api'                   //登录API接口
import type { User } from '../model/User'
import prompt from '@ohos.prompt'

@Entry
@Component
export default struct LoginPage {
  @State phone: string = ''
  @State password: string = ''
  @State verifyCode: string = ''
  @State errorMsg: string = ''

  private sentCode: string = '1234' // ✅ 模拟验证码
  @State countdown: number = 0 // 倒计时状态
  private timer: number = -1

  async onLoginClick() {
    if (!this.phone || !this.password || !this.verifyCode) {
      this.errorMsg = '请完整填写信息'
      return
    }

    if (this.verifyCode !== this.sentCode) {
      this.errorMsg = '验证码错误'
      return
    }

    try {
      const result = await loginApi(this.phone, this.password);

      if (result.code === '200' && result.data?.token) {

        AppStorage.setOrCreate('token', result.data.token)
        globalThis.token = result.data.token; // ✅ 全局变量兜底

        AppStorage.setOrCreate('isLoggedIn', true);
        AppStorage.setOrCreate('token', result.data.token);
        AppStorage.setOrCreate('user', result.data.user);

        console.log('登录用户：', JSON.stringify(result.data.user));

        prompt.showToast({ message: '登录成功' });
        router.replaceUrl({ url: 'pages/MainPage' });
      } else {
        prompt.showToast({ message: '登录失败：' + result.msg });
        this.errorMsg = result.msg;
      }
    } catch (err) {
      prompt.showToast({ message: '请求异常：' + err });
      this.errorMsg = '登录请求失败';
    }
  }





  sendVerifyCode() {
    if (!this.phone) {
      prompt.showToast({ message: '请先输入手机号' })
      return
    }

    prompt.showToast({ message: `验证码已发送：${this.sentCode}` })
    this.countdown = 60
    this.startCountdown()
  }

  startCountdown() {
    this.timer = setInterval(() => {
      this.countdown--
      if (this.countdown <= 0) {
        clearInterval(this.timer)
        this.timer = -1
      }
    }, 1000)
  }

  aboutToDisappear() {
    if (this.timer !== -1) {
      clearInterval(this.timer)
    }
  }

  build() {
    Column({ space: 16 }) {
      Text('智能农业管理系统')
        .fontSize(24)
        .margin({ top: 40 })

      TextInput({ placeholder: '请输入手机号' })
        .onChange(v => this.phone = v)

      TextInput({ placeholder: '请输入密码' })
        .type(InputType.Password)
        .onChange(v => this.password = v)

      // 验证码行：输入 + 倒计时按钮
      Row({ space: 12 }) {
        TextInput({ placeholder: '验证码', text: this.verifyCode })
          .onChange(v => this.verifyCode = v)
          .width('60%')

        if (this.countdown > 0) {
          Button(`重新获取(${this.countdown}s)`)
            .type(ButtonType.Capsule)
            .backgroundColor('#cccccc')
            .onClick(() => {}) // 冷却中不操作
        } else {
          Button('获取验证码')
            .type(ButtonType.Capsule)
            .backgroundColor('#1E88E5')
            .fontColor(Color.White)
            .onClick(() => this.sendVerifyCode())
        }
      }

      Button('登录')
        .margin({ top: 20 })
        .onClick(() => this.onLoginClick())

      if (this.errorMsg) {
        Text(this.errorMsg)
          .fontColor(Color.Red)
      }
    }
    .padding(24)
    .align(Alignment.Center)
  }
}
