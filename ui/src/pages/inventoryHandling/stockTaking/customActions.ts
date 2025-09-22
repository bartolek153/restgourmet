import { useApiUrl, useCustom, useResourceParams } from "@refinedev/core";

export const useProcessStockTaking = () => {
  const apiUrl = useApiUrl();
  const { resource, id } = useResourceParams();

  return useCustom({
    url: `${apiUrl}/${resource}/${id}/process`,
    method: "post"
  });
};
