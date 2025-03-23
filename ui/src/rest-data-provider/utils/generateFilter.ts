import { mapOperator } from "./mapOperator";
import type { CrudFilters } from "@refinedev/core";

export const generateFilter = (filters?: CrudFilters) => {
  const queryFilters: { [key: string]: string } = {};

  if (filters) {
    filters.map((filter) => {
      // WARN: This default code was commented out
      // because it is not used in the current app..
      //
      // if (filter.operator === "or" || filter.operator === "and") {
      //   throw new Error(
      //     `[@refinedev/simple-rest]: \`operator: ${filter.operator}\` is not supported. You can create custom data provider. https://refine.dev/docs/api-reference/core/providers/data-provider/#creating-a-data-provider`
      //   );
      // }
      //
      // if ("field" in filter) {
      //   const { field, operator, value } = filter;
      //
      //   if (field === "q") {
      //     queryFilters[field] = value;
      //     return;
      //   }
      //
      //   const mappedOperator = mapOperator(operator);
      //   queryFilters[`${field}${mappedOperator}`] = value;
      // }

      if ("field" in filter) {
        const { field, value } = filter;
        queryFilters[field] = value;
      }
    });
  }

  return queryFilters;
};
