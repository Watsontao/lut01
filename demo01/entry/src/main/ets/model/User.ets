export interface User {
  userId: number;        // 用户ID
  userName: string;      // 姓名
  phone: string;         // 手机号
  password: string;      // 密码
  role: string;          // 角色（管理员/农民工）
  gender: string;        // 性别
  idCard: string;        // 身份证号
  address: string;       // 住址
  createDate: string;    // 注册时间
  imageUrl: string;      // 头像URL ✅ 新增字段
}
