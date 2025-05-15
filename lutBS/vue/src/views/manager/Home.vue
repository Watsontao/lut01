<template>
  <div class="home-container">

    <!-- ① 欢迎 -->
    <el-card class="greet-card">
      您好，{{ user?.userName }}！欢迎使用本系统
    </el-card>

    <!-- ② 关键指标 -->
    <div class="kpi-wrap">
      <el-card class="kpi" body-style="padding:16px">
        <div class="kpi-num">{{ kpi.greenhouseTotal }}</div>
        <div class="kpi-label">在线大棚</div>
      </el-card>

      <el-card class="kpi" body-style="padding:16px">
        <div class="kpi-num">{{ kpi.taskTodo }}</div>
        <div class="kpi-label">待办任务</div>
      </el-card>

      <el-card class="kpi" body-style="padding:16px">
        <div class="kpi-num">{{ kpi.envAlarm }}</div>
        <div class="kpi-label">环境报警</div>
      </el-card>
    </div>

    <!-- ③ 大棚状态表 -->
    <el-card shadow="always" class="mt20">
      <div slot="header" class="card-title">大棚状态 (Top 5)</div>
      <el-table :data="greenhouses.slice(0,5)" border size="mini">
        <el-table-column prop="greenhouseName" label="名称"/>
        <el-table-column prop="irrigationStatus" label="灌溉" width="80">
          <template slot-scope="s">
            <el-tag :type="s.row.irrigationStatus?'success':'info'" size="mini">
              {{ s.row.irrigationStatus ? '✔' : '✘' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="ventilationStatus" label="通风" width="80">
          <template slot-scope="s">
            <el-tag :type="s.row.ventilationStatus?'success':'info'" size="mini">
              {{ s.row.ventilationStatus ? '✔' : '✘' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lightingStatus" label="灯光" width="80">
          <template slot-scope="s">
            <el-tag :type="s.row.lightingStatus?'warning':'info'" size="mini">
              {{ s.row.lightingStatus ? '✔' : '✘' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template slot-scope="s">
            <el-button type="text" size="mini"
                       @click="$router.push({name:'GreenhouseDetail',query:{id:s.row.greenhouseId}})">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- ④ mini 折线图 + 天气 -->
    <div class="mini-section">
      <el-card class="mini-chart-card" shadow="always">
        <div slot="header" class="card-title">温/湿最近 24h</div>
        <v-chart :option="miniChart" style="height:120px"/>
      </el-card>

      <el-card class="weather-card" shadow="always">
        <div slot="header" class="card-title">今日天气</div>
        <div v-if="weather">
          <div style="font-size:28px">{{ weather.temp }}°C</div>
          <div>{{ weather.text }} / {{ weather.wind }}</div>
        </div>
        <div v-else>加载中...</div>
      </el-card>
    </div>

    <!-- ⑤ 待办任务 -->
    <el-card shadow="always" class="mt20">
      <div slot="header" class="card-title">待办任务</div>
      <el-table :data="todoTasks" stripe size="mini" height="220">
        <el-table-column prop="name" label="任务"/>
        <el-table-column prop="taskGreenhouseName" label="大棚" width="120"/>
        <el-table-column prop="deadline" label="截止" width="160"/>
        <el-table-column width="80">
          <template slot="header">操作</template>
          <template slot-scope="s">
            <el-button type="text" size="mini" @click="finishTask(s.row)">完成</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- ⑥ 快捷操作 -->
    <el-card shadow="always" class="mt20">
      <div slot="header" class="card-title">快捷操作</div>
      <el-button type="primary" plain @click="quickToggle('irrigation',1)">全部开启灌溉</el-button>
      <el-button type="warning" plain class="mt10" @click="quickToggle('lighting',0)">全部关闭灯光</el-button>
    </el-card>

  </div>
</template>

<script>
/* ECharts 迷你折线图依赖 ↓ */
import VChart from 'vue-echarts'
import * as echarts from 'echarts/core'
import { LineChart }                from 'echarts/charts'
import { GridComponent,
  TooltipComponent,
  DatasetComponent }         from 'echarts/components'
import { CanvasRenderer }            from 'echarts/renderers'
echarts.use([LineChart, GridComponent, TooltipComponent, DatasetComponent, CanvasRenderer])

/* ► 仅天气卡片用到 axios；系统通用接口仍然走 this.$request */
import axios from 'axios'

export default {
  name: 'Home',
  components: { VChart },

  data () {
    return {
      /* 登录人 */
      user : JSON.parse(localStorage.getItem('xm-user') || '{}'),

      /* KPI */
      kpi  : { greenhouseTotal: 0, taskTodo: 0, envAlarm: 0 },

      /* 原始数据缓存 */
      greenhouses : [],
      tasks       : [],

      /* 迷你折线图 */
      miniChart : {},

      /* 天气卡片 */
      weather   : null
    }
  },

  computed: {
    /** 待办任务（前 5 条） */
    todoTasks () {
      return this.tasks.filter(t => t.completed !== '是').slice(0, 5)
    }
  },

  created () {
    this.loadGreenhouses()
    this.loadTasks()
    this.loadMiniChart()
    this.loadWeather()           // ← 实时天气
  },

  methods: {
    /* ─────────────────── 数据拉取 ─────────────────── */

    /** ① 大棚 */
    loadGreenhouses () {
      this.$request.get('/api/greenhouse/getAll').then(res => {
        this.greenhouses         = res.data || []
        this.kpi.greenhouseTotal = this.greenhouses.length
        this.kpi.envAlarm        = this.greenhouses
            .filter(g => g.temperature > 35 || g.humidity < 30).length
      })
    },

    /** ② 任务 */
    loadTasks () {
      this.$request.get('/api/task/getAll').then(res => {
        this.tasks        = res.data || []
        this.kpi.taskTodo = this.tasks.filter(t => t.completed !== '是').length
      })
    },

    /** ③ 迷你温湿折线图（最近 24h，48 点） */
    loadMiniChart () {
      this.$request.get('/api/environment/history/1').then(res => {
        const rows  = (res.data || []).slice(-48)
        const temp  = rows.map(i => i.temperature)
        const hum   = rows.map(i => i.humidity)

        this.miniChart = {
          grid   : { left: 0, right: 0, top: 10, bottom: 20 },
          tooltip: { trigger: 'axis' },
          xAxis  : { type: 'category', data: rows.map(i => i.recordDate), show: false },
          yAxis  : { type: 'value', show: false },
          series : [
            { data: temp, type: 'line', smooth: true, name: '温度', lineStyle: { color: '#ff7f50' } },
            { data: hum,  type: 'line', smooth: true, name: '湿度', lineStyle: { color: '#1e90ff' } }
          ]
        }
      })
    },

    /** ④ 实时天气（Open-Meteo 免费接口） */
    async loadWeather () {
      try {
        /* 固定兰州坐标（36.06, 103.84）；也可改成后端配置或浏览器地理定位 */
        const { data } = await axios.get(
            'https://api.open-meteo.com/v1/forecast',
            { params: {
                latitude        : 36.06,
                longitude       : 103.84,
                current_weather : true,
                timezone        : 'auto'
              }
            })

        const cur = data.current_weather || {}

        /* weathercode → 中文描述（常用项） */
        const codeMap = {
          0:'晴',1:'少云',2:'少云',3:'多云',
          45:'雾',48:'雾',51:'细雨',61:'小雨',
          71:'小雪',95:'雷阵雨'
        }

        this.weather = {
          temp : cur.temperature,
          text : codeMap[cur.weathercode] || '未知',
          wind : `风速 ${cur.windspeed} km/h`
        }
      } catch (e) {
        /* 网络失败兜底占位 */
        this.weather = { temp: '--', text: '获取失败', wind: '' }
      }
    },

    /* ─────────────────── 业务操作 ─────────────────── */

    /** 标记任务完成 */
    finishTask (row) {
      row.completed = '是'
      this.$request.post('/api/task/save', row).then(() => {
        this.$message.success('已标记完成')
        this.loadTasks()
      })
    },

    /** 两键全开/关快捷操作 */
    quickToggle (type, status) {
      const label = type === 'irrigation' ? '灌溉' : '灯光'

      this.$confirm(`确认将所有大棚「${label}」统一${status ? '开启' : '关闭'}？`,
          '提示', { type: 'warning' })
          .then(() => {
            // 真正去调接口
            return Promise.all(
                this.greenhouses.map(g =>
                    this.$request.post(`/api/greenhouse/${g.greenhouseId}/toggle/${type}`, { status })
                )
            )
          })
          .then(() => {
            this.$message.success('操作已执行')
            this.loadGreenhouses()
          })
          // ▼ 当用户点“取消 / 关闭”时会走这里，防止报错
          .catch(() => this.$message.info('已取消操作'))
    }

  }
}
</script>


<style scoped>
.home-container { padding:20px }
.mt20            { margin-top:20px }
.greet-card      { font-weight:600;font-size:20px }
.card-title      { font-weight:600 }

.kpi-wrap { display:flex; gap:20px; margin:20px 0 }
.kpi      { flex:1; text-align:center }
.kpi-num  { font-size:32px; font-weight:700 }
.kpi-label{ color:#888 }

.mini-section      { display:flex; gap:20px; margin-top:20px }
.mini-chart-card   { flex:2 }
.weather-card      { flex:1; display:flex; justify-content:center; align-items:center }
</style>
