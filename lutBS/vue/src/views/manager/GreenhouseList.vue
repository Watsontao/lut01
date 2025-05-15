<template>
  <div>
    <!-- 工具栏 -->
    <div class="toolbar">
      <el-button type="primary" plain @click="toggleView">
        {{ showDetail ? '简略信息' : '详细信息' }}
      </el-button>
      <el-button type="primary" @click="handleAdd">新增大棚</el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="greenhouses" stripe style="width: 100%">
      <!-- ↓↓↓ 字段列 ↓↓↓ -->
      <el-table-column v-if="showDetail" prop="greenhouseId" label="ID" width="60"/>
      <el-table-column prop="greenhouseName"      label="名称"/>
      <el-table-column v-if="showDetail" prop="greenhouseLocation" label="位置"/>
      <el-table-column prop="greenhouseType"      label="类型"/>
      <el-table-column prop="area"                label="面积"/>
      <el-table-column prop="status"              label="状态"/>
      <el-table-column v-if="showDetail" prop="description"       label="描述"/>
      <el-table-column prop="plants"              label="作物"/>
      <el-table-column prop="videoUrl"            label="监控链接"/>
      <el-table-column prop="supervisor"          label="负责人"/>
      <el-table-column v-if="showDetail" prop="cropMaturity"     label="成熟期"/>
      <el-table-column v-if="showDetail" prop="expectedHarvest"  label="预计采收"/>
      <el-table-column v-if="showDetail" prop="growDays"         label="生长天数"/>

      <!-- 灌溉 / 通风 / 灯光三列（美化布尔）-->
      <el-table-column v-if="showDetail" label="灌溉">
        <template slot-scope="scope">
          <span :style="{color: scope.row.irrigationStatus ? 'green' : 'gray'}">
            {{ scope.row.irrigationStatus ? '✅ 开启' : '❌ 关闭' }}
          </span>
        </template>
      </el-table-column>

      <el-table-column v-if="showDetail" label="通风">
        <template slot-scope="scope">
          <span :style="{color: scope.row.ventilationStatus ? 'green' : 'gray'}">
            {{ scope.row.ventilationStatus ? '✅ 开启' : '❌ 关闭' }}
          </span>
        </template>
      </el-table-column>

      <el-table-column v-if="showDetail" label="灯光">
        <template slot-scope="scope">
          <span :style="{color: scope.row.lightingStatus ? 'green' : 'gray'}">
            {{ scope.row.lightingStatus ? '✅ 开启' : '❌ 关闭' }}
          </span>
        </template>
      </el-table-column>

      <el-table-column v-if="showDetail" prop="createTime" label="创建时间"/>

      <!-- ▶▶ 操作列 ◀◀ -->
      <el-table-column label="操作" width="220">
        <template v-slot="scope">
          <el-button size="mini" type="primary"
                     @click="viewDetail(scope.row)">查看</el-button>
          <el-button size="mini" type="warning"
                     @click="handleEdit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'GreenhouseList',
  data () {
    return {
      greenhouses: [],
      showDetail: false
    }
  },
  created () {
    this.loadGreenhouses()
  },
  methods: {
    /* 拉取列表 */
    loadGreenhouses () {
      this.$request.get('/api/greenhouse/getAll').then(res => {
        this.greenhouses = res.data || []
      })
    },
    /* 简略 / 详细视图切换 */
    toggleView () {
      this.showDetail = !this.showDetail
    },
    /* 查看详情（跳详情页） */
    viewDetail (row) {
      this.$router.push({ name: 'GreenhouseDetail', query: { id: row.greenhouseId } })
    },
    /* 新增（不传 id） */
    handleAdd () {
      this.$router.push({ name: 'GreenhouseForm' })
    },
    /* **编辑**（带 id 跳表单页） */
    handleEdit (row) {
      this.$router.push({ name: 'GreenhouseForm', query: { id: row.greenhouseId } })
    }
  }
}
</script>

<style scoped>
.toolbar{
  margin-bottom:10px;
  display:flex;
  justify-content:space-between;
  align-items:center
}
</style>
