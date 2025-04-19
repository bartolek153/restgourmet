import React from "react";
import { Card, Col, Row, Statistic, Typography } from "antd";
import { FileTextOutlined, DollarOutlined, GiftOutlined } from "@ant-design/icons";
import { Line, Bar } from "@ant-design/plots";

const { Title } = Typography;

const Dashboard = () => {
  const dashboardData = {
    totalNotas: 254,
    valorTotalNotas: 125430.75,
    creditosTotais: 6271.54,
    notasPorStatus: {
      Ativa: 198,
      Cancelada: 34,
      Pendente: 22,
    },
    valorMensalNotas: [
      { mes: "Jan", valor: 10500.25 },
      { mes: "Feb", valor: 11430.5 },
      { mes: "Mar", valor: 12500.75 },
      { mes: "Apr", valor: 13500.9 },
      { mes: "May", valor: 13890.33 },
      { mes: "Jun", valor: 12400.0 },
      { mes: "Jul", valor: 11920.8 },
      { mes: "Aug", valor: 13045.12 },
      { mes: "Sep", valor: 12234.9 },
      { mes: "Oct", valor: 11880.45 },
      { mes: "Nov", valor: 12100.1 },
      { mes: "Dec", valor: 12727.55 },
    ],
    creditosMensais: [
      { mes: "Jan", creditos: 525.12 },
      { mes: "Feb", creditos: 571.8 },
      { mes: "Mar", creditos: 624.35 },
      { mes: "Apr", creditos: 678.42 },
      { mes: "May", creditos: 695.41 },
      { mes: "Jun", creditos: 620.0 },
      { mes: "Jul", creditos: 596.04 },
      { mes: "Aug", creditos: 652.26 },
      { mes: "Sep", creditos: 611.74 },
      { mes: "Oct", creditos: 595.02 },
      { mes: "Nov", creditos: 605.01 },
      { mes: "Dec", creditos: 667.37 },
    ],
    topEmpresas: [
      { razaoSocial: "Empresa Alfa Ltda", valorTotal: 32450.75 },
      { razaoSocial: "Beta Comércio S/A", valorTotal: 27600.0 },
      { razaoSocial: "Gama Serviços ME", valorTotal: 18500.2 },
      { razaoSocial: "Delta Industrial Ltda", valorTotal: 15820.4 },
      { razaoSocial: "Epsilon Tech S/A", valorTotal: 14530.15 },
    ],
  };

  // Combine monthly data for the line chart
  const combinedChartData = dashboardData.valorMensalNotas
    .map((item, index) => ({
      mes: item.mes,
      type: "Valor das Notas",
      value: item.valor,
    }))
    .concat(
      dashboardData.creditosMensais.map((item) => ({
        mes: item.mes,
        type: "Créditos",
        value: item.creditos,
      })),
    );

  // Line chart configuration
  const lineConfig = {
    data: combinedChartData,
    xField: "mes",
    yField: "value",
    seriesField: "type",
    color: ["#1890ff", "#52c41a"],
    legend: { position: "top" },
    yAxis: {
      label: {
        formatter: (v) => `${v}`.replace(/\B(?=(\d{3})+(?!\d))/g, ","),
      },
    },
    tooltip: {
      formatter: (datum) => ({
        name: datum.type,
        value: datum.value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" }),
      }),
    },
  };

  // Bar chart configuration
  const barConfig = {
    data: dashboardData.topEmpresas,
    xField: "valorTotal",
    yField: "razaoSocial",
    seriesField: "razaoSocial",
    legend: { position: "top-left" },
    label: {
      position: "middle",
      content: ({ valorTotal }) =>
        valorTotal.toLocaleString("pt-BR", { style: "currency", currency: "BRL" }),
      style: { fill: "#fff" },
    },
    xAxis: {
      title: { text: "Valor Total (R$)" },
    },
    yAxis: {
      title: { text: "Razão Social" },
    },
    meta: {
      valorTotal: {
        alias: "Valor Total (R$)",
      },
      razaoSocial: {
        alias: "Razão Social",
      },
    },
  };

  return (
    <div style={{ padding: 24 }}>
      <Title level={3}>Resumo Geral</Title>
      <Row gutter={[16, 16]}>
        <Col xs={24} sm={12} md={8}>
          <Card>
            <Statistic
              title="Total de Notas"
              value={dashboardData.totalNotas}
              prefix={<FileTextOutlined />}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8}>
          <Card>
            <Statistic
              title="Valor Total das Notas"
              value={dashboardData.valorTotalNotas}
              prefix={<DollarOutlined />}
              precision={2}
              valueStyle={{ color: "#3f8600" }}
              suffix="R$"
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8}>
          <Card>
            <Statistic
              title="Créditos Totais"
              value={dashboardData.creditosTotais}
              prefix={<GiftOutlined />}
              precision={2}
              valueStyle={{ color: "#cf1322" }}
              suffix="R$"
            />
          </Card>
        </Col>
      </Row>

      <Title level={3} style={{ marginTop: 24 }}>
        Notas por Status
      </Title>
      <Row gutter={[16, 16]}>
        {Object.entries(dashboardData.notasPorStatus).map(([status, count]) => (
          <Col xs={24} sm={12} md={8} key={status}>
            <Card>
              <Statistic title={status} value={count} />
            </Card>
          </Col>
        ))}
      </Row>

      <Title level={3} style={{ marginTop: 24 }}>
        Valores Mensais das Notas e Créditos
      </Title>
      <Card>
        <Line {...lineConfig} />
      </Card>

      <Title level={3} style={{ marginTop: 24 }}>
        Top Empresas por Valor Total
      </Title>
      <Card>
        <Bar {...barConfig} />
      </Card>
    </div>
  );
};

export default Dashboard;
