export interface Task {

  taskId: number                // 任务ID

  name: string                 // 任务名称

  taskContent: string              // 任务内容

  completed: '否' | '是' // 状态

  assignedUserId: number       // 分配的用户ID

  completeTime: string         // 完成时间（可为空字符串）

  taskGreenhouseName: string       // 所属大棚名称

  publishTime: string          // 发布时间

  deadline: string             // 截止时间

  taskDescription?: string         // 描述（选填）

}
