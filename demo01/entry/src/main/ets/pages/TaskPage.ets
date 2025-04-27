import router from '@ohos.router'
import prompt from '@ohos.prompt'
import type { User } from '../model/User'
import type { Task } from '../model/Task'
import { getTaskByUser } from '../utils/api'   // ✅ 引入我们封装的接口方法
import { finishTask } from '../utils/api'   // ✨ 新接口

@Entry
@Component
export default struct TaskPage {
  @State currentIndex: number = 0
  @State allTasks: Task[] = []
  @State pendingTasks: Task[] = []
  @State completedTasks: Task[] = []


  //onPageShow

  aboutToAppear() {

    this.refresh()          // 首次

    // 1️⃣ 拿到当前登录用户
    const user: User = AppStorage.get('user') as User
    if (!user) {
      prompt.showToast({ message: '尚未登录' })
      return
    }
  }


  onPageShow() {                // 每次从后台回到前台
    this.refresh()
  }

  refresh() {
    const user = AppStorage.get<User>('user')!
    getTaskByUser(user.userId)
      .then(tasks => { this.allTasks = tasks; this.updateTaskLists() })
  }


  updateTaskLists() {
    this.pendingTasks = this.allTasks.filter(t => t.completed === '否')
    this.completedTasks = this.allTasks.filter(t => t.completed === '是')
  }

  build() {
    Column() {
      // 顶部 TabBar
      Row({ space: 24 }) {
        Text('未完成')
          .fontSize(18)
          .fontWeight(FontWeight.Bold)
          .fontColor(this.currentIndex === 0 ? '#1E88E5' : '#999999')
          .onClick(() => this.currentIndex = 0)

        Text('已完成')
          .fontSize(18)
          .fontWeight(FontWeight.Bold)
          .fontColor(this.currentIndex === 1 ? '#1E88E5' : '#999999')
          .onClick(() => this.currentIndex = 1)
      }
      .margin({ top: 20, bottom: 16 })
      .width('100%')
      .alignItems(VerticalAlign.Center)
      .justifyContent(FlexAlign.Center)

      // 内容区域
      if (this.currentIndex === 0) {
        // 未完成任务
        Column({ space: 12 }) {
          ForEach(this.pendingTasks, (item: Task): void => {
            TaskCard(item, (): void => this.confirmComplete(item))
          }, (item: Task): string => item.taskId.toString())
        }
      } else {
        // 已完成任务
        Column({ space: 12 }) {
          ForEach(this.completedTasks, (item: Task): void => {
            TaskCard(item, () => {})
          }, (item: Task): string => item.taskId.toString())
        }
      }
    }
    .padding(16)
    .backgroundColor('#ffffff')
    .height('100%')
  }

  confirmComplete(task: Task) {
    finishTask(task.taskId).then(() => {
      task.completed    = '是'
      task.completeTime = new Date().toISOString().slice(0, 10)
      this.updateTaskLists()
      prompt.showToast({ message: '任务已标记为完成' })
    })
  }
}

// ✅ 单个任务卡片
@Builder
function TaskCard(task: Task, onConfirm: () => void) {
  Column() {
    Row({ space: 12 }) {
      Column({ space: 6 }) {
        Text(`任务名称：${task.name}`).fontSize(16).fontWeight(FontWeight.Bold)
        Text(`发布时间：${task.publishTime}`).fontSize(14).fontColor('#666')
        Text(`截止时间：${task.deadline}`).fontSize(14).fontColor('#666')
        Text(`状态：${task.completed}`)
          .fontSize(14)
          .fontColor(task.completed === '是' ? '#4CAF50' : '#FF9800')

        if (task.completed === '是' && task.completeTime) {
          Text(`完成时间：${task.completeTime}`).fontSize(14).fontColor('#888')
        }
      }
      .alignItems(HorizontalAlign.Start)

      Blank()

      if (task.completed === '否') {
        Button('确认完成')
          .fontColor('#ffffff')
          .backgroundColor('#FF3B30')
          .padding({ left: 10, right: 10, top: 4, bottom: 4 })
          .borderRadius(6)
          .onClick(onConfirm)
      }
    }
    .onClick(() => {
      router.pushUrl({ url: 'pages/TaskDetailPage',  params: { task: JSON.stringify(task) } })
    })
  }
  .padding(16)
  .margin({ bottom: 12 })
  .backgroundColor(task.completed === '是' ? '#f0f0f0' : '#ffffff')
  .borderRadius(10)
  .width('100%')
  .shadow({ radius: 2, color: '#ccc', offsetX: 0, offsetY: 2 })
}



// export function getMockTasks(): Task[] {
//   return [
//     {
//       taskId: 1,
//       name: '灌溉番茄',
//       content: '为大棚一号的番茄进行灌溉',
//       completed: '未完成',
//       assignedUserId: 101,
//       completeTime: '',
//       greenhouseName: '一号大棚',
//       publishDate: '2025-04-10',
//       deadline: '2025-04-15',
//       description: '确保土壤湿润至30cm，使用自动灌溉系统'
//     },
//     {
//       taskId: 2,
//       name: '施肥黄瓜',
//       content: '施加氮磷钾复合肥',
//       completed: '已完成',
//       assignedUserId: 101,
//       completeTime: '2025-04-08',
//       greenhouseName: '二号大棚',
//       publishDate: '2025-04-05',
//       deadline: '2025-04-08',
//       description: '使用绿色环保有机肥，注意控制用量'
//     },
//     {
//       taskId: 3,
//       name: '温度监测',
//       content: '检测大棚3号当前温度是否在合理区间',
//       completed: '未完成',
//       assignedUserId: 101,
//       completeTime: '',
//       greenhouseName: '三号大棚',
//       publishDate: '2025-04-12',
//       deadline: '2025-04-18',
//       description: '保持在20℃~28℃之间，如偏离需调整通风系统'
//     },
//     {
//       taskId: 4,
//       name: '病虫害检查',
//       content: '巡查大棚植物叶面有无病害或虫害',
//       completed: '已完成',
//       assignedUserId: 101,
//       completeTime: '2025-04-10',
//       greenhouseName: '一号大棚',
//       publishDate: '2025-04-06',
//       deadline: '2025-04-10',
//       description: '注意观察是否有黄斑、蚜虫、卷叶等异常现象'
//     }
//   ]
// }

