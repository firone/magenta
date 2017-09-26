package org.magenta.testing.domain.company;

import org.magenta.DataSet;
import org.magenta.Sequence;
import org.magenta.annotations.InjectDataSet;
import org.magenta.annotations.InjectSequence;
import org.magenta.random.FluentRandom;

import com.google.common.base.Supplier;

public class EmployeeGenerator3 implements Supplier<Employee> {

  @InjectSequence
  private Sequence<Occupation> occupations;

  @InjectSequence
  private Sequence<Address> address;

  @InjectDataSet
  private DataSet<PhoneNumber> phoneNumbers;

  @Override
  public Employee get() {
    Employee e = new Employee();
    e.setName(FluentRandom.strings().charabia(16));
    e.setOccupation(occupations.next());
    e.setAddress(address.next());
    e.getPhoneNumbers().addAll(phoneNumbers.set(2));
    return e;
  }

}
