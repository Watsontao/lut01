<template>
  <div class="task-page">
    <!-- ▍工具栏 -->
    <div class="toolbar">
      <div>
        <el-button type="success" @click="openEdit()">新增任务</el-button>
        <el-button type="danger"
                   :disabled="!multipleSelection.length"
                   @click="handleBatchDelete">批量删除</el-button>
      </div>

      <!-- ▍搜索 -->
      <el-input v-model="searchKey"
                placeholder="搜索任务 / 大棚 / 用户"
                size="small"
                style="max-width:260px"
                clearable
                @keyup.enter.native="loadTasks">
        <template #append>
          <el-button icon="el-icon-search" @click="loadTasks"/>
        </template>
      </el-input>
    </div>

    <!-- ▍任务表格 -->
    <el-table :data="filteredTasks" border stripe
              style="width:100%"
              @selection-change="multipleSelection = $event">
      <el-table-column type="selection" width="46"/>
      <el-table-column prop="taskId" label="ID" width="70" align="center"/>
      <el-table-column prop="name" label="任务名称" min-width="150"/>
      <el-table-column prop="taskContent" label="任务内容" min-width="180" show-overflow-tooltip/>
      <el-table-column prop="taskGreenhouseName" label="所属大棚" min-width="120"/>

      <!-- 只显示用户名 -->
      <el-table-column prop="assignedUserName" label="指派用户" min-width="120"/>

      <el-table-column label="完成状态" width="90" align="center">
        <template #default="{ row }">
          <el-tag :type="row.completed === '是' ? 'success' : 'info'" size="mini">
            {{ row.completed === '是' ? '已完成' : '未完成' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="publishTime" label="发布时间" min-width="140"/>
      <el-table-column prop="deadline"    label="截止时间"   min-width="140"/>
      <el-table-column prop="taskDescription" label="备注" min-width="140" show-overflow-tooltip/>

      <!-- ▍操作 -->
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button size="mini" type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button size="mini" type="danger"  @click="handleDelete(row.taskId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- ▍弹窗：新增 / 编辑 -->
    <el-dialog :title="form.taskId ? '编辑任务' : '新增任务'"
               :visible.sync="dialogVisible"
               width="540px"
               @close="resetForm">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="95px">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="form.name"/>
        </el-form-item>

        <el-form-item label="任务内容" prop="taskContent">
          <el-input v-model="form.taskContent"
                    type="textarea" :rows="3"
                    maxlength="200" show-word-limit/>
        </el-form-item>

        <el-form-item label="所属大棚" prop="taskGreenhouseName">
          <el-select v-model="form.taskGreenhouseName" placeholder="请选择" filterable clearable>
            <el-option v-for="g in greenhouses"
                       :key="g.greenhouseName"
                       :label="g.greenhouseName"
                       :value="g.greenhouseName"/>
          </el-select>
        </el-form-item>

        <!-- 指派用户 -->
        <el-form-item label="指派用户" prop="assignedUserId">
          <el-select v-model="form.assignedUserId"
                     placeholder="请选择"
                     filterable clearable
                     @change="syncUserName">
            <el-option v-for="u in farmers"
                       :key="u.userId"
                       :label="u.userName"
                       :value="u.userId"/>
          </el-select>
        </el-form-item>

        <el-form-item label="截止时间" prop="deadline">
          <el-date-picker v-model="form.deadline" type="datetime"
                          placeholder="选择截止时间" style="width:100%"/>
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="form.taskDescription"
                    type="textarea" :rows="2"
                    maxlength="120" show-word-limit/>
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'TaskList',
  data () {
    return {
      tasks: [],
      searchKey: '',
      multipleSelection: [],

      dialogVisible: false,
      greenhouses: [],
      farmers: [],
      form: {},

      rules: {
        name:               [{ required: true, message: '请输入任务名称', trigger: 'blur' }],
        taskContent:        [{ required: true, message: '请输入任务内容', trigger: 'blur' }],
        taskGreenhouseName: [{ required: true, message: '请选择所属大棚', trigger: 'change' }],
        assignedUserId:     [{ required: true, message: '请选择指派用户',  trigger: 'change' }]
      }
    };
  },

  computed: {
    filteredTasks () {
      const k = this.searchKey.trim();
      if (!k) return this.tasks;
      return this.tasks.filter(t =>
          (t.name || '').includes(k) ||
          (t.taskGreenhouseName || '').includes(k) ||
          (t.assignedUserName || '').includes(k)
      );
    }
  },

  created () { this.loadTasks(); },

  methods: {
    /* -------- 列表 -------- */
    loadTasks () {
      this.$request.get('/api/task/getAll').then(res => {
        this.tasks = res.data || [];
      });
    },

    handleDelete (id) {
      this.$confirm('确认删除该任务？', '提示', { type:'warning' })
          .then(() => this.$request.delete(`/api/task/delete/${id}`))
          .then(() => { this.$message.success('删除成功'); this.loadTasks(); });
    },

    handleBatchDelete () {
      const ids = this.multipleSelection.map(i => i.taskId);
      if (!ids.length) return;
      this.$confirm(`确认删除选中的 ${ids.length} 条任务？`, '提示', { type:'warning' })
          .then(() => this.$request.post('/api/task/deleteBatch', ids))
          .then(() => { this.$message.success('删除成功'); this.loadTasks(); });
    },

    /* -------- 弹窗 -------- */
    openEdit (row = null) {
      this.form          = row ? { ...row } : { completed:'否' };
      this.dialogVisible = true;
      this.$nextTick(() => this.$refs.formRef && this.$refs.formRef.clearValidate());

      // 懒加载下拉
      if (!this.greenhouses.length) {
        this.$request.get('/api/greenhouse/getAll').then(r => { this.greenhouses = r.data || []; });
      }
      if (!this.farmers.length) {
        this.$request.get('/api/user/getFarmers').then(r => { this.farmers = r.data || []; });
      }
    },

    /* 选完用户后，把用户名同步到表单，保存后前端立即可见 */
    syncUserName (uid) {
      const u = this.farmers.find(f => f.userId === uid);
      this.form.assignedUserName = u ? u.userName : '';
    },

    submitForm () {
      this.$refs.formRef.validate(valid => {
        if (!valid) return;
        this.$request.post('/api/task/save', this.form)
            .then(() => {
              this.$message.success('保存成功');
              this.dialogVisible = false;
              this.loadTasks();
            });
      });
    },

    resetForm () {
      this.$refs.formRef && this.$refs.formRef.resetFields();
      this.form = {};
    }
  }
};
</script>

<style scoped>
.task-page { padding: 15px }
.toolbar   { display: flex; justify-content: space-between; margin-bottom: 12px }
</style>
