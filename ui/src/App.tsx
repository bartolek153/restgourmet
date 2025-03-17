import { Authenticated, Refine } from "@refinedev/core";
import { RefineKbar, RefineKbarProvider } from "@refinedev/kbar";

import {
  ErrorComponent,
  ThemedLayoutV2,
  ThemedSiderV2,
  useNotificationProvider,
} from "@refinedev/antd";
import "@refinedev/antd/dist/reset.css";
import { AiOutlineTable, AiOutlineUser, AiTwotoneDatabase } from "react-icons/ai";

import routerBindings, {
  CatchAllNavigate,
  DocumentTitleHandler,
  NavigateToResource,
  UnsavedChangesNotifier,
} from "@refinedev/react-router";
import { App as AntdApp } from "antd";
import { BrowserRouter, Outlet, Route, Routes } from "react-router";
import { authProvider } from "./authProvider";
import { Header } from "./components/header";
import { ColorModeContextProvider } from "./contexts/color-mode";
import { ForgotPassword } from "./pages/forgotPassword";
import { Login } from "./pages/login";
import { Register } from "./pages/register";
import { API_URL, COMMON_DATA_PARENT_MENU, EMPLOYEE_MANAGEMENT_PARENT_MENU, FINANCIALS_PARENT_MENU, INVENTORY_HANDLING_PARENT_MENU, MASTER_DATA_PARENT_MENU, RESTAURANT_MANAGEMENT_PARENT_MENU, USER_MANAGEMENT_PARENT_MENU } from "./constants";
import { UserList } from "./pages/usersmanagement/users/list";
import { dataProvider } from "./rest-data-provider";
import { UserCreate, UserEdit } from "./pages/usersmanagement/users";
import { ProductCategoryList } from "./pages/masterData/productCategories/list";
import { ProductCategoryForm } from "./pages/masterData/productCategories";

function App() {
  return (
    <BrowserRouter>
      <RefineKbarProvider>
        <ColorModeContextProvider>
          <AntdApp>
            <Refine
              dataProvider={dataProvider(`${API_URL}`)}
              notificationProvider={useNotificationProvider}
              routerProvider={routerBindings}
              authProvider={authProvider}
              resources={[
                {
                  name: COMMON_DATA_PARENT_MENU, meta: { icon: <AiTwotoneDatabase /> }
                },
                { name: MASTER_DATA_PARENT_MENU, meta: { icon: <AiOutlineTable /> } },
                { name: FINANCIALS_PARENT_MENU, meta: { icon: "" } },
                { name: INVENTORY_HANDLING_PARENT_MENU, meta: { icon: "" } },
                { name: EMPLOYEE_MANAGEMENT_PARENT_MENU, meta: { icon: "" } },
                { name: RESTAURANT_MANAGEMENT_PARENT_MENU, meta: { icon: "" } },
                {
                  name: "users",
                  list: "/users",
                  create: "/users/create",
                  edit: "/users/edit/:id",
                  meta: {
                    parent: USER_MANAGEMENT_PARENT_MENU,
                    canDelete: true,
                    icon: <AiOutlineUser />
                  }
                },
                {
                  name: "/units/base",
                  list: "/units/base",
                  create: "/units/base/create",
                  edit: "/units/base/edit/:id",
                  meta: {
                    label: "Base Units",
                    parent: COMMON_DATA_PARENT_MENU,
                    canDelete: true,
                  }
                },
                {
                  name: "products/categories",
                  list: "/products/categories",
                  create: "/products/categories/create",
                  edit: "/products/categories/edit/:id",
                  meta: {
                    label: "Product Categories",
                    parent: MASTER_DATA_PARENT_MENU,
                    canDelete: true,
                  }
                },
                {
                  name: "products/families",
                  list: "/products/families",
                  create: "/products/families/create",
                  edit: "/products/families/edit/:id",
                  meta: {
                    label: "Product Families",
                    parent: MASTER_DATA_PARENT_MENU,
                    canDelete: true,
                  }
                },
                {
                  name: "product/groups",
                  list: "/products/groups",
                  create: "/products/groups/create",
                  edit: "/products/groups/edit/:id",
                  meta: {
                    label: "Product Groups",
                    parent: MASTER_DATA_PARENT_MENU,
                    canDelete: true,
                  }
                },
                {
                  name: "products",
                  list: "/products",
                  create: "/products/create",
                  edit: "/products/edit/:id",
                  meta: {
                    parent: MASTER_DATA_PARENT_MENU,
                    canDelete: true,
                  }
                },
                {
                  name: "units/measurements",
                  list: "/units/measurements",
                  create: "/units/measurements/create",
                  edit: "/units/measurements/edit/:id",
                  meta: {
                    label: "Measurement Units",
                    parent: MASTER_DATA_PARENT_MENU,
                    canDelete: true,
                  }
                }
              ]}
              options={{
                syncWithLocation: true,
                warnWhenUnsavedChanges: false,
                useNewQueryKeys: true,
                projectId: "wD3FKj-uGlLKe-6ouCYb",
              }}
            >
              <Routes>
                <Route
                  element={
                    <Authenticated
                      key="authenticated-inner"
                      fallback={<CatchAllNavigate to="/login" />}
                    >
                      <ThemedLayoutV2
                        Header={Header}
                        Sider={(props) => <ThemedSiderV2 {...props} fixed />}
                      >
                        <Outlet />
                      </ThemedLayoutV2>
                    </Authenticated>
                  }
                >
                  <Route index element={<NavigateToResource resource="users" />} />
                  <Route path="/products">
                    <Route path="categories">
                      <Route index element={<ProductCategoryList />} />
                    </Route>
                  </Route>
                  
                  <Route path="/users">
                    <Route index element={<UserList />} />
                    <Route path="create" element={<UserCreate />} />
                    <Route path="edit/:id" element={<UserEdit />} />
                  </Route>
                  <Route path="*" element={<ErrorComponent />} />
                </Route>

                <Route
                  element={
                    <Authenticated
                      key="authenticated-outer"
                      fallback={<Outlet />}
                    >
                      <NavigateToResource />
                    </Authenticated>
                  }
                >
                  <Route path="/login" element={<Login />} />
                  <Route path="/register" element={<Register />} />
                  <Route
                    path="/forgot-password"
                    element={<ForgotPassword />}
                  />
                </Route>
              </Routes>

              <RefineKbar />
              <UnsavedChangesNotifier />
              <DocumentTitleHandler />
            </Refine>
          </AntdApp>
        </ColorModeContextProvider>
      </RefineKbarProvider>
    </BrowserRouter>
  );
}

export default App;
