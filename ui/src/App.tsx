import { Authenticated, I18nProvider, Refine } from "@refinedev/core";
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
import { API_URL } from "./constants";
import { ColorModeContextProvider } from "./contexts/color-mode";
import { BaseUnitList } from "./pages/commonData/baseUnits";
import { ForgotPassword } from "./pages/forgotPassword";
import { Login } from "./pages/authentication/login";
import { AddressList } from "./pages/masterData/addresses";
import { BusinessPartnerEdit, BusinessPartnerList } from "./pages/masterData/businessPartners";
import { ProductCategoryList } from "./pages/masterData/productCategories";
import { ProductFamilyList } from "./pages/masterData/productFamilies";
import { ProductGroupList } from "./pages/masterData/productGroups";
import { ProductCreate, ProductEdit, ProductList } from "./pages/masterData/products";
import { UnitMeasurementList } from "./pages/masterData/unitsMeasurement";
import { WarehouseList } from "./pages/masterData/warehouses";
import { Register } from "./pages/authentication/register";
import { UserGroupCreate, UserGroupEdit, UserGroupList } from "./pages/usersmanagement/groups";
import { RoleCreate, RoleEdit, RoleList } from "./pages/usersmanagement/roles";
import { UserCreate, UserEdit, UserList } from "./pages/usersmanagement/users";
import { resources } from "./resources";
import { dataProvider } from "./rest-data-provider";
import Dashboard from "./pages/donations/dashboard";
import { PurchaseOrderList } from "./pages/procurement/orders/list";
import { PurchaseOrderEdit } from "./pages/procurement/orders/edit";
import "./i18n";
import { useTranslation } from "react-i18next";
import { StockTakingList } from "./pages/inventoryHandling/stockTaking";


function App() {
  const { t, i18n } = useTranslation();

  const i18nProvider: I18nProvider = {
    translate: (key: string, options?: any, defaultMessage?: string) => t(key, options),
    changeLocale: (lang: string) => i18n.changeLanguage(lang),
    getLocale: () => i18n.language,
  };

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
              i18nProvider={i18nProvider}
              options={{
                syncWithLocation: true,
                warnWhenUnsavedChanges: false,
                useNewQueryKeys: true,
                mutationMode: "pessimistic",
                disableTelemetry: true,
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
                  <Route index element={<Dashboard />} />

                  <Route path="/addresses">
                    <Route index element={<AddressList />} />
                  </Route>

                  <Route path="/products">
                    <Route index element={<ProductList />} />
                    <Route path="create" element={<ProductCreate />} />
                    <Route path="edit/:id" element={<ProductEdit />} />

                    <Route path="categories">
                      <Route index element={<ProductCategoryList />} />
                      <Route path="create" />
                      <Route path="edit/:id" />
                    </Route>

                    <Route path="families">
                      <Route index element={<ProductFamilyList />} />
                      <Route path="create" />
                      <Route path="edit/:id" />
                    </Route>

                    <Route path="groups">
                      <Route index element={<ProductGroupList />} />
                      <Route path="create" />
                      <Route path="edit/:id" />
                    </Route>
                  </Route>

                  <Route path="stock">
                    <Route path="takings">
                      <Route index element={<StockTakingList />} />
                    </Route>
                  </Route>

                  <Route path="purchases">
                    <Route path="orders">
                      <Route index element={<PurchaseOrderList />} />
                      <Route path="edit/:id" element={<PurchaseOrderEdit />} />
                    </Route>
                  </Route>

                  <Route path="/units">
                    <Route index element={<BaseUnitList />} />
                    <Route path="measurement">
                      <Route index element={<UnitMeasurementList />} />
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

                  <Route path="/warehouses">
                    <Route index element={<WarehouseList />} />
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
