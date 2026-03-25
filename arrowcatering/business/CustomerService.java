package com.arrowcatering.business;

import com.arrowcatering.dao.CustomerDAO;
import com.arrowcatering.model.Customer;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public boolean registerCustomer(Customer customer) {
        if (customer.getCustomerId() <= 0) return false;
        if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) return false;
        if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) return false;
        return customerDAO.addCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    public boolean deleteCustomer(int customerId) {
        return customerDAO.deleteCustomer(customerId);
    }
}