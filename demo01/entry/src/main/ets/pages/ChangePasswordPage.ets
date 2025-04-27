import router from '@ohos.router'
import prompt from '@ohos.prompt'
import { changePassword } from '../utils/api'   // ✨ 新接口


@Entry
@Component
export default struct ChangePasswordPage {
  @State oldPwd: string = ''
  @State newPwd: string = ''
  @State confirmPwd: string = ''
  @State errorMsg: string = ''
  @State updating: boolean = false

  async handleSubmit() {
    this.errorMsg = ''
    if (!this.oldPwd || !this.newPwd || !this.confirmPwd) {
      this.errorMsg = '请填写完整'
      return
    }
    if (this.newPwd !== this.confirmPwd) {
      this.errorMsg = '两次输入的新密码不一致'
      return
    }
    this.updating = true
    try {
      await changePassword(this.oldPwd, this.newPwd)
      prompt.showToast({ message: '密码修改成功' })
      router.back()
    } catch (e) {
      this.errorMsg = (e as Error).message || '修改失败'
    } finally {
      this.updating = false
    }
    // // ✅ 模拟旧密码校验（真实场景中应与后端交互）
    // if (this.oldPwd !== '123456') {
    //   this.errorMsg = '原密码错误'
    //   return
    // }
    //
    // // ✅ 修改成功模拟
    // prompt.showToast({ message: '密码修改成功' })
    // router.back()
  }

  build() {
    Column({ space: 16 }) {
      Text('修改密码')
        .fontSize(24)
        .fontWeight(FontWeight.Bold)
        .margin({ top: 32 })

      TextInput({ placeholder: '请输入原密码' })
        .type(InputType.Password)
        .onChange(v => this.oldPwd = v)

      TextInput({ placeholder: '请输入新密码' })
        .type(InputType.Password)
        .onChange(v => this.newPwd = v)

      TextInput({ placeholder: '确认新密码' })
        .type(InputType.Password)
        .onChange(v => this.confirmPwd = v)

      Button('提交修改')
        .margin({ top: 16 })
        .onClick(() => this.handleSubmit())

      if (this.errorMsg) {
        Text(this.errorMsg)
          .fontColor(Color.Red)
      }
    }
    .padding(24)
    .align(Alignment.Center)
  }
}
