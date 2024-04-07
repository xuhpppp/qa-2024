<template>
  <table class="mt-4 mx-auto">
    <tr>
      <th>Mã số BHYT</th>
      <th>Họ tên</th>
      <th>Ngày sinh</th>
      <th>Địa chỉ</th>
      <th>Nơi đăng ký khám chữa bệnh</th>
      <th>Giá trị sử dụng</th>
      <th>Tình trạng thẻ</th>
      <th>Thời điểm đạt 5 năm</th>
    </tr>

    <tr v-for="insurance in insuranceList" :key="insurance.id">
      <th>{{ insurance.insuranceCode }}</th>
      <th>{{ insurance.fullName }}</th>
      <th>{{ insurance.dob }}</th>
      <th>{{ insurance.address }}</th>
      <th>{{ insurance.registrationPlace }}</th>
      <th>{{ insurance.validFrom }} - {{ insurance.validTo }}</th>
      <th>fix sau</th>
      <th>{{ insurance.nearestValidDate }}</th>
    </tr>
  </table>
</template>

<script setup>
import { defineProps } from "vue";

defineProps({
  insuranceList: Array,
});
</script>

<style scoped>
table,
th {
  border: 1px solid gray;
}

th,
td {
  padding-left: 8px;
  padding-right: 8px;
}
</style>
