import { Create, getValueFromEvent, useForm, useTable } from "@refinedev/antd";
import {
  Button,
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
import { API_URL } from "../../../constants";
import { UploadOutlined } from "@ant-design/icons";
import { useState } from "react";

type FileType = Parameters<GetProp<UploadProps, "beforeUpload">>[0];

export const UserCreate = () => {
  const [roles, setRoles] = useState<React.Key[]>([]);
  const [groups, setGroups] = useState<React.Key[]>([]);

  const { formProps, saveButtonProps, onFinish } = useForm({});

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
    <Create saveButtonProps={saveButtonProps} title="Criar usuário">
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
                  required: false,
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
              <Table.Column dataIndex="name" title="Nome" />
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
              <Table.Column dataIndex="name" title="Nome" />
            </Table>
          </Tabs.TabPane>
        </Tabs>
      </Form>
    </Create>
  );
};
