package org.magenta.testing.domain;

import org.magenta.Sequence;
import org.magenta.annotation.InjectSequence;

import com.google.common.base.Supplier;

public class EmployeeGenerator2 implements Supplier<Employee> {

  @InjectSequence
  private Sequence<Occupation> occupations;

  @InjectSequence
  private Sequence<Address> address;

  @Override
  public Employee get() {
    Employee e = new Employee();
    e.setOccupation(occupations.get());
    e.setAddress(address.get());
    return e;
  }

}
