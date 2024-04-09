<template>
  <NavBar />

  <div class="w-full max-w-[512px] mx-auto mt-6">
    <h1 class="font-bold text-2xl text-center">Mức lương cơ sở:</h1>

    <p class="mt-4 text-lg text-center">
      <b>Công thức tính mức đóng BHYT:</b><br />
      <i>Mức đóng BHYT = Mức lương cơ sở * 4.5% * số tháng</i>
    </p>

    <p class="mt-4 text-lg">
      <b>Mức lương cơ sở hiện tại:</b> {{ salaryComputed }}
    </p>

    <p class="text-center text-red-500 mt-6" v-if="!updateValid">
      Vui lòng điền mức lương cơ sở
    </p>

    <div v-if="isUpdatingSalary" class="mt-6">
      <label for="salary-input">Mức lương cơ sở mới:</label>
      <input
        type="number"
        min="0"
        id="salary-input"
        class="outline-none border border-gray-300 h-8 ms-2"
        v-model="newSalary"
      />

      <div class="flex justify-center">
        <button
          type="button"
          class="mx-auto block bg-red-500 py-1 px-4 text-white font-semibold mt-6 me-1"
          @click="confirmNewSalary"
        >
          Xác nhận
        </button>
        <button
          type="button"
          class="mx-auto block bg-red-500 py-1 px-4 text-white font-semibold mt-6 ms-1"
          @click="
            isUpdatingSalary = false;
            updateValid = true;
          "
        >
          Hủy
        </button>
      </div>
    </div>

    <button
      type="button"
      class="mx-auto block bg-red-500 py-1 px-4 text-white font-semibold mt-6"
      v-if="!isUpdatingSalary"
      @click="isUpdatingSalary = !isUpdatingSalary"
    >
      Cập nhật lương cơ sở
    </button>
  </div>
</template>

<script setup>
import NavBar from "@/components/NavBar.vue";
import { computed, ref } from "vue";
import {
  getSalaryAPI,
  updateSalaryAPI,
} from "@/services/modules/medicalInsurance.js";

const isUpdatingSalary = ref(false);
const newSalary = ref("");
const updateValid = ref(true);

// get basic salary
const getSalaryAPIRes = await getSalaryAPI();
const currentBasicSalary = ref(Number(getSalaryAPIRes.data.amount));

const salaryComputed = computed(() => {
  let salaryStr = String(currentBasicSalary.value);
  let finalStr = "";

  let countFlag = 0;
  for (let i = salaryStr.length - 1; i >= 0; i--) {
    finalStr = salaryStr[i] + finalStr;

    countFlag++;
    if (countFlag === 3 && i !== 0) {
      finalStr = "." + finalStr;
      countFlag = 0;
    }
  }

  return finalStr + "đ";
});

async function confirmNewSalary() {
  if (newSalary.value === "") {
    updateValid.value = false;
  } else {
    const oldSalary = currentBasicSalary.value;
    currentBasicSalary.value = Number(newSalary.value);
    isUpdatingSalary.value = false;
    updateValid.value = true;

    try {
      const res = await updateSalaryAPI(String(newSalary.value));
      if (res.status === 200) {
        alert("Đã cập nhật lương cơ sở thành công");
      }
    } catch (error) {
      console.log(error);
      alert("Đã có lỗi xảy ra");
      // roll-back
      currentBasicSalary.value = oldSalary;
    }
  }
}
</script>
