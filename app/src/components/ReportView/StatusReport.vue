<template>
  <div class="w-full mt-8">
    <label for="region-select" class="font-semibold ms-6"
      >Tình trạng thẻ:</label
    >
    <select
      id="region-select"
      class="outline-none ms-4 mt-4"
      v-model="searchObj.isValid"
      @change="selectHandle"
    >
      <option value="" selected disabled>---</option>
      <option value="valid">Hợp lệ</option>
      <option value="invalid">Quá hạn</option>
    </select>

    <label for="region-select" class="font-semibold ms-6">Tỉnh thành:</label>
    <select
      id="region-select"
      class="outline-none ms-4 mt-4"
      v-model="searchObj.city"
      @change="selectHandle"
    >
      <option value="" selected disabled>---</option>
      <option value="north">Miền Bắc</option>
      <option value="mid">Miền Trung</option>
      <option value="south">Miền Nam</option>
    </select>

    <button
      type="button"
      class="mx-auto block bg-red-500 py-1 px-4 text-white font-semibold mt-8"
      @click="onSearchSubmit"
    >
      Tra cứu
    </button>

    <p class="text-center text-red-500 mt-6" v-if="blankAlert">
      Phải điền đủ cả 2 trường thông tin
    </p>

    <button
      type="button"
      class="mx-auto block bg-red-500 py-1 px-4 text-white font-semibold mt-6"
      v-if="exportValid"
    >
      Xuất báo cáo
    </button>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";

const searchObj = reactive({
  isValid: ref(""),
  city: ref(""),
});
const blankAlert = ref(false);
const exportValid = ref(false);

function onSearchSubmit() {
  // all the fields must not be blank
  blankAlert.value = Object.values(searchObj).some((value) => value === "");
}

// checking for export file available
function selectHandle() {
  const validFlag = Object.values(searchObj).every((value) => value !== "");
  exportValid.value = validFlag;
}
</script>
