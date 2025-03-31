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
import { ProductFamilyForm } from "./form";

export const ProductFamilyList = () => {
  const { tableProps, searchFormProps } = useTable({
    syncWithLocation: true,
    onSearch: (params: any) => {
      return [
        {
          field: "q",
          operator: "eq",
          value: params.q,
        },
        {
          field: "categoryId",
          operator: "eq",
          value: params.categoryId,
        },
      ];
    },
  });

  const categoryIds: any = tableProps?.dataSource?.map((record: any) => record.categoryId);

  const { data, isLoading } = useMany({
    resource: "products/categories",
    ids: categoryIds,
  });

  const { selectProps } = useSelect({
    resource: "products/categories",
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
            <Form.Item label="Category" name="categoryId">
              <Select allowClear {...selectProps} />
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
          loading={isLoading}
        >
          <Table.Column dataIndex="description" title={"Description"} sorter={true} />
          <Table.Column
            dataIndex="categoryId"
            title={"Category"}
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
      <ProductFamilyForm modalProps={createModalProps} formProps={createFormProps} />
      <ProductFamilyForm modalProps={editModalProps} formProps={editFormProps} />
    </>
  );
};
