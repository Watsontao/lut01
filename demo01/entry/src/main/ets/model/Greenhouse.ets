// src/main/ets/model/Greenhouse.ets

export interface RawGreenhouse {
  greenhouseId: number;               // 大棚 ID
  greenhouseName: string;            // 大棚名称
  userId: number;                    // 所属用户 ID
  greenhouseLocation: string;        // 大棚位置
  greenhouseType: string;            // 大棚类型
  area: number;                      // 面积
  status: string;                    // 状态（如 正常/异常）
  description: string;              // 描述
  imageUrl: string;                 // 图片路径字符串（展示用需转换）
  plants: string;                   // 当前种植作物名
  videoUrl: string;                 // 监控视频地址

  // 作物相关
  cropMaturity: string;             // 作物成熟期
  expectedHarvestDate: string;     // 预计采收时间
  growDays: number;                // 生长天数

  // 控制状态
  irrigationStatus: boolean;
  ventilationStatus: boolean;
  lightingStatus: boolean;

  createDate: string;              // 创建时间
  supervisor: string;              // 负责人
}


export interface Greenhouse extends Omit<RawGreenhouse, 'imageUrl'> {
  imageUrl: string; // ✅ string 类型
}


// ✅ 页面展示用类型（展示 Resource 类型图片）
// export interface Greenhouse extends Omit<RawGreenhouse, 'imageUrl'> {
//   imageUrl: Resource;
// }

// ✅ 单独定义类型以规避 ArkTS 限制
// ✅ 用 interface 替代 type，符合 ArkTS 规范
// export interface ImageResource {
//   src: string;
// }

// // ✅ 展示用模型，imageUrl 为 Resource 类型
// export interface Greenhouse extends Omit<RawGreenhouse, 'imageUrl'> {
//   imageUrl: ImageResource;
// }
