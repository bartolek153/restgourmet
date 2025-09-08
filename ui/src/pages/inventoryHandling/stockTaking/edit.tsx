import { Edit, EditButton, List, SaveButton, TextField, useEditableTable, useForm } from "@refinedev/antd";
import { Button, Form, Input, Space, Table } from "antd";
import { useEffect, useState } from "react";

export const StockTakingEdit = () => {
  const [items, setItems] = useState<React.Key[]>([]);
  
  const {
      formProps: formPropsEdit,
      saveButtonProps: saveButtonPropsEdit,
      query: { data, isLoading },
      onFinish,
      id
    } = useForm({});
    
  useEffect(() => {
    if (!isLoading && data?.data) {
      setItems(data?.data.items);
    }
  }, [isLoading]);

  return (
    <Edit saveButtonProps={saveButtonPropsEdit} isLoading={isLoading} title="Editar inventário">
      <Form {...formPropsEdit}>
        <Form.Item label="Data de ínício" name="startDate">
          <Input />
        </Form.Item>
        {/* https://github.com/refinedev/refine/blob/main/examples/invoicer/src/pages/invoices/create.tsx */}
      </Form>
    </Edit>
  );
};
