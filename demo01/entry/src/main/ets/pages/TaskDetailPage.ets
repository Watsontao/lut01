import router from '@ohos.router'
import prompt from '@ohos.prompt'
import type { Task } from '../model/Task'
import { finishTask } from '../utils/api'   // ✨ 新接口




@Entry
@Component
export default struct TaskDetailPage {
  @State task: Task = {} as Task        // 一定会赋值
  @State updating: boolean = false

  aboutToAppear() {
    const params = router.getParams() as Record<string, string> | undefined
    const str = params?.task

    if (!str) { prompt.showToast({ message: '参数错误' }); router.back(); return }
    this.task = JSON.parse(str)         // 反序列化
  }



  build() {
    Column({ space: 24 }) {
      // 任务标题
      Text(this.task.name)
        .fontSize(24)
        .fontWeight(FontWeight.Bold)
        .textAlign(TextAlign.Start)

      // 内容正文
      Text(this.task.taskContent)
        .fontSize(16)
        .lineHeight(24)
        .textAlign(TextAlign.Start)

      // 描述说明
      Text(this.task.taskDescription ?? '--')
        .fontSize(16)
        .fontColor('#666666')
        .lineHeight(24)
        .textAlign(TextAlign.Start)

      // 时间信息
      Column({ space: 8 }) {
        Text(`发布时间：${this.task.publishTime}`)
          .fontSize(14)
          .fontColor('#999')

        Text(`截止时间：${this.task.deadline}`)
          .fontSize(14)
          .fontColor('#999')

        if (this.task.completed === '是' && this.task.completeTime) {
          Text(`完成时间：${this.task.completeTime}`)
            .fontSize(14)
            .fontColor('#999')
        }
      }

      // 确认完成按钮
      if (this.task.completed === '否') {
        Button('确认完成')
          .type(ButtonType.Capsule)
          .fontColor('#ffffff')
          .backgroundColor('#FF3B30')
          .fontSize(16)
          .padding({ top: 10, bottom: 10, left: 20, right: 20 })
          .margin({ top: 20 })
          .onClick(async () => {
            this.updating = true
            try {
              await finishTask(this.task.taskId)
              this.task.completed   = '是'
              this.task.completeTime = new Date().toISOString().slice(0,10)
              prompt.showToast({ message: '已完成' })
            } catch (e) {
              console.error(e)
              prompt.showToast({ message: '提交失败' })
            } finally { this.updating = false }
          })
      }
    }
    .padding({ left: 20, right: 20, top: 24, bottom: 40 })
    .width('100%')
    .height('100%')
    .backgroundColor('#ffffff')
  }
}
