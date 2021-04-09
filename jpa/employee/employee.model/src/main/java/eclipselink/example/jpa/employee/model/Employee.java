/*******************************************************************************
 * Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *              dclarke - initial JPA Employee example using XML (bug 217884)
 *              mbraeuer - annotated version, transformation
 *              jclingan - Updated to use JPA 2.1 Attribute Converters
 ******************************************************************************/
package eclipselink.example.jpa.employee.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.QueryHint;
import javax.persistence.SecondaryTable;
import javax.persistence.Version;

import org.eclipse.persistence.annotations.PrivateOwned;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * TODO
 * 
 * @author dclarke
 * @author jclingan
 * @since EclipseLink 2.5.2
 */
@Entity
@SecondaryTable(name = "SALARY")
@NamedQueries({ 
	@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e ORDER BY e.id"),
	@NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.firstName LIKE :firstName AND e.lastName LIKE :lastName"),
	@NamedQuery(name = "Employee.count", query = "SELECT COUNT(e) FROM Employee e"),
	@NamedQuery(name = "Employee.countByName", query = "SELECT COUNT(e) FROM Employee e WHERE e.firstName LIKE :firstName AND e.lastName LIKE :lastName"),
	/**
	 * Query used in {@link IdInPaging}
	 */
	@NamedQuery(name = "Employee.idsIn", query = "SELECT e FROM Employee e WHERE e.id IN :IDS ORDER BY e.id", hints = { @QueryHint(name = QueryHints.QUERY_RESULTS_CACHE, value = HintValues.TRUE) })})
public class Employee {

    @Id
    @Column(name = "EMP_ID")
    @GeneratedValue
    private int id;

    @Column(name = "F_NAME")
    private String firstName;

    /**
     * Gender mapped using Basic with an ObjectTypeConverter to map between
     * single char code value in database to enum. JPA only supports mapping to
     * the full name of the enum or its ordinal value.
     */
    @Basic
    @Column(name = "GENDER")
    @Convert(converter = GenderConverter.class)
    private Gender gender = Gender.Male;

    @Column(name = "L_NAME")
    private String lastName;

    @Column(table = "SALARY")
    private double salary;

    @Version
    private Long version;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;

    @OneToMany(mappedBy = "managers")
    private List<Employee> managedEmployees = new ArrayList<Employee>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval=true)
    @PrivateOwned
    private List<ph no> phoneNumbers = new ArrayList<PhoneNumber>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
    @JoinColumn(name = "ADDR_ID")
    private Address address;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "startDate", column = @Column(name = "START_DATE")), @AttributeOverride(name = "endDate", column = @Column(name = "END_DATE")) })
    private EmploymentPeriod period;

    @ElementCollection
    @CollectionTable(name = "RESPONS")
    private List<String> responsibilities = new ArrayList<String>();

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public int getId() {
        return id;
    }

    public void setId(int empId) {
        this.id = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fName) {
        this.firstName = fName;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    public List<Employee> getManagedEmployees() {
        return this.managedEmployees;
    }

    public void setManagedEmployees(List<Employee> employeeList) {
        this.managedEmployees = employeeList;
    }

    public Employee addManagedEmployee(Employee employee) {
        getManagedEmployees().add(employee);
        employee.setManager(this);
        return employee;
    }

    public Employee removeManagedEmployee(Employee employee) {
        getManagedEmployees().remove(employee);
        employee.setManager(null);
        return employee;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumberList) {
        this.phoneNumbers = phoneNumberList;
    }

    public PhoneNumber addPhoneNumber(PhoneNumber phoneNumber) {
        getPhoneNumbers().add(phoneNumber);
        phoneNumber.setOwner(this);
        return phoneNumber;
    }

    public PhoneNumber addPhoneNumber(String type, String areaCode, String number) {
        PhoneNumber phoneNumber = new PhoneNumber(type, areaCode, number);
        return addPhoneNumber(phoneNumber);
    }

    public PhoneNumber removePhoneNumber(PhoneNumber phoneNumber) {
        getPhoneNumbers().remove(phoneNumber);
        phoneNumber.setOwner(null);
        return phoneNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setPeriod(EmploymentPeriod period) {
        this.period = period;
    }

    public EmploymentPeriod getPeriod() {
        return period;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<String> getResponsibilities() {
        return this.responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public void addResponsibility(String responsibility) {
        getResponsibilities().add(responsibility);
    }

    public void removeResponsibility(String responsibility) {
        getResponsibilities().remove(responsibility);
    }

    public String toString() {
        return "Employee(" + getId() + ": " + getLastName() + ", " + getFirstName() + ")";
    }
}
