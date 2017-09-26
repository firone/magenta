package org.magenta.testing.domain.company;

import org.magenta.Sequence;
import org.magenta.annotations.InjectSequence;

import com.google.common.base.Supplier;

public class EmployeeGenerator implements Supplier<Employee> {

  @InjectSequence
  private Sequence<Occupation> occupations;

  @Override
  public Employee get() {
    Employee e = new Employee();
    e.setOccupation(occupations.next());
    return e;
  }

}
