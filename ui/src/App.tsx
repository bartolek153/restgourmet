import { Authenticated, Refine } from "@refinedev/core";
import { RefineKbar, RefineKbarProvider } from "@refinedev/kbar";

import {
  ErrorComponent,
  ThemedLayoutV2,
  ThemedSiderV2,
  useNotificationProvider,
} from "@refinedev/antd";
import "@refinedev/antd/dist/reset.css";

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
import {
  API_URL,
} from "./constants";
import { UserList } from "./pages/usersmanagement/users/list";
import { dataProvider } from "./rest-data-provider";
import { UserCreate, UserEdit } from "./pages/usersmanagement/users";
import { ProductCategoryList } from "./pages/masterData/productCategories/list";
import { RoleCreate, RoleEdit, RoleList } from "./pages/usersmanagement/roles";
import { UserGroupCreate, UserGroupEdit, UserGroupList } from "./pages/usersmanagement/groups";
import { AddressList } from "./pages/masterData/addresses/list";
import { AddressForm } from "./pages/masterData/addresses";
import { resources } from "./resources";
import { BusinessPartnerCreateForm, BusinessPartnerEdit, BusinessPartnerList } from "./pages/masterData/businessPartners";

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
              resources={resources}
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

                  <Route path="/addresses">
                    <Route index element={<AddressList />} />
                    <Route path="create" element={<AddressForm modalProps={{}} formProps={{}} />} />
                    <Route path="edit/:id" element={<AddressForm modalProps={{}} formProps={{}} />} />
                  </Route>

                  <Route path="/partners">
                    <Route index element={<BusinessPartnerList/>} />
                    <Route path="create" element={<BusinessPartnerCreateForm modalProps={{}} formProps={{}}/>} />
                    <Route path="edit/:id" element={<BusinessPartnerEdit />} />
                    <Route path="customers">
                      <Route index />
                      <Route path="create" />
                      <Route path="edit/:id" />
                    </Route>
                  </Route>

                  <Route path="/users">
                    <Route index element={<UserList />} />
                    <Route path="create" element={<UserCreate />} />
                    <Route path="edit/:id" element={<UserEdit />} />
                    <Route path="roles">
                      <Route index element={<RoleList />} />
                      <Route path="create" element={<RoleCreate />} />
                      <Route path="edit/:id" element={<RoleEdit />} />
                    </Route>
                    <Route path="groups">
                      <Route index element={<UserGroupList />} />
                      <Route path="create" element={<UserGroupCreate />} />
                      <Route path="edit/:id" element={<UserGroupEdit />} />
                    </Route>
                  </Route>
                  <Route path="*" element={<ErrorComponent />} />
                </Route>

                <Route
                  element={
                    <Authenticated key="authenticated-outer" fallback={<Outlet />}>
                      <NavigateToResource />
                    </Authenticated>
                  }
                >
                  <Route path="/login" element={<Login />} />
                  <Route path="/register" element={<Register />} />
                  <Route path="/forgot-password" element={<ForgotPassword />} />
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
