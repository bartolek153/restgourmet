import { gold, green, red } from "@ant-design/colors";
import { CheckCircleFilled, DashboardOutlined, ExclamationCircleFilled, StopOutlined, UnorderedListOutlined, WarningFilled } from "@ant-design/icons";
import {
  EditButton,
  FilterDropdown,
  List,
  SaveButton,
  TextField,
  useEditableTable,
  useSelect
} from "@refinedev/antd";
import { useMany } from "@refinedev/core";
import {
  Button,
  Form,
  Input,
  Select,
  Space,
  Table, Tabs
} from "antd";
import { useState } from "react";


export const StockList = () => {
  const [editingUmId, setEditingUmId] = useState();
  const {
    tableProps,
    searchFormProps,
    formProps,
    isEditing,
    setId: setEditId,
    saveButtonProps,
    cancelButtonProps,
    editButtonProps, } = useEditableTable({
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

  const { selectProps: umSelectInputCellProps } = useSelect({
    resource: "units/measurement",
    optionLabel: "description",
    onSearch: (value) => [
      {  // TODO: fix
        field: "referenceUnitId",
        operator: "eq",
        value: editingUmId
      }
    ]
  });

  const unitIds: any = tableProps?.dataSource?.flatMap((record: any) => [
    record.minQtyUnitId,
    record.maxQtyUnitId,
  ]);
  const { data, isLoading: isLoadingUm } = useMany({
    resource: "units/measurement",
    ids: unitIds,
  });

  return (
    <List>
      <Tabs>
        <Tabs.TabPane key="1" tab="Listagem" icon={<UnorderedListOutlined />}>
          <Form {...formProps}>
            <Table
              {...tableProps}
              rowKey="id"
              onRow={(record) => ({
                onClick: (event: any) => {
                  if (event.target.nodeName === "TD") {
                    setEditId && setEditId(record.id);
                    setEditingUmId && setEditingUmId(record.stockUnitId)
                  }
                }
              })}
              pagination={{
                ...tableProps.pagination,
                showTotal: (total) => `Total ${total} items`,
                showQuickJumper: true,
                showSizeChanger: true,
              }}
              showSorterTooltip={false}
            >
              <Table.Column
                dataIndex={["product", "description"]}
                title={"Produto"}
                key={"productId"}
                sorter={true}
                filterDropdown={(props) => (
                  <FilterDropdown {...props}>
                    <Select allowClear {...pdSelectProps} style={{ minWidth: 200 }} />
                  </FilterDropdown>
                )}
              />
              <Table.Column
                dataIndex={["warehouse", "name"]}
                title={"Armazém"}
                key={"warehouseId"}
                sorter={true}
                filterDropdown={(props) => (
                  <FilterDropdown {...props}>
                    <Select allowClear {...whSelectProps} style={{ minWidth: 200 }} />
                  </FilterDropdown>
                )}
              />
              <Table.Column
                dataIndex="qty"
                width={100}
                title={"Qtd. Disponível"}
                sorter={true}
                render={(value, record) => {
                  let icon;

                  switch (record.level) {
                    case "OUT_OF_STOCK":
                      icon = <ExclamationCircleFilled style={{ color: red[5] }} />
                      break;
                    case "BELOW_MIN":
                    case "ABOVE_MAX":
                      icon = <WarningFilled style={{ color: gold[6] }} />
                      break;
                    case "WITHIN_RANGE":
                      icon = <CheckCircleFilled style={{ color: green[6] }} />
                      break;
                  }

                  return (
                    <Space align="baseline">
                      {value}
                      {icon}
                    </Space>
                  )
                }}
              />
              <Table.Column
                dataIndex="stockUnitId"
                sorter={true}
                title={"Unidade"}
                render={(value, record) => {
                  if (isLoadingUm) {
                    return <TextField value="Loading..." />;
                  }

                  return <TextField value={data?.find((item) => item.id === value)?.description} />;
                }}
                filterDropdown={(props) => (
                  <FilterDropdown {...props}>
                    <Select allowClear {...umSelectProps} style={{ minWidth: 200 }} />
                  </FilterDropdown>
                )}
              />
              <Table.Column
                dataIndex="minQty"
                title={"Qtd. Mín."}
                sorter={true}
                width={100}
                render={(value, record) => {
                  if (isEditing(record.id)) {
                    return (
                      <Form.Item name="minQty" style={{ margin: 0, minWidth: 100 }}>
                        <Input />
                      </Form.Item>
                    );
                  }
                  return <TextField value={value} />;
                }}
              />
              <Table.Column
                dataIndex="minQtyUnitId"
                sorter={true}
                title={"Unid. Qtd. Mínima"}
                render={(value, record) => {
                  if (isLoadingUm) {
                    return <TextField value="Loading..." />;
                  } else if (isEditing(record.id)) {
                    return (
                      <Form.Item name="minQtyUnitId" style={{ margin: 0 }}>
                        <Select allowClear {...umSelectInputCellProps} />
                      </Form.Item>
                    );
                  }

                  return <TextField value={data?.find((item) => item.id === value)?.description} />;
                }}
                filterDropdown={(props) => (
                  <FilterDropdown {...props}>
                    <Select allowClear {...umSelectProps} style={{ minWidth: 200 }} />
                  </FilterDropdown>
                )}
              />
              <Table.Column
                dataIndex="maxQty"
                title={"Qtd. Máx."}
                sorter={true}
                width={100}
                render={(value, record) => {
                  if (isEditing(record.id)) {
                    return (
                      <Form.Item name="maxQty" style={{ margin: 0, minWidth: 100 }}>
                        <Input />
                      </Form.Item>
                    );
                  }
                  return <TextField value={value} />;
                }}
              />
              <Table.Column
                dataIndex="maxQtyUnitId"
                sorter={true}
                title={"Unid. Qtd. Máxima"}
                render={(value, record) => {
                  if (isLoadingUm) {
                    return <TextField value="Loading..." />;
                  } else if (isEditing(record.id)) {
                    return (
                      <Form.Item name="maxQtyUnitId" style={{ margin: 0 }}>
                        <Select allowClear {...umSelectInputCellProps} />
                      </Form.Item>
                    );
                  }

                  return <TextField value={data?.find((item) => item.id === value)?.description} />;
                }}
                filterDropdown={(props) => (
                  <FilterDropdown {...props}>
                    <Select allowClear {...umSelectProps} style={{ minWidth: 200 }} />
                  </FilterDropdown>
                )}
              />
              <Table.Column
                title="Ações"
                dataIndex="actions"
                render={(_, record) => {
                  if (isEditing(record.id)) {
                    return (
                      <Space>
                        <SaveButton {...saveButtonProps} hideText size="small" />
                        <Button {...cancelButtonProps} size="small" icon={<StopOutlined />}>
                          {/* Cancelar */}
                        </Button>
                      </Space>
                    );
                  }
                  return (
                    <EditButton
                      {...editButtonProps(record.id)}
                      hideText
                      size="small"
                    />
                  );
                }}
              />
            </Table>
          </Form>
        </Tabs.TabPane>
        <Tabs.TabPane key="2" tab="Dashboard" icon={<DashboardOutlined />}>
          <Tabs tabPosition="left" size="small">
            <Tabs.TabPane key="1" tab="Visão geral"></Tabs.TabPane>
            <Tabs.TabPane key="2" tab="Transações"></Tabs.TabPane>
            <Tabs.TabPane key="3" tab="Compras e Vendas"></Tabs.TabPane>
            <Tabs.TabPane key="4" tab="KPIs"></Tabs.TabPane>
          </Tabs>
        </Tabs.TabPane>
      </Tabs>
    </List>
  );
}