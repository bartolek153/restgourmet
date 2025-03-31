import { Modal, Form, Input, ModalProps, FormProps, message } from "antd";
import { useEffect, useState } from "react";
import axios from "axios";
import { API_URL } from "../../../constants";

interface AddressFormProps {
  modalProps: ModalProps;
  formProps: FormProps;
}

export const AddressForm: React.FC<AddressFormProps> = ({
  modalProps,
  formProps,
}) => {
  const [isLoadingAddress, setIsLoadingAddress] = useState<boolean>(false);

  const currentForm = formProps.form;

  if (!currentForm) {
    throw new Error("Form instance is required");
  }

  // Function to fetch address details from the CEP endpoint
  const fetchAddressFromZipCode = async (zipCode: string) => {

    if (!zipCode || zipCode.length < 8) return;

    setIsLoadingAddress(true);
    try {
      const formattedZipCode = zipCode.replace(/\D/g, "");
      const response = await axios.get(`${API_URL}/addresses/cep/${formattedZipCode}`);
      
      if (response.data) {
        currentForm.setFieldsValue({
          street: response.data.street,
          city: response.data.city,
          state: response.data.state,
          country: response.data.country
        });
      }
    } catch (error) {
      message.warning("Failed to fetch address information. Please enter manually.");
      console.error("Error fetching address:", error);
    } finally {
      setIsLoadingAddress(false);
    }
  };

  // Handle zip code changes
  const handleZipCodeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTimeout(() => {
      console.log("Fetching address...");
      fetchAddressFromZipCode(e.target.value);
    }, 1000);
    
  };

  return (
    <Modal {...modalProps} loading={isLoadingAddress}>
      <Form {...formProps} layout="vertical">
        <Form.Item
          label="Zip Code"
          name="zipCode"
          rules={[
            {
              required: true,
              message: "Zip code is required",
            },
            {
              pattern: /^\d{5}-?\d{3}$/,
              message: "Please enter a valid zip code (format: 00000-000 or 00000000)",
            },
          ]}
        >
          <Input 
            placeholder="Enter zip code" 
            onChange={handleZipCodeChange}
            maxLength={9}
          />
        </Form.Item>
        
        <Form.Item
          label="Street"
          name="street"
          rules={[
            {
              required: true,
              message: "Street is required",
            },
          ]}
        >
          <Input placeholder="Street name" />
        </Form.Item>
        
        <Form.Item
          label="Number"
          name="number"
          rules={[
            {
              required: true,
              message: "Number is required",
            },
          ]}
        >
          <Input placeholder="Building/House number" />
        </Form.Item>
        
        <Form.Item
          label="Additional Info"
          name="additionalInfo"
        >
          <Input placeholder="Apartment, suite, floor, etc." />
        </Form.Item>
        
        <Form.Item
          label="City"
          name="city"
          rules={[
            {
              required: true,
              message: "City is required",
            },
          ]}
        >
          <Input placeholder="City" />
        </Form.Item>
        
        <Form.Item
          label="State"
          name="state"
          rules={[
            {
              required: true,
              message: "State is required",
            },
          ]}
        >
          <Input placeholder="State" />
        </Form.Item>
        
        <Form.Item
          label="Country"
          name="country"
          rules={[
            {
              required: true,
              message: "Country is required",
            },
          ]}
          initialValue="BR"
        >
          <Input placeholder="Country" />
        </Form.Item>
      </Form>
    </Modal>
  );
};
