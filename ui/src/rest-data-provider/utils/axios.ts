import { HttpError } from "@refinedev/core";
import axios from "axios";

const axiosInstance = axios.create({
  withCredentials: true,
});

axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    // if (error.response.status === 401) {

    //   // make sure to return the new promise
    //   const response = await axiosInstance
    //     .post(`${API_URL}/auth/refresh`);

    //   return await axiosInstance.request(error.config);
    // }

    // not a 401, simply fail the response
    const customError: HttpError = {
      ...error,
      message: error.response?.data?.message,
      statusCode: error.response?.status,
      errors: error.response?.data?.errors,
    };

    return Promise.reject(customError);
  }
);

export { axiosInstance };
