<template>
  <NavBar />

  <div class="w-full max-w-[512px] mx-auto mt-6">
    <div class="flex mx-auto w-fit">
      <div
        class="h-14 w-60 font-semibold text-center border border-gray-300 leading-[56px] duration-300 hover:text-white hover:bg-black hover:cursor-pointer"
        @click="changeCurrentState(0)"
        :class="[
          currentState === 0 ? 'bg-black text-white' : 'bg-white text-black',
        ]"
      >
        Báo cáo tình trạng thẻ BHYT
      </div>

      <div
        class="h-14 w-60 font-semibold text-center border border-gray-300 leading-[56px] duration-300 hover:text-white hover:bg-black hover:cursor-pointer"
        @click="changeCurrentState(1)"
        :class="[
          currentState === 1 ? 'bg-black text-white' : 'bg-white text-black',
        ]"
      >
        Báo cáo mức hưởng thẻ BHYT
      </div>
    </div>

    <StatusReport v-if="currentState === 0" />
    <EarningOptionReport v-if="currentState === 1" />
  </div>
</template>

<script setup>
import NavBar from "@/components/NavBar.vue";
import { ref } from "vue";
import StatusReport from "@/components/ReportView/StatusReport.vue";
import EarningOptionReport from "@/components/ReportView/EarningOptionReport.vue";

const currentState = ref(0);

function changeCurrentState(newState) {
  currentState.value = newState;
}
</script>
