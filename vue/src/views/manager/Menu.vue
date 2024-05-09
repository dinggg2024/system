<template>
  <div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table default-expand-all row-key="id" stripe :data="data.tableData">
        <el-table-column prop="id" label="ID" width="80" sortable />
        <el-table-column prop="name" label="菜单名称" />
        <el-table-column prop="path" label="组件路径" />
        <el-table-column prop="icon" label="图标">
          <template v-slot="scope">
            <el-icon v-if="scope.row.icon"><component :is="scope.row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" />
        <el-table-column prop="type" label="菜单类型" />
        <el-table-column prop="parent" label="父级目录" />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button link type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card" v-if="data.total">
      <el-pagination background layout="prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog v-model="data.formVisible" title="菜单信息" width="40%" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px 30px" status-icon>
        <el-form-item prop="name" label="名称" >
          <el-input v-model="data.form.name" />
        </el-form-item>
        <el-form-item prop="type" label="菜单类型" >
          <el-radio-group v-model="data.form.type">
            <el-radio label="目录"></el-radio>
            <el-radio label="菜单"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="path" label="组件路径">
          <el-input v-model="data.form.path" />
        </el-form-item>
        <el-form-item prop="icon" label="图标" >
          <el-select clearable v-model="data.form.icon"  placeholder="请选择">
            <template #prefix>
              <el-icon><component :is="data.form.icon" /></el-icon>
            </template>
            <el-option
                v-for="item in data.icons"
                :key="item.id"
                :label="item.name"
                :value="item.value"
            >
              <el-icon>
                <component :is="item.value" />
              </el-icon>
              <span style="font-size: 14px; position: relative; top: -2px; left: 5px">{{ item.name }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="sort" label="排序">
          <el-input-number v-model="data.form.sort" />
        </el-form-item>
        <el-form-item prop="pid" label="父级目录" >
          <el-select clearable v-model="data.form.pid">
            <el-option v-if="data.form.type === '菜单'" v-for="item in data.folderList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
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
import {reactive, ref} from "vue"
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";
const baseApi = '/menu'

const data = reactive({
  tableData: [],
  formVisible: false,
  form: {},
  icons: [],
  folderList: [],
  rules: {
    name: [
      {required: true, message: '请输入菜单名称', trigger: 'blur'},
    ],
  }
})
const formRef = ref()

// 加载表格数据
const load = () => {
  request.get(baseApi + '/selectTree').then(res => {
    data.tableData = res.data || []
  })
}

// 打开新增弹窗
const handleAdd = () => {
  loadFolders()
  data.form = {sort: 1, type: '菜单'}
  data.formVisible = true
}

// 打开编辑弹窗
const handleEdit = (row) => {
  loadFolders()
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

// 新增
const add = () => {
  formRef.value.validate((valid => {
    if (valid) {
      request.post(baseApi + '/add', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('操作成功')
          data.formVisible = false
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  }))

}

// 更新
const update = () => {
  formRef.value.validate((valid => {
    if (valid) {
      request.put(baseApi + '/update', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('更新成功')
          data.formVisible = false
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  }))
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

const save = () => {
  data.form.id ? update() : add()
}

const loadIcons = () => {
  request.get('/dict/selectByType', { params: { type: 'icon' } }).then(res => {
    data.icons = res.data || []
  })
}

const loadFolders = () => {
  request.get(baseApi + '/selectFolder').then(res => {
    data.folderList = res.data || []
  })
}

load()
loadIcons()

</script>