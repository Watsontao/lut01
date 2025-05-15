<template>
  <div class="main-content">
    <!-- 顶部欢迎 -->
    <div class="card" style="margin-bottom: 20px; font-weight: bold; font-size: 20px;">
      您好，{{ user?.userName }}！欢迎使用本系统
    </div>

    <!-- 搜索区域 -->
    <div class="card" style="margin-bottom: 10px;">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="姓名">
          <el-input v-model="searchForm.userName" placeholder="请输入姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="searchForm.phone" placeholder="请输入电话" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="searchUsers">查询</el-button>
          <el-button type="info" icon="el-icon-refresh" @click="resetSearch">重置</el-button>
          <el-button type="success" icon="el-icon-plus" @click="addUser">新增</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteBatch" :disabled="!selectedIds.length">批量删除
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 表格 -->
    <div class="card">
      <el-table :data="users" border stripe style="width: 100%;" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column prop="userId" label="ID" width="60"/>
        <el-table-column prop="userName" label="姓名"/>
        <el-table-column prop="phone" label="电话"/>
        <el-table-column prop="role" label="角色"/>
        <el-table-column prop="gender" label="性别"/>
        <el-table-column prop="idCard" label="身份证号"/>
        <el-table-column prop="address" label="地址"/>
        <el-table-column prop="createDate" label="创建时间"/>
        <el-table-column prop="imageUrl" label="头像URL"/>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="editUser(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 新增弹窗 -->
    <el-dialog title="新增用户" :visible.sync="formVisible" width="600px">
      <el-form :model="form" ref="formRef" :rules="rules" label-width="100px">
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="form.userName"/>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password"/>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" placeholder="请选择角色">
            <el-option label="农民工" value="农民工"/>
<!--            <el-option label="管理员" value="管理员"/>-->
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="男"/>
            <el-radio label="女"/>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard"/>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address"/>
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
  name: 'Farmer',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      searchForm: {
        userName: '',
        phone: ''
      },
      users: [],
      selectedIds: [],
      formVisible: false,
      form: {},
      rules: {
        userName: [{required: true, message: '请输入姓名', trigger: 'blur'}],
        phone: [{required: true, message: '请输入电话', trigger: 'blur'},
          {pattern: /^\d{11}$/, message: '手机号必须为11位数字', trigger: 'blur'}],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        role: [{required: true, message: '请选择角色', trigger: 'change'}],
        gender: [{required: true, message: '请选择性别', trigger: 'change'}],
        idCard: [
          {required: true, message: '请输入身份证号', trigger: 'blur'},
          {
            validator: (rule, value, callback) => {
              const idCardPattern = /^\d{17}[\dXx]$/;
              if (!idCardPattern.test(value)) {
                callback(new Error('身份证号格式不正确'));
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ],
        address: [{required: true, message: '请输入地址', trigger: 'blur'}]
      }
    }
  },
  created() {
    this.loadUsers()
  },


  methods: {

    loadUsers() {
      this.$request.get('/api/user/getAllUser', {
        params: this.searchForm
      }).then(res => {
        const all = res.data || []
        this.users = all.filter(u => u.role && (u.role.toLowerCase() === 'farmer' || u.role === '农民工'))
      })
    },


    searchUsers() {
      this.$request.get('/api/user/search', {
        params: {
          userName: this.searchForm.userName,
          phone: this.searchForm.phone
        }
      }).then(res => {
        const all = res.data || []
        // 与 loadUsers 保持一致，仅显示“农民工”
        this.users = all.filter(u => u.role && (u.role.toLowerCase() === 'farmer' || u.role === '农民工'))
        this.users = all.filter(u => u.role && (u.role.toLowerCase() === 'farmer' || u.role === '农民工'))
      })
    },



    resetSearch() {
      this.searchForm = {userName: '', phone: ''}
      this.loadUsers()
    },


    addUser() {
      this.form = {}
      this.form.role = 'farmer'
      this.formVisible = true
    },

    editUser(row) {
      this.form = JSON.parse(JSON.stringify(row)) // 深拷贝以避免修改原数据
      this.formVisible = true
    },



    handleSelectionChange(rows) {
      this.selectedIds = rows.map(item => item.userId)
    },


    deleteBatch() {
      this.$confirm(`是否确认删除选中的 ${this.selectedIds.length} 个用户？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$request.post('/api/user/deleteBatch', this.selectedIds).then(res => {
          if (res.code === '200') {
            this.$message.success('删除成功')
            this.loadUsers()
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {
      })
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
              this.loadUsers()
              this.formVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.main-content {
  padding: 10px;
}
</style>