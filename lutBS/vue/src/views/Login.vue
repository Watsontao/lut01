<template>
  <div class="container">
    <div style="width: 400px; padding: 30px; background-color: white; border-radius: 5px;">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">管理员登录</div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="userName">
          <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; background-color: #333; border-color: #333; color: white" @click="login">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      form: {
        userName: '',
        password: ''
      },
      rules: {
        userName: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    login() {
      this.$request.post('/api/user/login', this.form).then(res => {
        if (res.code === '200') {
          localStorage.setItem("token", res.data.token)
          localStorage.setItem("xm-user", JSON.stringify(res.data.user))  // ✅统一key

          this.$message.success('登录成功')

          console.log("log一下token = ", localStorage.getItem("token"))
          console.log("log一下user = ", localStorage.getItem("xm-user"))

          this.$router.push('/home')
        } else {
          localStorage.removeItem("token")
          localStorage.removeItem("xm-user")
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/ppp.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}
</style>
