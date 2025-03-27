import {
  DeleteButton,
  EditButton,
  FilterDropdown,
  List,
  TextField,
  useModalForm,
  useSelect,
  useTable,
} from "@refinedev/antd";
import { BaseRecord, useMany } from "@refinedev/core";
import { Checkbox, Form, Input, Radio, Select, Space, Table } from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { ProductGroupForm } from "./create";
import { useEffect } from "react";

export const ProductGroupList = () => {
  const { tableProps, searchFormProps } = useTable({
    syncWithLocation: true,
    onSearch: (params: any) => {
      return [
        { field: "q", operator: "eq", value: params.q },
        {
          field: "familyId",
          operator: "eq",
          value: params.familyId,
        },
      ];
    },
  });

  const familyIds: any = tableProps?.dataSource?.map((record: any) => record.familyId);
  const { data, isLoading } = useMany({
    resource: "products/families",
    ids: familyIds,
  });

  const { selectProps } = useSelect({
    resource: "products/families",
    optionLabel: "description",
  });

  const filterPageDelayed = () => {
    setTimeout(() => {
      searchFormProps.form?.submit();
    }, 1000);
  };

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
            <Form.Item label="Search" name="q">
              <Input placeholder="Description" prefix={<AiOutlineSearch />} allowClear />
            </Form.Item>
            <Form.Item label="Family" name="familyId">
              <Select placeholder="Family" allowClear {...selectProps} />
            </Form.Item>
          </Space>
        </Form>

        <Table
          {...tableProps}
          rowKey="id"
          pagination={{
            ...tableProps.pagination,
            showTotal: (total) => `Total ${total} items`,
            showQuickJumper: true,
            showSizeChanger: true,
          }}
          showSorterTooltip={true}
        >
          <Table.Column dataIndex="description" title={"Description"} sorter={true} />
          <Table.Column
            dataIndex="familyId"
            title={"Family"}
            sorter={true}
            render={(value) => {
              if (isLoading) {
                return <TextField value="Loading..." />;
              }

              return <TextField value={data?.find((item) => item.id === value)?.description} />;
            }}
          />
          <Table.Column
            title={"Actions"}
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
      <ProductGroupForm modalProps={createModalProps} formProps={createFormProps} />
      <ProductGroupForm modalProps={editModalProps} formProps={editFormProps} />
    </>
  );
};
