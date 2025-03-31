import { DeleteButton, EditButton, List, useModalForm, useSelect, useTable } from "@refinedev/antd";
import { BaseRecord, useMany } from "@refinedev/core";
import { Form, Input, Select, Space, Table } from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { UnitMeasurementForm } from "./form";

export const UnitMeasurementList = () => {
  const { tableProps, searchFormProps } = useTable({
    syncWithLocation: true,
    onSearch: (params: any) => {
      return [
        { field: "q", operator: "eq", value: params.q },
        {
          field: "baseUnitId",
          operator: "eq",
          value: params.baseUnitId,
        },
      ];
    },
  });

  const { selectProps } = useSelect({
    resource: "units",
    optionLabel: "shortDescription",
  });

  const filterPageDelayed = () => {
    setTimeout(() => {
      searchFormProps.form?.submit();
    }, 1000);
  };

  const unitsIds: any = tableProps?.dataSource?.map((item) => item.baseUnitId);
  const { data, isLoading } = useMany({
    resource: "units",
    ids: unitsIds,
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
            <Form.Item label="Search" name="q">
              <Input placeholder="Description" prefix={<AiOutlineSearch />} allowClear />
            </Form.Item>
            <Form.Item label="Base Unit" name="baseUnitId">
              <Select allowClear {...selectProps} />
            </Form.Item>
          </Space>
        </Form>
        <Table {...tableProps} rowKey="id">
          <Table.Column dataIndex={"description"} title="Description" />
          <Table.Column dataIndex={"shortDescription"} title="Short Description" />
          <Table.Column
            dataIndex={"baseUnitId"}
            title="Base Unit"
            render={(value: any) => {
              const unit = data?.find((item: any) => item.id === value);
              return unit ? unit.description : "";
            }}
          />
          <Table.Column dataIndex={"conversionFactor"} title="Conversion Factor" />
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
      <UnitMeasurementForm modalProps={createModalProps} formProps={createFormProps} />
      <UnitMeasurementForm modalProps={editModalProps} formProps={editFormProps} />
    </>
  );
};
