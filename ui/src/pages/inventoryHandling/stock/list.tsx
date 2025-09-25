import { gold, green, red, yellow } from "@ant-design/colors";
import { CheckCircleFilled, CheckCircleOutlined, DashboardOutlined, ExclamationCircleFilled, ExclamationCircleOutlined, StopOutlined, UnorderedListOutlined, WarningFilled } from "@ant-design/icons";
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


export const StockList = () => {
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
                render={(value, record) => {
                  let icon;

                  if (value < record.minQty)
                    icon = <WarningFilled style={{ color: gold[6] }} />
                  else if (value === 0)
                    icon = <ExclamationCircleFilled style={{ color: red[5] }} />
                  else
                    icon = <CheckCircleFilled style={{ color: green[6] }} />
                  return (
                    <Space align="baseline">
                      {value}
                      {icon}
                    </Space>
                  )
                }}
              />
              <Table.Column
                dataIndex="minQty"
                title={"Qtd. Mín."}
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
                        <Select allowClear {...umSelectProps} />
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
                        <Select allowClear {...umSelectProps} />
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

        </Tabs.TabPane>
      </Tabs>
    </List>
  );
}