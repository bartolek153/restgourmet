import { Authenticated, Refine } from "@refinedev/core";
import { RefineKbar, RefineKbarProvider } from "@refinedev/kbar";

import {
  ErrorComponent,
  ThemedLayoutV2,
  ThemedSiderV2,
  useNotificationProvider,
} from "@refinedev/antd";
import "@refinedev/antd/dist/reset.css";
import { AiOutlineUser, AiTwotoneDatabase } from "react-icons/ai";

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
import { API_URL, COMMON_DATA_PARENT_MENU, EMPLOYEE_MANAGEMENT_PARENT_MENU, FINANCIALS_PARENT_MENU, INVENTORY_HANDLING_PARENT_MENU, MASTER_DATA_PARENT_MENU, RESTAURANT_MANAGEMENT_PARENT_MENU } from "./constants";
import { UserList } from "./pages/common/users/list";
import { dataProvider } from "./rest-data-provider";
import { UserCreate, UserEdit } from "./pages/common/users";

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
                  name: COMMON_DATA_PARENT_MENU, meta: {icon: <AiTwotoneDatabase />}
                },
                { name: MASTER_DATA_PARENT_MENU, meta: { icon: "" } },
                { name: FINANCIALS_PARENT_MENU, meta: { icon: "" } },
                { name: INVENTORY_HANDLING_PARENT_MENU, meta: { icon: "" } },
                { name: EMPLOYEE_MANAGEMENT_PARENT_MENU, meta: { icon: "" } },
                { name: RESTAURANT_MANAGEMENT_PARENT_MENU, meta: { icon: "" } },
                {
                  name: "users",
                  list: "/users",
                  create: "/users/create",
                  edit: "/users/edit/:id",
                  show: "/users/show/:id",
                  meta: {
                    parent: COMMON_DATA_PARENT_MENU,
                    canDelete: true,
                    icon: <AiOutlineUser />
                  }
                },
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
                  <Route
                    index
                    element={<NavigateToResource resource="users" />}
                  />
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
