<template>
  <div>

    <el-row :gutter="10">
      <el-col :span="24">
        <div class="card" style="margin-bottom: 10px">
          <div>欢迎您，{{ data.user.name }} 祝您今天过得开心！</div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="10">
     <el-col :span="12">
       <el-row>
         <el-col :span="24">
           <div class="card" style="padding: 20px">
             <div style="font-size: 20px; margin-bottom: 20px">技术栈</div>
             <div style="margin-bottom: 10px; color: #666">
               后台：<strong style="color: red">SpringBoot3</strong> + MyBatis / MyBatis-plus + JWT + Hutool
             </div>
             <div style="color:#666; margin-bottom: 10px;">
               前台：<strong style="color: red">Vue3 + Element-Plus</strong> + Vue-Router + Axios
             </div>
             <div style="color:#666; margin-bottom: 10px;">
               数据库：Mysql 5.7 / 8.0
             </div>
             <div style="color:#666; margin-bottom: 10px;">
               编程工具：IDEA、Navicat
             </div>
             <div style="color:#666; margin-bottom: 10px;">
               模式：前后端分离
             </div>
             <div style="color:#666; margin-bottom: 10px;">
               操作系统：Windows / Mac
             </div>
           </div>
         </el-col>
       </el-row>
     </el-col>
      <el-col :span="12">
        <div class="card" id="pie" style="height: 435px"></div>
      </el-col>
    </el-row>


  </div>
</template>

<script setup>
  import request from "@/utils/request";
  import {onMounted, reactive} from "vue";
  import * as echarts from 'echarts';

  const data = reactive({
    user: JSON.parse(localStorage.getItem('user') || '{}'),
  })

  const pieOptions = {
    title: {
      text: '', // 主标题
      subtext: '', // 副标题
      left: 'center'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '', // 鼠标移上去显示内容
        type: 'pie',
        radius: '50%',
        center: ['50%', '60%'],
        data: [
          {value: 1048, name: '瑞幸咖啡'}, // 示例数据：name表示维度，value表示对应的值
          {value: 735, name: '雀巢咖啡'},
          {value: 580, name: '星巴克咖啡'},
          {value: 484, name: '栖巢咖啡'},
          {value: 300, name: '小武哥咖啡'}
        ]
      }
    ]
  }

  onMounted(() => {
    request.get('/book/pie').then(res => {
      let chartDom = document.getElementById('pie');
      let myChart = echarts.init(chartDom);
      pieOptions.title.text = res.data.text
      pieOptions.title.subtext = res.data.subText
      // pieOptions.series[0].name = res.data.name
      pieOptions.series[0].data = res.data.data
      myChart.setOption(pieOptions);
    })
  })


</script>