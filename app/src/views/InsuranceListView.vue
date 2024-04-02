<template>
  <NavBar />

  <div class="w-full max-w-[512px] mx-auto mt-6">
    <h1 class="font-bold text-2xl text-center">Tra cứu BHYT</h1>

    <div class="mt-6 flex justify-center">
      <div class="me-2">
        <label for="insurance-id-input" class="font-semibold"
          >Mã số BHYT:</label
        >
        <br />
        <input
          type="text"
          id="insurance-id-input"
          class="outline-none border border-gray-300 duration-300 h-10 w-52 focus:border-black ps-2"
          placeholder="Mã số BHYT..."
          v-model="searchObj.insurranceCode"
        />
      </div>

      <div class="ms-2">
        <label for="fullname-input" class="font-semibold">Họ tên:</label>
        <br />
        <input
          type="text"
          id="fullname-input"
          class="outline-none border border-gray-300 duration-300 h-10 w-52 focus:border-black ps-2"
          placeholder="Họ và tên..."
          v-model="searchObj.name"
        />
      </div>
    </div>

    <div class="w-full">
      <label for="region-select" class="font-semibold ms-6">Tỉnh thành:</label>
      <select
        id="region-select"
        class="outline-none ms-4 mt-4"
        v-model="searchObj.city"
      >
        <option value="" selected disabled>---</option>
        <option value="north">Miền Bắc</option>
        <option value="mid">Miền Trung</option>
        <option value="south">Miền Nam</option>
      </select>

      <label for="region-select" class="font-semibold ms-6">Nơi đăng ký:</label>
      <select
        id="region-select"
        class="outline-none ms-4 mt-4"
        v-model="searchObj.registrationPlace"
      >
        <option value="" selected disabled>---</option>
        <option value="north">Miền Bắc</option>
        <option value="mid">Miền Trung</option>
        <option value="south">Miền Nam</option>
      </select>

      <label for="region-select" class="font-semibold ms-6"
        >Tình trạng thẻ:</label
      >
      <select
        id="region-select"
        class="outline-none ms-4 mt-4"
        v-model="searchObj.isValid"
      >
        <option value="" selected disabled>---</option>
        <option value="valid">Hợp lệ</option>
        <option value="invalid">Quá hạn</option>
      </select>

      <label for="region-select" class="font-semibold ms-6"
        >5 năm liên tục:</label
      >
      <select
        id="region-select"
        class="outline-none ms-4 mt-4"
        v-model="searchObj.fiveYearsContinuos"
      >
        <option value="" selected disabled>---</option>
        <option value="true">Rồi</option>
        <option value="false">Chưa</option>
      </select>
    </div>

    <button
      type="button"
      class="mx-auto block bg-red-500 py-1 px-4 text-white font-semibold mt-8"
      @click="onSearchSubmit"
    >
      Tra cứu
    </button>

    <p class="text-center text-red-500 mt-6" v-if="blankAlert">
      Vui lòng điền ít nhất 1 trường thông tin
    </p>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import NavBar from "../components/NavBar.vue";

const searchObj = reactive({
  insurranceCode: ref(""),
  name: ref(""),
  city: ref(""),
  registrationPlace: ref(""),
  isValid: ref(""),
  fiveYearsContinuos: ref(""),
});
const blankAlert = ref(false);

function onSearchSubmit() {
  // check there is at least 1 field having data
  blankAlert.value = !Object.values(searchObj).some((value) => value !== "");
}
</script>
