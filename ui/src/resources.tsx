import { AiOutlineTable, AiOutlineUser, AiTwotoneDatabase } from "react-icons/ai";
import { FaUsers } from "react-icons/fa";
import { FaClipboardUser } from "react-icons/fa6";
import { LuBriefcaseBusiness } from "react-icons/lu";
import { MdOutlineShoppingCart } from "react-icons/md";
import {
  COMMON_DATA_PARENT_MENU,
  FINANCIALS_PARENT_MENU,
  INVENTORY_HANDLING_PARENT_MENU,
  MASTER_DATA_PARENT_MENU,
  PROCUREMENT_PARENT_MENU,
  USER_MANAGEMENT_PARENT_MENU,
} from "./constants";

export const resources = [
  {
    name: COMMON_DATA_PARENT_MENU,
    meta: { icon: <AiTwotoneDatabase /> },
  },
  { name: MASTER_DATA_PARENT_MENU, meta: { icon: <AiOutlineTable /> } },
  { name: FINANCIALS_PARENT_MENU, meta: { icon: "" } },
  { name: INVENTORY_HANDLING_PARENT_MENU, meta: { icon: "" } },
  { name: PROCUREMENT_PARENT_MENU, meta: { icon: <MdOutlineShoppingCart/> }},
  { name: USER_MANAGEMENT_PARENT_MENU, meta: { icon: <FaClipboardUser /> } },
  {
    name: "addresses",
    list: "/addresses",
    create: "/addresses/create",
    edit: "/addresses/edit/:id",
    meta: {
      parent: MASTER_DATA_PARENT_MENU,
      canDelete: true,
      label: "Endereços"
    },
  },
  {
    name: "partners/customers",
    create: "/partners/:partnerId/customers/create",
    edit: "/partners/:id/customers/edit",
    meta: {
      label: "Customers",
      canDelete: true,
    },
  },
  {
    name: "users",
    list: "/users",
    create: "/users/create",
    edit: "/users/edit/:id",
    meta: {
      parent: USER_MANAGEMENT_PARENT_MENU,
      canDelete: true,
      icon: <AiOutlineUser />,
      label: "Usuários"
    },
  },
  {
    name: "users/roles",
    list: "/users/roles",
    create: "/users/roles/create",
    edit: "/users/roles/edit/:id",
    meta: {
      label: "Funções",
      parent: USER_MANAGEMENT_PARENT_MENU,
      canDelete: true,
      icon: <LuBriefcaseBusiness />,
    },
  },
  {
    name: "users/groups",
    list: "/users/groups",
    create: "/users/groups/create",
    edit: "/users/groups/edit/:id",
    meta: {
      label: "Grupos",  
      parent: USER_MANAGEMENT_PARENT_MENU,
      canDelete: true,
      icon: <FaUsers />,
    },
  },
  {
    name: "units",
    list: "/units",
    create: "/units/create",
    edit: "/units/edit/:id",
    meta: {
      label: "Unidades básicas",
      parent: COMMON_DATA_PARENT_MENU,
      canDelete: true,
    },
  },
  {
    name: "units/measurement",
    list: "/units/measurement",
    create: "/units/measurement/create",
    edit: "/units/measurement/edit/:id",
    meta: {
      label: "Unidades de medida",
      parent: MASTER_DATA_PARENT_MENU,
      canDelete: true,
    },
  },
  {
    name: "partners",
    list: "/partners",
    create: "/partners/create",
    edit: "/partners/:id/edit",
    meta: {
      label: "Parceiros",
      parent: MASTER_DATA_PARENT_MENU,
      canDelete: true,
    },
  },
  {
    name: "products/categories",
    list: "/products/categories",
    create: "/products/categories/create",
    edit: "/products/categories/edit/:id",
    meta: {
      label: "Categorias de Produto",
      parent: MASTER_DATA_PARENT_MENU,
      canDelete: true,
    },
  },
  {
    name: "products/families",
    list: "/products/families",
    create: "/products/families/create",
    edit: "/products/families/edit/:id",
    meta: {
      label: "Famílias de Produto",
      parent: MASTER_DATA_PARENT_MENU,
      canDelete: true,
    },
  },
  {
    name: "products/groups",
    list: "/products/groups",
    create: "/products/groups/create",
    edit: "/products/groups/edit/:id",
    meta: {
      label: "Grupos de Produto",
      parent: MASTER_DATA_PARENT_MENU,
      canDelete: true,
    },
  },
  {
    name: "products",
    list: "/products",
    create: "/products/create",
    edit: "/products/edit/:id",
    meta: {
      label: "Produtos",
      parent: MASTER_DATA_PARENT_MENU,
      canDelete: true,
    },
  },
  {
    name: "purchases/orders",
    list: "/purchases/orders",
    create: "/purchases/create",
    meta: {
      parent: PROCUREMENT_PARENT_MENU,
      canDelete: true,
      label: "Pedidos"
    }
  },
  {
    name: "warehouses",
    list: "/warehouses",
    create: "/warehouses/create",
    edit: "/warehouses/edit/:id",
    meta: {
      label: "Armazéns",
      parent: MASTER_DATA_PARENT_MENU,
      canDelete: true,
    },
  },
];
