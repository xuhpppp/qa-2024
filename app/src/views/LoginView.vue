<template>
  <Form
    class="w-full max-w-[512px] mx-auto mt-16"
    :validation-schema="loginSchema"
    @submit="onLoginSubmit"
  >
    <div class="flex w-fit mx-auto">
      <img src="../assets/images/logo.svg" class="h-20" />
      <img src="../assets/images/banner_chu.svg" class="h-14 mt-2 mx-2" />
    </div>

    <h1 class="uppercase font-bold text-3xl text-center mt-4">Đăng nhập</h1>

    <div class="flex flex-col w-fit mx-auto mt-4">
      <label for="username-input" class="text-lg font-semibold ms-2"
        >Tên đăng nhập (*)</label
      >
      <Field
        type="text"
        placeholder="Tên đăng nhập..."
        name="username"
        id="username-input"
        class="h-12 outline-none ps-3 border border-gray-300 focus:border-black duration-300 mt-1 w-[400px]"
      />
      <ErrorMessage name="username" class="text-red-500 mt-2" />
    </div>

    <div class="flex flex-col w-fit mx-auto mt-4">
      <label for="password-input" class="text-lg font-semibold ms-2"
        >Mật khẩu (*)</label
      >
      <Field
        type="text"
        placeholder="Mật khẩu..."
        name="password"
        id="password-input"
        class="h-12 outline-none ps-3 border border-gray-300 focus:border-black duration-300 mt-1 w-[400px]"
      />
      <ErrorMessage name="password" class="text-red-500 mt-2" />
    </div>

    <button
      type="submit"
      class="bg-blue-400 text-white w-32 h-9 font-semibold rounded duration-300 hover:bg-blue-200 mt-8 mx-auto block"
    >
      Đăng nhập
    </button>
  </Form>
</template>

<script setup>
import router from "@/router";
import { Form, Field, ErrorMessage } from "vee-validate";
import * as Yup from "yup";

const loginSchema = Yup.object().shape({
  username: Yup.string().required("Tên đăng nhập không được bỏ trống"),
  password: Yup.string().required("Mật khẩu không được bỏ trống"),
});

function onLoginSubmit(values) {
  console.log(values);
  router.push("/danh-sach-bhyt");
}
</script>
