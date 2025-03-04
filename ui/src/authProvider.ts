import type { AuthProvider } from "@refinedev/core";
import { API_URL, IDENTITY_KEY, TOKEN_KEY } from "./constants";
import { axiosInstance } from "./rest-data-provider/utils/axios";
import { IUser } from "./components/header";

export const authProvider: AuthProvider = {
  login: async ({ username, email, password }) => {
    if ((username || email) && password) {
      try {
        await axiosInstance.post(`${API_URL}/auth/login`, { identifier: username || email, password });
        
        localStorage.setItem(TOKEN_KEY, username); 
        
        await axiosInstance.get(`${API_URL}/users/profile`)
          .then((res) => {
            localStorage.setItem(IDENTITY_KEY, JSON.stringify(res.data));
          });

        return { 
          success: true, 
          redirectTo: "/" 
        };
      } catch (error: any) {

        return {
          success: false,
          error: {
            name: "Login error",
            message: error.message
          },
        };
      }
    }
    
    return {
      success: false,
      error: {
        name: "LoginError",
        message: "Invalid username or password",
      },
    };
  },
  logout: async () => {
    localStorage.removeItem(TOKEN_KEY);
    return {
      success: true,
      redirectTo: "/login",
    };
  },
  check: async () => {
    const token = localStorage.getItem(TOKEN_KEY);
    if (token) {
      return {
        authenticated: true,
      };
    }

    return {
      authenticated: false,
      redirectTo: "/login",
    };
  },
  getPermissions: async () => null,
  getIdentity: async () => {
    const idt = localStorage.getItem(IDENTITY_KEY);
    if (idt)
      return JSON.parse(idt) as IUser;
    return null;
  },
  onError: async (error) => {
    console.error(error);
    return { error };
  },
};
