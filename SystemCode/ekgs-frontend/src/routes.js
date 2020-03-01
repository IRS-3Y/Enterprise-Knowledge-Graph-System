import Dashboard from "@material-ui/icons/Dashboard";
import DashboardPage from "./views/Dashboard";

export default [
  {
    path: "/dashboard",
    name: "Dashboard",
    icon: Dashboard,
    component: DashboardPage
  },
  {
    path: "/",
    redirect: true,
    to: "/dashboard"
  }
];