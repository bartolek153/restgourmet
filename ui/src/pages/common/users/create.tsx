import { Create, useForm } from "@refinedev/antd";
import { Checkbox, Col, Form, GetProp, Input, message, Row, Select, Upload, UploadProps } from "antd";
import { useState } from "react";
import { AiOutlineLoading, AiOutlinePlus } from "react-icons/ai";
import { API_URL } from "../../../constants";
import { axiosInstance } from "../../../rest-data-provider/utils";

type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0];

const getBase64 = (img: FileType, callback: (url: string) => void) => {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result as string));
  reader.readAsDataURL(img);
};

export const UserCreate = () => {
  const { formProps, saveButtonProps } = useForm({});
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<string>();
  
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

  const handleChange: UploadProps['onChange'] = (info) => {
    if (info.file.status === 'uploading') {
      setLoading(true);
      return;
    }
    if (info.file.status === 'done') {
      
      // Get this url from response in real world.
      getBase64(info.file.originFileObj as FileType, (url) => {
        setLoading(false);
        setImageUrl(url);
      });
    }
  };

  const uploadButton = (
    <button style={{ border: 0, background: 'none' }} type="button">
      {loading ? <AiOutlineLoading /> : <AiOutlinePlus /> }
      <div style={{ marginTop: 8 }}>Upload</div>
    </button>
  );

  const customUpload = async (options: any) => {
    const { file, onSuccess, onError } = options;
    
    var formData = new FormData();
    formData.append("file", file);

    await axiosInstance.post(`${API_URL}/upload`, formData)
      .then((res) => { 
        onSuccess("Ok"); 
        formProps.form?.setFieldsValue({ pictureId: res.data });
      })
      .catch((err) => { onError({ err }); });
  };

  return (
    <Create saveButtonProps={saveButtonProps}>
      <Form {...formProps} layout="vertical">
        <Row gutter={24}>
          <Col flex={3}>
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
            >
              <Input placeholder="(optional)"/>
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
                style={{ width: 120 }}
              />
            </Form.Item>
          </Col>
          <Col flex={1}>
            
            <Form.Item
              label={"Picture"}
              >
              <Upload
                listType="picture-card"
                showUploadList={false}
                customRequest={customUpload}
                beforeUpload={beforeUpload}
                onChange={handleChange}
              >
                {imageUrl ? <img src={imageUrl} alt="avatar" style={{ width: '100%' }} /> : uploadButton}
              </Upload>
              <Form.Item name="pictureId" hidden>
                <Input/>
              </Form.Item>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </Create>
  );
};
