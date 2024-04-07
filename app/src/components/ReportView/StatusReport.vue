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
      <option v-for="city in citiesList" :value="city" :key="city">
        {{ city }}
      </option>
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
      v-if="insuranceList.length > 0"
      @click="exportCsv"
    >
      Xuất báo cáo
    </button>
  </div>

  <InsuranceTable
    v-if="insuranceList.length > 0"
    :insurance-list="insuranceList"
    class="w-[1000px] relative -left-1/2"
    ref="insuranceTableComp"
  />

  <div class="mx-auto w-fit mt-8" v-if="!firstSearch">
    <p>Hiện đang xem kết quả tại trang {{ page + 1 }}</p>
    <label>Trang muốn xem: </label>
    <input
      type="number"
      min="0"
      class="outline-none border border-black w-10 px-1"
      v-model="pageInput"
    />
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import { citiesList } from "@/utils/variables.js";
import { insuranceListView } from "@/services/modules/medicalInsurance.js";
import InsuranceTable from "../InsuranceTable.vue";

const searchObj = reactive({
  isValid: ref(""),
  city: ref(""),
});
const blankAlert = ref(false);
const insuranceList = ref([]);
const page = ref(0);
const pageInput = ref("");
const firstSearch = ref(true);

const insuranceTableComp = ref(null);

async function onSearchSubmit() {
  // all the fields must not be blank
  blankAlert.value = Object.values(searchObj).some((value) => value === "");

  if (!blankAlert.value) {
    try {
      firstSearch.value = false;

      if (pageInput.value !== "") {
        if (Number(pageInput.value) >= 0) {
          page.value = Number(pageInput.value) - 1;
        } else {
          page.value = 0;
        }
      }

      const res = await insuranceListView(searchObj, page.value);
      insuranceList.value = res.data;
    } catch (error) {
      console.log(error);
    }
  }
}

function exportCsv() {
  insuranceTableComp.value.downloadCsv();
}
</script>
