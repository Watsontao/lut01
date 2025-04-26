<template>
  <div class="detail-page">
    <!-- ► 监控视频 + 控制按钮 ◄ -->
    <div class="video-wrap">
      <!-- 视频 -->
      <div class="video-container" v-if="greenhouse.videoUrl">
        <video :src="greenhouse.videoUrl"
               controls
               class="video-player"
               @error="handleVideoError"/>
      </div>

      <!-- 右侧纵向按钮组 -->
      <div class="control-panel">
        <el-button :type="greenhouse.irrigationStatus ? 'success' : 'info'"
                   icon="el-icon-s-platform"
                   @click="toggle('irrigation')">
          {{ greenhouse.irrigationStatus ? '停止灌溉' : '开始灌溉' }}
        </el-button>

        <el-button :type="greenhouse.ventilationStatus ? 'success' : 'info'"
                   icon="el-icon-wind-power"
                   @click="toggle('ventilation')">
          {{ greenhouse.ventilationStatus ? '关闭通风' : '开启通风' }}
        </el-button>

        <el-button :type="greenhouse.lightingStatus ? 'warning' : 'info'"
                   icon="el-icon-lightning"
                   @click="toggle('lighting')">
          {{ greenhouse.lightingStatus ? '关闭灯光' : '开启灯光' }}
        </el-button>
      </div>
    </div>

    <!-- 图表区 -->
    <div class="charts">
      <!-- 第一行 -->
      <div class="chart-row">
        <div class="chart-card">
          <h3>温度变化（℃）</h3>
          <v-chart :option="temperatureChart" class="chart"/>
        </div>
        <div class="chart-card">
          <h3>湿度变化（%）</h3>
          <v-chart :option="humidityChart" class="chart"/>
        </div>
      </div>

      <!-- 第二行 -->
      <div class="chart-row">
        <div class="chart-card">
          <h3>土壤湿度（%）</h3>
          <v-chart :option="soilChart" class="chart"/>
        </div>
        <div class="chart-card">
          <h3>光照强度（lx）</h3>
          <v-chart :option="lightChart" class="chart"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VChart from 'vue-echarts'
import * as echarts from 'echarts/core'
import {LineChart} from 'echarts/charts'
import {
  TitleComponent, TooltipComponent, GridComponent,
  DatasetComponent, LegendComponent, DataZoomComponent, ToolboxComponent
} from 'echarts/components'
import {CanvasRenderer} from 'echarts/renderers'

echarts.use([
  LineChart, TitleComponent, TooltipComponent, GridComponent,
  DatasetComponent, LegendComponent, DataZoomComponent, ToolboxComponent, CanvasRenderer
])

/* ---------- 通用工具 ---------- */
function formatDateLabel(v) {
  const d = new Date(v)
  const M = d.getMonth() + 1
  const D = d.getDate()
  const h = String(d.getHours()).padStart(2, '0')
  const m = String(d.getMinutes()).padStart(2, '0')
  return `${M}-${D} ${h}:${m}`
}

function buildYAxis(u) {
  return {
    type: 'value',
    name: u,
    axisLabel: {formatter: `{value} ${u}`},
    max: v => v.max * 1.1,
    minInterval: 1
  }
}

function grad(c) {             // 线条渐变
  return {
    type: 'linear', x: 0, y: 0, x2: 0, y2: 1,
    colorStops: [{offset: 0, color: c}, {offset: 1, color: `${c}22`}]
  }
}

/* 导出函数：format = csv | xlsx */
function exportData(format, name, labels, values) {
  const rows = [['时间', name]].concat(labels.map((t, i) => [t, values[i]]))
  let blob, mime, ext
  if (format === 'csv') {
    const csv = rows.map(r => r.join(',')).join('\r\n')
    blob = new Blob([csv], {type: 'text/csv;charset=utf-8'})
    mime = 'csv';
    ext = '.csv'
  } else {
    /* 极简 xlsx：Excel 能够解析的 XML Spreadsheet */
    const xmlRows = rows.map(r => `<Row>${r.map(c => `<Cell><Data ss:Type="String">${c}</Data></Cell>`).join('')}</Row>`).join('')
    const xml = `<?xml version="1.0"?><Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
             xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"><Worksheet ss:Name="${name}">
             <Table>${xmlRows}</Table></Worksheet></Workbook>`
    blob = new Blob([xml], {type: 'application/vnd.ms-excel'})
    mime = 'xlsx';
    ext = '.xlsx'
  }
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url;
  a.download = `${name}${ext}`;
  a.click()
  URL.revokeObjectURL(url)
}

/* ---------- /工具 ---------- */

