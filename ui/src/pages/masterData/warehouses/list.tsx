import {
  DeleteButton,
  EditButton,
  List,
  TextField,
  useModalForm,
  useSelect,
  useTable,
} from "@refinedev/antd";
import { BaseRecord, useMany } from "@refinedev/core";
import { Form, Input, Select, Space, Table } from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { WarehouseForm } from "./form";

export const WarehouseList = () => {
  const { tableProps, searchFormProps } = useTable({
    syncWithLocation: true,
    onSearch: (params: any) => {
      return [
        { field: "q", operator: "eq", value: params.q },
        {
          field: "addressId",
          operator: "eq",
          value: params.addressId,
        },
      ];
    },
  });

  const { selectProps } = useSelect({
    resource: "addresses",
    optionLabel: (item: any) =>
      `${item.street} ${item.number}, ${item.city} - ${item.state}, ${item.country} - ${item.zipCode}`,
  });

  const filterPageDelayed = () => {
    setTimeout(() => {
      searchFormProps.form?.submit();
    }, 1000);
  };

  const addressIds: any = tableProps?.dataSource?.map((item) => item.addressId);
  const { data, isLoading } = useMany({
    resource: "addresses",
    ids: addressIds,
  });

  const {
    modalProps: createModalProps,
    formProps: createFormProps,
    show: createModalShow,
  } = useModalForm({
    action: "create",
  });

  const {
    modalProps: editModalProps,
    formProps: editFormProps,
    show: editModalShow,
  } = useModalForm({
    action: "edit",
    warnWhenUnsavedChanges: true,
  });

  return (
    <>
      <List
        createButtonProps={{
          onClick: () => {
            createModalShow();
          },
        }}
      >
        <Form {...searchFormProps} layout="vertical" onValuesChange={filterPageDelayed}>
          <Space wrap>
            <Form.Item label="Pesquisa" name="q">
              <Input placeholder="Descrição" prefix={<AiOutlineSearch />} allowClear />
            </Form.Item>
            <Form.Item label="Endereço" name="addressId">
              <Select allowClear {...selectProps} />
            </Form.Item>
          </Space>
        </Form>
        <Table {...tableProps} rowKey="id">
          <Table.Column dataIndex={"name"} title="Nome" />
          <Table.Column
            dataIndex={"addressId"}
            title="Endereço"
            render={(value: any) => {
              if (isLoading) {
                return <TextField value="Carregando..." />;
              }

              return <TextField value={data?.find((item) => item.id === value)?.street} />;
            }}
          />
          <Table.Column dataIndex={"status"} title="Status" />
          <Table.Column
            title={"Ações"}
            dataIndex="actions"
            render={(_, record: BaseRecord) => (
              <Space>
                <EditButton
                  hideText
                  size="small"
                  recordItemId={record.id}
                  onClick={() => editModalShow(record.id)}
                />
                <DeleteButton hideText size="small" recordItemId={record.id} />
              </Space>
            )}
          />
        </Table>
      </List>
      <WarehouseForm modalProps={createModalProps} formProps={createFormProps} />
      <WarehouseForm modalProps={editModalProps} formProps={editFormProps} />
    </>
  );
};
