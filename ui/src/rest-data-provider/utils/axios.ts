import { HttpError } from "@refinedev/core";
import axios from "axios";
import { API_URL } from "../../constants";

const axiosInstance = axios.create({
  withCredentials: true,
});

axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response.status === 401) {
      await axios.create({ withCredentials: true }).post(`${API_URL}/auth/refresh`);

      return await axiosInstance.request(error.config);
    }

    // not a 401, simply fail the response
    const customError: HttpError = {
      ...error,
      message: error.response?.data?.message || error.reponse?.data?.error,
      statusCode: error.response?.status,
      errors: error.response?.data?.errors,
    };

    return Promise.reject(customError);
  }
);

export { axiosInstance };
