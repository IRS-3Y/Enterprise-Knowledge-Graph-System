import render from "@lib/material-dashboard-react";

import routes from "./routes";

import logo from "./assets/img/reactlogo.png";
import image from "./assets/img/sidebar-4.jpg";

import NavbarLinks from "./components/layout/NavbarLinks";

render({
  layout: "Admin",
  sidebar: {
    name: "EKGS",
    logo, image
  },
  navbar: {
    links: {
      component: NavbarLinks
    }
  },
  footer: {
    name: "EKGS Team",
    href: "https://github.com/IRS-3Y/Enterprise-Knowledge-Graph-System",
    items: [{
      name: "GitHub",
      href: "https://github.com/IRS-3Y/Enterprise-Knowledge-Graph-System"
    }]
  },
  routes
});