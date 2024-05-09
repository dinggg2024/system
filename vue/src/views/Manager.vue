<template>
  <div class="manager-container">
    <div class="manager-header">
      <div class="manager-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">图书管理系统</div>
      </div>
      <div class="manager-header-center">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/manager/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: router.currentRoute.value.path }">{{ router.currentRoute.value.meta.name }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="manager-header-right">
        <el-dropdown style="cursor: pointer;">
          <div style="padding-right: 20px; display: flex; align-items: center;">
            <img v-if="data.user.avatar" :src="data.user.avatar" alt="" style="width: 40px; height: 40px; display: block; border-radius: 50%">
            <img v-else src="@/assets/imgs/avatar.png" alt="" style="width: 40px; height: 40px; display: block; border-radius: 50%">
            <span style="margin-left: 5px">{{ data.user?.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item><div @click="router.push('/manager/person')">个人资料</div></el-dropdown-item>
              <el-dropdown-item><div @click="router.push('/manager/password')">修改密码</div></el-dropdown-item>
              <el-dropdown-item><div @click="logout">退出登录</div></el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <div style="display: flex">
      <div class="manager-main-left">
        <el-menu
            :default-active="router.currentRoute.value.path"
            :default-openeds="data.user.menus.map(v => v.id + '')"
            router
        >
          <el-menu-item index="/manager/home">
            <el-icon><home-filled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <div v-for="item in data.user.menus" :key="item.id">
            <div v-if="item.type === '菜单'">
              <el-menu-item :index="'/manager/' + item.path">
                <el-icon v-if="item.icon">
                  <component :is="item.icon"></component>
                </el-icon>
                <span>{{ item.name }}</span>
              </el-menu-item>
            </div>
            <div v-else>
              <el-sub-menu :index="item.id + ''">
                <template #title>
                  <el-icon v-if="item.icon">
                    <component :is="item.icon"></component>
                  </el-icon>
                  <span>{{ item.name }}</span>
                </template>
                <div v-for="subItem in item.children" :key="subItem.id">
                  <el-menu-item :index="'/manager/' + subItem.path">
                    <template #title>
                      <el-icon v-if="subItem.icon">
                        <component :is="subItem.icon"></component>
                      </el-icon>
                      <span>{{ subItem.name }}</span>
                    </template>
                  </el-menu-item>
                </div>
              </el-sub-menu>
            </div>
          </div>
        </el-menu>
      </div>

      <div class="manager-main-right">
        <router-view @updateUser="updateUser" />
      </div>
    </div>

  </div>
</template>

<script setup>
import {HomeFilled, User, UserFilled} from "@element-plus/icons-vue";
import {reactive} from "vue";
import router from "@/router";

console.log(router.currentRoute.value.path)

const data = reactive({
  user: JSON.parse(localStorage.getItem('user') || '{}')
})

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

const updateUser = (user) => {
  localStorage.setItem('user', JSON.stringify(user))
  data.user = JSON.parse(JSON.stringify(user))
}
</script>

<style scoped>
@import "@/assets/css/manager.css";
</style>