export default {
  name: 'GreenhouseDetail',
  components: {VChart},
  data() {
    return {
      greenhouse: {
        rrigationStatus: 0,
        ventilationStatus: 0,
        lightingStatus: 0
      },
      temperatureChart: {}, humidityChart: {},
      soilChart: {}, lightChart: {}
    }
  },
  created() {
    const id = this.$route.query.id
    this.loadGreenhouse(id)
    this.loadEnvData(id)
  },
  methods: {
    /** 统一开关 */
    toggle(type) {
      const map = {irrigation: '灌溉', ventilation: '通风', lighting: '灯光'}
      const id = this.$route.query.id
      this.$request.post(`/api/greenhouse/${id}/toggle/${type}`).then(res => {
        this.$message.success(`${map[type]}状态已切换`)
        // 更新本地状态，让按钮立即变色
        const key = `${type}Status`
        this.greenhouse[key] = this.greenhouse[key] ? 0 : 1
      })
    },

    handleVideoError() {
      this.$message.error('无信号，请检查摄像设备')
    },
    loadGreenhouse(id) {
      this.$request.get(`/api/greenhouse/getById?id=${id}`).then(r => {
        this.greenhouse = r.data || {}
        if (this.greenhouse.videoUrl && !/^https?:/.test(this.greenhouse.videoUrl))
          this.greenhouse.videoUrl = this.$baseUrl + this.greenhouse.videoUrl
      })
    },
    /* toolbox buttons 公用定义 */
    mkToolbox(seriesName, labels, dataArr) {
      return {
        right: 20,
        feature: {
          saveAsImage: {},
          myCSV: {
            title: '下载 csv文件',
            icon: 'path://M896 704H640v-64h256V128H128v512h256v64H128c-35.296 0-64-28.704-64-64V128c0-35.296 28.704-64 64-64h768c35.296 0 64 28.704 64 64v512c0 35.296-28.704 64-64 64z M704 768l128 128 128-128h-96V448h-64v320H704z',
            onclick: () => exportData('csv', seriesName, labels, dataArr)
          },
          myXLSX: {
            title: '下载 xlsx文件',
            icon: 'path://M704 768l128 128 128-128h-96V448h-64v320H704z M896 704H640v-64h256V128H128v512h256v64H128c-35.296 0-64-28.704-64-64V128c0-35.296 28.704-64 64-64h768c35.296 0 64 28.704 64 64v512c0 35.296-28.704 64-64 64z',
            onclick: () => exportData('xlsx', seriesName, labels, dataArr)
          }
        }
      }
    },
    loadEnvData(id) {
      this.$request.get(`/api/environment/history/${id}`).then(res => {
        const rows = res.data || []
        const dates = rows.map(r => r.recordDate)

        const xAxis = {type: 'category', data: dates, axisLabel: {formatter: formatDateLabel}}
        const zoom = {dataZoom: [{type: 'inside', xAxisIndex: 0}, {type: 'slider', xAxisIndex: 0}]}

        // 温度
        const temp = rows.map(r => r.temperature)
        this.temperatureChart = {
          color: [grad('#ff7f50')], tooltip: {trigger: 'axis'}, xAxis, yAxis: buildYAxis('℃'),
          ...zoom, toolbox: this.mkToolbox('温度', dates, temp),
          series: [{name: '温度', type: 'line', smooth: true, data: temp}]
        }

        // 湿度
        const hum = rows.map(r => r.humidity)
        this.humidityChart = {
          color: [grad('#1e90ff')], tooltip: {trigger: 'axis'}, xAxis, yAxis: buildYAxis('%'),
          ...zoom, toolbox: this.mkToolbox('湿度', dates, hum),
          series: [{name: '湿度', type: 'line', smooth: true, data: hum}]
        }

        // 土壤湿度
        const soil = rows.map(r => r.soilMoisture)
        this.soilChart = {
          color: [grad('#3cb371')], tooltip: {trigger: 'axis'}, xAxis, yAxis: buildYAxis('%'),
          ...zoom, toolbox: this.mkToolbox('土壤湿度', dates, soil),
          series: [{name: '土壤湿度', type: 'line', smooth: true, data: soil}]
        }

        // 光照强度
        const light = rows.map(r => r.lightIntensity)
        this.lightChart = {
          color: [grad('#ffa500')], tooltip: {trigger: 'axis'}, xAxis, yAxis: buildYAxis('lx'),
          ...zoom, toolbox: this.mkToolbox('光照强度', dates, light),
          series: [{name: '光照强度', type: 'line', smooth: true, data: light}]
        }
      })
    }
  }
}
</script>

<style scoped>
.detail-page {
  padding: 20px
}

/* ▶▶ 新增布局：视频左、按钮右 ◀◀ */
.video-wrap {
  display: flex;
  gap: 20px;
  align-items: flex-start
}

.video-container {
  width: 100%;
  height: 400px
}

.video-player {
  width: 100%;
  height: 100%;
  border-radius: 8px
}

.control-panel {
  display: flex;
  flex-direction: column;
  gap: 15px
}

.detail-page {
  padding: 20px
}

.video-container {
  width: 100%;
  height: 400px;
  margin-bottom: 30px
}

.video-player {
  width: 100%;
  height: 100%;
  border-radius: 8px
}

.charts {
  display: flex;
  flex-direction: column;
  gap: 30px
}

.chart-row {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 30px
}

.chart-card {
  flex: 1;
  min-width: 48%;
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .08)
}

.chart {
  width: 100%;
  height: 300px
}
</style>
