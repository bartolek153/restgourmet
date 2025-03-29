import { Modal, ModalProps, Button, Flex } from "antd";
import { useState } from "react";
import { AiOutlineImport, AiOutlineExport } from "react-icons/ai";
import { IoDocumentOutline } from "react-icons/io5";
import { RiFileExcel2Line, RiFilePdfLine } from "react-icons/ri";
import { TbFileTypeCsv } from "react-icons/tb";
import { motion, AnimatePresence } from "framer-motion";

// Props interface for sub-components
interface ActionProps {
  onSelect: (action: "main" | "export" | "import") => void;
}

// Main Modal Component
interface BatchActionsModalProps {
  modalProps: ModalProps;
}

export const BatchActionsModal = (batchProps: BatchActionsModalProps) => {
  const [action, setAction] = useState<"main" | "export" | "import">("main");

  return (
    <Modal {...batchProps.modalProps} footer={null} style={{height: 100}}>
      <Flex vertical gap={16} style={{ padding: 32 }}>
        {action === "main" && <MainActions key="main" onSelect={setAction} />}
        {action === "export" && <ExportOptions key="export" onSelect={setAction} />}
        {action === "import" && <ImportOptions key="import" onSelect={setAction} />}
      </Flex>
    </Modal>
  );
};

// Main Actions Component
const MainActions: React.FC<ActionProps> = ({ onSelect }) => {
  return (
    <>
      <Button icon={<AiOutlineExport />} onClick={() => onSelect("export")}>
        Export
      </Button>
      <Button icon={<AiOutlineImport />} onClick={() => onSelect("import")}>
        Import
      </Button>
      <Button icon={<IoDocumentOutline />}>Fill template</Button>
    </>
  );
};

// Export Options Component
const ExportOptions: React.FC<ActionProps> = ({ onSelect }) => {
  return (
    <>
      <Button icon={<RiFilePdfLine />} onClick={() => console.log("Export as PDF")}>
        Export as PDF
      </Button>
      <Button icon={<RiFileExcel2Line />} onClick={() => console.log("Export as Excel")}>
        Export as Excel
      </Button>
      <Button icon={<TbFileTypeCsv />} onClick={() => console.log("Export as CSV")}>
        Export as CSV
      </Button>
      <Button type="default" onClick={() => onSelect("main")}>
        Back
      </Button>
    </>
  );
};

// Import Options Component
const ImportOptions: React.FC<ActionProps> = ({ onSelect }) => {
  return (
    <>
      <Button icon={<TbFileTypeCsv />} onClick={() => console.log("Import CSV")}>
        Import CSV
      </Button>
      <Button icon={<RiFileExcel2Line />} onClick={() => console.log("Import XLSX")}>
        Import XLSX
      </Button>
      <Button type="default" onClick={() => onSelect("main")}>
        Back
      </Button>
    </>
  );
};
