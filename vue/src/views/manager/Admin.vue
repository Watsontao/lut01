<template>
  <div>
    <!-- 搜索栏 -->
    <div class="search">
      <el-input placeholder="请输入姓名" style="width: 200px" v-model="searchForm.userName"></el-input>
      <el-input placeholder="请输入电话" style="width: 200px; margin-left: 10px" v-model="searchForm.phone"></el-input>
      <el-button type="primary" plain style="margin-left: 10px" @click="searchUsers">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="resetSearch">重置</el-button>
    </div>

    <!-- 操作栏 -->
    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <!-- 表格 -->
    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="userId" label="ID" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="userName" label="姓名"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="role" label="角色"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="idCard" label="身份证号"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="createDate" label="创建时间"></el-table-column>
        <el-table-column prop="imageUrl" label="头像URL"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 弹窗表单 -->
    <el-dialog title="管理员信息" :visible.sync="formVisible" width="600px">
      <el-form :model="form" label-width="100px" :rules="rules" ref="formRef">
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" type="password"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role">
            <el-option label="管理员" value="管理员" />
<!--            <el-option label="admin" value="admin" />-->
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="男" />
            <el-radio label="女" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Admin',
  data() {
    return {
      tableData: [],
      searchForm: { userName: '', phone: '' },
      formVisible: false,
      form: {},
      rules: {
        userName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        role: [{ required: true, message: '请选择角色', trigger: 'change' }],
        gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
        idCard: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地址', trigger: 'blur' }]
      },
      selectedIds: [],
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.$request.get('/api/user/getAllUser').then(res => {
        const all = res.data || []
        this.tableData = all.filter(u => u.role && (u.role.toLowerCase() === 'admin' || u.role === '管理员'))
      })
    },
    searchUsers() {
      this.$request.get('/api/user/search', {
        params: this.searchForm
      }).then(res => {
        const all = res.data || []
        this.tableData = all.filter(u => u.role && (u.role.toLowerCase() === 'admin' || u.role === '管理员'))
      })
    },
    resetSearch() {
      this.searchForm = { userName: '', phone: '' }
      this.load()
    },
    handleAdd() {
      this.form = {}
      this.form.role = '管理员'
      this.formVisible = true
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.formVisible = true
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.userId ? '/api/user/update' : '/api/user/add',
            method: this.form.userId ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('保存成功')
              this.load()
              this.formVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    handleSelectionChange(rows) {
      this.selectedIds = rows.map(item => item.userId)
    },
    delBatch() {
      if (!this.selectedIds.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', { type: "warning" }).then(() => {
        this.$request.post('/api/user/deleteBatch', this.selectedIds).then(res => {
          if (res.code === '200') {
            this.$message.success('删除成功')
            this.load()
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
  }
}
</script>

<style scoped>
.search, .operation {
  margin-bottom: 10px;
}
</style>
