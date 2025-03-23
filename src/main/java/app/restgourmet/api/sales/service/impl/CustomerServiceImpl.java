package app.restgourmet.api.sales.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.restgourmet.api.exceptions.ResourceNotFoundException;
import app.restgourmet.api.masterdata.mappers.AddressMapper;
import app.restgourmet.api.masterdata.models.Address;
import app.restgourmet.api.masterdata.repository.AddressRepository;
import app.restgourmet.api.sales.dto.CustomerDto;
import app.restgourmet.api.sales.dto.CustomerListDto;
import app.restgourmet.api.sales.dto.CustomerListFiltersDto;
import app.restgourmet.api.sales.mappers.CustomerMapper;
import app.restgourmet.api.sales.models.Customer;
import app.restgourmet.api.sales.repository.CustomerRepository;
import app.restgourmet.api.sales.service.spec.CustomerService;
import jakarta.persistence.criteria.Predicate;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            AddressRepository addressRepository,
            CustomerMapper customerMapper,
            AddressMapper addressMapper) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public PagedModel<CustomerListDto> list(PageRequest pageReq, CustomerListFiltersDto filters) {
        Specification<Customer> spec = createSpecification(filters);
        Page<Customer> page = customerRepository.findAll(spec, pageReq);
        Page<CustomerListDto> res = page.map(customerMapper::toListDto);
        return new PagedModel<>(res);
    }

    @Override
    public CustomerDto getOne(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toDto(customer);
    }

    @Override
    @Transactional
    public UUID create(CustomerDto dto) {
        // Create address if provided
        Address address = null;
        if (dto.getAddress() != null) {
            address = addressMapper.toEntity(dto.getAddress());
            address = addressRepository.save(address);
        } else if (dto.getAddressId() != null) {
            address = addressRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + dto.getAddressId()));
        }
        
        // Create customer
        Customer customer = customerMapper.toEntity(dto);
        customer.setAddress(address);
        customer = customerRepository.save(customer);
        return customer.getId();
    }

    @Override
    @Transactional
    public void edit(UUID id, CustomerDto dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        
        // Handle address
        if (dto.getAddress() != null) {
            Address address;
            if (customer.getAddress() != null) {
                // Update existing address
                address = customer.getAddress();
                addressMapper.updateEntity(dto.getAddress(), address);
            } else {
                // Create new address
                address = addressMapper.toEntity(dto.getAddress());
            }
            address = addressRepository.save(address);
            customer.setAddress(address);
        } else if (dto.getAddressId() != null) {
            // Link to existing address
            Address address = addressRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + dto.getAddressId()));
            customer.setAddress(address);
        }
        
        // Update customer
        customerMapper.updateEntity(dto, customer);
        customerRepository.save(customer);
    }

    @Override
    public void delete(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private Specification<Customer> createSpecification(CustomerListFiltersDto filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new java.util.ArrayList<>();

            if (filters.getName() != null && !filters.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filters.getName().toLowerCase() + "%"));
            }

            if (filters.getEmail() != null && !filters.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")),
                        "%" + filters.getEmail().toLowerCase() + "%"));
            }

            if (filters.getPhone() != null && !filters.getPhone().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("phone")),
                        "%" + filters.getPhone().toLowerCase() + "%"));
            }

            if (filters.getCity() != null && !filters.getCity().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("address").get("city")),
                        "%" + filters.getCity().toLowerCase() + "%"));
            }

            if (filters.getState() != null && !filters.getState().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("address").get("state")),
                        "%" + filters.getState().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
