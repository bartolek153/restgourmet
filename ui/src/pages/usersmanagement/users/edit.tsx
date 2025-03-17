import { Edit, getValueFromEvent, useForm } from "@refinedev/antd";
import { Button, Checkbox, Col, Form, GetProp, Input, message, Row, Select, Upload, UploadProps } from "antd";
import { useEffect, useState } from "react";
import { API_URL } from "../../../constants";
import { LoadingOutlined, PlusOutlined, UploadOutlined } from "@ant-design/icons";

type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0];


export const UserEdit = () => {
  const { formProps, saveButtonProps } = useForm({});

  const beforeUpload = (file: FileType) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
      message.error('You can only upload JPG/PNG file!');
    }
    const isLt3M = file.size / 1024 / 1024 < 3;
    if (!isLt3M) {
      message.error('Image must smaller than 3MB!');
    }

    return isJpgOrPng && isLt3M;
  };



  return (
    <Edit saveButtonProps={saveButtonProps}>
      <Form {...formProps}
        layout="vertical"
        wrapperCol={{ span: 6 }}
        autoComplete="off">
        <Form.Item
          label={"Name"}
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
          label={"Email"}
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
          label={"Nickname"}
          name="nickname"
          rules={[
            {
              required: true,
            }
          ]}
        >
          <Input placeholder="" />
        </Form.Item>
        <Form.Item
          label={"Permissions"}
          name={"permissions"}
          rules={[
            {
              required: true,
            },
          ]}
        >
          <Checkbox.Group options={['READ_USERS', 'WRITE_USERS']} defaultValue={[]} />
        </Form.Item>
        <Form.Item
          label={"Role"}
          name={["role"]}
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
              { value: "ADMIN", label: "Administrator" },
              { value: "MODERATOR", label: "Moderator" },
              { value: "VIEWER", label: "Viewer" },
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
            <Button icon={<UploadOutlined />}>Profile picture</Button>
          </Upload>
        </Form.Item>
      </Form>
    </Edit>
  );
};
