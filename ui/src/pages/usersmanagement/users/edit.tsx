import { Edit, getValueFromEvent, useForm } from "@refinedev/antd";
import {
  Button,
  Descriptions,
  Form,
  GetProp,
  Input,
  message,
  Select,
  Table,
  Tabs,
  Upload,
  UploadProps,
} from "antd";
import React, { useEffect, useState } from "react";
import { API_URL } from "../../../constants";
import { UploadOutlined } from "@ant-design/icons";
import { useTable } from "@refinedev/antd";

type FileType = Parameters<GetProp<UploadProps, "beforeUpload">>[0];

export const UserEdit = () => {
  const [items, setItems] = useState<any[]>([]);
  const [roles, setRoles] = useState<React.Key[]>([]);
  const [groups, setGroups] = useState<React.Key[]>([]);

  const {
    formProps,
    saveButtonProps,
    query: { data, isLoading },
    onFinish,
  } = useForm({});

  const handleOnFinish = (values: any) => {
    onFinish({
      ...values,
      roleIds: roles,
      groupIds: groups,
    });
  };

  const { tableProps: rlTableProps } = useTable({
    resource: "users/roles",
  });

  const { tableProps: grTableProps } = useTable({
    resource: "users/groups",
  });

  useEffect(() => {
    if (!isLoading && data?.data) {
      const user = data.data;
      setRoles(user.roles);
      setGroups(user.groups);

      setItems([
        {
          key: "1",
          label: "Criado em",
          children: new Date(user.createdAt).toLocaleString(),
        },
        {
          key: "2",
          label: "Atualizado em",
          children: new Date(user.updatedAt).toLocaleString(),
        },
      ]);
    }
  }, [isLoading]);

  const onRoleSelectChange = (selectedRowKeys: React.Key[]) => {
    setRoles(selectedRowKeys);
  };

  const onGroupSelectChange = (selectedRowKeys: React.Key[]) => {
    setGroups(selectedRowKeys);
  };

  const beforeUpload = (file: FileType) => {
    const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
    if (!isJpgOrPng) {
      message.error("Apenas arquivos PNG/PNJ são permitidos!");
    }
    const isLt3M = file.size / 1024 / 1024 < 3;
    if (!isLt3M) {
      message.error("Imagem deve ser menor que 3MB!");
    }

    return isJpgOrPng && isLt3M;
  };

  return (
    <Edit saveButtonProps={saveButtonProps} isLoading={isLoading} title="Editar usuário"> 
      <Form
        {...formProps}
        layout="vertical"
        wrapperCol={{ span: 6 }}
        autoComplete="off"
        onFinish={handleOnFinish}
      >
        <Tabs>
          <Tabs.TabPane key="1" tab="Geral">
            <Form.Item
              label={"Nome"}
              name={["name"]}
              rules={[
                {
                  required: true,
                },
              ]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label={"E-mail"}
              name="email"
              rules={[
                {
                  required: true,
                  type: "email",
                },
              ]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label={"Usuário"}
              name="nickname"
              rules={[
                {
                  required: true,
                },
              ]}
            >
              <Input placeholder="" />
            </Form.Item>
            <Form.Item
              label={"Tipo"}
              name={["type"]}
              initialValue={"VIEWER"}
              rules={[
                {
                  required: true,
                },
              ]}
            >
              <Select
                defaultValue={"VIEWER"}
                options={[
                  { value: "ADMIN", label: "Administrador" },
                  { value: "NORMAL", label: "Normal" },
                  { value: "VIEWER", label: "Visualizador" },
                ]}
                style={{ width: 200 }}
              />
            </Form.Item>
            <Form.Item
              name="picture"
              valuePropName="fileList"
              getValueFromEvent={getValueFromEvent}
            >
              <Upload
                name="file"
                action={`${API_URL}/upload`}
                listType="picture"
                accept="image/*"
                maxCount={1}
                beforeUpload={beforeUpload}
              >
                <Button icon={<UploadOutlined />}>Foto de perfil</Button>
              </Upload>
            </Form.Item>
            <Descriptions column={4} items={items} layout="vertical" />
          </Tabs.TabPane>
          <Tabs.TabPane key="2" tab="Funções">
            <Table
              {...rlTableProps}
              rowKey="id"
              pagination={{
                ...rlTableProps.pagination,
                showSizeChanger: true,
              }}
              showSorterTooltip={true}
              rowSelection={{
                selectedRowKeys: roles,
                onChange: onRoleSelectChange,
                preserveSelectedRowKeys: true,
              }}
            >
              <Table.Column dataIndex="name" title="Name" />
            </Table>
          </Tabs.TabPane>
          <Tabs.TabPane key="3" tab="Grupos">
            <Table
              {...grTableProps}
              rowKey="id"
              pagination={{
                ...grTableProps.pagination,
                showSizeChanger: true,
              }}
              showSorterTooltip={true}
              rowSelection={{
                selectedRowKeys: groups,
                onChange: onGroupSelectChange,
                preserveSelectedRowKeys: true,
              }}
            >
              <Table.Column dataIndex="name" title="Name" />
            </Table>
          </Tabs.TabPane>
        </Tabs>
      </Form>
    </Edit>
  );
};
