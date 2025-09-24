import {
  DeleteButton,
  EditButton,
  List,
  TextField,
  useModal,
  useSelect,
  useTable
} from "@refinedev/antd";
import { useMany, type BaseRecord } from "@refinedev/core";
import {
  Button,
  Card,
  Checkbox,
  Col,
  Form,
  Input,
  Row,
  Select,
  Space,
  Switch,
  Table, Tabs
} from "antd";
import { AiOutlineSearch } from "react-icons/ai";


export const StockList = () => {
  const { tableProps, searchFormProps } = useTable({
    syncWithLocation: true,
    onSearch: (params: any) => {
      return [
        { field: "q", operator: "eq", value: params.q },
        { field: "productId", operator: "eq", value: params.productId },
        { field: "warehouseId", operator: "eq", value: params.warehouseId },
        { field: "hasStock", operator: "eq", value: params.hasStock },
      ];
    },
  });

  const vertRadioStyle: React.CSSProperties = {
    display: "flex",
    flexDirection: "column",
    gap: 8,
  };

  const { selectProps: pdSelectProps } = useSelect({
    resource: "products",
    optionLabel: "description",
  });

  const { selectProps: whSelectProps } = useSelect({
    resource: "warehouses",
    optionLabel: "name",
  });

  const { selectProps: umSelectProps } = useSelect({
    resource: "units/measurement",
    optionLabel: "description",
  });

  const unitIds: any = tableProps?.dataSource?.flatMap((record: any) => [
    record.minQtyUnitId,
    record.maxQtyUnitId,
  ]);
  const { data, isLoading } = useMany({
    resource: "units/measurement",
    ids: unitIds,
  });

  const filterPageDelayed = () => {
    setTimeout(() => {
      searchFormProps.form?.submit();
    }, 1000);
  };

  const { show, modalProps } = useModal({});

  return (
    <Tabs>
      <Tabs.TabPane key="1" tab="Listagem">
        <List>
          <Table
            {...tableProps}
            rowKey="id"
            pagination={{
              ...tableProps.pagination,
              showTotal: (total) => `Total ${total} items`,
              showQuickJumper: true,
              showSizeChanger: true,
            }}
            showSorterTooltip={false}
          >
            <Table.Column dataIndex="product.description" title={"Produto"} sorter={true} />
            <Table.Column dataIndex="warehouse.name" title={"Armazém"} sorter={true} />
            <Table.Column
              dataIndex="minQtyUnitId"
              sorter={true}
              title={"Unid. Qtd. Mínima"}
              render={(value) => {
                if (isLoading) {
                  return <TextField value="Loading..." />;
                }

                return <TextField value={data?.find((item) => item.id === value)?.description} />;
              }}
            />
            <Table.Column
              dataIndex="maxQtyUnitId"
              sorter={true}
              title={"Unid. Qtd. Máxima"}
              render={(value) => {
                if (isLoading) {
                  return <TextField value="Loading..." />;
                }

                return <TextField value={data?.find((item) => item.id === value)?.description} />;
              }}
            />
            <Table.Column dataIndex="qty" title={"Quantidade Atual"} />
            <Table.Column dataIndex="maxQty" title={"Qtd. Máx."} />
            <Table.Column dataIndex="minQty" title={"Qtd. Mín."} />
          </Table>
        </List>
      </Tabs.TabPane>
      <Tabs.TabPane key="2" tab="Dashboard"></Tabs.TabPane>
    </Tabs>
  );
}