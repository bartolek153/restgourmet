import { Edit, useForm, useEditableTable, SaveButton } from "@refinedev/antd";
import { Form, Input, DatePicker, Table } from "antd";
import React, { useEffect, useState } from "react";

export const StockTakingEdit = () => {
  const { formProps: formPropsEdit, saveButtonProps: saveButtonPropsEdit, query } = useForm();
  const { data, isLoading } = query;

  // useEditableTable para o grid de items
  const {
    tableProps,
    form: tableForm,
    saveButtonProps,
  } = useEditableTable({
    resource: "stockTakingItems",
    queryOptions: {
      enabled: !!data?.data?.id,
    },
    initialSorter: [{ field: "id", order: "asc" }],
    meta: {
      stockTakingId: data?.data?.id,
    },
  });

  useEffect(() => {
    if (!isLoading && data?.data) {
      tableForm.setFieldsValue({
        items: data.data.items || [],
      });
    }
  }, [data, isLoading]);

  return (
    <Edit saveButtonProps={saveButtonPropsEdit} isLoading={isLoading} title="Editar inventário">
      <Form {...formPropsEdit} layout="vertical">
        <Form.Item label="Data de ínício" name="startDate">
          <DatePicker />
        </Form.Item>
        <Form.Item label="Data final" name="endDate">
          <DatePicker />
        </Form.Item>
        <Form.Item label="Criado por" name="createdById">
          <Input />
        </Form.Item>
        <Form.Item label="Armazém" name="warehouseId">
          <Input />
        </Form.Item>
        <Form.Item label="Status" name="status">
          <Input />
        </Form.Item>
        <Form.Item label="Observação" name="observation">
          <Input />
        </Form.Item>

        {/* Grid editável */}
        <Form form={tableForm} component={false}>
          <Table
            {...tableProps}
            rowKey="id"
            bordered
            pagination={false}
            components={{
              body: {
                cell: tableProps.components?.body?.cell,
              },
            }}
            columns={[
              {
                title: "Produto",
                dataIndex: "productName",
                editable: true,
              },
              {
                title: "Quantidade",
                dataIndex: "quantity",
                editable: true,
              },
              {
                title: "Preço",
                dataIndex: "price",
                editable: true,
              },
            ]}
          />
        </Form>

        {/* Botão para salvar header + grid */}
        <SaveButton
          {...saveButtonPropsEdit}
          onClick={async () => {
            // Salva header
            await saveButtonPropsEdit?.onClick?.();

            // Salva grid
            await saveButtonProps?.onClick?.();
          }}
          style={{ marginTop: 16 }}
        />
      </Form>
    </Edit>
  );
};
