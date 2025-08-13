import {
  DeleteButton,
  EditButton,
  List,
  TextField,
  useModal,
  useSelect,
  useTable
} from "@refinedev/antd";
import { useMany, type BaseRecord } from "@refinedev/core";
import {
  Button,
  Card,
  Checkbox,
  Col,
  Form,
  Input,
  Row,
  Select,
  Space,
  Switch,
  Table
} from "antd";
import { AiOutlineSearch } from "react-icons/ai";
import { BatchActionsModal } from "./batchActionsModal";

export const ProductList = () => {
  const { tableProps, searchFormProps } = useTable({
    syncWithLocation: true,
    onSearch: (params: any) => {
      return [
        { field: "q", operator: "eq", value: params.q },
        { field: "groupId", operator: "eq", value: params.groupId },
        { field: "familyId", operator: "eq", value: params.familyId },
        { field: "categoryId", operator: "eq", value: params.categoryId },
        { field: "origin", operator: "eq", value: params.origin },
        { field: "inventoryUnitId", operator: "eq", value: params.inventoryUnitId },
        { field: "status", operator: "eq", value: params.status },
        { field: "deleted", operator: "eq", value: params.deleted },
      ];
    },
  });

  const vertRadioStyle: React.CSSProperties = {
    display: "flex",
    flexDirection: "column",
    gap: 8,
  };

  const { selectProps: catSelectProps } = useSelect({
    resource: "products/categories",
    optionLabel: "description",
  });

  const { selectProps: famSelectProps } = useSelect({
    resource: "products/families",
    optionLabel: "description",
  });

  const { selectProps: gtpSelectProps } = useSelect({
    resource: "products/groups",
    optionLabel: "description",
  });

  const { selectProps: umSelectProps } = useSelect({
    resource: "units/measurement",
    optionLabel: "description",
  });

  const groupIds: any = tableProps?.dataSource?.map((record: any) => record.groupId);
  const { data, isLoading } = useMany({
    resource: "products/groups",
    ids: groupIds,
  });

  const filterPageDelayed = () => {
    setTimeout(() => {
      searchFormProps.form?.submit();
    }, 1000);
  };

  const { show, modalProps } = useModal({});

  return (
    <>
      <Row gutter={[16, 16]}>
        <Col lg={6} xs={24}>
          <Card title="Filtros">
            <Form {...searchFormProps} layout="vertical" onValuesChange={filterPageDelayed}>
              <Space wrap direction="vertical">
                <Form.Item label="Pesquisa" name="q">
                  <Input
                    placeholder="ID, Descrição, SKU"
                    prefix={<AiOutlineSearch />}
                    allowClear
                  />
                </Form.Item>
                <Form.Item label="Grupo" name="groupId">
                  <Select {...gtpSelectProps} allowClear />
                </Form.Item>
                <Form.Item label="Família" name="familyId">
                  <Select {...famSelectProps} allowClear />
                </Form.Item>
                <Form.Item label="Categoria" name="categoryId">
                  <Select {...catSelectProps} allowClear />
                </Form.Item>
                <Form.Item label="Origem" name="origin">
                  <Select allowClear>
                    <Select.Option value="PRODUCED">Produzido</Select.Option>
                    <Select.Option value="SUPPLIED">Fornecido</Select.Option>
                    <Select.Option value="IMPORTED">Importado</Select.Option>
                    <Select.Option value="OTHER">Outro</Select.Option>
                  </Select>
                </Form.Item>
                <Form.Item label="Unidade de Estoque" name="inventoryUnitId">
                  <Select {...umSelectProps} allowClear />
                </Form.Item>
                <Form.Item label="Status" name="status">
                  <Checkbox.Group style={vertRadioStyle}>
                    <Checkbox value="ACTIVE">Ativo</Checkbox>
                    <Checkbox value="DISCONTINUED">Descontinuado</Checkbox>
                    <Checkbox value="OBSOLETE">Obsoleto</Checkbox>
                    <Checkbox value="BLOCKED">Bloqueado</Checkbox>
                  </Checkbox.Group>
                </Form.Item>
              </Space>
            </Form>
          </Card>
        </Col>
        <Col lg={18} xs={24}>
          <List
            breadcrumb={false}
            headerButtons={({ defaultButtons }) => (
              <>
                {defaultButtons}
                <Button onClick={show}>Batch</Button>
              </>
            )}
          >
            <Table
              {...tableProps}
              rowKey="id"
              pagination={{
                ...tableProps.pagination,
                showTotal: (total) => `Total ${total} items`,
                showQuickJumper: true,
                showSizeChanger: true,
              }}
              showSorterTooltip={false}
            >
              <Table.Column dataIndex="description" title={"Descrição"} sorter={true} />
              <Table.Column dataIndex="sku" title={"SKU"} />
              <Table.Column
                dataIndex="groupId"
                sorter={true}
                title={"Grupo"}
                render={(value) => {
                  if (isLoading) {
                    return <TextField value="Loading..." />;
                  }

                  return <TextField value={data?.find((item) => item.id === value)?.description} />;
                }}
              />
              <Table.Column dataIndex="origin" title={"Origem"} />
              <Table.Column dataIndex="price" title={"Preço"} />
              <Table.Column
                title={"Ações"}
                dataIndex="actions"
                render={(_, record: BaseRecord) => (
                  <Space>
                    {record.deleted ? null : (
                      <>
                        <EditButton hideText size="small" recordItemId={record.id} />
                        <DeleteButton hideText size="small" recordItemId={record.id} />
                      </>
                    )}
                  </Space>
                )}
              />
            </Table>
          </List>
        </Col>
      </Row>
      <BatchActionsModal modalProps={modalProps} />
    </>
  );
};
