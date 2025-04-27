// src/main/ets/model/GreenhouseWithEnv.ets

import type { Greenhouse } from './Greenhouse'
import type { EnvironmentData } from './EnvironmentData'

export interface GreenhouseWithEnv extends Greenhouse {
  environment?: EnvironmentData // 可选字段：用于详情或卡片展示
}
