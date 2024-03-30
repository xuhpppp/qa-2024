const routes = [
  {
    path: "/dang-nhap",
    name: "Login Page",
    meta: {
      title: "BHYT | Đăng nhập",
    },
    component: () => import("@/views/LoginView.vue"),
  },
  {
    path: "/danh-sach-bhyt",
    name: "Insurance List Page",
    meta: {
      title: "BHYT | Danh sách",
    },
    component: () => import("@/views/InsuranceListView.vue"),
  },
];

export default routes;
