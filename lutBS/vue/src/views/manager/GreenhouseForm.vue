<template>
  <div>
    <el-card>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="大棚名称" prop="greenhouseName">
          <el-input v-model="form.greenhouseName" placeholder="请输入大棚名称" />
        </el-form-item>

        <el-form-item label="地理位置" prop="greenhouseLocation">
          <el-input v-model="form.greenhouseLocation" placeholder="请输入地理位置" />
        </el-form-item>

        <el-form-item label="大棚类型" prop="greenhouseType">
          <el-select v-model="form.greenhouseType" placeholder="请选择类型">
            <el-option label="塑料" value="塑料" />
            <el-option label="玻璃" value="玻璃" />
          </el-select>
        </el-form-item>

        <el-form-item label="面积（㎡）" prop="area">
          <el-input-number v-model="form.area" :min="0" />
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="正常" value="正常" />
            <el-option label="异常" value="异常" />
          </el-select>
        </el-form-item>

        <el-form-item label="作物名称" prop="plants">
          <el-input v-model="form.plants" placeholder="请输入当前作物名称" />
        </el-form-item>

        <el-form-item label="作物成熟期" prop="cropMaturity">
          <el-input v-model="form.cropMaturity" placeholder="请输入作物成熟期" />
        </el-form-item>

        <el-form-item label="预计采收时间" prop="expectedHarvest">
          <el-date-picker v-model="form.expectedHarvest" type="date" placeholder="选择预计采收时间" />
        </el-form-item>

        <el-form-item label="生长天数" prop="growDays">
          <el-input-number v-model="form.growDays" :min="0" />
        </el-form-item>

        <el-form-item label="负责人" prop="supervisor">
          <el-input v-model="form.supervisor" placeholder="请输入负责人姓名" />
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
        </el-form-item>

        <el-form-item label="视频监控链接" prop="videoUrl">
          <el-input v-model="form.videoUrl" placeholder="请输入监控链接" />
        </el-form-item>

        <el-form-item label="图片链接" prop="imageUrl">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              accept="image/*">
            <img v-if="form.imageUrl" :src="form.imageUrl" class="uploaded-img" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="状态控制">
          <el-switch v-model="form.irrigationStatus" active-text="灌溉开" inactive-text="灌溉关" />
          <el-switch v-model="form.ventilationStatus" active-text="通风开" inactive-text="通风关" style="margin-left: 20px" />
          <el-switch v-model="form.lightingStatus" active-text="灯光开" inactive-text="灯光关" style="margin-left: 20px" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submit">保存</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  created () {
    const id = this.$route.query.id
    if (id) { // 编辑
      this.$request.get('/api/greenhouse/getById', { params: { id } })
          .then(res => { this.form = res.data || {} })
    }
  },

  name: 'GreenhouseForm',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      form: {
        greenhouseName: '',
        greenhouseLocation: '',
        greenhouseType: '',
        area: null,
        status: '正常',
        description: '',
        imageUrl: '',
        plants: '',
        videoUrl: '',
        cropMaturity: '',
        expectedHarvest: '',
        growDays: 0,
        irrigationStatus: false,
        ventilationStatus: false,
        lightingStatus: false,
        createTime: new Date().toISOString().slice(0, 10),
        supervisor: ''
      },
      rules: {
        greenhouseName: [{ required: true, message: '请输入大棚名称', trigger: 'blur' }],
        greenhouseLocation: [{ required: true, message: '请输入位置', trigger: 'blur' }],
        greenhouseType: [{ required: true, message: '请选择类型', trigger: 'change' }],
        area: [{ required: true, message: '请输入面积', trigger: 'blur' }],
        plants: [{ required: true, message: '请输入作物名称', trigger: 'blur' }],
        supervisor: [{ required: true, message: '请输入负责人', trigger: 'blur' }]
      }
    }
  },
  methods: {



    submit() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          // 格式化日期为字符串（防止对象类型传后端）
          if (this.form.expectedHarvest instanceof Date) {
            this.form.expectedHarvest = this.form.expectedHarvest.toISOString().split('T')[0]
          }

          this.$request.post('/api/greenhouse/save', this.form).then(res => {
            if (res.code === '200') {
              this.$message.success('保存成功')
              this.$router.push({ name: 'Greenhouse' })
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    handleUploadSuccess(response) {
      this.form.imageUrl = response.data
      this.$message.success('图片上传成功')
    }
  }

}
</script>

<style scoped>
.el-card {
  max-width: 800px;
  margin: 20px auto;
}
</style>
