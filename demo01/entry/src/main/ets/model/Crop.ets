export interface Crop {
  cropId: number  //作物id

  greenhouseName: string  //所属的大棚名字

  cropName: string    //作物名字

  plantingDate: string //种植时间

  growthCycle: string //生长周期


  imageUrl: Resource        //  crop 图片

  cropContent: string           //  crop 的详细介绍文本

}
