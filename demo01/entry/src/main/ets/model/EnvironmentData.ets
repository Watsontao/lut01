export interface EnvironmentData {

  envid: number

  greenhouseId: number            // ✅ 更标准的主外键关联字段

  recordDate: string

  temperature: number

  humidity: number

  soilMoisture: number

  lightIntensity: number

}
