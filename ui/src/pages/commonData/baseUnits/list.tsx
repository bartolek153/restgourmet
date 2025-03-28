import { useTable } from "@refinedev/antd";
import { List, Table } from "antd"

export const BaseUnitList = () => {
  const { tableProps } = useTable({
    resource: "units"
  });
  
  return (
    <List>
      <Table {...tableProps} rowKey="id">
        <Table.Column dataIndex={"description"} title="Description" />
        <Table.Column dataIndex={"shortDescription"} title="Short Description" />
      </Table>
    </List>
  )
}