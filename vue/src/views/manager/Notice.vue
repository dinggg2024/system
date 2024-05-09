<template>
  <div>
    <div class="card" style="margin-bottom: 10px;">
      <el-input v-model="data.name" placeholder="请输入关键字查询" style="width: 240px"></el-input>
      <el-button type="info" style="margin-left: 10px" @click="load">查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px" v-if="data.user.role === 'ADMIN'">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="danger" @click="delBatch">批量删除</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px;">
      <el-table :data="data.tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" v-if="data.user.role === 'ADMIN'"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="公告标题" />
        <el-table-column prop="content" label="公告内容" />
        <el-table-column prop="time" label="发布时间" />
        <el-table-column label="操作" align="center" width="200" fixed="right" v-if="data.user.role === 'ADMIN'">
          <template v-slot="scope">
            <el-button type="success" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="card" v-if="data.total">
        <el-pagination background layout="prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
      </div>
    </div>

    <el-dialog title="系统公告表" v-model="data.formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="80px"  style="padding: 20px 30px" ref="formRef">
        <el-form-item label="公告标题" prop="name">
          <el-input v-model="data.form.name" placeholder="公告标题"></el-input>
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input type="textarea" :rows="4" v-model="data.form.content" placeholder="公告内容"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive } from "vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
const baseApi = '/notice'

const data = reactive({
  user: JSON.parse(localStorage.getItem('user') || '{}'),
  tableData: [],
  total: 0,
  pageNum: 1,  // 当前的页码
  pageSize: 5,  // 每页的个数
  formVisible: false,
  form: {},
  ids: [],
  name: '',
})

// 加载表格数据
const load = () => {
  request.get(baseApi + '/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total
  })
}

// 打开新增弹窗
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

// 打开编辑弹窗
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

// 新增
const add = () => {
  request.post(baseApi + '/add', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 更新
const update = () => {
  request.put(baseApi + '/update', data.form).then(res => {
    if (res.code === '200') {
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 删除
const del = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete(baseApi + '/delete/' + id).then(res => {
      if (res.code === '200') {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

// 批量删除
const handleSelectionChange = (rows) => {
  data.ids = rows.map(v => v.id)
}

const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning("请选择数据")
    return
  }
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete(baseApi + '/delete/batch', {data: data.ids}).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()  // 刷新表格数据
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => console.log(err))
}

const save = () => {
  data.form.id ? update() : add()
}

const reset = () => {
  data.name = ''
  load()
}

load()

</script>