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
  {
    path: "/bao-cao",
    name: "Insurance Report Page",
    meta: {
      title: "BHYT | Báo cáo",
    },
    component: () => import("@/views/ReportView.vue"),
  },
  {
    path: "/cau-hinh-bhyt",
    name: "Base Salary Config View",
    meta: {
      title: "BHYT | Cấu hình",
    },
    component: () => import("@/views/BaseSalaryConfigView.vue"),
  },
];

export default routes;
