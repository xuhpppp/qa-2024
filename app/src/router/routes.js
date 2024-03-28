const routes = [
  {
    path: "/login",
    name: "Login Page",
    meta: {
      title: "BHYT | Đăng nhập",
    },
    component: () => import("@/views/LoginView.vue"),
  },
];

export default routes;
