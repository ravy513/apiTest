/*******************************************************************************
 * Copyright (c) 1998, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 * 		dclarke - initial JPA Employee example using XML (bug 217884)
 *      mbraeuer - annotated version
 ******************************************************************************/
package eclipselink.example.jpa.employee.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.eclipse.persistence.annotations.Cache;

@Entity
@Cache(refreshOnlyIfNewer=true)
public class Address {
    @Id
    @Column(name = "ADDRESS_ID")
    @GeneratedValue(generator="ADDR_SEQ")
    @SequenceGenerator(name="ADDR_SEQ")
    private int id;

    @Basic
    private String city;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String country;
    
    @Basic
    private String province;
    
    @Basic
    @Column(name = "P_CODE")
    private String postalCode;
    
    @Basic
    private String street;
    
    @Version
    private long version;

    public Address() {
    }

    public Address(String city, String country, String province, String postalCode, String street) {
        super();
        this.city = city;
        this.country = country;
        this.province = province;
        this.postalCode = postalCode;
        this.street = street;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int addressId) {
        this.id = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String pCode) {
        this.postalCode = pCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
