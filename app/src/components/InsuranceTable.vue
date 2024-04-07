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

    <tr
      v-for="insurance in insuranceList"
      :key="insurance.id"
      @click="tickInsuranceRow(insurance.id)"
      :class="
        checkedInsurance.includes(insurance.id) ? 'bg-gray-200' : 'bg-white'
      "
    >
      <th>{{ insurance.insuranceCode }}</th>
      <th>{{ insurance.fullName }}</th>
      <th>{{ insurance.dob }}</th>
      <th>{{ insurance.address }}</th>
      <th>{{ insurance.registrationPlace }}</th>
      <th>{{ insurance.validFrom }} - {{ insurance.validTo }}</th>
      <th>{{ isValidCalculated(insurance.validFrom, insurance.validTo) }}</th>
      <th>{{ insurance.nearestValidDate }}</th>
    </tr>
  </table>
</template>

<script setup>
import { defineProps, ref, defineExpose } from "vue";
import { parseISO, isWithinInterval } from "date-fns";

defineProps({
  insuranceList: Array,
});

const checkedInsurance = ref([]);

function isValidCalculated(startDate, endDate) {
  // Parse the string dates into Date objects
  const start = parseISO(startDate);
  const end = parseISO(endDate);

  // Get today's date
  const today = new Date();

  // Check if today's date falls within the interval
  const isTodayWithinInterval = isWithinInterval(today, { start, end });

  if (isTodayWithinInterval) {
    return "Hợp lệ";
  } else {
    return "Quá hạn";
  }
}

function tickInsuranceRow(id) {
  if (!checkedInsurance.value.includes(id)) {
    checkedInsurance.value.push(id);
  } else {
    let index = checkedInsurance.value.indexOf(id);
    if (index !== -1) {
      checkedInsurance.value.splice(index, 1);
    }
  }
}

function downloadCsv() {
  let requestStr = "";
  for (let id of checkedInsurance.value) {
    requestStr += String(id) + ";";
  }
  if (requestStr.length > 0) {
    requestStr = requestStr.substring(0, requestStr.length - 1);
  }

  window
    .open(
      "http://localhost:8080/api/medical-insurance/export_xls?request=" +
        requestStr,
      "_blank"
    )
    .focus();
}

defineExpose({ downloadCsv });
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
